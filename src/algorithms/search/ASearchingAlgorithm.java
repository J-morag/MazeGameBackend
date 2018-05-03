package algorithms.search;

import java.util.*;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int numberOfNodesEvaluated;
    protected  Set<AState> visitedVertices; //all vertices before start of search

    public ASearchingAlgorithm() {
        this.numberOfNodesEvaluated = 0;
        this.visitedVertices = new HashSet<>((int)(600000/0.75 +1));
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

        //fill solution
        AState lastStep = runAlgorithm(searchProblem, startState, goalState);
        if(null != lastStep){
            AState stepInSolution = lastStep;
            while(null != stepInSolution){
                solution.add(0, stepInSolution);
                stepInSolution = stepInSolution.getParent();
            }
            return solution;
        }
        else return null;
    }

    protected abstract AState runAlgorithm(ISearchable searchProblem, AState startState, AState goalState);
}
