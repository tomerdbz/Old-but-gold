package view.gui;

import org.eclipse.swt.events.PaintEvent;

import algorithms.search.Solution;
import algorithms.search.State;
/**
 * 
 * @author Alon,Tomer
 *an interface of board
 */
public interface Board {
	/**	drawBoard for the paintListener - how would one board likes to be drawn and under which conditions.
	 * 	This drawBoard will be given to the new Paint Listener
	 * @param arg0 - the paintEvent
	 */
	void drawBoard(PaintEvent arg0);
	void applyInputDirection(Direction direction);


	 /**
	  * Checks if there is a path to go Up in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  */
	 boolean hasPathUP(int characterRow,int characterCol);
	 /**
 * Checks if there is a path to go Right in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  */
	 boolean hasPathRIGHT(int characterRow,int characterCol);
	 /**
	 * Checks if there is a path to go Down in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  * */
	 boolean hasPathDOWN(int characterRow,int characterCol);
	 /**
	 * Checks if there is a path to go Left in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  * 
	  */
	 boolean hasPathLEFT(int characterRow,int characterCol);
	 /***
	  * Disposes of all things created in board
	  */
	 void destructBoard();
	 
	 /**Through this method the Board will display its Problem.
	  * 
	 * @param o - the problem to display
	 */
	void displayProblem(Object o);
	/** displays the solution in the Board.
	 * @param s - the Solution to display.
	 */
	void displaySolution(Solution s);
	/** displays hint in the Board.
	 * @param h - the State - the Hint to display.
	 */
	void displayHint(State h);
}
