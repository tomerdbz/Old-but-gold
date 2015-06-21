package algorithms.mazeGenerators;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author Alon Orlovsky
 *a class which is an implementation
 * of MazeGenerator and it generates a cell in a random way
 */
public class RandomMazeGenerator implements MazeGenerator {

	@Override
	/**
	 * this function build a path randomly from start(0,0) to exit (rows-1,cols-1)
	 * and then breaks other cells walls randomly
	 * @param rows number of rows in a maze
	 * @ param cols number of cols in a maze
	 * @return a maze which is the generated maze
	 */
	public Maze generateMaze(int rows, int cols,int rowSource,int colSource,int rowGoal,int colGoal) {
		Maze map= new Maze(rows,cols,rowSource,colSource,rowGoal,colGoal);
		Cell C=map.getCell(rowSource, colSource);
		while(C!=map.getCell(rowGoal, colGoal)) //Building a path and then we will randomly remove walls
		{
			C.setVisited(true);
			boolean a=false; //if no options can be applied 
			boolean b=false;  // so we wont loop to infinity 
			boolean c=false;
			boolean d=false;
		boolean flag = false;
		Random rnd= new Random();
		while(!flag){ //selecting a random neighbor
		int num = rnd.nextInt(4); //all similar so one will be demonstrated
			if(num ==0 && C.getRow()!=0 && C.getHasTopWall()){ //if not out of bounds and has a wall between them
				C.setHasTopWall(false); // break the wall
				Cell temp2=map.getCell(C.getRow()-1, C.getCol());
				temp2.setHasBottomWall(false);
				flag = true;
				C=temp2; // now the neighbor is the current state
			}
			if(num==0)
				a=true;
			if(num==1 && C.getRow()!=map.getRows()-1 && C.getHasBottomWall()){
				C.setHasBottomWall(false);
				Cell temp2=map.getCell(C.getRow()+1, C.getCol());
				temp2.setHasTopWall(false);
				flag=true;
				C=temp2;
			}
			if(num==1)
				b=true;
			if(num==2 && C.getCol()!=0 && C.getHasLeftWall()){
				C.setHasLeftWall(false);
				Cell temp2=map.getCell(C.getRow(), C.getCol()-1);
				temp2.setHasRightWall(false);
				flag=true;
				C=temp2;
			}
			if(num==2)
				c=true;
			if(num==3 && C.getCol()!=map.getCols()-1 && C.getHasRightWall()){
				C.setHasRightWall(false);
				Cell temp2=map.getCell(C.getRow(), C.getCol()+1);
				temp2.setHasLeftWall(false);
				flag=true;
				C=temp2;
			}
			if(num==3)
				d=true;
			
				if(a&&b&&c&&d){
					do{
						int num1 = rnd.nextInt(map.getUnvisitedCells().size());
					 C=map.getUnvisitedCells().get(num1);
					}while(!this.BreakWallWithVisitedNeigbor(C, map)); //checks if any wall can be destoryed
						flag =true;
				}
			}
		}
		
		 // finished building a path
		while(map.UnvisitedCellExists()){ //after the path is set we randomly remove walls
			boolean a=false; //if no options can be applied 
			boolean b=false;
			boolean c=false;
			boolean d=false;
		List<Cell> nVistied=map.getUnvisitedCells(); //get all not visited neighbors
		Random randomGenerator = new Random();
		int rand = randomGenerator.nextInt(nVistied.size());
		Cell temp= nVistied.get(rand); //randomly get cell from unvisited
		temp.setVisited(true);
		boolean flag = false;
		Random rnd= new Random();
		while(!flag){ // choose a wall randomly to remove
		int num = rnd.nextInt(4);
			if(num ==0 && temp.getRow()!=0 && temp.getHasTopWall()){ //if not out of bounds and has wall
				temp.setHasTopWall(false); // break it
				Cell temp2=map.getCell(temp.getRow()-1, temp.getCol());
				temp2.setHasBottomWall(false);
				flag = true;
			}
			if(num==0)
				a=true;
			if(num==1 && temp.getRow()!=map.getRows()-1 && temp.getHasBottomWall()){
				temp.setHasBottomWall(false);
				Cell temp2=map.getCell(temp.getRow()+1, temp.getCol());
				temp2.setHasTopWall(false);
				flag=true;
			}
			if(num==1)
				b=true;
			if(num==2 && temp.getCol()!=0 && temp.getHasLeftWall()){
				temp.setHasLeftWall(false);
				Cell temp2=map.getCell(temp.getRow(), temp.getCol()-1);
				temp2.setHasRightWall(false);
				flag=true;
			}
			if(num==2)
				c=true;
			if(num==3 && temp.getCol()!=map.getCols()-1 && temp.getHasRightWall()){
				temp.setHasRightWall(false);
				Cell temp2=map.getCell(temp.getRow(), temp.getCol()+1);
				temp2.setHasLeftWall(false);
				flag=true;
			}
			if(num==3)
				d=true;
			
			if(a&&b&&c&&d)
					flag = true;
			}
		nVistied.remove(rand);
		}
		
		return map;
	}
	/**
	 * Tries to break a wall with a visited neighbor
	 * @param temp current state
	 * @param map the maze
	 * @return true or false can a wall be broken here 
	 */
	public boolean BreakWallWithVisitedNeigbor(Cell temp,Maze map){ //function that was made in order to remove wall with a visited Neighbor
		boolean flag = false;
		boolean a=false; //if no options can be applied 
		boolean b=false;
		boolean c=false;
		boolean d=false;
		Random rnd= new Random();
		while(!flag){
		int num = rnd.nextInt(4); // randomly select a neighbor
			if(num ==0 && temp.getRow()!=0 && temp.getHasTopWall()){ // if not out of bound and has a wall between them
				Cell temp2=map.getCell(temp.getRow()-1, temp.getCol()); //get the cell
				if(temp2.isVisited()){ // if he is visited break the wall between them
					temp.setHasTopWall(false);
				temp2.setHasBottomWall(false);
				flag = true;
				return true; //retrun we can break a wall
				}
			}
			if(num==0)
				a=true;
			if(num==1 && temp.getRow()!=map.getRows()-1 && temp.getHasBottomWall()){
				Cell temp2=map.getCell(temp.getRow()+1, temp.getCol());
				if(temp2.isVisited()){
				temp.setHasBottomWall(false);
				temp2.setHasTopWall(false);
				flag=true;
				return true;
				}
			}
			if(num==1)
				b=true;
			if(num==2 && temp.getCol()!=0 && temp.getHasLeftWall()){
				Cell temp2=map.getCell(temp.getRow(), temp.getCol()-1);
				if(temp2.isVisited()){
					temp.setHasLeftWall(false);
				temp2.setHasRightWall(false);
				flag=true;
				return true;
				}
			}
			if(num==2)
				c=true;
			if(num==3 && temp.getCol()!=map.getCols()-1 && temp.getHasRightWall()){
				Cell temp2=map.getCell(temp.getRow(), temp.getCol()+1);
				if(temp2.isVisited()){
					temp.setHasRightWall(false);
				temp2.setHasLeftWall(false);
				flag=true;
				return true;
				}
			}
			if(num==3)
				d=true;
			
			if(a&&b&&c&&d)
					flag = true;
			}
		return false; //no option can be apllied so we return false 
	}

}
