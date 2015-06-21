package view.cli;

import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze;
/**
 *   
 * @author Alon
 * This interface was created in order
 * to display mazes in different ways 
 * for example: Displaying a maze as a visual image
 * or display a maze as a collection of walls
 * and so on.
 *
 */
public interface MazeDisplayer {
	
	/**
	 * The function of maze displaying later to
	 * be implemented by other functions that
	 * implement Maze Displayer
	 * @param out a Print Writer 
	 * @param m a maze 
	 */
	public void DisplayMaze(PrintWriter out,Maze m);

}