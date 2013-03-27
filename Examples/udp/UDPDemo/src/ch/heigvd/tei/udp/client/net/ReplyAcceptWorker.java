package ch.heigvd.tei.udp.client.net;

import ch.heigvd.tei.udp.protocol.Protocol;
import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.ReplyListener;
import ch.heigvd.tei.udp.protocol.Request;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oliechti
 */
public class ReplyAcceptWorker implements Runnable {

	private DatagramSocket socket;
	private ReplyListener callbackListener;
	
	private String serverAddress;
	private int serverPort;

	public ReplyAcceptWorker(ReplyListener listener, String serverAddress, int serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		try {
			socket = new DatagramSocket();
		} catch (SocketException ex) {
			Logger.getLogger(ReplyAcceptWorker.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.callbackListener = listener;
	}

	public void submitRequest(Request request) {
		try {
			byte[] data = request.marshal().getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length);
			packet.setAddress(InetAddress.getByName("localhost"));
			packet.setPort(Protocol.REQUEST_ACCEPT_PORT);
			socket.send(packet);
		} catch (IOException ex) {
			Logger.getLogger(ReplyAcceptWorker.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				byte[] buffer = new byte[Protocol.BUFFER_SIZE];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				System.out.println("Received reply: " + packet.getLength());
				Reply reply = new Reply();
				reply.unmarshal(new String(buffer, 0, packet.getLength()));
				callbackListener.onReplyAvailable(reply);
			} catch (IOException ex) {
				Logger.getLogger(ReplyAcceptWorker.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
