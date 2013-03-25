package ch.heigvd.tei.udp.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author oliechti
 */
public class Request {
	
	private static long counter = 1000;
	
	private String command;
	
	private Map<String, String> parameters = new HashMap();
	
	private long requestId;

	
	public Request() {
		
		requestId = counter++; //UUID.randomUUID().getLeastSignificantBits();
	}
	
	public Request(String command, Map<String, String> parameters) {
		this();
		this.command = command;
		this.parameters = parameters;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getParameterValue(String paramName) {
		String paramValue = parameters.get(paramName);
		return paramValue;
	}
	
	public String marshal() {
		StringBuilder sb = new StringBuilder();
		sb.append(command);
		sb.append(Protocol.SEPARATOR);
		for (String parameter : parameters.keySet()) {
			sb.append(parameter);
			sb.append("=");
			sb.append(getParameterValue(parameter));
			sb.append(Protocol.SEPARATOR);
		}
		sb.append(requestId);
		return sb.toString();
	}
	
  public void unmarshal(String data) {
		String[] tokens = data.split(Character.toString(Protocol.SEPARATOR));
		this.command = tokens[0];
		String rid = tokens[tokens.length-1];
		analyze(rid);
		this.requestId = Integer.parseInt(rid);
		parameters.clear();
		for (int i=1; i<tokens.length-1; i++) {
			String[] pTokens = tokens[i].split("=");
			parameters.put(pTokens[0], pTokens[1]);
		}
	}

	private void analyze(String rid) {
		System.out.println("length: " + rid.length());
		for (int i=0; i<rid.length(); i++) {
			System.out.println(">" + rid.charAt(i) + " " + (int)rid.charAt(i));
		}
	}

	@Override
	public String toString() {
		return "Request{" + "command=" + command + ", parameters=" + parameters + ", requestId=" + requestId + '}';
	}

	
}
