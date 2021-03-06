package algorithms.search;

import java.util.Comparator;

/** State Heuristic Comparator - will give it to Priority Queue in Searcher.
 *  it's a type of State Comparator but the difference lies in the Compare - it adds the Heuristic Calculation  
 * @author Tomer Cabouly
 *
 */
public class StateHeuristicComparator extends StateComparator implements Comparator<State> {
	private Heuristic heuristic;
	public StateHeuristicComparator(Heuristic heuristic) {
		this.heuristic=heuristic;
	}
	@Override
	public int compare(State state1, State state2) {
		double state1val=state1.getCost();
		double state2val=state2.getCost();
		if(heuristic==null)
			return (int) (state1val-state2val);
		Double state1Est=heuristic.hCalc(state1);
		Double state2Est=heuristic.hCalc(state2);
		if(state1Est==null || state2Est==null)
			return (int) (state1val-state2val);
		else
			return (int) (state1val+state1Est - state2val - state2Est);
	}

}
