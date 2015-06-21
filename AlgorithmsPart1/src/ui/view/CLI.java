package ui.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;

/** A command line interface.
 * @author Tomer Cabouly
 *
 */
public class CLI extends Observable { 
	/** BufferedReader from which we will read input.
	 */
	protected BufferedReader in;
	/**PrintWriter to which we will print output.
	 * 
	 */
	protected PrintWriter out;
	/** User commands we will initiate as necessary.
	 * contains the interface UserCommands so CLI supports every User Commands class that implements it.
	 */
	private UserCommands userCommands;
	
	public CLI(BufferedReader in, PrintWriter out, UserCommands uc)
	{
		this.in = in;
		this.out = out;
		this.userCommands = uc;
	}
	
	/** Start the CLI. "exit" will end it.
	 * 
	 */
	public void start()
	{
		out.print("Enter command: ");
		out.flush();
		try {
			String line = in.readLine();
			
			while (!line.equals("exit"))
			{
				String[] sp = line.split(" ");
								
				String commandName = sp[0];
				String arg = null;
				if (sp.length > 1)
					arg = sp[1];
				// Invoke the command
				Command command = userCommands.selectCommand(commandName);
				if(command!=null && arg!=null)
					command.doCommand(arg);
				out.flush();
				
				out.print("Enter command: ");
				out.flush();
				line = in.readLine();
			}
			out.write("Goodbye");
						
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

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
}