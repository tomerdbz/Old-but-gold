package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/** This class has common fields for searchers. a PriorityQueue and number of evaluated nodes.
 * 
 * notice the need of a Comparator in ct'r- whether it's as an argument or not. that's how the priorities are managed.
 * @author Tomer Cabouly
 *
 */
public abstract class CommonSearcher implements Searcher {

	 private PriorityQueue<State> openList;
	 private int evaluatedNodes;
	 public CommonSearcher() {
		 openList=new PriorityQueue<State>(new StateComparator());
		 evaluatedNodes=0;
	 }
	 public CommonSearcher(StateComparator comp)
	 {
		 openList=new PriorityQueue<State>(comp);
		  evaluatedNodes=0;
	 }

	 public State popOpenList() {
	  evaluatedNodes++;
	  return openList.poll();
	 }
	 @Override
	 public abstract Solution search(Searchable s);

	 @Override
	 public int getNumberOfNodesEvaluated() {
	  return evaluatedNodes;
	 }
	 public boolean addToOpenList(State state)
	 {
		 	return openList.add(state);
	 }
	 public boolean containedInOpenList(State state)
	 {
		 return openList.contains(state);
	 }
	 public boolean removeFromOpenList(State state)
	 {
		 return openList.remove(state);
	 }
	 protected Solution backTrace(State goal,State source)
	 {		 
		 return new Solution(getPath(goal,source),goal.getCost());//getOverallCost(goal,source));
	 }

	 public ArrayList<State> getPath(State goal,State source)
	 {
		 Stack<State> stack=new Stack<State>();
		 boolean flag=false;
		 while(!flag)
		 {
			 if(goal.equals(source))
				 break;
			 stack.push(goal);
			 goal=goal.getCameFrom();
		 }
		 flag=false;
		 ArrayList<State> list=new ArrayList<State>();
		 list.add(source);
		 while(!flag)
		 {
			 list.add(stack.pop());
			 flag=stack.isEmpty();
		 }
		 return list;
	 }
	 public PriorityQueue<State> getOpenList()
	 {
		 return openList;
	 }
	 public void setOpenList(PriorityQueue<State> openList)
	 {
		 this.openList=openList;
	 }
	 
}

