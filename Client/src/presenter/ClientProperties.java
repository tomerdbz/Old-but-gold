package presenter;

import java.io.Serializable;

import algorithms.search.Movement;

/** This class contains all the properties one should desire to choose.
 * This class implements Serializable since Properties are meant to be written and read and XML's are readable as well.
 * NOTICE: SINCE I'VE USED ENUMS AND DUE TO A BUG (READ ABOUT IT..) XML IS BEING WRITTEN WEIRD - BUT IT WORKS IN THE MODEL LIKE CHARM.
 * 	it will given to a good model who will give users the choice (you should try MyModel - it will give you that!)
 * @author Tomer
 *
 */
public class ClientProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**enum for readability - choose maze generator
	 * @author Tomer
	 *
	 */
	
	/**	Server to connect IP address
	 * 
	 */
	private String serverIP;
	/**Server to connect Port
	 * 
	 */
	private int serverPort;
	public enum MazeGenerator {
		DFS,RANDOM
	};
	public enum UI{
		CLI,GUI
	};
	/**enum for readability - choose maze solver
	 * @author Tomer
	 *
	 */
	public enum MazeSolver {
		MANHATTAN_DISTANCE_ASTAR,AIR_DISTANCE_ASTAR,BFS
	};
	/** Number of threads that will be executed simultaneously
	 * 
	 */
	private UI ui;
	//private int threadNumber;
	/**	Which maze solver
	 * 
	 */
	private MazeSolver mazeSolver;
	/**which maze generator
	 * 
	 */
	private MazeGenerator mazeGenerator;
	/**	How should movement be allowed in the maze
	 * 
	 */
	private Movement movement;
	/**	Regular Movement cost
	 * 
	 */
	private double movementCost;
	/**	Diagonal movement cost
	 * 
	 */
	private double diagonalMovementCost;
	//private int mazeRows;
	//private int mazeCols;
	//private int rowSource;
	//private int colSource;
	//private int rowGoal;
	//private int colGoal;
	//tomorrow add generated maze rows, cols, source and dest
	
	/**	Default Ct'r - default Properties I have defined.
	 * 
	 */
	public ClientProperties()
	{
		this.ui=UI.CLI;
		//this.threadNumber=16;
		this.mazeSolver=MazeSolver.MANHATTAN_DISTANCE_ASTAR;
		this.mazeGenerator=MazeGenerator.DFS;
		this.movement=Movement.DIAGONAL;
		this.movementCost=10;
		this.diagonalMovementCost=15;
		//this.mazeRows=10;
		//this.mazeCols=10;
		//this.rowSource=0;
		//this.colSource=0;
		//this.rowGoal=9;
		//this.colGoal=9;
		//more to come...
	}
	/** give this Ct'r the params and he will do as you please.
	 * @param mazeSolver
	 * @param threadNumber
	 * @param mazeGenerator
	 * @param movement
	 * @param movement cost
	 * @param diagonal movement cost
	 */
	public ClientProperties(UI ui, MazeSolver mazeSolver,int threadNumber,MazeGenerator mazeGenerator, Movement movement,double mCost,double mdCost)
	{
		this.ui=ui;
		//this.threadNumber=threadNumber;
		this.mazeSolver=mazeSolver;
		this.mazeGenerator=mazeGenerator;
		this.movement=movement;
		this.movementCost=mCost;
		this.diagonalMovementCost=mdCost;
		//this.mazeRows=mazeRows;
		//this.mazeCols=mazeCols;
		//this.rowSource=rowSource;
		//this.colSource=colSource;
		//this.rowGoal=rowGoal;
		//this.colGoal=colGoal;
		//more to come...
	}

	/*public int getThreadNumber() {
		return threadNumber;
	}
	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}*/
	public MazeSolver getMazeSolver() {
		return mazeSolver;
	}
	public void setMazeSolver(MazeSolver mazeSolver) {
		this.mazeSolver = mazeSolver;
	}
	public MazeGenerator getMazeGenerator() {
		return mazeGenerator;
	}
	public void setMazeGenerator(MazeGenerator mazeGenerator) {
		this.mazeGenerator = mazeGenerator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Movement getMovement() {
		return movement;
	}
	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	public double getMovementCost() {
		return movementCost;
	}
	public void setMovementCost(double movementCost) {
		this.movementCost = movementCost;
	}
	public double getDiagonalMovementCost() {
		return diagonalMovementCost;
	}
	public void setDiagonalMovementCost(double diagonalMovementCost) {
		this.diagonalMovementCost = diagonalMovementCost;
	}
	
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder("");
		str.append("Maze Generator: "+ mazeGenerator+"\n");
		str.append("Maze Solver: " + mazeSolver +"\n");
		str.append("Movement: " + movement +"\n");
		str.append("Movement Cost: "+movementCost+"\n");
		str.append("Diagonal Movement Cost: "+diagonalMovementCost + "\n");
		//str.append("Number of Threads: "+threadNumber + "\n");
		return str.toString();
	}
	/*public int getMazeRows() {
		return mazeRows;
	}
	public void setMazeRows(int mazeRows) {
		this.mazeRows = mazeRows;
	}
	public int getMazeCols() {
		return mazeCols;
	}
	public void setMazeCols(int mazeCols) {
		this.mazeCols = mazeCols;
	}
	public int getRowSource() {
		return rowSource;
	}
	public void setRowSource(int rowSource) {
		this.rowSource = rowSource;
	}
	public int getColSource() {
		return colSource;
	}
	public void setColSource(int colSource) {
		this.colSource = colSource;
	}
	public int getRowGoal() {
		return rowGoal;
	}
	public void setRowGoal(int rowGoal) {
		this.rowGoal = rowGoal;
	}
	public int getColGoal() {
		return colGoal;
	}
	public void setColGoal(int colGoal) {
		this.colGoal = colGoal;
	}*/
	public UI getUi() {
		return ui;
	}
	public void setUi(UI ui) {
		this.ui = ui;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
}
