package algorithms.search;

public interface ISearchable {

    /**
     * @return the state of the start position
     */
    public AState getStartState();

    /**
     * @return the state of the goal position
     */
    public AState getGoalState();

}
