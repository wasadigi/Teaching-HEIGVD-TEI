package ch.heigvd.tei.udp;

import ch.heigvd.tei.udp.client.net.ReplyAcceptWorker;
import ch.heigvd.tei.udp.protocol.Reply;
import ch.heigvd.tei.udp.protocol.ReplyListener;
import ch.heigvd.tei.udp.protocol.Request;
import ch.heigvd.tei.udp.server.RequestAcceptWorker;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author oliechti
 */
public class UDPDemo {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		RequestAcceptWorker serverWorker = new RequestAcceptWorker();
		Thread t1 = new Thread(serverWorker);
		t1.start();
		
		ReplyAcceptWorker clientWorker = new ReplyAcceptWorker(new ReplyListener() {
			@Override
			public void onReplyAvailable(Reply reply) {
				System.out.println("Received a reply: " + reply);
			}
		});
		Thread t2 = new Thread(clientWorker);
		t2.start();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("p1", "v1");
		params.put("p2", "v2");
		Request r1 = new Request("doSomething", params);
		clientWorker.submitRequest(r1);
		
		
	}
}
