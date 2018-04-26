package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    Queue<AState> greyVerticesQueue;

    public BreadthFirstSearch() {
        super();
        Queue<AState> greyVertices = new LinkedList<AState>();
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    @Override
    public Solution solve(ISearchable searchProblem) {
        Solution solution = new Solution();
        AState startState = searchProblem.getStartState();
        AState goalState = searchProblem.getGoalState();
        List<AState> vertices = searchProblem.getAllPossibleStates();
        Set<AState> whiteVertices = new HashSet<>((int)(vertices.size()/0.75) +1);
                                                    //(max number of entries divided by load factor) +1
        whiteVertices.addAll(vertices);
        HashMap<AState, AState> pi = new HashMap<AState, AState>((int)(vertices.size()/0.75) +1 );

        //fill solution
        if(null != runBFS(startState, goalState, whiteVertices, pi)){
            AState stepInSolution = goalState;
            while(null != stepInSolution){
                solution.add(0, stepInSolution);
                stepInSolution = pi.get(stepInSolution);
            }
            return solution;
        }
        else return null;
    }

    /**
     * run bfs search on graph.
     * @param startState
     * @param goalState
     * @param whiteVertices - all vertices before start of search.
     * @param pi - records which vertex was discovered by which.
     * @return - the goal position if found. Else, null.
     */
    private AState runBFS(AState startState, AState goalState, Set<AState> whiteVertices, HashMap<AState, AState> pi){
        //BFS initialization
        whiteVertices.remove(startState);
        greyVerticesQueue.add(startState);
        pi.put(startState, null);
        //progress
        while(!greyVerticesQueue.isEmpty()){
            AState u = greyVerticesQueue.poll();
            numberOfNodesEvaluated++; // TODO should this be incremented here, or when a vertex is discovered?
            for (AState v:
                    u.getSuccessors()) {
                if(whiteVertices.contains(v)){
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
