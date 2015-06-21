package presenter;
/**
 * 
 * @author Alon
 *An interface that will be later implemented by other classes
 *for the MVP Structure 
 */
public interface RemoteControlCommand {
	
	/**
	 * Doing a command
	 * @param params -data for command
	 */
	void doCommand(String params);

}

