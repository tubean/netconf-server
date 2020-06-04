package server.messageQueue;

import server.rpc.RPCElement;

public interface MessageQueueListener {

    void receiveRPCElement(RPCElement element);
}