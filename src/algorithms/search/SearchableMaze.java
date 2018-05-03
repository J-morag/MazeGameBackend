package algorithms.search;

import algorithms.mazeGenerators.*;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{

    private boolean debug = false;
    private Maze maze;
    private MazeState startState;
    private MazeState goalState;

    /**
     * Constructor
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;

        //initialize start and goal states
        this.startState = new MazeState(0.0 ,maze.getStartPosition());
        this.goalState = new MazeState(0.0 ,maze.getGoalPosition());
    }

    /**
     * Creates states for each passage in the maze, and update it's successors
     * @return a list of all possible maze states
     */
    public ArrayList<AState> getAllPossibleStates(AState mState){
        ArrayList<AState> allPossibleStates = new ArrayList<AState>();
        int[][] mazeMap = maze.getMazeMap();
        Position statePosition = ((MazeState)mState).getPosition();
        int rowPosition = statePosition.getRowIndex();
        int colPosition = statePosition.getColumnIndex();

        //upper state
        if (isValid(mazeMap,rowPosition - 1,colPosition)&& mazeMap[rowPosition - 1][colPosition] == 0) {
                allPossibleStates.add(new MazeState(mState.getCost() + 1, new Position(rowPosition - 1, colPosition)));
        }
        //upper right diagonal
        if (isValid(mazeMap,rowPosition - 1,colPosition+1)&& mazeMap[rowPosition - 1][colPosition + 1] == 0) {
            if (isValid(mazeMap,rowPosition - 1,colPosition)&& mazeMap[rowPosition - 1][colPosition] == 0 ||
                    isValid(mazeMap,rowPosition,colPosition+1)&& mazeMap[rowPosition][colPosition + 1] == 0)
                allPossibleStates.add(new MazeState(mState.getCost()+1.5, new Position(rowPosition-1,colPosition+1)));

        }
        //right cell
        if (isValid(mazeMap,rowPosition,colPosition+1)&&mazeMap[rowPosition][colPosition + 1] == 0)
            allPossibleStates.add(new MazeState(mState.getCost()+1, new Position(rowPosition,colPosition+1)));
        //lower right diagonal
        if (isValid(mazeMap,rowPosition+1,colPosition+1) && mazeMap[rowPosition + 1][colPosition + 1] == 0) {
            if (isValid(mazeMap,rowPosition+1,colPosition)&& mazeMap[rowPosition + 1][colPosition] == 0 ||
                    isValid(mazeMap,rowPosition,colPosition+1)&& mazeMap[rowPosition][colPosition + 1] == 0)
                allPossibleStates.add(new MazeState(mState.getCost()+1.5, new Position(rowPosition+1,colPosition+1)));
        }
        //lower cell
        if (isValid(mazeMap,rowPosition+1,colPosition)&& mazeMap[rowPosition + 1][colPosition] == 0)
            allPossibleStates.add(new MazeState(mState.getCost()+1, new Position(rowPosition+1,colPosition)));
        //lower left diagonal
        if (isValid(mazeMap,rowPosition+1,colPosition-1)&& mazeMap[rowPosition + 1][colPosition - 1] == 0) {
            if (isValid(mazeMap,rowPosition+1,colPosition)&& mazeMap[rowPosition + 1][colPosition] == 0 ||
                    isValid(mazeMap,rowPosition,colPosition-1)&& mazeMap[rowPosition][colPosition - 1] == 0)
                allPossibleStates.add(new MazeState(mState.getCost()+1.5, new Position(rowPosition+1,colPosition-1)));
        }
        //left cell
        if (isValid(mazeMap,rowPosition,colPosition-1)&& mazeMap[rowPosition][colPosition - 1] == 0)
            allPossibleStates.add(new MazeState(mState.getCost()+1, new Position(rowPosition,colPosition-1)));
        //upper left diagonal
        if (isValid(mazeMap,rowPosition - 1,colPosition-1)&& mazeMap[rowPosition - 1][colPosition - 1] == 0) {
            if (isValid(mazeMap,rowPosition - 1,colPosition)&& mazeMap[rowPosition - 1][colPosition] == 0 ||
                    isValid(mazeMap,rowPosition,colPosition-1)&& mazeMap[rowPosition][colPosition - 1] == 0)
                allPossibleStates.add(new MazeState(mState.getCost()+1.5, new Position(rowPosition-1,colPosition-1)));
        }
        if(debug) System.out.println("finished creating states");
        return allPossibleStates;
    }

    //check validity of position in the maze
    private boolean isValid (int[][] mazeMap, int row, int column){
        if (row < 0 || row >= mazeMap.length || column < 0 || column >= mazeMap[0].length)
            return false;
        else return true;
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

    @Override
    public String toString() {
        return maze.toString();
    }
}
