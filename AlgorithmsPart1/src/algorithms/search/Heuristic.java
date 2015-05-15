package algorithms.search;

/** here we define what a Heuristic has to do - calculate h and getGoalState.
 * goal is domain specific - thus heuristic specific.
 * notice that the getGoalState may seem unnecessary, but it will make sure that any future heuristic someone'll make will need to use a goalState.
 *It's logically necessary. 
 * @author Tomer Cabouly
 *
 */
public interface Heuristic {
	/**calculates heuristic to goal
	 * @param source
	 * @return
	 */
	public Double hCalc(State source); //Heurtistic to goal
	public State getGoalState();
}
