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
     * @param pi - records which vertex was discovered by which.
     * @return - the goal position if found. Else, null.
     */
    @Override
    protected AState runAlgorithm(AState startState, AState goalState, Set<AState> visitedVertices, HashMap<AState, AState> pi, HashMap<AState, Integer> distance){
        AState u;
        neighborsStack.push(startState);
        pi.put(startState,null);
        while (!neighborsStack.isEmpty()){
            u = neighborsStack.pop(); //O(1)
            if(!visitedVertices.contains(u)){ //O(1)
//                System.out.println(u);  //debug
                numberOfNodesEvaluated++;
                if (u.equals(goalState)) return u;
                visitedVertices.add(u); //O(1)
                List<AState> neighbors = u.getSuccessors();
                for (int i = neighbors.size()-1; i >= 0 ; i--) {
                    AState v = neighbors.get(i); //O(1)
                    if(!visitedVertices.contains(v)){ //O(1)
                        neighborsStack.push(v); //O(1)
                        pi.put(v, u); //O(1)
                    }
                }
            }
        }
        return null;

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
