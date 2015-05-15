package algorithms.mazeGenerators;
/** This class is all about Maze Cells. 
 * A cell is a maze unit which can have walls.
 * In a maze, we want to know occasionally if a cell was visited or not. thus the class has an appropriate field.
 * @author Tomer Cabouly
 * 
 *
 */
public class Cell {
	private boolean hasRightWall = true;
	private boolean hasLeftWall = true;
	private boolean hasTopWall = true;
	private boolean hasBottomWall = true;
	
	private boolean visited;
	private int row;
	private int col;
	
	public boolean getHasRightWall() {
		return hasRightWall;
	}
	public void setHasRightWall(boolean hasRightWall) {
		this.hasRightWall = hasRightWall;
	}
	public boolean getHasLeftWall() {
		return hasLeftWall;
	}
	public void setHasLeftWall(boolean hasLeftWall) {
		this.hasLeftWall = hasLeftWall;
	}
	public boolean getHasTopWall() {
		return hasTopWall;
	}
	public void setHasTopWall(boolean hasTopWall) {
		this.hasTopWall = hasTopWall;
	}
	public boolean getHasBottomWall() {
		return hasBottomWall;
	}
	public void setHasBottomWall(boolean hasBottomWall) {
		this.hasBottomWall = hasBottomWall;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
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
	
	public Cell(int row, int col)
	{
		setRow(row);
		setCol(col);
	}
	public void print()
	{
		System.out.println("Cell "+ this.getRow()+","+this.getCol());
	}
	@Override
	public String toString() {
		return ""+this.getRow()+","+this.getCol();
	}
	@Override
	public boolean equals(Object obj) {
		Cell other=(Cell) obj;
		if(this.hasBottomWall==other.hasBottomWall && this.hasLeftWall==other.hasLeftWall &&
				this.hasRightWall==other.hasRightWall && this.hasTopWall==other.hasTopWall &&
				this.row==other.row && this.col==other.col)
			return true;
		return false;
	}
}

