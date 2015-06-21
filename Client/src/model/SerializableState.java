package model;

import java.io.Serializable;

import algorithms.search.State;

/**For Hibernate - we've rewritten State so it will be Serializable. for JavaDoc check out original State.
 * @author Tomer
 *
 */
public class SerializableState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;    // the state represented by a string
    private Double cost;     // cost to reach this state
    private SerializableState cameFrom;  // the state we came from to this state
    public SerializableState() {
	}
    public SerializableState(State s)
    {
    	if(s!=null)
    	{
    		this.state=s.getState();
    		this.cost=s.getCost();
    		this.cameFrom=new SerializableState(s.getCameFrom());
    	}
    }
    public State getOriginalState()
    {
    	if(this.cameFrom!=null)
    		return new State(this.state,this.cost,this.cameFrom.getOriginalState());
    	else
    		return new State(this.state,this.cost);
    }
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public SerializableState getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(SerializableState cameFrom) {
		this.cameFrom = cameFrom;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
