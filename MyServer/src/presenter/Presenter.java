package presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import model.Model;
import view.View;


/**
 * 
 * @author Alon
 *This is the presenter as part of the MVP Architecture 
 */
public class Presenter implements Observer{
	/**
	 * 
	 * @author Alon
	 *Check Connection Status Class
	 */
	public class ConnectionStatus implements RemoteControlCommand {

		/**
		 * Checks connection of client
		 * @param params - the data about the client needed which IP,PORT
		 */
		@Override
		public void doCommand(String params) {
			m.getStatusClient(params); //get status about client
			
		}

	}
	/**
	 * 
	 * @author Alon
	 *Disconnect User Class
	 */
	public class DisconnectUser implements RemoteControlCommand{

		/**
		 * Disconnect user 
		 * @param params is the data about client IP,PORT
		 */
		@Override
		public void doCommand(String params) {
			m.DisconnectClient(params);
			
		}
		
	}
	/**
	 * 
	 * @author Alon
	 *A class for starting the server
	 */
	public class StartServer implements RemoteControlCommand{

		@Override
		/**
		 * Starts the server
		 */
		public void doCommand(String params) {
			m.StartServer();
			
		}
		
	}
	/**
	 * 
	 * @author Alon
	 *a class for stopping the server
	 */
	public class StopServer implements RemoteControlCommand{
		/**
		 * Stop the server
		 */
		@Override
		public void doCommand(String params) {
			m.DisconnectServer();
			
		}
		
	}
	/**
	 * 
	 * @author Alon
	 *a class for exiting properly
	 */
	public class ExitWindow implements RemoteControlCommand{
		
		/**
		 * exit properly
		 */
		@Override
		public void doCommand(String params) {
			m.exit();
		}
		
	}
	/**
	 * Model of the MVP
	 */
	Model m; //Model
	/**
	 * View of the MVP
	 */
	View v;//view
	/**
	 * Constructor
	 * @param m model
	 * @param v view
	 */
	public Presenter(Model m,View v)
	{
		this.m=m;
		this.v=v;
		ConcurrentHashMap<String, RemoteControlCommand> commandMap=new ConcurrentHashMap<String, RemoteControlCommand>(); //creating a safe hash map for threads which is a map for strings and commands
		commandMap.put("connection status", new ConnectionStatus());
		commandMap.put("disconnect user", new DisconnectUser());
		commandMap.put("start server",new StartServer());
		commandMap.put("stop server", new StopServer());
		commandMap.put("exit", new ExitWindow());
		//initalizing the commands for the strings and setting them in the view
			v.setCommands(commandMap);
		}
	/**
	 * The update function as part of the MVP
	 * @param arg -data we recieve from view
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o == v){
			String data = (String)(arg); //getting the data
			if(data!=null)
			v.getCommand().doCommand(data);
			else
				v.getCommand().doCommand(""); //doing command
			
		}
		if(o == m ){
			String data = m.takedata(); //here we take the data from a special fucntion in the model
			if(data.split(" ")[0].equals("msg")){ //understanding the meaning of the data
			v.Display(data.substring(4)); //msg to view
			}
			else
				if(data.split(" ")[0].equals("add"))
					v.addClient(data.substring(4)); //add client
				else
					if(data.split(" ")[0].equals("remove"))
						v.removeClient(data.substring(7)); //remove client
				else
			v.saveData(data); //save needed data
		}
		
		
	}
}

