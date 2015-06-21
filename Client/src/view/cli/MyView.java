package view.cli;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import presenter.ClientProperties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * 
 * @author Alon, Tomer
 * This function is an implementation of the interface
 * View it is an Observable and is being viewed by the Presenter
 * for the MVP Architecture and is also an Observer to Observer over
 * the MVPRunnable CLI
 *
 */
public class MyView extends Observable implements View,Observer {
	public MVPRunnableCli cl; // a data member which represents the CLI
	public String arguments; // arguments of commands we last ran
	public presenter.Presenter.Command c; // Last command we ran
	public MyView(BufferedReader in, PrintWriter out)
	{
		this.cl=new MVPRunnableCli(in,out);
		cl.addObserver(this);
	}
	/**
	 * This funcion sets the HashMap that links between names of commands
	 * to the actual commands
	 * @param commands the Hashmap which links between names of command to the commands
	 * 
	 */
	@Override
	public void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands) {
		cl.setCommands(commands);
		
	}

	/**
	 * Displays things sent to the view
	 * using the cl.out which is the way we print thing to the screen
	 * 
	 */
	@Override
	public void Display(String s) {
		this.cl.getOut().println(s);
		this.cl.getOut().flush();
	}
	
	/*public void ChangeNotify(String arg){
		setChanged();
		notifyObservers(arg);
		
	}*/
	
	/**
	 * This function runs the CLI as a thread and starts the whole MVP 
	 * Architecture
	 */
	@Override
	public void start() {
		Thread t = new Thread(cl); // the cli which is a data member of MyView
	    t.start();
		}

	/**
	 * Displays the solution using The class of Regular Solution Displayer
	 *@param s which is the solution
	 */
	@Override
	public void displaySolution(Solution s) {
		
		RegularSolutionDisplayer sd = new RegularSolutionDisplayer();
		sd.SolutionDisplay(this.cl.getOut(), s);
		
	}
	/**
	 * Displays the maze using The class of Regular Maze Displayer
	 *@param m which is the maze
	 */
	@Override
	public void displayMaze(Maze m) {
		
		RegularMazeDisplayer dp = new RegularMazeDisplayer();
		dp.DisplayMaze(this.cl.getOut(),m);
		

	}
	@Override
	public presenter.Presenter.Command getUserCommand() {
		return c;
	}

	/*@Override
	public void exit() {
		cl.out.println("Exiting now");
		setChanged();
		notifyObservers();
	
	}*/
	/** since MyView observes CLI it watches it. for any update it will be called here, arg will by COMMAND NAME
	 * Arguments for command transfered through a data memeber of CLI which is updated when called here. 
	 * @param Observable - the CLI
	 * @param - arg - symbolizes command Name
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o==cl){
			if(arg!=null && arg.toString().equals("error"))
			{
				setChanged();
				notifyObservers("error");
			}
			else
			{
				this.c = (presenter.Presenter.Command) arg;
				setChanged();
				notifyObservers(cl.args);
			}
		}
		
	}
	@Override
	public void receiveExistsMaze(Maze data) {
		if(data!=null)
			displayMaze(data);
		
	}
	@Override
	public ClientProperties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void displayHint(State h) {
		// TODO Auto-generated method stub
		
	}

	
}