package algorithms.search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A searching algorithm. Like BreadthFirstSearch , but with priority queue (according to the cost).
 */
public class BestFirstSearch extends BreadthFirstSearch  {

    public BestFirstSearch() {
        super();
        this.greyVerticesQueue = new PriorityQueue<>();
    }

    /**
     * returns the name of the algorithm.
     */
    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
