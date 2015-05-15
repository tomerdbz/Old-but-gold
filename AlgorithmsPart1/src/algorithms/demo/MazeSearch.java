package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Movement;
import algorithms.search.Searchable;
import algorithms.search.State;

/** This class adapts Mazes so they would be Searchables - thus we could search them with the generic Searchers.
 * 	to do so - we need to know what type of movement is permitted and movements cost.
 * @author Tomer Cabouly
 *
 */
public class MazeSearch implements Searchable {

	private Maze maze;
	private Movement movement;
	private double mCost;
	private double mDiagonalCost;
	public MazeSearch(Maze maze,Movement movement,double mCost,double mDiagonalCost)
	{
		this.maze=maze;
		this.movement=movement;
		this.mCost=mCost;
		this.mDiagonalCost=mDiagonalCost;
	}
	public MazeSearch(Maze maze,double mCost)
	{
		this.maze=maze;
		this.movement=Movement.NONDIAGONAL;
		this.mCost=mCost;
		this.mDiagonalCost=-1;
	}
	@Override
	public State getStartState() {
		
		return new State(maze.getCell(maze.getRowSource(), maze.getColSource()).toString(),0.0,null);
	}

	@Override
	public State getGoalState() {
		return new State(maze.getCell(maze.getRowGoal(), maze.getColGoal()).toString());
	}

	@Override
	public ArrayList<State> getAllPossibleStates(State s) {
		ArrayList<State> states=new ArrayList<State>();
		String[] coordinates=s.getState().split(",");
		int row=Integer.parseInt(coordinates[0]);
		int col=Integer.parseInt(coordinates[1]);
			if(row!=0)
			{
				if((!maze.getCell(row, col).getHasTopWall()) && (!maze.getCell(row-1, col).getHasBottomWall()))
				states.add(new State(""+(row-1)+","+col,s.getCost()+mCost,s));
			}
			if(row!=maze.getRows()-1)
			{
				if((!maze.getCell(row, col).getHasBottomWall()) && (!maze.getCell(row+1, col).getHasTopWall()))
				states.add(new State(""+(row+1)+","+col,s.getCost()+mCost,s));
			}
			if(col!=0)
			{
				if((!maze.getCell(row, col).getHasLeftWall()) && (!maze.getCell(row, col-1).getHasRightWall()))
				states.add(new State(""+(row)+","+(col-1),s.getCost()+mCost,s));
			}
			if(col!=maze.getCols()-1)
			{
				if((!maze.getCell(row, col).getHasRightWall()) && (!maze.getCell(row, col+1).getHasLeftWall()))
				states.add(new State(""+(row)+","+(col+1),s.getCost()+mCost,s));
			}
		if(movement==Movement.DIAGONAL)//work on diagonal ifs
		{
			boolean flag;
			if(row!=0 && col!=0)
			{
				flag=(!maze.getCell(row, col).getHasTopWall() &&  !maze.getCell(row-1, col-1).getHasRightWall()) || (!maze.getCell(row, col).getHasLeftWall() && !maze.getCell(row-1, col-1).getHasBottomWall());
				if(flag==true)
					states.add(new State(""+(row-1)+","+(col-1),s.getCost()+mDiagonalCost,s));
			}
			if(col!=maze.getCols()-1 && row!=0)
			{
				flag=(!maze.getCell(row, col).getHasTopWall() && !maze.getCell(row-1, col+1).getHasLeftWall()) || (!maze.getCell(row, col).getHasRightWall() && !maze.getCell(row-1, col+1).getHasBottomWall()); 
				if(flag==true)
				states.add(new State(""+(row-1)+","+(col+1),s.getCost()+mDiagonalCost,s));
			}
			if(row!=maze.getRows()-1 && col!=0)
			{
				flag=(!maze.getCell(row, col).getHasBottomWall() && !maze.getCell(row+1, col-1).getHasRightWall()) || (!maze.getCell(row, col).getHasLeftWall() && !maze.getCell(row+1, col-1).getHasTopWall());
				if(flag==true)
				states.add(new State(""+(row+1)+","+(col-1),s.getCost()+mDiagonalCost,s));
			}
			if(row!=maze.getRows()-1 && col!=maze.getCols()-1)
			{
				flag=(!maze.getCell(row, col).getHasBottomWall() && !maze.getCell(row+1, col+1).getHasLeftWall()) || (!maze.getCell(row, col).getHasRightWall() && !maze.getCell(row+1, col+1).getHasTopWall());
				if(flag==true)
					states.add(new State(""+(row+1)+","+(col+1),s.getCost()+mDiagonalCost,s));
			}
		}
		return states;
	}

}
