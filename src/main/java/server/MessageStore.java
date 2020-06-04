package server;

import server.rpc.RPCElement;

import java.util.List;

public interface MessageStore {

	/**
	 * Store a Netconf message
	 * 
	 * @param message
	 *            any Netconf message to store
	 */
	public void storeMessage(RPCElement message);

	/**
	 * Get the full list of stored messages
	 * 
	 * @return a {@link List} of stored messages
	 */
	public List<RPCElement> getStoredMessages();
}