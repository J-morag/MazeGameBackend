package algorithms.search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

public class BestFirstSearch extends BreadthFirstSearch  {

    public BestFirstSearch() {
        super();
        this.greyVerticesQueue = new PriorityQueue<>();
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
