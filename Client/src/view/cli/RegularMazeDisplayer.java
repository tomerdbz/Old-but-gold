package view.cli;

import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze;

/** Class which displays mazes
 * @author Alon
 *
 */
public class RegularMazeDisplayer implements MazeDisplayer{



	@Override
	public void DisplayMaze(PrintWriter out, Maze m) {
		if(m!=null){
		out.println(m.toString());
		out.flush();
		}
	}



}
