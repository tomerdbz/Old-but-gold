package algorithms.demo; //consider creating a package for Heuristics

import algorithms.search.Heuristic;
import algorithms.search.State;

/** A Maze specific Heuristic for Diagonal movement
 * @author Tomer Cabouly
 *
 */
public class MazeAirDistance implements Heuristic {
	private double movementCost;
	private int rowGoal;
	private int colGoal;
	/**
	 * @param Row Goal
	 * @param Col Goal
	 * @param Movement Cost
	 */
	public MazeAirDistance(int rowGoal,int colGoal,double movementCost)
	{
		this.setMovementCost(movementCost);
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
		return Math.sqrt(Math.pow((double)(destrow-srcrow),2.0)+Math.pow((double)(destcol-srccol),2.0));
	}
	public double getMovementCost() {
		return movementCost;
	}
	public void setMovementCost(double movementCost) {
		this.movementCost = movementCost;
	}
	@Override
	public State getGoalState() {		
		return new State(""+rowGoal+","+colGoal);
	}

}
