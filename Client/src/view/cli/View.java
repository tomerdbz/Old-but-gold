package view.cli;

import java.util.concurrent.ConcurrentHashMap;

import presenter.Presenter.Command;
import presenter.ClientProperties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

/**	the interface defines what each View in this MVP architecture has to do!
 * @author Alon
 *
 */
public interface View {
	void start();
	void displayHint(State h);
	void receiveExistsMaze(Maze data);
	ClientProperties getProperties();
	void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands);
	Command getUserCommand();
	void Display(String s);
	void displayMaze(Maze m);
	void displaySolution(Solution s);
	//void exit();
}