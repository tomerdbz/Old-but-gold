package algorithms.demo;

import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.Movement;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;

/** A short demonstration what the jar does. you could generate mazes and solve them.
 * generation will be via a Random, pretty stupid algorithm and DFS.
 * solving will be via BFS or A*
 * Have fun!
 * @author Tomer Cabouly
 *
 */
public class Demo {

	public void run()
	{
		MazeGenerator mg=new DFSMazeGenerator(); // my choice of algorithm
		Maze m = mg.generateMaze(8,8,0,0,7,7);
		m.print();
		Searchable ms=new MazeSearch(m,Movement.NONDIAGONAL,10,15);
		Searcher choice=new BFS();//new MazeAirDistance(1));
		Solution sol=choice.search(ms);
		System.out.println("BFS Without Diagonals:\n");
		System.out.print(sol);
		System.out.println("\n\n");
		Searchable ms1=new MazeSearch(m,Movement.DIAGONAL,10,15);
		Solution sol1=choice.search(ms1);
		System.out.println("BFS With Diagonals:\n");
		System.out.print(sol1);
		System.out.println("\n\n");
		System.out.println("AStar Without Diagonals:\n");
		Searcher choiceA=new AStar(new MazeManhattanDistance(7,7,10));//new MazeAirDistance(1));
		Solution solA=choiceA.search(ms);
		System.out.print(solA);
		System.out.println("\n\n");
		System.out.println("AStar With Diagonals:\n");
		Searcher choiceA2=new AStar(new MazeAirDistance(7,7,10));//new MazeAirDistance(1));
		Solution solA2=choiceA2.search(ms1);
		System.out.print(solA2);
		System.out.println("\n\n");
	}

}
