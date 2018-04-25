package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    Queue<AState> greyVertices;

    public BreadthFirstSearch() {
        super();
        Queue<AState> greyVertices = new LinkedList<AState>();
    }

    @Override
    public Solution solve(ISearchable searchProblem) {
        Solution solution = new Solution();
        AState startState = searchProblem.getStartState();
        AState goalState = searchProblem.getGoalState();
        HashMap<AState, Boolean> blackVertices =
                new HashMap<AState, Boolean>((int)(searchProblem.getAllPossibleStates().size()/0.75) +1 );
                                                                    //(max number of entries divided by load factor) +1

        // TODO insert algorithm!





        return null;
    }
}
