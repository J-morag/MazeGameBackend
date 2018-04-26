package algorithms.search;

public interface ISearchingAlgorithm {
    Solution solve(ISearchable searchProblem);
    int getNumberOfNodesEvaluated();

    String getName();
}
