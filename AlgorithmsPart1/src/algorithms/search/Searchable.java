package algorithms.search;

import java.util.ArrayList;

/**This interface defines what each Searchable problem must implement - give Start and Goal state and give Possible successor states from one state.
 * @author Tomer Cabouly
 *
 */
public interface Searchable {
	   State getStartState();
	   State getGoalState();
	   ArrayList<State> getAllPossibleStates(State s);
}
