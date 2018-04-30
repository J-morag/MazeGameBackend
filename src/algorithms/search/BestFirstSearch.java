package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch  {

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    /**
     * // replaces the queue with a priority queue. giving lower heuristic distance a higher priority.
     */
    public BestFirstSearch() {
        super();
        this.greyVerticesQueue = new PriorityQueue<>();
    }
}
