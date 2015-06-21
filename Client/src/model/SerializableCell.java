package model;

import java.io.Serializable;

import algorithms.mazeGenerators.Cell;

/**	For Hibernate - we've rewritten Cell so it will be Serializable. for JavaDoc check out original Cell.
 * @author Tomer
 *
 */
public class SerializableCell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hasRightWall = true;
	private boolean hasLeftWall = true;
	private boolean hasTopWall = true;
	private boolean hasBottomWall = true;
	private int row;
	private int col;
	public Cell getOriginalCell()
	{
		Cell c=new Cell(row,col);
		c.setHasTopWall(hasTopWall);
		c.setHasBottomWall(hasBottomWall);
		c.setHasLeftWall(hasLeftWall);
		c.setHasRightWall(hasRightWall);
		return c;
	}
	public SerializableCell() {
		// TODO Auto-generated constructor stub
	}
	public SerializableCell(Cell c) {
		// TODO Auto-generated constructor stub
		this.hasTopWall=c.getHasTopWall();
		this.hasRightWall=c.getHasRightWall();
		this.hasBottomWall=c.getHasBottomWall();
		this.hasLeftWall=c.getHasLeftWall();
		this.row=c.getRow();
		this.col=c.getCol();
	}
	public boolean isHasRightWall() {
		return hasRightWall;
	}
	public void setHasRightWall(boolean hasRightWall) {
		this.hasRightWall = hasRightWall;
	}
	public boolean isHasLeftWall() {
		return hasLeftWall;
	}
	public void setHasLeftWall(boolean hasLeftWall) {
		this.hasLeftWall = hasLeftWall;
	}
	public boolean isHasTopWall() {
		return hasTopWall;
	}
	public void setHasTopWall(boolean hasTopWall) {
		this.hasTopWall = hasTopWall;
	}
	public boolean isHasBottomWall() {
		return hasBottomWall;
	}
	public void setHasBottomWall(boolean hasBottomWall) {
		this.hasBottomWall = hasBottomWall;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
