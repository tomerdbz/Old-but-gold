package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import presenter.ClientProperties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

/** This Model is connecting to a Server which is doing all of MyModel jobs.
 * 	Instead of calculating and working hard, it's querying a server.
 * @author Tomer
 *
 */
public class ClientModel extends Observable implements Model {
	/**	ClientProperties - see JavaDoc of this class
	 * 
	 */
	private ClientProperties properties;
	/**	upon generating a maze I'd like to save it for the presenter to come and take it. 
	 * 
	 */
	private Maze maze;
	
	/**
	 *	upon solving a maze I'd like to save it for the presenter to come and take it. 
	 */
	private Solution solution;
	/**
	 * 	upon calculating a hint I'd like to save it for the presenter to come and take it.
	 */
	private State hint;
	
	public ClientModel(ClientProperties properties) {
		this.properties=properties;
	}
	/**	this method generates a maze - but it doesn't do that actually.
	 * it finds out what maze generator should the server use and then tells it to generate a maze based on that and user input the presenter gave ClientModel. 
	 */
	@Override
	public void generateMaze(String name, int rows, int cols, int rowSource,
			int colSource, int rowGoal, int colGoal, String notifyArgument) {
		String property=null;
		switch(properties.getMazeGenerator())
		{
			case DFS:
				property="DFS";
				break;
			case RANDOM:
				property="RANDOM";
				break;
			default:
				return;
		}
		SerializableMaze serializableMaze=(SerializableMaze)queryServer(properties.getServerIP(),properties.getServerPort(),"generate maze",name+" "+rows+","+cols+","+rowSource+","+colSource+","+rowGoal+","+colGoal,property);
		if(serializableMaze==null)
		{
			setChanged();
			notifyObservers("disconnect");
			return;
		}
		maze=serializableMaze.getOriginalMaze();
		setChanged();
		notifyObservers(notifyArgument+" "+name);
	}

	@Override
	public Maze getMaze(String mazeName) {
		Object value=queryServer(properties.getServerIP(), properties.getServerPort(), "exists maze", mazeName, null);
		if(maze==null && value==null)
		{
			return null;
		}
		else if(value!=null)
		{
			return ((SerializableMaze)value).getOriginalMaze();
		}
		Maze retMaze=maze;
		maze=null;
		return retMaze;
	}

	/** This method solves a maze - but it doesn't do that actually.
	 * 	it checks how should the solution should be calculated - which algorithm, which movement is allowed and movements cost.
	 * then it tells the Server all this info and lets him solve the maze.
	 */
	@Override
	public void solveMaze(String mazeName, String notifyArgument) {
		String property=null;
		switch(properties.getMazeSolver())
		{
			case BFS:
				property="BFS";
				break;
			case MANHATTAN_DISTANCE_ASTAR:
				property="MANHATTAN_DISTANCE_ASTAR";
				break;
			case AIR_DISTANCE_ASTAR:
				property="AIR_DISTANCE_ASTAR";
				break;
			default:
				return;
		}
		switch(properties.getMovement())
		{
		case DIAGONAL:
			property+=" DIAGONAL";
			break;
		case NONDIAGONAL:
			property+=" NONDIAGONAL";
		}
		property+=" "+properties.getMovementCost()+" "+properties.getDiagonalMovementCost();
		SerializableSolution serializableSolution=(SerializableSolution)queryServer(properties.getServerIP(),properties.getServerPort(),"solve maze",mazeName,property);
		if(serializableSolution==null)
		{
			setChanged();
			notifyObservers("disconnect");
			return;
		}
		solution=serializableSolution.getOriginalSolution();
		System.out.println(solution);
		setChanged();
		notifyObservers(notifyArgument+" "+mazeName);
	}

	@Override
	public Solution getSolution(String mazeName) {
		if(solution==null)
		{
			setChanged();
			notifyObservers("error");
		}
		Solution retSol=solution;
		solution=null;
		return retSol;
	}

	@Override
	public State getHint(String mazeName) {
		if(hint==null)
		{
			setChanged();
			notifyObservers("error");
		}
		State retHint=hint;
		hint=null;
		return retHint;
	}

	/** This method calculates a hint - but it doesn't do that actually.
	 * 	it checks how the solution was calculated and passes this information to the Server to calculate it.
	 */
	@Override
	public void calculateHint(String mazeName, int row, int col,
			String notifyArgument) {
		String property=null;
		switch(properties.getMazeSolver())
		{
			case BFS:
				property="BFS";
				break;
			case MANHATTAN_DISTANCE_ASTAR:
				property="MANHATTAN_DISTANCE_ASTAR";
				break;
			case AIR_DISTANCE_ASTAR:
				property="AIR_DISTANCE_ASTAR";
				break;
			default:
				return;
		}
		switch(properties.getMovement())
		{
		case DIAGONAL:
			property+=" DIAGONAL";
			break;
		case NONDIAGONAL:
			property+=" NONDIAGONAL";
		}
		property+=" "+properties.getMovementCost()+" "+properties.getDiagonalMovementCost();
		SerializableState serializableHint=(SerializableState)queryServer(properties.getServerIP(),properties.getServerPort(),"calculate hint",mazeName+" "+row+","+col,property);
		if(serializableHint==null)
		{
			setChanged();
			notifyObservers("disconnect");
			return;
		}
		hint=serializableHint.getOriginalState();
		setChanged();
		notifyObservers(notifyArgument+ " "+mazeName);
		//what to notify check in MyModel//notifyObservers(notifyArgument);
	}

	@Override
	public void stop() {
	
		
	}
	
	
	/** Ahh - the cherry pie!
	 * This method does it all - the excitement, the thrill, the tears...
	 * it queries the Server and handles the Client-Server session.
	 * @param serverIP - IP for server socket
	 * @param serverPort - port for server socket
	 * @param command - like "generate maze", "solve maze",...
	 * @param data - arguments like maze name, number of rows,...
	 * @param property - a String describes how the query should be done.
	 * @return Server's response to the query - maze, solution, hint,...
	 */
	private Object queryServer(String serverIP,int serverPort,String command,String data,String property)
	{
		Object result=null;
			Socket server;			
			try {
				server = new Socket(serverIP,serverPort);
				PrintWriter writerToServer=new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
				writerToServer.println(command);
				writerToServer.flush();
				writerToServer.println(property);
				writerToServer.flush();
				writerToServer.println(data);
				writerToServer.flush();
				ObjectInputStream inputDecompressed;
				inputDecompressed = new ObjectInputStream(new GZIPInputStream(server.getInputStream()));
				result=inputDecompressed.readObject();
				if(result.toString().contains("disconnect"))
				{
					setChanged();
					notifyObservers("disconnect");
				}
				writerToServer.close();
				inputDecompressed.close();
				server.close();
			} catch (ClassNotFoundException e) {
			
			} catch (IOException e) {
			
			}
		
		
		return result;
		
	}
	@Override
	public void setProperties(ClientProperties properties) {
		this.properties=properties;
	}

}
