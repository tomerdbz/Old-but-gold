package view;

import java.util.concurrent.ConcurrentHashMap;

import presenter.RemoteControlCommand;
/**
 * 
 * @author Alon
 *	This class is an interface of the view as
 *part of the MVP Structure later to be implemented
 *by other classess
 */
public interface View {
	/**
	 * get Command from view
	 * @return the command
	 */
	public RemoteControlCommand getCommand();
	/**
	 * Set the hashMAP of commands in the view
	 * @param commandMap a hashmap of String to server commands
	 */
	public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commandMap);
	/**
	 * display data to view
	 * @param msg the data to be displayed
	 */
	public void Display(String msg);
	/**
	 * Save data to view so he can use it
	 * @param data -data to be saved
	 */
	public void saveData(String data);
	/**
	 * adding a client to the list or whatever we choose
	 * @param Client the client to be added
	 */
	public void addClient(String Client);
	/**
	 * remove client from a list or something we choose
	 * @param Client -the client to be removed
	 */
	public void removeClient(String Client);
}
