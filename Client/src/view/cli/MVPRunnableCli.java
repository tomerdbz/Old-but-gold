package view.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import ui.view.CLI;

/**
 * @author Alon
 * This is a CLI class which extends the original
 * CLI recieved in the UI.JAR the ORIGINAL CLI class
 * is an Observable therfore this trait is inherited 
 * this class also implements Runnable in order to later be runned
 * as a thread 
 * This CLI will be observed by THE VIEW
 *
 */
public class MVPRunnableCli extends CLI implements Runnable {
	
	
	
	protected ConcurrentHashMap<String, presenter.Presenter.Command>  commands; //A hash map containing the name of the commands as keys and commands as values
	public void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands2){
		this.commands=commands2;
	}
	public String args; //a String we use later to pass arguments to the view
	public MVPRunnableCli(BufferedReader in, PrintWriter out) {
		
		super(in, out, null);
		
	}
	/**
	 * This fucntion  an Override of the original
	 * start function of the CLI
	 * it is the user interface of the programm
	 * and designed to work with the MVP architecture
	 * every time a function recieves input  the commands and arguments recieved
	 * are delivered to the View
	 * run untill the command exit is inserted
	 */
	@Override
	public void start ()
	{
		out.print("Enter command: ");
		out.flush();
		try {
			String line = in.readLine(); //receiving from user
			
			while (!line.equals("exit")) // while not exit
			{
				String[] sp = line.split(" ");
				if(sp.length>2)
				{
					String commandName = sp[0]+" "+sp[1]; //calculating the command name
					String arg= "";
					for(int i =2;i<sp.length;i++){
						arg = arg+ " "+ sp[i]; //calculating the args
					}
					presenter.Presenter.Command c= this.commands.get(commandName);
					this.args=arg; //passing the arguments to the MVPCLIRunnable data member
					setChanged(); //tells the observer a change has occurred
					notifyObservers(c);	//passes the command 
				}
				else //if a bad input is recieved such as not a real command bad parmaters
				{
					setChanged();
					notifyObservers("error");
				}
				out.print("Enter command: ");
				out.flush();
				line = in.readLine();
			}
		
			
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}	
	}
	
	/**
	 * This function needs to be implemented
	 * in order for the MVPCLIRUNNABLE to run as a thread
	 * it starts the USER Interface
	 */
	@Override
	public void run() {

		this.start();
		setChanged();
		notifyObservers(this.commands.get("exit")); //in the end of the thread we exit
	}


}