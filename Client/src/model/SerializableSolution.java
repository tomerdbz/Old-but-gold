package model;

import java.io.Serializable;
import java.util.ArrayList;

import algorithms.search.Solution;
import algorithms.search.State;

/**For Hibernate - we've rewritten Solution so it will be Serializable. for JavaDoc check out original Solution.
 * @author Tomer
 *
 */
public class SerializableSolution implements Serializable {
	private ArrayList<SerializableState> path;
	private double pathCost;
	public SerializableSolution(ArrayList<State> path, double pathCost) {
		this.pathCost=pathCost;
		this.path=new ArrayList<SerializableState>();
		for(State state: path)
			this.path.add(new SerializableState(state));
	}
	public SerializableSolution() {
		// TODO Auto-generated constructor stub
	}
	public SerializableSolution(Solution s) {
		pathCost=s.getPathCost();
		path=new ArrayList<SerializableState>();
		for(State state: s.getPath())
			path.add(new SerializableState(state));
	}
	public Solution getOriginalSolution()
	{
		ArrayList<State> send=new ArrayList<State>();
		for(SerializableState state : path)
			send.add(state.getOriginalState());
		return new Solution(send,pathCost);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ArrayList<SerializableState> getPath() {
		return path;
	}
	public void setPath(ArrayList<SerializableState> path) {
		this.path = path;
	}
	public double getPathCost() {
		return pathCost;
	}
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}

}
