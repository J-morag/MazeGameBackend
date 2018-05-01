package algorithms.search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

public class BestFirstSearch extends BreadthFirstSearch  {

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    @Override
    protected AState runAlgorithm(AState startState, AState goalState, Set<AState> whiteVertices, HashMap<AState, AState> pi, HashMap<AState, Integer> distance) {
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
                    distance.put(v, distance.get(u) + 1);
                    if(v.equals(goalState)) return v;
                    whiteVertices.remove(v);
                    greyVerticesQueue.add(v);
                }
                else if (distance.get(u) + 1 < distance.get(v)){
                    distance.put(v, distance.get(u) + 1);
                    pi.put(v, u);
                }
            }
        }
        return null;
    }
}
