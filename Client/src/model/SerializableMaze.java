package model;

import java.io.Serializable;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

/**For Hibernate - we've rewritten Maze so it will be Serializable. for JavaDoc check out original Maze.
 * @author Tomer
 *
 */
public class SerializableMaze implements Serializable {
	
	private SerializableCell[][] matrix;
	private int rows;
	private int cols;
	private int rowSource;
	private int colSource;
	private int rowGoal;
	private int colGoal;
	
	
	
	public Maze getOriginalMaze()
	{
		Maze m=new Maze(rows,cols,rowSource,colSource,rowGoal,colGoal);
		Cell[][] originalMatrix=new Cell[rows][cols];
		for(int i=0;i<this.rows;i++)
			for(int j=0;j<this.cols;j++)
				originalMatrix[i][j]=this.matrix[i][j].getOriginalCell();
		m.setMatrix(originalMatrix);
		return m;
	}
	public SerializableMaze(Maze m) {
		this.rows=m.getRows();
		this.cols=m.getCols();
		this.rowSource=m.getRowSource();
		this.colSource=m.getColSource();
		this.rowGoal=m.getRowGoal();
		this.colGoal=m.getColGoal();
		this.matrix=new SerializableCell[rows][cols];
		for(int i=0;i<this.rows;i++)
			for(int j=0;j<this.cols;j++)
			{
				Cell c=m.getMatrix()[i][j];
				c.getClass();
				this.matrix[i][j]=new SerializableCell(m.getMatrix()[i][j]);
			}
	}
	public SerializableMaze() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public SerializableCell[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(SerializableCell[][] matrix) {
		this.matrix = matrix;
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
