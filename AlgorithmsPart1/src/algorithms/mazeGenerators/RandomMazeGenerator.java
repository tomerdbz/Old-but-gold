package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** This class generates a maze by a new, pretty stupid method
 * 
 * @author Tomer Cabouly
 *
 */
public class RandomMazeGenerator implements MazeGenerator {


	/** This enum makes things brighter in the method generateMaze. it uses it to know which wall it should break
	 * 
	 *
	 */
	private enum Direction {
		UP,
		RIGHT,
		LEFT,
		BOTTOM;
		private static final List<Direction> VALUES=Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
		public static Direction randomDirection()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	  }
}
	
	/** The following method consists of two parts:
	 * 1. generating a random path between the starting point and the goal.
	 * 2. removing random walls from random cells which aren't part of the above path.
	 * @return a Maze with a path between the chosen source to destination.
	 */
	@Override
	public Maze generateMaze(int rows, int cols,int rowSource,int colSource,int rowGoal ,int colGoal) 
	{
		Maze maze=new Maze(rows,cols,rowSource,colSource,rowGoal ,colGoal);
		Random rowrand=new Random();
		Random colrand=new Random();
		Random rand=new Random();
		Cell currCell;
		int rCell = 0;
		int Cellrow=rowSource,Cellcol=colSource;//0 is top,1 is right,2 is bottom,3 is left
		Direction selectedwall;
		currCell=maze.getCell(Cellrow, Cellcol);
		List<Cell> arrNeighborCells;
		List<Cell> arrCells=new ArrayList<Cell>();
		arrCells.add(currCell);
		
		while(!(currCell.getRow()==rowGoal && currCell.getCol()==colGoal)) //until you get to the goal
		{
			currCell.setVisited(true);
			arrNeighborCells=maze.getNeighbors(currCell); //go to your neighbors 
			boolean flag=false;
			while(!flag){
			Cell tempCell;
			rCell=rand.nextInt(arrNeighborCells.size()); //select one randomly, I made sure the cells on the path would be unique
			tempCell=arrNeighborCells.get(rCell);
			flag=!tempCell.isVisited();
			if(flag)
			{
				currCell=tempCell;
				break;
			}
			}
				arrCells.add(currCell);
			
		}
		currCell.setVisited(true);
		for(int i=0;i<arrCells.size()-1;i++) //looking at the path and breaking the walls between cells. creating an actual maze path
		{
			Cell curr=arrCells.get(i);
			Cell next=arrCells.get(i+1);
			if(next.getRow() == curr.getRow()-1)
			{
				curr.setHasTopWall(false);
				next.setHasBottomWall(false);
			}
			if(next.getRow() == curr.getRow()+1)
			{
				curr.setHasBottomWall(false);
				next.setHasTopWall(false);
			}
			if(next.getCol() == curr.getCol()-1)
			{
				curr.setHasLeftWall(false);
				next.setHasRightWall(false);
			}
			if(next.getCol() == curr.getCol()+1)
			{
				curr.setHasRightWall(false);
				next.setHasLeftWall(false);
			}
			curr.setVisited(true);
			next.setVisited(true);
		}
		maze.print();
		while(maze.UnvisitedCellExists()) //from here on - I'll visit new Cells until I've visited all and remove random walls
		{
		Cellrow=rowrand.nextInt(rows);
		Cellcol=colrand.nextInt(cols);
		currCell = maze.getCell(Cellrow, Cellcol);
		if(currCell.isVisited())
			continue;
		currCell.setVisited(true);
		
		selectedwall=Direction.randomDirection();
		switch(selectedwall)
		{
		case UP:
			if(Cellrow!= 0)
			{	
				currCell.setHasTopWall(false);	
			}
			break;
		case RIGHT:
			if(Cellcol!=cols-1)
			{
				currCell.setHasRightWall(false);
				
			}
			break;
		case BOTTOM:
			if(Cellrow!=rows-1)
			{
				currCell.setHasBottomWall(false);
				
			}
			break;
		case LEFT:
			if(Cellcol!=0)
			{
				currCell.setHasLeftWall(false);
				
			}
			break;
		default:
			break;
		}
		}
		
		return maze;
	}
	
}
