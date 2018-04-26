package algorithms.search;

import java.util.ArrayList;

interface ISearchable {

    /**
     * @return the state of the start position
     */
    AState getStartState();


    /**
     * @return the state of the goal position
     */
    AState getGoalState();

    ArrayList<AState> getAllPossibleStates();

}
