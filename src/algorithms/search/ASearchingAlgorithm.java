package algorithms.search;

import java.util.*;

/**
 * An abstract class of searching algorithm
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int numberOfNodesEvaluated;
    protected  Set<AState> visitedVertices; //all vertices before start of search

    /**
     * Constructor
     */
    public ASearchingAlgorithm() {
        this.numberOfNodesEvaluated = 0;
        this.visitedVertices = new HashSet<>((int)(600000/0.75 +1));
    }

    /**
     * @return how many nodes the algorithm evaluates
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }

    /**
     * @param searchProblem
     * @return the solution path on the maze
     */
    public Solution solve(ISearchable searchProblem) {
        if(searchProblem == null)
            return null;
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
        }
        return solution;
    }

    /**
     * Helper method - called by 'solve', and implemented in each kind of searching algorithms
     * @return the last state in the solution
     */
    protected abstract AState runAlgorithm(ISearchable searchProblem, AState startState, AState goalState);
}
