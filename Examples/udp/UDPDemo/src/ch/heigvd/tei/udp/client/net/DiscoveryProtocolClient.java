package ch.heigvd.tei.udp.client.net;

import ch.heigvd.tei.udp.protocol.Protocol;
import ch.heigvd.tei.udp.server.ServerCoordinates;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a method for running the discovery protocol, in a synchronous fashion. When the method "discover()" is called,
 * a DISCOVERY message is sent to the multicast group. Any server that is listening on the group will send a response with his coordinates
 * (the coordinates are defined by the IP address and the port). After sending the DISCOVERY message, the client will block and wait for
 * the first answer - and will return the coordinates of this server.
 * 
 * @author oliechti
 */
public class DiscoveryProtocolClient {

	/**
	 * This method uses the discovery protocol to retrieve the coordinates (address + port) of a server on the
	 * local network
	 * 
	 * @return the coordinates of the first server having responded to the discovery protocol
	 */
	public ServerCoordinates discover() {
		try {
			
			/*
			 * This first thing we need to do is to send a discovery message on the multicast group
			 */
			
			InetAddress group = InetAddress.getByName(Protocol.DISCOVERY_MULTICAST_GROUP);
			DatagramPacket packet;
			String message = Protocol.DISCOVERY_HELLO_MSG;
			packet = new DatagramPacket(message.getBytes(), message.length(), group, Protocol.DISCOVERY_PORT);
			MulticastSocket socket = new MulticastSocket();
			System.out.println("Client sent discovery packet");
			System.out.println("-> " + message);
			socket.send(packet);
			
			/*
			 * We have sent the discovery message, now let's wait until the (first) server comes back with his
			 * coordinates.
			 */
			
			byte[] data = new byte[Protocol.BUFFER_SIZE];
			DatagramPacket disoveryResponse = new DatagramPacket(data, data.length);
			socket.receive(disoveryResponse);
			System.out.println("Client received discovery response packet of " + disoveryResponse.getLength() + " bytes");
			System.out.println("data: " + new String(data));
			byte[] payload = new byte[disoveryResponse.getLength()];
			System.arraycopy(data, 0, payload, 0, payload.length);
			String[] tokens = new String(payload).split(Character.toString(Protocol.SEPARATOR));
			String hostname = tokens[0];
			int port = Integer.parseInt(tokens[1]);
			return new ServerCoordinates(hostname, port);
		} catch (IOException ex) {
			Logger.getLogger(DiscoveryProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}


