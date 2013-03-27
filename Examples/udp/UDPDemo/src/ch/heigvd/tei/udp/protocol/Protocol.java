package ch.heigvd.tei.udp.protocol;

/**
 *
 * @author oliechti
 */
public class Protocol {

	public static final char SEPARATOR = '#';
	public static int BUFFER_SIZE = 512;
	public static int DISCOVERY_PORT = 2446;
	public static String DISCOVERY_MULTICAST_GROUP = "239.255.14.46";
	public static int REQUEST_ACCEPT_PORT = 1446;
	public static String DISCOVERY_HELLO_MSG = "HELLO";
}
