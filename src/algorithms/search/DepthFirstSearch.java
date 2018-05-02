package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    Deque<AState> neighborsStack;

    public DepthFirstSearch() {
        super();
        neighborsStack = new LinkedList<>();
    }

    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    /**
     * run DFS search on graph.
     * @param startState
     * @param goalState
     * @param visitedVertices - all vertices before start of search.
     * @return - the goal position if found. Else, null.
     */
    @Override
    protected AState runAlgorithm(ISearchable searchProblem, AState startState, AState goalState, Set<AState> visitedVertices){
        AState u;
        neighborsStack.push(startState);
        numberOfNodesEvaluated++;
        while (!neighborsStack.isEmpty()){
            u = neighborsStack.pop(); //O(1)
            if(!visitedVertices.contains(u)){ //O(1)
//                System.out.println(u);  //debug
                numberOfNodesEvaluated++;
                if (u.equals(goalState)) return u;
                visitedVertices.add(u); //O(1)
                List<AState> neighbors = searchProblem.getAllPossibleStates(u);
                for (int i = neighbors.size()-1; i >= 0 ; i--) {
                    AState v = neighbors.get(i); //O(1)
                    if(!visitedVertices.contains(v)){ //O(1)
                        neighborsStack.push(v); //O(1)
                        v.parent = u; //O(1)
                    }
                }
            }
        }
        return null;

    }
}
