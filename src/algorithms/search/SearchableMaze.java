package algorithms.search;

import algorithms.mazeGenerators.*;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{

    private boolean debug = false;
    private Maze maze;
    private MazeState startState;
    private MazeState goalState;
    //private double heuristicDistance;
    private ArrayList<AState> allPossibleStates;
    private MazeState[][] mazeStatesMap;

    /**
     * Constructor
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
        //this.heuristicDistance = 0.0;
        this.allPossibleStates = new ArrayList<AState>();
        this.mazeStatesMap = new MazeState[maze.getMazeMap().length][(maze.getMazeMap()[0]).length];
        //initialize start and goal states
        Position startPosition = maze.getStartPosition();
        double distanceToGoal = getHeuristicDistance(startPosition.getRowIndex(),startPosition.getColumnIndex());
        this.startState = new MazeState(distanceToGoal,startPosition);
        this.goalState = new MazeState(0.0,maze.getGoalPosition());
        //put start and goal states on the map, and in the list
        mazeStatesMap[startPosition.getRowIndex()][startPosition.getColumnIndex()] = this.startState;
        mazeStatesMap[maze.getGoalPosition().getRowIndex()][maze.getGoalPosition().getColumnIndex()] = this.goalState;
        allPossibleStates.add(this.startState);
        allPossibleStates.add(this.goalState);
    }

    /**
     * Creates states for each passage in the maze, and update it's successors
     * @return a list of all possible maze states
     */
    public ArrayList<AState> getAllPossibleStates(){
        int[][] mazeMap = maze.getMazeMap();

        //create MazeStates for all the passages in the maze
        for(int row=0; row < mazeMap.length; row++){
            for(int col=0; col < mazeMap[0].length; col++){
                if(mazeMap[row][col] == 0 && null == mazeStatesMap[row][col]) { //a passage and not the start or goal states
                    AState mState = new MazeState(getHeuristicDistance(row, col), new Position(row, col));
                    allPossibleStates.add(mState);
                    mazeStatesMap[row][col] = (MazeState)mState;
                }
            }
        }

        //update successors for all the MazeStates
        updateSuccessors(mazeMap);
        if(debug) System.out.println("finished creating states");
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

//    /**
//     * Calculates the euclidean distance from a given state to the goal state
//     * @param rowPosition - row coordinate of the position of the given state
//     * @param colPosition - column coordinate of the position of the given state
//     * @return the heuristic distance from start state to goal state
//     */
//    private double getHeuristicDistance(int rowPosition, int colPosition){
//
//        Position goalPosition = maze.getGoalPosition();
//
//        int deltaRow = goalPosition.getRowIndex() - rowPosition;
//        int deltaCol = goalPosition.getColumnIndex() - colPosition;
//
//        return Math.sqrt(Math.pow(deltaRow,2) + Math.pow(deltaCol,2));
//    }

    /**
     * Prefer diagonals
     * @param rowPosition - row coordinate of the position of the given state
     * @param colPosition - column coordinate of the position of the given state
     * @return the heuristic distance from start state to goal state
     */
    private double getHeuristicDistance(int rowPosition, int colPosition){

        Position goalPosition = maze.getGoalPosition();

        int deltaRow = goalPosition.getRowIndex() - rowPosition;
        int deltaCol = goalPosition.getColumnIndex() - colPosition;

        return Math.sqrt(Math.pow(deltaRow,2) + Math.pow(deltaCol,2));
    }

    //update successors for all the MazeStates
    private void updateSuccessors (int[][] mazeMap) {
        for (AState mState : allPossibleStates) {
            Position statePosition = ((MazeState)mState).getPosition();
            int rowPosition = statePosition.getRowIndex();
            int colPosition = statePosition.getColumnIndex();

            //upper state
            if (isValid(mazeMap,rowPosition - 1,colPosition)&& mazeMap[rowPosition - 1][colPosition] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition - 1][colPosition]);
            //upper right diagonal
            if (isValid(mazeMap,rowPosition - 1,colPosition+1)&& mazeMap[rowPosition - 1][colPosition + 1] == 0) {
                if (isValid(mazeMap,rowPosition - 1,colPosition)&& mazeMap[rowPosition - 1][colPosition] == 0 ||
                        isValid(mazeMap,rowPosition,colPosition+1)&& mazeMap[rowPosition][colPosition + 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition - 1][colPosition + 1]);
            }
            //right cell
            if (isValid(mazeMap,rowPosition,colPosition+1)&&mazeMap[rowPosition][colPosition + 1] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition][colPosition + 1]);
            //lower right diagonal
            if (isValid(mazeMap,rowPosition+1,colPosition+1) && mazeMap[rowPosition + 1][colPosition + 1] == 0) {
                if (isValid(mazeMap,rowPosition+1,colPosition)&& mazeMap[rowPosition + 1][colPosition] == 0 ||
                        isValid(mazeMap,rowPosition,colPosition+1)&& mazeMap[rowPosition][colPosition + 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition + 1][colPosition + 1]);
            }
            //lower cell
            if (isValid(mazeMap,rowPosition+1,colPosition)&& mazeMap[rowPosition + 1][colPosition] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition + 1][colPosition]);
            //lower left diagonal
            if (isValid(mazeMap,rowPosition+1,colPosition-1)&& mazeMap[rowPosition + 1][colPosition - 1] == 0) {
                if (isValid(mazeMap,rowPosition+1,colPosition)&& mazeMap[rowPosition + 1][colPosition] == 0 ||
                        isValid(mazeMap,rowPosition,colPosition-1)&& mazeMap[rowPosition][colPosition - 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition + 1][colPosition - 1]);
            }
            //left cell
            if (isValid(mazeMap,rowPosition,colPosition-1)&& mazeMap[rowPosition][colPosition - 1] == 0)
                mState.getSuccessors().add(mazeStatesMap[rowPosition][colPosition - 1]);
            //upper left diagonal
            if (isValid(mazeMap,rowPosition - 1,colPosition-1)&& mazeMap[rowPosition - 1][colPosition - 1] == 0) {
                if (isValid(mazeMap,rowPosition - 1,colPosition)&& mazeMap[rowPosition - 1][colPosition] == 0 ||
                        isValid(mazeMap,rowPosition,colPosition-1)&& mazeMap[rowPosition][colPosition - 1] == 0)
                    mState.getSuccessors().add(mazeStatesMap[rowPosition - 1][colPosition - 1]);
            }
        }
    }

    private boolean isValid (int[][] mazeMap, int row, int column){
        if (row < 0 || row >= mazeMap.length || column < 0 || column >= mazeMap[0].length)
            return false;
        else return true;
    }

    @Override
    public String toString() {
        return maze.toString();
    }

//    public ArrayList<AState> getSuccessors(AState parent)
//    {
//        ArrayList<AState> sons = parent.getSuccessors();
//        for (AState son:
//             sons) {
//            if
//        }
//    }
}
