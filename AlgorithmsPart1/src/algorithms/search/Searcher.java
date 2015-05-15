package algorithms.search;

/** this interface defines what every Searcher has to do - search and know how many Nodes it has evaluated.
 * @author Tomer Cabouly
 *
 */
public interface Searcher {
    // the search method
    public Solution search(Searchable s);
    // get how many nodes were evaluated by the algorithm
    public int getNumberOfNodesEvaluated();
}
