package server.messageQueue;

import server.rpc.RPCElement;

public interface MessageQueueListener {

	public void receiveRPCElement(RPCElement element);
}