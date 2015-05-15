package algorithms.mazeGenerators;

/** This interface defines what any Maze Generator has to do - generate a maze
 *
 * @author Tomer Cabouly
 *
 */
public interface MazeGenerator {
	public Maze generateMaze(int rows, int cols,int rowSource,int colSource,int rowGoal ,int colGoal);
}
