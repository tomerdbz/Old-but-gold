package presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import model.Model;
import view.cli.View;

/*
 * @author Tomer
 *
 */
public class Presenter implements Observer {
	public interface Command {
		/** Inspired from the Observer's update() method - doCommand gets the Maze name and special parameters. 
		 * NOTE: FOR ANY COMMAND OTHER THAN GENERATE MAZE PARAMS WILL NOT BE TAKEN INTO CONSIDERATION.
		 * @param name
		 * @param params
		 */
		void doCommand(String name,String params);
	}
	public class GenerateMazeCommand implements Command {

		/**	NOTE: PARAMETERS SHOULD BE GIVEN IN THE FOLLOWING ORDER: Maze Rows, Maze Cols, Row Source, Col Source, Row Goal, Col Goal!
		 * @param Name of Maze
		 * @param Parameters - given at the format of "x,y,z,w,....."
		 */
		@Override
		public void doCommand(String arg,String params) {
			String[] parameters=params.split(",");
			boolean flag=true;
			if(parameters.length>=6)
			{
				for(String s : parameters)
					flag&=isInteger(s);
				if(flag==true)
					m.generateMaze(arg, Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]), Integer.parseInt(parameters[5]),"maze generated display");
			}
			else
				v.Display("An Error Has Occured.");
		}
		/**
		 * @param s - String that will be checked if represents a number
		 * @return
		 */
		private boolean isInteger(String s)
		{
			if(s.isEmpty()) return false;
		    for(int i = 0; i < s.length(); i++) {
		        if(i == 0 && s.charAt(i) == '-') {
		            if(s.length() == 1) return false;
		            else continue;
		        }
		        if(Character.digit(s.charAt(i),10) < 0) return false;
		    }
		    return true;
		}
		

	}
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String arg,String params) {
			
			v.displayMaze(m.getMaze(arg));
		}

	}
	
	public class SolveMazeCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			
			m.solveMaze(arg,"maze solved display");
		}

	}
	public class DisplaySolutionCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			v.displaySolution(m.getSolution(arg));
		}

	}
	public class DisplayHintCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			v.displayHint(m.getHint(arg));
		}

	}
	public class CalculateHintCommand implements Command{

		@Override
		public void doCommand(String name, String params) {
			m.calculateHint(name, Integer.parseInt(params.split(",")[0]), Integer.parseInt(params.split(",")[1]),"hint calculated display");
		}
		
		
	}
	public class MazeExistsCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			v.receiveExistsMaze(m.getMaze(arg));
		}

	}
	public class WritePropertiesCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			m.setProperties(v.getProperties());
		}

	}
	
	public class ExitCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			m.stop();
		}

	}
	
	/** MVP View 
	 * 
	 */
	private View v;
	/**MVP Model
	 * 
	 */
	private Model m;
	
	public Presenter(Model m,View v)
	{
		this.m=m;
		this.v=v;
		ConcurrentHashMap<String, Command> commandMap=new ConcurrentHashMap<String, Command>();
		commandMap.put("generate maze",new GenerateMazeCommand());
		commandMap.put("display maze",new DisplayMazeCommand());
		commandMap.put("solve maze",new SolveMazeCommand());
		commandMap.put("display solution",new DisplaySolutionCommand());
		commandMap.put("calculate hint",new CalculateHintCommand());
		commandMap.put("exit",new ExitCommand());
		commandMap.put("properties", new WritePropertiesCommand());
		commandMap.put("maze exists", new MazeExistsCommand());
		v.setCommands(commandMap);
		}
	
	/** FOR MODEL arg1 IS "maze generated" or "maze solved" or "calculated hint". FOR VIEW arg1 is COMMAND ARGUMENTS.
	 * 	NOTICE: IF ARG1 = "error" Presenter evaluates and knows there was something wrong. model and view doesn't think for themselves. 
	 */
	@Override
	public void update(Observable o, Object arg1) {
		if(o == v)
		{
			String name=null;
			String Params=null;
			if(arg1!=null && !arg1.toString().equals("error")) //by making sure the arg1 is not a reprentation of error we avoid errors
			{
				String data =(String)arg1; //we convert the data which is the arguments transfered to us to a String
				data=data.substring(1); //we remove the first char which is a space
				name = data.split(" ")[0]; //we split the array by spaces the first place is not the name
				Params = "";
				for(int i=1; i<data.split(" ").length;i++)
				{
					Params = Params +  data.split(" ")[i]; //we combine all the other arguments
				} 
			}	
			if(v.getUserCommand()!=null) //we make sure that the command is valid and not null
			v.getUserCommand().doCommand(name,Params); //we tell the presenter to handle the command now
			else
				v.Display("An Error Has Occurred."); //error managment
		}
		else if (o==m)
		{
			if(arg1.toString().equals("disconnect"))
				v.Display("Server could not handle the request");
			String name;
			if(arg1!=null && !arg1.toString().equals("error"))
			{
				if((name=modelNotifiedGeneratedWithDisplay(arg1.toString()))!=null)
						new DisplayMazeCommand().doCommand(name,null);//v.displayMaze(m.getMaze(name));
				else if((name=modelNotifiedSolvedWithDisplay(arg1.toString()))!=null)
					new DisplaySolutionCommand().doCommand(name, null);//v.displaySolution(m.getSolution(name));
				else if((name=modelNotifiedCalculateHint(arg1.toString()))!=null)
				{
					String [] values=name.split(" ");
					new CalculateHintCommand().doCommand(values[1], values[0]);
				}
				else if((name=modelNotifiedDisplayHint(arg1.toString()))!=null)
					new DisplayHintCommand().doCommand(name, null);
			}
			else
				if(arg1!=null)
					v.Display("Error: " + arg1.toString());

		}
	}
	/** Helper for update - checks if Model notified solve
	 * @param arg from the model
	 * @return name of maze to solve if it notified solve, else null
	 */
	private String modelNotifiedSolvedWithDisplay(String arg)
	{
		if(arg.startsWith("maze solved display") && !arg.contains("hint"))
			return arg.split(" ")[3];
		/*String[] args=arg.split(" ");
		if(args.length==4)
			if(args[2].equals("solved") && args[3].equals("display"))
				return args[1];*/
		return null;
	}
	/** Helper for update - checks if Model notified generated
	 * @param arg from the model
	 * @return name of maze to generate if it notified generated, else null
	 */
	private String modelNotifiedGeneratedWithDisplay(String arg)
	{
		if(arg.startsWith("maze generated display"))
			return arg.split(" ")[3];
		/*String[] args=arg.split(" ");
		if(args.length==3)
			if(args[2].equals("generated"))
				return args[1];*/
		return null;
	}
	private String modelNotifiedCalculateHint(String arg)
	{
		if(arg.startsWith("hint") && !arg.contains("display"))
			return arg.substring(5);//get rid of "hint "
		/*String[] args=arg.split(" ");
		if(args.length==3)
			if(args[2].equals("generated"))
				return args[1];*/
		return null;
	}
	private String modelNotifiedDisplayHint(String arg)
	{
		if(arg.startsWith("hint calculated display"))
			return arg.split(" ")[3];
		return null;
	}
	
	
}
