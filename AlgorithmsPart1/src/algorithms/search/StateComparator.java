package algorithms.search;

import java.util.Comparator;

/** State Comparator - will give it to Priority Queue in Searcher  
 * @author Tomer Cabouly
 *
 */
public class StateComparator implements Comparator<State> {

	@Override
	public int compare(State state1, State state2) {
		
		return state1.getCost().compareTo(state2.getCost());
	}

}
