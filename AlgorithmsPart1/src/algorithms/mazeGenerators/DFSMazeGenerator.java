package algorithms.mazeGenerators;

import java.util.List;
import java.util.Random;
import java.util.Stack;
/** This class generates a maze by the old-fashioned DFS algorithm
 * 
 * @author Tomer Cabouly
 *
 */
public class DFSMazeGenerator implements MazeGenerator {
	
	
	
	
	
	@Override
	/** Here's the magic happens - the DFS maze generator
	 * Simply put, it starts from a Cell and goes deep into the maze.
	 * one interesting quality of the generated maze is that there's a path between every two cells.
	 * the source and destination Cells I asked for in the ct'r weren't used.
	 * @return Maze
	 */
	public Maze generateMaze(int rows, int cols,int rowSource,int colSource,int rowGoal ,int colGoal) 
	{
		
		Maze maze = new Maze(rows, cols,rowSource,colSource,rowGoal ,colGoal);
		Cell currCell = maze.getCell(0, 0);
		currCell.setVisited(true);
		Stack<Cell> visited=new Stack<Cell>();
		while (maze.UnvisitedCellExists()==true) //DFS needs to get to every single cell. just like DFS in graph theory.
		{
				Cell ncell=getRandomUnvisitedNeighbor(maze,currCell);
				if(ncell==null) //want to make sure I'm in the maze bounds
				{
					currCell.setVisited(true);
					currCell=visited.pop();
					continue;
				}
					ncell.setVisited(true);
					int nrow=ncell.getRow();
					int ncol=ncell.getCol();
					int crow=currCell.getRow();
					int ccol=currCell.getCol();
					if(crow== nrow && ncol== ccol+1) //the following if's are breaking the wall between one Cell to its randomly chosen neighbor
					{
						currCell.setHasRightWall(false);
						ncell.setHasLeftWall(false);
					}
					if(crow== nrow && ncol==ccol -1)
					{
						currCell.setHasLeftWall(false);
						ncell.setHasRightWall(false);
					}
					if(ccol==ncol && nrow==crow -1)
					{
						currCell.setHasTopWall(false);
						ncell.setHasBottomWall(false);
					}
					if(ccol==ncol && nrow==crow+1)
					{
						currCell.setHasBottomWall(false);
						ncell.setHasTopWall(false);
					}
				
			currCell.setVisited(true);
			visited.push(currCell);
			currCell=ncell;
			}
		
		return maze;
	}
	
	/**
	 * this method helps the DFS get a Random Unvisited Neighbor
	 * @param maze
	 * @param cell
	 * @return a random unvisited neighbor of cell in the maze
	 */
	private Cell getRandomUnvisitedNeighbor(Maze maze,Cell cell) 
	{
		Cell retCell;
		Random rand=new Random();
		boolean flag=true;
		List<Cell> list=maze.getNeighbors(cell);
		for(Cell c: list)
			if(!c.isVisited())
				flag=false;
		if(flag==true)
			return null;
		do{

		int cellIndex = rand.nextInt(list.size());

		retCell=list.get(cellIndex);

		}while(retCell.isVisited()==true);

		return retCell;
	}
}


