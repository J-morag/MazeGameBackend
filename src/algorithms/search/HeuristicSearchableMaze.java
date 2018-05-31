package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public class HeuristicSearchableMaze extends SearchableMaze{
    public HeuristicSearchableMaze(Maze maze) {
        super(maze);
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState mState) {
        ArrayList<AState> states =  super.getAllPossibleStates(mState);
        for (AState state:
             states) {
            state.cost = distanceToGoal((MazeState)state);
        }
        return states;
    }

    private double distanceToGoal(MazeState state) {
        double deltaX = Math.abs(state.getPosition().getColumnIndex() - goalState.getPosition().getColumnIndex());
        double deltaY = Math.abs(state.getPosition().getRowIndex() - goalState.getPosition().getRowIndex());
        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }
}
