package ch.heigvd.tei.udp.server;

import ch.heigvd.tei.udp.protocol.Protocol;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the discovery protocol on the server side. The server joins a multicast group and waits for
 * "HELLO" discovery messages. Whenever he receives a message, he sends back a response with his own coordinates (address and port).
 * 
 * @author oliechti
 */
public class DiscoveryProtocolWorker implements Runnable {

	private MulticastSocket socket;

	private boolean shouldRun = true;
	
	@Override
	public void run() {
		try {
			socket = new MulticastSocket(Protocol.DISCOVERY_PORT);
			InetAddress group = InetAddress.getByName(Protocol.DISCOVERY_MULTICAST_GROUP);
			socket.joinGroup(group);
			System.out.println("Server joined the multicast group " + group);

			while (shouldRun) {
				byte[] buf = new byte[Protocol.BUFFER_SIZE];
				DatagramPacket discoveryRequest = new DatagramPacket(buf, buf.length);
				socket.receive(discoveryRequest);
				System.out.println("Server received a DISCOVERY request.");
				System.out.println("-> " + new String(buf));
				String coordinates = InetAddress.getLocalHost().getHostAddress() + Protocol.SEPARATOR + Protocol.REQUEST_ACCEPT_PORT;
				DatagramPacket discoveryReply = new DatagramPacket(coordinates.getBytes(), coordinates.length());
				discoveryReply.setAddress(discoveryRequest.getAddress());
				discoveryReply.setPort(discoveryRequest.getPort());
				socket.send(discoveryReply);
				System.out.println("Server sent a DISCOVERY reply of " + discoveryReply.getLength() + " bytes on port " + discoveryRequest.getPort());
				System.out.println("-> " + coordinates);
			}

			socket.leaveGroup(group);
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(DiscoveryProtocolWorker.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
