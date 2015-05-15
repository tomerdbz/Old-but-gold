package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;

/** This class defines a Maze - a matrix of cells with defined source and goal indexes.
 * @author Tomer Cabouly
 *
 */
public class Maze {
	private Cell[][] matrix;
	private int rows;
	private int cols;
	private int rowSource;
	private int colSource;
	private int rowGoal;
	private int colGoal;
	public Maze(int rows, int cols, int rowSource,int colSource,int rowGoal,int colGoal) {
		this.rows = rows;
		this.cols = cols;
		this.rowSource=rowSource;
		this.colSource=colSource;
		this.rowGoal=rowGoal;
		this.colGoal=colGoal;
		this.matrix = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = new Cell(i, j);				
			}
		}
	}
	@Override
	public boolean equals(Object obj) {
		Maze other=(Maze)obj;
		boolean flag=true;
		if(this.rows==other.rows && this.cols==other.cols)
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if(this.matrix[i][j] != other.matrix[i][j])
						flag=false;
				}
			}
		else
			return false;
		return flag;
	}
	public void print() 
	{
		for (int j = 0; j < cols; j++)
			System.out.print("__");
		System.out.println("_");
		
		for (int i = 0; i < rows; i++) {
			System.out.print("|");
			for (int j = 0; j < cols; j++) {				
				Cell cell = matrix[i][j];
				if (cell.getHasBottomWall())
					System.out.print("_");
				else
					System.out.print(" ");
				
				if (cell.getHasRightWall())
					System.out.print("|");
				else
					System.out.print(" ");	
								
			}
			System.out.println();
		}
	}
	
	public Cell getCell(int row, int col) {
		return matrix[row][col];
	}
	
	public boolean UnvisitedCellExists() {
		List<Cell> list = getUnvisitedCells();
		if (list.size() == 0)
			return false;
		else
			return true;
	}
	
	public List<Cell> getUnvisitedCells() {
		List<Cell> list = new ArrayList<Cell>();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = matrix[i][j];
				if (!cell.isVisited())
					list.add(cell);
			}
		}
		return list;
	}
	public List<Cell> getNeighbors(Cell cell)
	{
		List<Cell> list=new ArrayList<Cell>();
		int row=cell.getRow();
		int col=cell.getCol();
		/*if(row==0)
			if(col==0)
			{
				list.add(matrix[0][1]);
				list.add(matrix[1][0]);
			}
			else if(col==cols-1)
			{
				list.add(matrix[row][col-1]);
				list.add(matrix[row+1][col]);
			}
			else
			{
				list.add(matrix[row][col-1]);
				list.add(matrix[row+1][col]);
				list.add(matrix[row][col+1]);
			}
		else if(row==rows-1)
		{
			
			
			
		}*/
		
			if(row!=0)
			list.add(matrix[row-1][col]);
			if(col!=cols-1)
				list.add(matrix[row][col+1]);
			if(col!=0)
				list.add(matrix[row][col-1]);
			if(row!=rows-1)
				list.add(matrix[row+1][col]);

			return list;
			
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRowSource() {
		return rowSource;
	}

	public void setRowSource(int rowSource) {
		this.rowSource = rowSource;
	}

	public int getColSource() {
		return colSource;
	}

	public void setColSource(int colSource) {
		this.colSource = colSource;
	}

	public int getRowGoal() {
		return rowGoal;
	}

	public void setRowGoal(int rowGoal) {
		this.rowGoal = rowGoal;
	}

	public int getColGoal() {
		return colGoal;
	}

	public void setColGoal(int colGoal) {
		this.colGoal = colGoal;
	}
	
}
