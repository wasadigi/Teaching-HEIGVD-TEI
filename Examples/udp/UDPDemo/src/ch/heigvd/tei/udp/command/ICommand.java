package ch.heigvd.tei.udp.command;

import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.Request;

/**
 * This interface specifies a contract for implementing the business logic on top of the basic
 * communication protocol. In order to implement a specific feature, the developer should create implementations
 * of this interface. 
 * 
 * @author oliechti
 */
public interface ICommand {
	
	/**
	 * Implements the logic of a specific command
	 * @param request the request sent by the client (it contains a number of attributes)
	 * @return the reply to be sent to the client
	 */
	public Reply execute(Request request);
	
}
