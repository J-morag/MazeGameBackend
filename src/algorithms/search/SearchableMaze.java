package algorithms.search;

import algorithms.mazeGenerators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SearchableMaze implements ISearchable{

    private Maze maze;
    private MazeState startState;
    private MazeState goalState;
    private double heuristicDistance;
    private ArrayList<MazeState> allPossibleStates;
    private MazeState[][] mazeStatesMap;

    /**
     * Constructor
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.heuristicDistance = 0.0;
        this.allPossibleStates = new ArrayList<MazeState>();
        this.mazeStatesMap = new MazeState[maze.getMazeMap().length][(maze.getMazeMap()[0]).length];
        //initialize start and goal states
        Position startPosition = maze.getStartPosition();
        double distanceToGoal = getHeuristicDistance(startPosition.getRowIndex(),startPosition.getColomnIndex());
        this.startState = new MazeState(distanceToGoal,startPosition);
        this.goalState = new MazeState(0.0,maze.getGoalPosition());
    }

    /**
     * Creates states for each passage in the maze, and update it's successors
     * @return a list of all possible maze states
     */
    public ArrayList<MazeState> getAllPossibleStates(){
        int[][] mazeMap = maze.getMazeMap();

        //create MazeStates for all the passages in the maze
        for(int row=0; row < mazeMap.length; row++){
            for(int col=0; col < mazeMap[0].length; col++){
                if(mazeMap[row][col] == 0) { //a passage
                    MazeState mState = new MazeState(getHeuristicDistance(row,col), new Position(row,col));
                    allPossibleStates.add(mState);
                    mazeStatesMap[row][col] = mState;
                }
            }
        }

        //update successors for all the MazeStates
        updateSuccessors(mazeMap);
        return allPossibleStates;
    }

    /**
     * @return the maze state of the start position in the maze
     */
    @Override
    public MazeState getStartState() {
        return startState;
    }

    /**
     * @return the maze state of the goal position in the maze
     */
    @Override
    public MazeState getGoalState() {
        return goalState;
    }

    /**
     * Calculates the euclidean distance from a given state to the goal state
     * @param rowPosition - row coordinate of the position of the given state
     * @param colPosition - column coordinate of the position of the given state
     * @return the heuristic distance from start state to goal state
     */
    private double getHeuristicDistance(int rowPosition, int colPosition){

        Position goalPosition = maze.getGoalPosition();

        int deltaRow = goalPosition.getRowIndex() - rowPosition;
        int deltaCol = goalPosition.getColomnIndex() - colPosition;

        heuristicDistance = Math.sqrt(Math.pow(deltaRow,2) + Math.pow(deltaCol,2));
        return heuristicDistance;
    }

    //update successors for all the MazeStates
    private void updateSuccessors (int[][] mazeMap) {
        for (MazeState mState : allPossibleStates) {
            Position statePosition = mState.getPosition();
            int rowPosition = statePosition.getRowIndex();
            int colPosition = statePosition.getColomnIndex();

            //upper state
            if (mazeMap[rowPosition - 1][colPosition] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition - 1][colPosition]);
            //upper right diagonal
            if (mazeMap[rowPosition - 1][colPosition + 1] == 0) {
                if (mazeMap[rowPosition - 1][colPosition] == 0 || mazeMap[rowPosition][colPosition + 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition - 1][colPosition + 1]);
            }
            //right cell
            if (mazeMap[rowPosition][colPosition + 1] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition][colPosition + 1]);
            //lower right diagonal
            if (mazeMap[rowPosition + 1][colPosition + 1] == 0) {
                if (mazeMap[rowPosition + 1][colPosition] == 0 || mazeMap[rowPosition][colPosition + 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition + 1][colPosition + 1]);
            }
            //lower cell
            if (mazeMap[rowPosition + 1][colPosition] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition + 1][colPosition]);
            //lower left diagonal
            if (mazeMap[rowPosition + 1][colPosition - 1] == 0) {
                if (mazeMap[rowPosition + 1][colPosition] == 0 || mazeMap[rowPosition][colPosition - 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition + 1][colPosition - 1]);
            }
            //left cell
            if (mazeMap[rowPosition][colPosition - 1] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition][colPosition - 1]);
            //upper left diagonal
            if (mazeMap[rowPosition - 1][colPosition - 1] == 0) {
                if (mazeMap[rowPosition - 1][colPosition] == 0 || mazeMap[rowPosition][colPosition - 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition - 1][colPosition - 1]);
            }
        }
    }
}
