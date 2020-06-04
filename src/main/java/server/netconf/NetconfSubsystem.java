package server.netconf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.session.ServerSession;
import server.BehaviourContainer;
import server.MessageStore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NetconfSubsystem implements Command, SessionAware {

    private static final Log log = LogFactory.getLog(NetconfSubsystem.class);

    // subsystem fields
    private ExitCallback callback;
    private InputStream in;
    private OutputStream out;
    private OutputStream err;
    private Environment env;

    private ServerSession session;

    private MessageStore messageStore;
    private BehaviourContainer behaviourContainer = null;

    private NetconfProcessor netconfProcessor;
    private Thread clientThread;

    public NetconfSubsystem(MessageStore messageStore, BehaviourContainer behaviourContainer) {
        this.messageStore = messageStore;
        this.behaviourContainer = behaviourContainer;
    }

    public InputStream getInputStream() {
        return in;
    }

    public void setInputStream(InputStream in) {
        this.in = in;
    }

    public OutputStream getOutputStream() {
        return out;
    }

    public void setOutputStream(OutputStream out) {
        this.out = out;
    }

    public OutputStream getErrorStream() {
        return err;
    }

    public void setErrorStream(OutputStream err) {
        this.err = err;
    }

    public void setExitCallback(ExitCallback callback) {
        this.callback = callback;
    }

    public void start(Environment env) throws IOException {
        this.env = env;

        // initialize Netconf processor
        netconfProcessor = new NetconfProcessor(in, out, err, callback);
        netconfProcessor.setMessageStore(messageStore);
        netconfProcessor.setBehaviors(behaviourContainer);

        log.info("Starting new client thread...");
        (clientThread = new Thread(netconfProcessor, "Client thread")).start();
    }

    public void destroy() {
        netconfProcessor.waitAndInterruptThreads();
        try {
            clientThread.join(2000);
        } catch (InterruptedException e) {
            log.warn("Error joining Client thread" + e.getMessage());
        }
        clientThread.interrupt();
        log.info("Netconf Subsystem destroyed");
    }

    public void setSession(ServerSession session) {
        this.session = session;
    }

	public void start(ChannelSession channelSession, Environment environment) throws IOException {
		this.env = env;

		// initialize Netconf processor
		netconfProcessor = new NetconfProcessor(in, out, err, callback);
		netconfProcessor.setMessageStore(messageStore);
		netconfProcessor.setBehaviors(behaviourContainer);

		log.info("Starting new client thread...");
		(clientThread = new Thread(netconfProcessor, "Client thread")).start();
	}

	public void destroy(ChannelSession channelSession) throws Exception {
		netconfProcessor.waitAndInterruptThreads();
		try {
			clientThread.join(2000);
		} catch (InterruptedException e) {
			log.warn("Error joining Client thread" + e.getMessage());
		}
		clientThread.interrupt();
		log.info("Netconf Subsystem destroyed");
	}

	/**
     * Netconf Subsystem Factory
     *
     * @author Julio Carlos Barrera
     */
    public static class Factory implements NamedFactory<Command> {
        private static final Log log = LogFactory.getLog(Factory.class);
        private MessageStore messageStore = null;
        private BehaviourContainer behaviourContainer = null;

        private Factory(MessageStore messageStore, BehaviourContainer behaviourContainer) {
            this.messageStore = messageStore;
            this.behaviourContainer = behaviourContainer;
        }

        public static Factory createFactory(MessageStore messageStore, BehaviourContainer behaviourContainer) {
            return new Factory(messageStore, behaviourContainer);
        }

        public Command create() {
            log.info("Creating Netconf Subsystem Factory");
            return new NetconfSubsystem(messageStore, behaviourContainer);
        }

        public String getName() {
            return "netconf";
        }
    }

}