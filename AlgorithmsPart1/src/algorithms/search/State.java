package algorithms.search;

/** class for a generic State in Search Problems - consists of a string describing the state, the cost to get to that state, and the parent state
 * notice the implementation of Comparable. Until now it wasn't useful but it may be in the future, for other search algorithms.
 * @author Tomer Cabouly
 *
 */
public class State implements Comparable<State> {
    private String state;    // the state represented by a string
    private Double cost;     // cost to reach this state
    private State cameFrom;  // the state we came from to this state
    public State(String state){    // CTOR    
        this.state = state;
    }
    public State(String state,Double cost)
    {
    	this.state=state;
    	this.cost=cost;
    }
    public State(String state,Double cost,State cameFrom)
    {
    	this.state=state;
    	this.cost=cost;
    	this.cameFrom=cameFrom;
    }
    @Override
    public boolean equals(Object obj){ // we override Object's equals method
        return state.equals(((State)obj).state);
    } 
   // ...

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

	public State getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public int compareTo(State state) {
		return (int)(this.cost-state.cost);
	}
}
