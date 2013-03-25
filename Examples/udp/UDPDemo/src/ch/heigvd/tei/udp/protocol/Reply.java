package ch.heigvd.tei.udp.protocol;

/**
 *
 * @author oliechti
 */
public class Reply {

	private String statusCode;
	private String result;
	private long requestId;

	public Reply() {
	}

	public Reply(String statusCode, String result, long requestId) {
		this.statusCode = statusCode;
		this.result = result;
		this.requestId = requestId;
	}

	public String getResult() {
		return result;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public long getRequestId() {
		return requestId;
	}

	public String marshal() {
		StringBuilder sb = new StringBuilder();
		sb.append(statusCode);
		sb.append(Protocol.SEPARATOR);
		sb.append(result);
		sb.append(Protocol.SEPARATOR);
		sb.append(requestId);
		return sb.toString();
	}

	public void unmarshal(String data) {
		String[] tokens = data.split(Character.toString(Protocol.SEPARATOR));
		this.statusCode = tokens[0];
		this.result = tokens[1];
		String rid = tokens[tokens.length - 1];
		analyze(rid);
		this.requestId = Long.parseLong(tokens[tokens.length - 1]);
	}

	private void analyze(String rid) {
		System.out.println("length: " + rid.length());
		for (int i = 0; i < rid.length(); i++) {
			System.out.println(">" + rid.charAt(i) + " " + (int) rid.charAt(i));
		}
	}

	@Override
	public String toString() {
		return "Reply{" + "statusCode=" + statusCode + ", result=" + result + ", requestId=" + requestId + '}';
	}
	
	
	
}
