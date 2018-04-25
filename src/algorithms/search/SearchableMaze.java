package algorithms.search;

import algorithms.mazeGenerators.*;
import java.util.List;

public class SearchableMaze implements ISearchable{

    private Maze maze;
    private MazeState startState;
    private MazeState goalState;
    private double heuristicDistance;

    public SearchableMaze(Maze maze, MazeState startState, MazeState goalState) {
        this.maze = maze;
        this.startState = startState;
        this.goalState = goalState;
    }

    // TODO not implemented
    public List<AState> getAllPossibleStates(){
        return null;
    }

    // TODO not implemented
    @Override
    public AState getStartState() {
        return startState;
    }

    // TODO not implemented
    @Override
    public AState getGoalState() {
        return goalState;
    }
}
