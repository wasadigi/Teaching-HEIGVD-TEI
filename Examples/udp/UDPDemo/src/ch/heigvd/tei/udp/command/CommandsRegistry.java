package ch.heigvd.tei.udp.command;

import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps track of all available commands implemented on top of the basic protocol. When the
 * client sends a request, the server lookups the command and runs the execute method on this object.
 *
 * @author oliechti
 */
public class CommandsRegistry {

	private Map<String, ICommand> commands = new HashMap<String, ICommand>();
	
	/**
	 * Constructor
	 */
	public CommandsRegistry() {
		init();
	}
	
	/**
	 * Initializes the commands registry, by creating one prototype for every command
	 */
	private void init() {
		commands.put("toUpperCase".toUpperCase(), new ToUpperCaseCommand());
		commands.put("toLowerCase".toUpperCase(), new ToLowerCaseCommand());
		commands.put("add".toUpperCase(), new AddCommand());
	}
	
	
	/**
	 * Perform a lookup to retrieve a command object, based on the command string
	 * @param command
	 * @return 
	 */
	public ICommand lookup(String command) {
		return commands.get(command.toUpperCase());
	}
}
