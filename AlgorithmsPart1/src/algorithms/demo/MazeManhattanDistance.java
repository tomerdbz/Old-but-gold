package algorithms.demo;//consider creating a package for Heuristics

import algorithms.search.Heuristic;
import algorithms.search.State;

/** A Maze specific Heuristic for Non Diagonal movement
 * @author Tomer Cabouly
 *
 */
public class MazeManhattanDistance implements Heuristic {
	private double movementCost;
	private int rowGoal;
	private int colGoal;
	/**
	 * @param Goal row
	 * @param Goal col
	 * @param Movement Cost
	 */
	public MazeManhattanDistance(int rowGoal,int colGoal,double movementCost)
	{
		this.movementCost=movementCost;
		this.rowGoal=rowGoal;
		this.colGoal=colGoal;
	}
	@Override
	public Double hCalc(State source)
	{
		String src=source.getState();
		String[] srccoordinates=src.split(",");
		int srcrow=Integer.parseInt(srccoordinates[0]);
		int srccol=Integer.parseInt(srccoordinates[1]);
		int destrow=rowGoal;
		int destcol=colGoal;
		int absrow=Math.abs(destrow-srcrow);
		int abscol=Math.abs(destcol-srccol);
		
		return (double) ((absrow+abscol)*movementCost);
	}

	public double getMovementCost() {
		return movementCost;
	}

	public void setMovementCost(double movementCost) {
		this.movementCost = movementCost;
	}
	@Override
	public State getGoalState() {
		
		return new State(""+rowGoal +"," +colGoal);
	}

}
