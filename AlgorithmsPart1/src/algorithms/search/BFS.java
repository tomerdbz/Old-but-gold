package algorithms.search;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
/** The BFS search algorithm class!
 * @author Tomer Cabouly
 *
 */
public class BFS extends CommonSearcher {
	
	/** The BFS algorithm
	 * @return Solution - contains the path from the Source state to the Goal state and its cost
	 */
	@Override
	public Solution search(Searchable s) {
		  addToOpenList(s.getStartState());
		  HashSet<State> closedSet=new HashSet<State>();

		  while(getOpenList().size()>0){
		    State n=popOpenList();// dequeue
		    closedSet.add(n);

		    if(n.equals(s.getGoalState()))
		    {
		    	return backTrace(n, s.getStartState());// private method, back traces through the parents

		    }

		    ArrayList<State> successors=s.getAllPossibleStates(n); 
		    for(State state : successors){
		    	if(!closedSet.contains(state) && ! containedInOpenList(state))
		    	{
		    		state.setCameFrom(n);
		    		addToOpenList(state);
		    	} 
		    	else
		    	{
		    		double pathCost=oldPathPrice(getOpenList(),state);
		    		if(state.getCost() < pathCost)
		    		{
		    			if(containedInOpenList(state))
		    			{
		    				removeFromOpenList(state);
		    				state.setCost(state.getCost());
		    				addToOpenList(state);
		    			}
		    			else
		    			{
		    				state.setCost(state.getCost());
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
		private double oldPathPrice(PriorityQueue<State> openList,State state)
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
	}
