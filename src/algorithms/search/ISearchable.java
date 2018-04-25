package algorithms.search;

import java.util.ArrayList;

interface ISearchable {

    AState getStartState();

    AState getGoalState();

    ArrayList<AState> getAllPossibleStates();

}
