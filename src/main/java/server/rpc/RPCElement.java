package server.rpc;

import org.apache.commons.configuration.CompositeConfiguration;

public class RPCElement implements java.io.Serializable {

    String messageId;
    // extra parameters from
    CompositeConfiguration ctx;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public CompositeConfiguration getCtx() {
        return ctx;
    }

    public void setCtx(CompositeConfiguration ctx) {
        this.ctx = ctx;
    }

    public boolean existCtx() {
        return (ctx != null);
    }

    public String toXML() {
        return "NOT IMPLEMENTED";
    }
}