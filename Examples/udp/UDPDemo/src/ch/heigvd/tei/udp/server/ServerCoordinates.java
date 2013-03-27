package ch.heigvd.tei.udp.server;

/**
 * This class encapsulates the coordinates of a server, which are defined by its IP address and port number.
 * 
 * @author oliechti
 */
public class ServerCoordinates {

	private String address;
	
	private int port;

	public ServerCoordinates(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
