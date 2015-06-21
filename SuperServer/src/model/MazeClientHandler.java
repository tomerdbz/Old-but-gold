package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeAirDistance;
import algorithms.demo.MazeManhattanDistance;
import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.RandomMazeGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.Movement;
import algorithms.search.Solution;
import algorithms.search.State;


/**	This class implements ClientHandler as a TCP/IP client handler
 * but - BONUS-  also implements Observer and extending Observable for REMOTE CONTROL COMMUNICATION!
 * the remote control could tell the handler to stop handle a client (when disconnecting) but the remote control also want to know statuses of clients and what the handler - and the server are doing at the moment.
 * 
 * @author Tomer
 *
 */
public class MazeClientHandler extends Observable implements ClientHandler,Observer  {

	/**	A server reference. I'd like to save mazes and solutions. those lies in data structures in the server. 
	 * 
	 */
	MazeServer server;
	/**	A HashMap linking between Client IP and Port - as a String and its Socket.
	 * that way if the remote control will notify to disconnect I could reach the socket through this HashMap.
	 * 
	 */
	volatile ConcurrentHashMap<String,Socket> activeConnections=new ConcurrentHashMap<String,Socket>();
	
	/**	A queue containing all the messages - all the things the handler is doing right now. Every time it changes it notifies the remote control.
	 * 
	 */
	volatile ConcurrentLinkedQueue<String> messages=new ConcurrentLinkedQueue<String>();
	/**	A reference of the UDP Server that send messages to the remote control is saved to notify him in the method update() I got by being an Observer.
	 * 
	 */
	RemoteControlUDPServer remote;
	public MazeClientHandler(MazeServer server) {
		this.server=server;
	}
	public MazeClientHandler(RemoteControlUDPServer remote) {
		this.remote=remote;
	}
	
	/** The handleClient Method! it notifies what it's doing to the client throughout it's operation.
	 * 	it gets the command from the client, additional arguments and parameters. does as requested and sends.
	 */
	@Override
	public void handleClient(Socket client)
	{
		String clientIP=client.getInetAddress().getHostAddress();
		int clientPort=client.getPort();
		activeConnections.put(clientIP+","+clientPort, client);
		String message=new String(clientIP +","+ clientPort+",connected");
		messages.add(message);
		setChanged();
		notifyObservers();//check messages
		messages.remove(message);
		try {
			BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(client.getInputStream()));
			String command=readerFromClient.readLine();
			ObjectOutputStream outputCompressedToClient=new ObjectOutputStream(new GZIPOutputStream(client.getOutputStream()));
			outputCompressedToClient.flush();
			if(command.contains("generate maze"))
			{
				String generator=readerFromClient.readLine();
				String arg=readerFromClient.readLine();
				String[] params=parseGenerateMazeArgument(arg);
				message=clientIP+ ","+clientPort+",generating maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(generateMaze(generator,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6])));
				outputCompressedToClient.flush();
				setChanged();
				notifyObservers();
				//outputToClient.writeObject(generateMaze(clientIP,clientPort,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6]),"generating maze"));
				messages.remove(message);
				//outputToClient.flush();
				

			}
			else if(command.contains("solve maze"))
			{
				String solverProperties=readerFromClient.readLine();
				String arg=readerFromClient.readLine();
				String[] params=parseSolveMazeArgument(arg);
				String[] properties=parseProperties(solverProperties);
				message=clientIP+ ","+clientPort+",solving maze";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(solveMaze(properties[0],properties[1],Double.parseDouble(properties[2]),Double.parseDouble(properties[3]),params[0]));
				outputCompressedToClient.flush();
			
				//outputToClient.writeObject(solveMaze(clientIP,clientPort,params[0],"solving maze"));
				messages.remove(message);
				//outputToClient.flush();
				


			}
			else if(command.contains("calculate hint"))
			{
				String solverProperties=readerFromClient.readLine();
				String arg=readerFromClient.readLine();
				String[] params=parseCalculateHintArgument(arg);
				String[] properties=parseProperties(solverProperties);
				message=clientIP+ ","+clientPort+",calculating hint";
				messages.add(message);
				setChanged();
				notifyObservers();
				outputCompressedToClient.writeObject(calculateHint(properties[0],properties[1],Double.parseDouble(properties[2]),Double.parseDouble(properties[3]),params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2])));
				outputCompressedToClient.flush();
				//outputToClient.writeObject(calculateHint(clientIP,clientPort,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),"calculating hint"));
				
				messages.remove(message);
				//outputToClient.flush();
				
			}
			else if(command.contains("maze exists"))
			{
				readerFromClient.readLine();//property doesn't matter
				String mazeName=readerFromClient.readLine();
				message=clientIP+ ","+clientPort+",checking maze existance";
				messages.add(message);
				setChanged();
				notifyObservers();
				if(server.generatedMazes.containsKey(mazeName))
					outputCompressedToClient.writeObject(server.generatedMazes.get(mazeName));
				else
					outputCompressedToClient.writeObject(null);
				outputCompressedToClient.flush();
				//outputToClient.writeObject(calculateHint(clientIP,clientPort,params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),"calculating hint"));
				messages.remove(message);
			}
			outputCompressedToClient.close();
			readerFromClient.close();

			client.close();
			
		} catch (Exception e1) {
			
		}
		
		activeConnections.remove(clientIP+","+clientPort);
		String last=new String(clientIP +","+ clientPort+",disconnected");
		messages.add(last);
		setChanged();
		notifyObservers();
		messages.remove(last);

	}
	
	public ConcurrentLinkedQueue<String> getMessages() {
		return messages;
	}
	/** parses the properties string
	 * @param properties - the string describing all the properties
	 * @return string array containing each property
	 */
	private String[] parseProperties(String properties)
	{		
		return properties.split(" ");
	}
	/**	parses the argument that came as String to all the parameters.
	 * @param arg - One String that contains all - maze name, row and col.
	 * @return String array containing them parsed.
	 */
	private String[] parseCalculateHintArgument(Object arg) {
		String[]values=((String)arg).split(" ");
		String[]result=new String[3];
		result[0]=values[0];
		result[1]=values[1].split(",")[0];
		result[2]=values[1].split(",")[1];
		return result;
	}

	/**	parses the argument that came as String to all the parameters.
	 * @param arg - One String that contains all - maze name
	 * @return String array containing them parsed.
	 */
	private String[] parseSolveMazeArgument(Object arg) {
		String[] result=new String[1];
		result[0]=(String)arg;
		return result;
	}

	/**	parses the argument that came as String to all the parameters.
	 * @param arg - One String that contains all - maze name, rows and cols,...
	 * @return String array containing them parsed.
	 */
	private String[] parseGenerateMazeArgument(Object arg) {
		String params=(String)arg;
		String[] values=params.split(" ");
		String[] additionalParams=values[1].split(",");
		String[] result=new String[1+additionalParams.length];
		result[0]=values[0];
		for(int i=1;i<additionalParams.length+1;i++)
			result[i]=additionalParams[i-1];
		return result;
	}


	/** This method generates a maze! of course, it checks if the maze name was not asked before - don't try to fool it.
	 * generating algorithm will be based on the one given at Ct'r by Property.
	 * @param name - the Maze name given.
	 * @param rows - number of rows in generated maze
	 * @param - cols - number of cols in generated maze
	 * @param - rowSource - Source row of generated maze
	 * @param - colSource - Source col of generated maze
	 * @param - rowGoal - Goal row of generated maze
	 * @param - colGoal - Goal col of generated maze
	 * @return - a SerializableMaze to send to client
	 */
	public SerializableMaze generateMaze(String generator,String name,int rows, int cols, int rowSource, int colSource,
			int rowGoal, int colGoal) {
		/* for video*/
		/*  int i=0;
		while(i!=-30)
		{
			i=i;
		}*/
		if(server.generatedMazes.containsKey(name))
			return new SerializableMaze(server.generatedMazes.get(name));
		Maze maze=null;
		switch(generator) 
		{
			case "DFS":
				maze=new DFSMazeGenerator().generateMaze(rows, cols, rowSource, colSource, rowGoal, colGoal);
				break;
			case "RANDOM":
				maze=new RandomMazeGenerator().generateMaze(rows, cols, rowSource, colSource, rowGoal, colGoal);;
				break;
			default:
				break;
		}
		server.generatedMazes.put(name, maze);
		
		return new SerializableMaze(maze);
				
	}
	
	
	/** solves a generated maze. upon solving - saves in cache.
	 * @param - mazeName - maze name to solve.
	 * @return - a SerializableSolution to send to the client.
	 */
	public SerializableSolution solveMaze(String solver,String move,double moveCost,double moveDiagonalCost,String mazeName) {
		
		Maze m=server.generatedMazes.get(mazeName);
		if(m==null)
		{
			return null;
		}
		if(server.cache.containsKey(m))
		{
			return new SerializableSolution(server.cache.get(m));
		}
		Solution solution=null;
		Movement movement;
		if(move.equals("DIAGONAL"))
			movement=Movement.DIAGONAL;
		else
			movement=Movement.NONDIAGONAL;
		switch(solver)
		{
		case "BFS":
			solution=new BFS().search(new MazeSearch(m,movement,moveCost,moveDiagonalCost));
			break;
		case "AIR_DISTANCE_ASTAR":
			solution=new AStar(new MazeAirDistance(m.getRowGoal(),m.getColGoal(),moveCost)).search(new MazeSearch(m,movement,moveCost,moveDiagonalCost));
			break;
		case "MANHATTAN_DISTANCE_ASTAR":
			solution=new AStar(new MazeManhattanDistance(m.getRowGoal(),m.getColGoal(),moveCost)).search(new MazeSearch(m,movement,moveCost,moveDiagonalCost));;
			break;
			
		default:
			break;
		}
		server.cache.put(m, solution);
		return new SerializableSolution(solution);
		
	}
	/**	calculates a hint based on an existing solution.
	 * 	if a solution does not exist - it first solves the maze and only then calculating the hint.
	 * @param solver - how the maze was solved - which algorithm
	 * @param move - which movement was allowed - Diagonal or nonDiagonal?
	 * @param moveCost - movement cost
	 * @param moveDiagonalCost - diagonal movement cost
	 * @param mazeName - name of maze
	 * @param row - from which row to calculate the hint
	 * @param col - from which col to calculate the hint
	 * @return a SerializableState - which is the hint - that will be sent to the client.
	 */
	public SerializableState calculateHint(String solver,String move,double moveCost,double moveDiagonalCost,String mazeName, int row, int col)
	{
		Maze m;
		if((m=server.generatedMazes.get(mazeName))==null)
		{
			return null;
		}
		if(server.cache.get(m)==null)
			solveMaze(solver,move,moveCost,moveDiagonalCost,mazeName);	
		return new SerializableState(solutionToHint(mazeName,server.cache.get(m),row,col));
	}
	/** The method that actually calculates the hint.
	 * @param mazeName
	 * @param sol - the maze solution
	 * @param row
	 * @param col
	 * @return a State which is the hint.
	 */
	private State solutionToHint(String mazeName, Solution sol,int row,int col)
	{
		Maze m=server.generatedMazes.get(mazeName);
		int min=m.getRows()+m.getCols();//mazeDisplay.Ch.currentCellX) + Math.abs(mazeDisplay.Ch.currentCellY));
		String []indexes;
		int x=0;
		int y=0; //puts hints on the maze
		int solSize=sol.getPath().size();
		State lastState = null;
		ArrayList<State> path=sol.getPath();
		for(int i=0;i<solSize-2;i++){
			indexes = path.get(i).getState().toString().split(",");
			int xt=Integer.parseInt(indexes[0]); //t stands for temp
			int yt=Integer.parseInt(indexes[1]);
			double temp=Math.sqrt(Math.pow(row-xt,2) + Math.pow(col-yt,2)); //caclulates minimal differnce between a hint and the character
			if(temp!=0 &&  min>=temp && path.indexOf(path.get(i))>path.indexOf(lastState) ){
				min= (int)temp;
				x=xt;
				y=yt;
				lastState=path.get(i);
			}
		}
		lastState=path.get(path.indexOf(lastState)+3);
		indexes = lastState.getState().toString().split(",");
		x=Integer.parseInt(indexes[0]); //t stands for temp
		y=Integer.parseInt(indexes[1]);
		return new State(""+x+","+y);
	}


	public MazeServer getServer() {
		return server;
	}


	public void setServer(MazeServer server) {
		this.server = server;
	}
	/** upon a disconnection request client will be sent a nice message and the server will await for his ack. then it will disconnect it.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o==remote)
			if(arg.toString().contains("disconnect"))
			{
				Socket clientToDisconnect=activeConnections.get(arg.toString().substring(0, arg.toString().length()-"disconnect".length()-1));
			/*	
				//BufferedReader readerFromClient=new BufferedReader(new InputStreamReader(clientToDisconnect.getInputStream()));
				ObjectOutputStream objOut=activeConnectionsOutputStream.get(arg.toString().substring(0, arg.toString().length()-"disconnect".length()-1));
				try {
					objOut.writeObject("disconnect");
					objOut.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
				//readerFromClient.readLine(); //Client answers and acks.
			*/
				try{
					clientToDisconnect.close();
				}catch(Exception e)
				{
					
				}

			}
	}
}
