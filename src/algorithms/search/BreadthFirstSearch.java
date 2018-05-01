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
     * @param whiteVertices - all vertices before start of search.
     * @param pi - records which vertex was discovered by which.
     * @return - the goal position if found. Else, null.
     */
    @Override
    protected AState runAlgorithm(AState startState, AState goalState, Set<AState> whiteVertices, HashMap<AState, AState> pi, HashMap<AState, Integer> distance){
        //BFS initialization
        whiteVertices.remove(startState);
        greyVerticesQueue.add(startState);
        pi.put(startState, null);
        //progress
        while(!greyVerticesQueue.isEmpty()){
            AState u = greyVerticesQueue.poll();
            if(debug) System.out.println(u.toString());
            for (AState v:
                    u.getSuccessors()) {
                if(whiteVertices.contains(v)){
                    numberOfNodesEvaluated++;
                    pi.put(v, u);
                    if(v.equals(goalState)) return v;
                    whiteVertices.remove(v);
                    greyVerticesQueue.add(v);
                }
            }
        }
        return null;
    }
}
