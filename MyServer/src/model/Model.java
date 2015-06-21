package model;

/**
 * 
 * @author Alon
 *This is an interface of the model later to be implemented by other classes
 *it is part of the MVP Architecture
 *this model will be able to later communicate directly with the SERVER
 *activate him disconnect him and manage a list of connections  
 */
public interface Model {
	/**
	 * 
	 * @param client- represents a client in the server
	 * This function will return the status of this
	 * specific client on the server
	 */
	public void getStatusClient(String client);
	/**
	 * 
	 * @param client - represents a client in the server
	 * This function will alow us to disconnect the client 
	 * from the server
	 */
	public void DisconnectClient(String client);
	/**
	 * This functon will allow us to start the server in a remote way
	 */
	public void StartServer();
	/**
	 * This function will allow us to disconnect the server in a remote way
	 */
	public void DisconnectServer();
	/**
	 * this function will close the everyting properly
	 */
	public void exit();
	/**
	 * 
	 * @return this function returns a string that was created by some actions in the presenter later to be used by the view through the presneter
	 */
	public String takedata();
}
