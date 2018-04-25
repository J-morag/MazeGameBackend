package algorithms.search;

interface ISearchingAlgorithm {
    Solution solve(ISearchable searchProblem);
    int getNumberOfNodesEvaluated();
}
