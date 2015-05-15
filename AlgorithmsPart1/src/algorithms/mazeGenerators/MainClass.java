package algorithms.mazeGenerators;

public class MainClass {

	public static void main(String[] args) {
		System.out.println("bamba");
		MazeGenerator mg=new DFSMazeGenerator(); // my choice of algorithm
		Maze m = mg.generateMaze(4,4,0,0,3,3);
		m.print(); // displays the maze on the console 

	}

}
