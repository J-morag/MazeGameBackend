package algorithms.mazeGenerators;

import java.awt.*;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.LinkedList;
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

		Position startPos = null;
		Position endPos = null;
		boolean stop = false;
		for(int i=0; !stop && i<numOfRows ; i++){ //will succeed as long as wall density is not very close to 1
			for(int j=0; !stop && j<numOfColumns ; j++){
				if (0 == mazeMap[i][j]){
					startPos = new Position(i, j);
					stop = true;
				}
			}
		}
		stop = false;
		for(int i=numOfRows-1; !stop && i>0 ; i--){
			for(int j=numOfColumns-1; !stop && j>0 ; j--){
				if (0 == mazeMap[i][j]){
					endPos = new Position(i, j);
					stop = true;
				}
			}
		}
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
		mazeMap[rndCell.getRowIndex()][rndCell.getColomnIndex()] = 0;

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
				mazeMap[rndFrontier.getRowIndex()][rndFrontier.getColomnIndex()] = 0; //change the frontier wall to passage
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
        if (cell.getRowIndex() < 0 || cell.getRowIndex() >= mazeMap.length || cell.getColomnIndex() < 0 || cell.getColomnIndex() > mazeMap[0].length)
            return false;
        else return true;
    }
	private List<Position> getFrontiers (int[][] mazeMap, Position cell) {
		//check validity of the given position
		if (!isValidPosition(mazeMap,cell))
			throw new IllegalArgumentException("cell position is out of bounds of the maze");

		List<Position> frontiers = new ArrayList<>();

		for (int row = 0; row < mazeMap.length; row++) {
			for (int col = 0; col < mazeMap[0].length; col++) {
				//check if the current position is a frontier of 'cell' - a wall with a distance of 2
				if (mazeMap[row][col] == 1) { //a wall
					if ((row == cell.getRowIndex() && Math.abs(col - cell.getColomnIndex()) == 2) //on the right or on the left
							|| col == cell.getColomnIndex() && Math.abs(row - cell.getRowIndex()) == 2) //above or below
					{
						frontiers.add(new Position(row, col));
					}
				}
			}
		}
		return frontiers;
	}

	private List<Position> getNeighbors (int[][] mazeMap, Position cell) {
		//check validity of the position
		if (cell.getRowIndex() < 0 || cell.getRowIndex() >= mazeMap.length || cell.getColomnIndex() < 0 || cell.getColomnIndex() > mazeMap[0].length)
			throw new IllegalArgumentException("cell position is out of bounds of the maze");

		List<Position> neighbors = new ArrayList<>();

		for (int row = 0; row < mazeMap.length; row++) {
			for (int col = 0; col < mazeMap[0].length; col++) {
				//check if the current position is a neighbor of 'cell' - a passage with a distance of 2
				if (mazeMap[row][col] == 0) { //a passage
					if ((row == cell.getRowIndex() && Math.abs(col - cell.getColomnIndex()) == 2) //on the right or on the left
							|| col == cell.getColomnIndex() && Math.abs(row - cell.getRowIndex()) == 2) //above or below
					{
						neighbors.add(new Position(row, col));
					}
				}
			}
		}
		return neighbors;
	}

	private int[][] connectFrontierToNeighbor (int[][] mazeMap, Position frontier, Position neighbor){
		int rowBetween, columnBetween;
		if(frontier.getColomnIndex() == neighbor.getColomnIndex()){
			rowBetween = Math.min(frontier.getRowIndex(), neighbor.getRowIndex()) + 1;
			columnBetween = frontier.getColomnIndex();
		}
		else if(frontier.getRowIndex() == neighbor.getRowIndex()){
			rowBetween = frontier.getRowIndex();
			columnBetween = Math.min(frontier.getColomnIndex(), neighbor.getColomnIndex()) + 1;
		}
		else return null;

		mazeMap[rowBetween][columnBetween] = 0; //set a passage between the frontier and the neighbor
		return mazeMap;
	}
}
