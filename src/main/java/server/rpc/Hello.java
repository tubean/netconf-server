package server.rpc;

import java.util.ArrayList;

public class Hello extends RPCElement {

    ArrayList<Capability> capabilities;
    String sessionId;

    public Hello() {
        messageId = "0";
    }

    public String toXML() {

        String xml = "<hello xmlns=\"" + Capability.BASE + "\">\n";

        xml += "\t<capabilities>\n";

        for (Capability capability : capabilities) {
            xml += "\t\t<capability>" + capability + "</capability>\n";
        }

        xml += "\t</capabilities>\n";

        if (sessionId != null)
            xml += "\t<session-id>" + sessionId + "</session-id>\n";

        xml += "</hello>";

        return xml;
    }

    public ArrayList<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(ArrayList<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}