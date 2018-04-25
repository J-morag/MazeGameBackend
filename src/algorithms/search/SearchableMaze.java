package algorithms.search;

import algorithms.mazeGenerators.*;

import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable{

    private Maze maze;
    private MazeState startState;
    private MazeState goalState;
    private double heuristicDistance;
    private ArrayList<MazeState> allPossibleStates;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.allPossibleStates = new ArrayList<MazeState>();
    }

    // TODO not implemented
    public ArrayList<MazeState> getAllPossibleStates(){
        int[][] mazeMap = maze.getMazeMap();
        //creates MazeStates for all the passages in the maze
        for(int row=0; row < mazeMap.length; row++){
            for(int col=0; col < mazeMap[0].length; col++){
                if(mazeMap[row][col] == 0) { //a passage

                }
            }
        }
        return allPossibleStates;
    }

    // TODO not implemented
    @Override
    public MazeState getStartState() {
        return startState;
    }

    // TODO not implemented
    @Override
    public MazeState getGoalState() {
        return goalState;
    }

    private double getHeuristicDistance(int rowPosition, int colPosition){

        Position goalPosition = maze.getGoalPosition();

        int deltaRow = goalPosition.getRowIndex() - rowPosition;
        int deltaCol = goalPosition.getColomnIndex() - colPosition;

        heuristicDistance = Math.sqrt(Math.pow(deltaRow,2) + Math.pow(deltaCol,2));
        return heuristicDistance;
    }
}
