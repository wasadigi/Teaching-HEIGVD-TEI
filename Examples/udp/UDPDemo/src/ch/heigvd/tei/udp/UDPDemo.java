package ch.heigvd.tei.udp;

import ch.heigvd.tei.udp.client.net.DiscoveryProtocolClient;
import ch.heigvd.tei.udp.client.net.ReplyAcceptWorker;
import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.ReplyListener;
import ch.heigvd.tei.udp.protocol.Request;
import ch.heigvd.tei.udp.server.ServerCoordinates;
import ch.heigvd.tei.udp.server.DiscoveryProtocolWorker;
import ch.heigvd.tei.udp.server.RequestAcceptWorker;
import java.util.HashMap;
import java.util.Map;

/**
 * This class demonstrates the entire protocol, by starting both a servera and a
 * client.
 *
 * @author oliechti
 */
public class UDPDemo {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		/*
		 * Start the server worker who will process PROTOCOL REQUESTS
		 */
		RequestAcceptWorker serverWorker = new RequestAcceptWorker();
		Thread t1 = new Thread(serverWorker);
		t1.start();

		/*
		 * Start the server worker who will process DISCOVERY REQUESTS
		 */
		DiscoveryProtocolWorker discoveryWorker = new DiscoveryProtocolWorker();
		Thread t3 = new Thread(discoveryWorker);
		t3.start();

		/*
		 * Run the discovery protocol (synchronously - we will wait for one server
		 * to respond)
		 */
		DiscoveryProtocolClient client = new DiscoveryProtocolClient();
		ServerCoordinates serverCoordinates = client.discover();

		/*
		 * Start the client worker who will process PROTOCOL REPLIES sent by the
		 * server
		 */
		ReplyAcceptWorker clientWorker = new ReplyAcceptWorker(new ReplyListener() {

			@Override
			public void onReplyAvailable(Reply reply) {
				System.out.println("Received a reply: " + reply);
			}
		}, serverCoordinates.getAddress(), serverCoordinates.getPort());
		Thread t2 = new Thread(clientWorker);
		t2.start();

		/*
		 * Submit two requests to the server
		 */
		Map<String, String> params = new HashMap<String, String>();
		params.put("message", "Hello World");
		Request r1 = new Request("toUpperCase", params);
		clientWorker.submitRequest(r1);

		Request r2 = new Request("toLowerCase", params);
		clientWorker.submitRequest(r2);

		Map<String, String> params2= new HashMap<String, String>();
		params2.put("op1", Double.toString(30));
		params2.put("op2", Double.toString(12));
		Request r3 = new Request("add", params2);
		clientWorker.submitRequest(r3);
		
	}
}
