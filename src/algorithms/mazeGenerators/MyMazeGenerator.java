package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a generation of maze according to known algorithm, in our case - Prim.
 */

public class MyMazeGenerator extends AMazeGenerator
{
	/**
	 * Default constructor
	 */
	public MyMazeGenerator(){
		super();
	}

	@Override
	public Maze generate(int numOfRows, int numOfColumns) {

		int[][] mazeMap = new int[numOfRows][numOfColumns];
		mazeMap = getMapByPrim(mazeMap);

		Position startPos = super.getStartOrEndPosition(true, mazeMap);
		Position endPos = super.getStartOrEndPosition(false, mazeMap);

		return new Maze(mazeMap, startPos, endPos);
	}

	//creates a maze map according to randomized Prim's algorithm
	private int[][] getMapByPrim (int[][] mazeMap){

		List<Position> lFrontierCells = new ArrayList<>();
		//initialize the entire maze with walls
		for(int row = 0; row < mazeMap.length; row++){
			for(int col = 0; col < mazeMap[0].length; col++){
				mazeMap[row][col] = 1;
			}
		}
		//choose a random cell in the maze and change it to a passage
		Position rndCell = getRandomCellInArray(mazeMap);
		mazeMap[rndCell.getRowIndex()][rndCell.getColumnIndex()] = 0;

		//add frontiers of the random cell to the list
		lFrontierCells.addAll(getFrontiers(mazeMap,rndCell));

		while (!lFrontierCells.isEmpty()){
			Position rndFrontier = getRandomCellInList(lFrontierCells);
            //check validity position
            if (!isValidPosition(mazeMap,rndFrontier))
                throw new IllegalArgumentException("The position is out of bounds of the maze");
			List<Position> lNeighborCells = getNeighbors(mazeMap,rndFrontier); //gets the neighbors of the current frontier cell
			if(!lNeighborCells.isEmpty()){
				Position rndNeighbor = getRandomCellInList(lNeighborCells);
                if (!isValidPosition(mazeMap,rndNeighbor))
                    throw new IllegalArgumentException("The position is out of bounds of the maze");
				mazeMap = connectFrontierToNeighbor(mazeMap,rndFrontier,rndNeighbor); //connect the frontier to the neighbor and set 0 between them
				mazeMap[rndFrontier.getRowIndex()][rndFrontier.getColumnIndex()] = 0; //change the frontier wall to passage
				lFrontierCells.addAll(getFrontiers(mazeMap,rndFrontier)); //add the frontiers of the frontier cell to the list
			}
			lFrontierCells.remove(rndFrontier);
		}
		return mazeMap;
	}
	//select a random cell in array
	private Position getRandomCellInArray(int[][] array) {
		int rndRow = new Random().nextInt(array.length);
		int rndCol = new Random().nextInt(array[0].length);
		return new Position(rndRow,rndCol);
	}

	//select a random cell in list
	private Position getRandomCellInList(List<Position> list) {
		int rndIndex = new Random().nextInt(list.size());
		Position rndPosition = list.get(rndIndex);
		return rndPosition;
	}

	private boolean isValidPosition (int[][] mazeMap, Position cell){
        if (cell.getRowIndex() < 0 || cell.getRowIndex() >= mazeMap.length || cell.getColumnIndex() < 0 || cell.getColumnIndex() > mazeMap[0].length)
            return false;
        else return true;
    }
    //looking for frontiers of 'cell' - a frontier is a wall with a distance of 2
    private List<Position> getFrontiers (int[][] mazeMap, Position cell) {
        //check validity of the given position
        if (!isValidPosition(mazeMap,cell))
            throw new IllegalArgumentException("cell position is out of bounds of the maze");

        List<Position> frontiers = new ArrayList<>();
        int rowCell = cell.getRowIndex();
        int colCell = cell.getColumnIndex();

        //on the right
        if(colCell+2 < mazeMap[0].length && mazeMap[rowCell][colCell+2] == 1)
            frontiers.add(new Position(rowCell, (colCell+2)));
        //on the left
        if(colCell-2 >= 0 && mazeMap[rowCell][colCell-2] == 1)
            frontiers.add(new Position(rowCell, (colCell-2)));
        //below the cell
        if(rowCell+2 < mazeMap.length && mazeMap[rowCell+2][colCell] == 1)
            frontiers.add(new Position((rowCell+2), colCell));
        //above the cell
        if(rowCell-2 >= 0 && mazeMap[rowCell-2][colCell] == 1)
            frontiers.add(new Position((rowCell-2), colCell));

        return frontiers;
    }

    //looking for neighbors of 'cell' - a neighbor is a passage with a distance of 2
    private List<Position> getNeighbors (int[][] mazeMap, Position cell) {
        //check validity of the given position
        if (!isValidPosition(mazeMap,cell))
            throw new IllegalArgumentException("cell position is out of bounds of the maze");

        List<Position> neighbors = new ArrayList<>();
        int rowCell = cell.getRowIndex();
        int colCell = cell.getColumnIndex();

        //on the right
        if(colCell+2 < mazeMap[0].length && mazeMap[rowCell][colCell+2] == 0)
            neighbors.add(new Position(rowCell, (colCell+2)));
        //on the left
        if(colCell-2 >= 0 && mazeMap[rowCell][colCell-2] == 0)
            neighbors.add(new Position(rowCell, (colCell-2)));
        //below the cell
        if(rowCell+2 < mazeMap.length && mazeMap[rowCell+2][colCell] == 0)
            neighbors.add(new Position((rowCell+2), colCell));
        //above the cell
        if(rowCell-2 >= 0 && mazeMap[rowCell-2][colCell] == 0)
            neighbors.add(new Position((rowCell-2), colCell));

        return neighbors;
    }

	private int[][] connectFrontierToNeighbor (int[][] mazeMap, Position frontier, Position neighbor){
		int rowBetween, columnBetween;
		if(frontier.getColumnIndex() == neighbor.getColumnIndex()){
			rowBetween = Math.min(frontier.getRowIndex(), neighbor.getRowIndex()) + 1;
			columnBetween = frontier.getColumnIndex();
		}
		else if(frontier.getRowIndex() == neighbor.getRowIndex()){
			rowBetween = frontier.getRowIndex();
			columnBetween = Math.min(frontier.getColumnIndex(), neighbor.getColumnIndex()) + 1;
		}
		else return null;

		mazeMap[rowBetween][columnBetween] = 0; //set a passage between the frontier and the neighbor
		return mazeMap;
	}
}

