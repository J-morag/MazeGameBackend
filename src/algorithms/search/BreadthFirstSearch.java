package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    boolean debug = false;
    Queue<AState> greyVerticesQueue;

    public BreadthFirstSearch() {
        super();
        greyVerticesQueue = new LinkedList<AState>();
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    /**
     * run bfs search on graph.
     * @param startState
     * @param goalState
     * @param visitedVertices - all vertices that were visited already.
     * @param pi - records which vertex was discovered by which.
     * @return - the goal position if found. Else, null.
     */
    @Override
    protected AState runAlgorithm(ISearchable searchProblem, AState startState, AState goalState, Set<AState> visitedVertices, HashMap<AState, Integer> distance){
        //BFS initialization
        visitedVertices.add(startState);
        greyVerticesQueue.add(startState);
        numberOfNodesEvaluated++;
        //progress
        while(!greyVerticesQueue.isEmpty()){
            AState u = greyVerticesQueue.poll();
            if(debug) System.out.println(u.toString());
            for (AState v:
                    searchProblem.getAllPossibleStates(u)) {
                if(!visitedVertices.contains(v)){
                    numberOfNodesEvaluated++;
                    v.parent = u;
                    if(v.equals(goalState)) return v;
                    visitedVertices.add(v);
                    greyVerticesQueue.add(v);
                }
            }
        }
        return null;
    }
}
