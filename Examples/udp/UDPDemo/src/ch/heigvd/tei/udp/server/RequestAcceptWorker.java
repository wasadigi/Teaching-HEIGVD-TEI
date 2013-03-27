package ch.heigvd.tei.udp.server;

import ch.heigvd.tei.udp.command.CommandsRegistry;
import ch.heigvd.tei.udp.command.ICommand;
import ch.heigvd.tei.udp.protocol.Protocol;
import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.Request;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the main loop for the "request-reply" phase of the application protocol. The
 * server accepts datagram on a UDP port. For every request, it generates a reply and sends it back to
 * the originating client.
 * 
 * @author oliechti
 */
public class RequestAcceptWorker implements Runnable {
	
	CommandsRegistry commandsRegistry = new CommandsRegistry();

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

				ICommand command = commandsRegistry.lookup(request.getCommand());
				Reply reply = command.execute(request);
				
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
