package algorithms.search;

import java.util.ArrayList;

/**This class defines Solution for search algorithms - path as an ArrayList of States and path cost
 * @author Tomer Cabouly
 *
 */
public class Solution {
	private ArrayList<State> path;
	private double pathCost;
	public Solution(ArrayList<State> path,double pathCost){//, double d) {
		// TODO Auto-generated constructor stub
		this.path=path;
		this.pathCost=pathCost;
	}
	public Solution() {
	}
	public ArrayList<State> getPath() {
		return path;
	}
	public void setPath(ArrayList<State> path) {
		this.path = path;
	}
	public double getPathCost() {
		return pathCost;
	}
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}
	@Override
	public String toString() {
		String str;
		str="path is: ";
		for(State s:path)
		{
			str=str+s.getState()+"\t";
		}
		str=str+"The Cost:"+this.pathCost+"\n";
		return str;
	}
	@Override
	public boolean equals(Object arg0) {
		Solution sol2=(Solution) arg0;
		if(this.pathCost!=sol2.pathCost)
			return false;
		for(State s: path)
			if(!sol2.path.contains(s))
				return false;
		return true;
	}
	
}
