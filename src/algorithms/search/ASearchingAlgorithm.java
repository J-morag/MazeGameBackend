package algorithms.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int numberOfNodesEvaluated;

    public ASearchingAlgorithm() {
        this.numberOfNodesEvaluated = 0;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }

    public Solution solve(ISearchable searchProblem) {
        numberOfNodesEvaluated = 0;
        Solution solution = new Solution();
        AState startState = searchProblem.getStartState();
        AState goalState = searchProblem.getGoalState();
//      List<AState> vertices = searchProblem.getAllPossibleStates();
        Set<AState> visitedVertices = new HashSet<>((int)(vertices.size()/0.75) +1);
//        HashMap<AState, AState> pi = new HashMap<AState, AState>((int)(vertices.size()/0.75) +1 );
        HashMap<AState, Integer> distance = new HashMap<AState, Integer>((int)(vertices.size()/0.75) +1 );
        distance.put(startState, 0);

        //fill solution
        if(null != runAlgorithm(searchProblem, startState, goalState, visitedVertices, distance)){
            AState stepInSolution = goalState;
            while(null != stepInSolution){
                solution.add(0, stepInSolution);
                stepInSolution = pi.get(stepInSolution);
            }
            return solution;
        }
        else return null;
    }

    protected abstract AState runAlgorithm(ISearchable searchProblem, AState startState, AState goalState, Set<AState> visitedVertices, HashMap<AState, Integer> distance);
}
