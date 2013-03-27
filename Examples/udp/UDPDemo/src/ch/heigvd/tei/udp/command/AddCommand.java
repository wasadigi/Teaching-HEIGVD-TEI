package ch.heigvd.tei.udp.command;

import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.Request;

/**
 * This command adds the "op1" and "op2" parameters.
 * 
 * @author oliechti
 */
public class AddCommand implements ICommand {

	@Override
	public Reply execute(Request request) {
		String op1 = request.getParameterValue("op1");
		String op2 = request.getParameterValue("op2");
		Double d1 = Double.parseDouble(op1);
		Double d2 = Double.parseDouble(op2);
		Reply reply = new Reply(request, "OK", Double.toString(d1+d2));
		return reply;
	}
		
}
