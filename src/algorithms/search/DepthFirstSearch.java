package algorithms.search;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    Stack<AState> neighborsStack;

    public DepthFirstSearch() {
        super();
        neighborsStack = new Stack<>();
    }

    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    /**
     * run DFS search on graph.
     * @param startState
     * @param goalState
     * @param whiteVertices - all vertices before start of search.
     * @param pi - records which vertex was discovered by which.
     * @return - the goal position if found. Else, null.
     */
    @Override
    protected AState runAlgorithm(AState startState, AState goalState, Set<AState> whiteVertices, HashMap<AState, AState> pi, HashMap<AState, Integer> distance){
        neighborsStack.setSize(whiteVertices.size());
        //DFS initialization
        whiteVertices.remove(startState);
        pi.put(startState, null);
        numberOfNodesEvaluated++;
        //progress
        AState u = startState;
        while (!u.equals(goalState)){
            List<AState> neighbors = u.getSuccessors();
            for (int i = neighbors.size()-1; i >= 0 ; i--) {
                AState v = neighbors.get(i);
                if(whiteVertices.contains(v)){
                    neighborsStack.push(v);
                }
            }
            AState v = neighborsStack.pop();
            if(whiteVertices.contains(v)){
                numberOfNodesEvaluated++;
                whiteVertices.remove(v);
                pi.put(v, u);
                u = v;
            }
        }
        return u;

    }

    /**
     * recursive function for visiting a vertex in DFS.
     * @param u - current vertex
     * @param goalState - the goal state.
     * @param whiteVertices - set of all white (undiscovered vertices).
     * @param pi - maps parents.
     * @return the goal state if found, Else null.
     */
    private AState DFSVisit(AState u, AState goalState, Set<AState> whiteVertices, HashMap<AState, AState> pi){
        for (AState v:
                u.getSuccessors()) {
            if(whiteVertices.contains(v)){
                numberOfNodesEvaluated++;
                whiteVertices.remove(v);
                pi.put(u, v);
                if(v.equals(goalState)) return v;
                else {
                    AState searchBranchResult = DFSVisit(v, goalState, whiteVertices, pi);
                    if (null != searchBranchResult) return searchBranchResult;
                }
            }
        }
        return null;
    }
}
