package ch.heigvd.tei.udp.server;

import ch.heigvd.tei.udp.protocol.Protocol;
import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.Request;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oliechti
 */
public class RequestAcceptWorker implements Runnable {

	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket(Protocol.REQUEST_ACCEPT_PORT);

			while (true) {
				byte[] buffer = new byte[Protocol.BUFFER_SIZE];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				System.out.println("Request packet " + packet.getLength());
				byte[] payload = new byte[packet.getLength()];
				System.arraycopy(buffer, 0, payload, 0, packet.getLength());
				Request request = new Request();
				request.unmarshal(new String(payload));
				System.out.println("Request received " + request);

				Reply reply = new Reply("200", "your result", request.getRequestId());
				
				byte[] data = reply.marshal().getBytes();
				DatagramPacket replyPacket = new DatagramPacket(data, data.length);
				replyPacket.setAddress(packet.getAddress());
				replyPacket.setPort(packet.getPort());
				socket.send(replyPacket);
			}

		} catch (IOException ex) {
			Logger.getLogger(RequestAcceptWorker.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
