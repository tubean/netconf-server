package server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.SubsystemFactory;
import server.exceptions.ServerException;
import server.netconf.NetconfSubsystem;
import server.rpc.RPCElement;
import server.ssh.AlwaysTruePasswordAuthenticator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server implements MessageStore, BehaviourContainer {

    private static final Log log = LogFactory.getLog(Server.class);
    private SshServer sshd;

    // stored messages
    private boolean storeMessages = false;
    private List<RPCElement> messages;

    // behaviours
    private List<Behaviour> behaviours;

    // hide default constructor, forcing using factory method
    private Server() {
    }

    /**
     * Creates an standard server listening in loopback interface
     *
     * @param listeningPort where the server will listen for SSH connections
     */
    public static Server createServer(int listeningPort) {
        Server server = new Server();
        server.storeMessages = false;

        server.initializeServer("localhost", listeningPort);

        return server;
    }

    /**
     * Creates an standard server
     *
     * @param host          host name (use null to listen in all interfaces)
     * @param listeningPort TPC port where the server will listen for SSH connections
     */
    public static Server createServer(String host, int listeningPort) {
        Server server = new Server();
        server.storeMessages = false;

        server.initializeServer(host, listeningPort);

        return server;
    }

    /**
     * Creates a server listening in loopback interface and store all received messages
     *
     * @param listeningPort where the server will listen for SSH connections
     */
    public static Server createServerStoringMessages(int listeningPort) {
        Server server = new Server();
        server.messages = new ArrayList<>();
        server.storeMessages = true;

        server.initializeServer("localhost", listeningPort);

        return server;
    }

    /**
     * Creates a server and store all received messages
     *
     * @param host          host name (use null to listen in all interfaces)
     * @param listeningPort where the server will listen for SSH connections
     */
    public static Server createServerStoringMessages(String host, int listeiningPort) {
        Server server = new Server();
        server.messages = new ArrayList<>();
        server.storeMessages = true;

        server.initializeServer(host, listeiningPort);

        return server;
    }

    private void initializeServer(String host, int listeningPort) {
        log.info("Configuring server...");
        sshd = SshServer.setUpDefaultServer();
        sshd.setHost(host);
        sshd.setPort(listeningPort);

        log.info("Host: '" + host + "', listenig port: " + listeningPort);

        sshd.setPasswordAuthenticator(new AlwaysTruePasswordAuthenticator());
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());

//        List<NamedFactory<Command>> subsystemFactories = new ArrayList<NamedFactory<Command>>();
//        subsystemFactories.add(NetconfSubsystem.Factory.createFactory(this, this));
//        sshd.setSubsystemFactories(subsystemFactories);

        List<SubsystemFactory> subsystemFactories = new ArrayList<>();
        subsystemFactories.add(NetconfSubsystem.Factory.createFactory(this, this));
        sshd.setSubsystemFactories(subsystemFactories);


        log.info("server.Server configured.");
    }

    public void defineBehaviour(Behaviour behaviour) {
        if (behaviours == null) {
            behaviours = new ArrayList<>();
        }
        synchronized (behaviours) {
            behaviours.add(behaviour);
        }
    }

    public List<Behaviour> getBehaviours() {
        if (behaviours == null) {
            return null;
        }
        synchronized (behaviours) {
            return behaviours;
        }
    }

    public void startServer() throws ServerException {
        log.info("Starting server...");
        try {
            sshd.start();
        } catch (IOException e) {
            log.error("Error starting server!", e);
            throw new ServerException("Error starting server", e);
        }
        log.info("server.Server started.");
    }

    public void stopServer() throws IOException {
        log.info("Stopping server...");
        sshd.stop();
        log.info("server.Server stopped.");
    }

    public void storeMessage(RPCElement message) {
        if (messages != null) {
            synchronized (messages) {
                log.info("Storing message");
                messages.add(message);
            }
        }
    }

    public List<RPCElement> getStoredMessages() {
        if (storeMessages) {
            synchronized (messages) {
                return Collections.unmodifiableList(messages);
            }
        } else {
            throw new ServerException(new UnsupportedOperationException("server.Server is configured to not store messages!"));
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = Server.createServerStoringMessages(830);
        server.startServer();

        // read lines form input
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            if (buffer.readLine().equalsIgnoreCase("EXIT")) {
                break;
            }

            log.info("Messages received(" + server.getStoredMessages().size() + "):");
            for (RPCElement rpcElement : server.getStoredMessages()) {
                log.info("#####  BEGIN message #####\n" +
                        rpcElement.toXML() + '\n' +
                        "#####   END message  #####");
            }
        }

        log.info("Exiting");
        System.exit(0);
    }

}