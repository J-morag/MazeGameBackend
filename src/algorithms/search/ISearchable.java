package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {

    /**
     * @return the state of the start position
     */
    AState getStartState();


    /**
     * @return the state of the goal position
     */
    AState getGoalState();

    public ArrayList<AState> getAllPossibleStates();

}
