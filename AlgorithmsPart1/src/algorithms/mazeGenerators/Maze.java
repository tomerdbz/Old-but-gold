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
		if(rows>0)
			this.rows = rows;
		if(cols>0)
			this.cols = cols;
		if(rowSource>=0 && rowSource < rows)
			this.rowSource=rowSource;
		if(colSource>=0 && colSource < cols)
			this.colSource=colSource;
		if(rowGoal>=0 && rowGoal < rows)
			this.rowGoal=rowGoal;
		if(colGoal>=0 && colGoal < cols)
			this.colGoal=colGoal;
		if(rows!=0 && cols!=0)
		{
			this.matrix = new Cell[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					matrix[i][j] = new Cell(i, j);				
				}
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
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder();
		
		for (int j = 0; j < cols; j++)
			str.append("__");
		str.append("_\n");
		
		for (int i = 0; i < rows; i++) {
			str.append("|");
			for (int j = 0; j < cols; j++) {				
				Cell cell = matrix[i][j];
				if (cell.getHasBottomWall())
					str.append("_");
				else
					str.append(" ");
				
				if (cell.getHasRightWall())
					str.append("|");
				else
					str.append(" ");	
								
			}
			str.append("\n");
		}
		return str.toString();
	}
	public Cell getCell(int row, int col) {
		if(row>=0 && row<=rows-1 && col>=0 && col<=cols-1)
			return matrix[row][col];
		else
			return null;
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
	public Cell[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(Cell[][] matrix) {
		this.matrix = matrix;
	}
	
	
}
