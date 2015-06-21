package model;

import presenter.ClientProperties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

/**	MVP Model interface
 * @author Tomer
 *
 */
public interface Model {
	void generateMaze(String name,int rows, int cols,int rowSource,int colSource,int rowGoal ,int colGoal,String notifyArgument);
	Maze getMaze(String mazeName);
	void solveMaze(String mazeName, String notifyArgument);
	Solution getSolution(String mazeName);
	State getHint(String mazeName);
	void calculateHint(String mazeName, int row, int col,String notifyArgument);
	void setProperties(ClientProperties prop);
	void stop();
}
