package ui.view;

import java.util.HashMap;

/** This class has what's common to every User Commands wannabe class - a HashMap between String and Commands.
 * @author Tomer Cabouly
 *
 */
public abstract class CommonUserCommands implements UserCommands{
	/**A HashMap between String and Command as part of the Command Pattern each extender will have to do.
	 * 
	 */
	protected HashMap<String, Command> commands;
	public HashMap<String, Command> getCommands() {
		return commands;
	}
	@Override
	public abstract Command selectCommand(String commandName);
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	public CommonUserCommands(HashMap<String, Command> commands)
	{
		this.commands=commands;
	}
	public CommonUserCommands()
	{
		commands =new HashMap<String,Command>();
	}

}
