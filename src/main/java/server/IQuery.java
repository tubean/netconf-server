package server;

import server.rpc.Operation;
import server.rpc.RPCElement;

public interface IQuery {
    String toXML();

    String getMessageId();

    void setMessageId(String messageId);

    Operation getOperation();

    RPCElement getRpcElement();
}