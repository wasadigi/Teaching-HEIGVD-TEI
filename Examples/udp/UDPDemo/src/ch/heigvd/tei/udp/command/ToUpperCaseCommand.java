package ch.heigvd.tei.udp.command;

import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.Request;

/**
 * This command transforms the "message" parameter into upper case.
 *
 * @author oliechti
 */
public class ToUpperCaseCommand implements ICommand {

	@Override
	public Reply execute(Request request) {
		String message = request.getParameterValue("message");
		Reply reply = new Reply(request, "OK", message.toLowerCase());
		return reply;
	}
		
}
