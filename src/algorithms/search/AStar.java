package algorithms.search;

import java.util.PriorityQueue;

public class AStar extends BreadthFirstSearch {

    @Override
    public String getName() {
        return "AStar";
    }

    /**
     * // replaces the queue with a priority queue. giving lower heuristic distance a higher priority.
     */
    public AStar() {
        super();
        this.greyVerticesQueue = new PriorityQueue<>();
    }
}
