package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/** The A* Search algorithm class!
 * it has a ct'r that gets a Heuristic.
 *	and of course the search method where all the magic happens.
 * @author Tomer Cabouly
 *
 */
public class AStar extends CommonSearcher {
	private Heuristic h;
	public AStar(Heuristic h) {
		super(new StateHeuristicComparator(h));
		this.h=h;
	}

	

	/**the A* algorithm
	 * @return Solution - contains the path from the Source state to the Goal state and its cost
	 */
	@Override
	public Solution search(Searchable s) {
		if(s==null || s.getStartState()==null || s.getGoalState()==null)
			return null;
		addToOpenList(s.getStartState());
		  HashSet<State> closedSet=new HashSet<State>();

		  s.getStartState().setCost(0.0);
		  
		  while(getOpenList().size()>0){
			  State n=popOpenList();// dequeue
		    
		    if(n.equals(s.getGoalState()))
			      return backTrace(n, s.getStartState()); // private method, back traces through the parents
		    
		    closedSet.add(n);
		     
		      

		    ArrayList<State> successors=s.getAllPossibleStates(n); //brings successors 
		    for(State state : successors){
		    	if(closedSet.contains(state)) //if you already got to that state -  greedy
		    	{
		    		continue;
		    	}
		        else
		        {
		    	  double g=state.getCost();
		    	  if(!containedInOpenList(state) || oldPathPrice(getOpenList(),state) > g)
		    		  {
		    		  	  state.setCameFrom(n);
		    		  	  state.setCost(g);
		    			  if(!containedInOpenList(state))
		    			  {
		    				  removeFromOpenList(state);
		    				  addToOpenList(state);
		    			  }
		    		  }
		        }	

		      }
		    }
		return null;
	}
	
	/** an helping method for the search. here we calculate the old price to get to a certain state.
	 *  so we could compare it with the new one in search()
	 * @param openList
	 * @param state
	 * @return the old path price
	 */
	private  double oldPathPrice(PriorityQueue<State> openList,State state)
	{
			if(openList.contains(state))
			{
			int i=0;
			State[] tempList=new State[10];
			tempList=openList.toArray(tempList);
			while(true)
			{
				if(state.equals(tempList[i]))
				{
					return tempList[i].getCost();
				}
				i++;
			}
			}
			else
				return 0;
			
	}



	public Heuristic getH() {
		return h;
	}



	/** while updating the class Heuristic, there's a need to build a new Priority Queue. 
	 * Priorities are different now...
	 * @param heuristic
	 */
	public void setH(Heuristic h) {
		this.h = h;
		setOpenList(new PriorityQueue<State>(new StateHeuristicComparator(h)));
	}
}
