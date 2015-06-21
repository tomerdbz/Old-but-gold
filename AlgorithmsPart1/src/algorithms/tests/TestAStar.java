package algorithms.tests;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import algorithms.demo.MazeManhattanDistance;
import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.Heuristic;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import algorithms.search.State;

/**	Tests for AStar.
 * @author Tomer
 *
 */
public class TestAStar extends TestCase {

	public TestAStar(String name) {
		super(name);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**	checks how AStar will respond to a dumb heuristic - with functions returning null.
	 * I will expect it to act as BFS.
	 * 
	 */
	@Test
	public void testDumbHeuristic() {
		AStar a=new AStar(new Heuristic() {
			
			@Override
			public Double hCalc(State source) {
				return null;
			}
			
			@Override
			public State getGoalState() {
				return null;
			}
		});
		Maze m=new DFSMazeGenerator().generateMaze(10,10,0,0,5,5);
		Solution sol=a.search(new MazeSearch(m,10));
		Solution solBFS=new BFS().search(new MazeSearch(m,10));
		assertEquals(sol, solBFS);
		
	}

	/**checks how AStar will respond without heuristic.
	 * I will expect it to act as BFS.
	 * 
	 */
	@Test
	public void testNullHeuristic() {
		AStar a=new AStar(null);
		Maze m=new DFSMazeGenerator().generateMaze(10, 10, 0, 0, 5, 5);
		System.out.println(m);
		Solution sol=a.search(new MazeSearch(m,10));
		Solution solBFS=new BFS().search(new MazeSearch(m,10));
		System.out.println(" Astar sol "+sol);
		System.out.println("BFS SOL " + solBFS);
		assertEquals(sol, solBFS);
	}
	/* Checks if it gets in an infinite loop
	@Test
	public void testNegativeCost() {
		AStar a=new AStar(new MazeManhattanDistance(5, 5, -50));
		Solution sol= a.search(new MazeSearch(new DFSMazeGenerator().generateMaze(10,10,0,0,5,5), -50));
		System.out.println(sol);
		assertNotNull(sol);
	}*/
	
	/**	What happens if heuristic doesn't fit the goal of the problem?
	 * 	Expect it to solve - but not neccesarrily the best solution.
	 * 
	 */
	@Test
	public void testUnfittingGoalHeuristic() {
		AStar a=new AStar(new MazeManhattanDistance(5, 5, 10));
		Solution sol=a.search(new MazeSearch(new DFSMazeGenerator().generateMaze(10,10,0,0,8,8),10));
		System.out.println("unfitting" + sol);
		assertNotNull(sol);
	}
	
	/**	What happens when heuristic has bad args?
	 * 	default value for any bad arg is 0. 
	 * 
	 */
	@Test
	public void testInvalidHeuristicArguments() {
		AStar a=new AStar(new MazeManhattanDistance(-2, -3, 10));
		Solution sol=a.search(new MazeSearch(new DFSMazeGenerator().generateMaze(10,10,-2,-3,8,8),10));
		System.out.println("invalod" + sol);
	}
	
	/**	What happens without a given problem to solve?
	 * 	Expects null.
	 * 
	 */
	@Test
	public void testNullSearchArgument() {
		AStar a=new AStar(new MazeManhattanDistance(9, 9, 10));
		Solution sol=a.search(null);
		System.out.println("invalid" + sol);
		assertNull(sol);
	}
	
	/**	What happens when there's an empty maze?
	 * 	Expects null.
	 * 
	 */
	@Test
	public void testEmptyMazeArgument() {
		AStar a=new AStar(new MazeManhattanDistance(5, 5, 10));
		Solution sol=a.search(new MazeSearch(new Maze(0,0,0,0,0,0),10));
		System.out.println("emptymaze" + sol);
		assertNull(sol);
	}
	/*@Test
	public void testNullMovement() {
		AStar a=new AStar(new MazeAirDistance(-2, -3, 10));
		Solution sol=a.search(new MazeSearch(new DFSMazeGenerator().generateMaze(10,10,-2,-3,8,8),null,10,15));
		System.out.println("invalid" + sol);
		assertNull(sol);
	}*/
	/** What happens when the problem to solve is empty?
	 * Expects null.
	 * 
	 */
	@Test
	public void testDumbSearchableArgument() {
		AStar a=new AStar(new MazeManhattanDistance(-2, -3, 10));
		Solution sol=a.search(new Searchable() {
			
			@Override
			public State getStartState() {
				return null;
			}
			
			@Override
			public State getGoalState() {
				return null;
			}
			
			@Override
			public ArrayList<State> getAllPossibleStates(State s) {
				return null;
			}
		});
		System.out.println("invalid" + sol);
		assertNull(sol);
	}
	/**	What happens when The problem and the heuristic are from different domains?
	 * 	Expects it to act as BFS.
	 * 
	 */
	@Test
	public void testUncompatibleSearchableAndHeuristic() {
		AStar a=new AStar(new MazeManhattanDistance(-2, -3, 10));
		Solution sol=a.search(new Searchable() { //won't create a new problem to search. create something stupid instead.
			
			@Override
			public State getStartState() {
				return new State("Lior Narkis",15.0);
			}
			
			@Override
			public State getGoalState() {
				return new State("Lior Narkik");
			}
			
			@Override
			public ArrayList<State> getAllPossibleStates(State s) {
				ArrayList<State> arr=new ArrayList<State>();
				if(s.getState().equals("Lior Narkis"))
				{
					arr.add(new State("Lior Narkip",12.0));
					arr.add(new State("Lior Narkid",5.0));
				}
				else if(s.getState().equals("Lior Narkip"))
				{
					arr.add(new State("Lior Narkia",3.0));
				}
				else
					arr.add(new State("Lior Narkik",3.0));
				return arr;
			}
		});
		Solution sol2=new BFS().search(new Searchable() { //won't create a new problem to search. create something stupid instead.
			
			@Override
			public State getStartState() {
				return new State("Lior Narkis",15.0);
			}
			
			@Override
			public State getGoalState() {
				return new State("Lior Narkik");
			}
			
			@Override
			public ArrayList<State> getAllPossibleStates(State s) {
				ArrayList<State> arr=new ArrayList<State>();
				if(s.getState().equals("Lior Narkis"))
				{
					arr.add(new State("Lior Narkip",12.0));
					arr.add(new State("Lior Narkid",5.0));
				}
				else if(s.getState().equals("Lior Narkip"))
				{
					arr.add(new State("Lior Narkia",3.0));
				}
				else
					arr.add(new State("Lior Narkik",3.0));
				return arr;
			}
		});
		System.out.println(sol);
		assertEquals(sol,sol2);
	}
	
}
