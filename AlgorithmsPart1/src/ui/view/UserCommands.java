package ui.view;


/** This interface defines what every UserCommands class has to do - have the ability to select a command.
 * @author Tomer Cabouly
 *
 */
public interface UserCommands {
	Command selectCommand(String commandName);
}
