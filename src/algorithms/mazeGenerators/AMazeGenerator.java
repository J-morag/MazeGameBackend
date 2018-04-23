package algorithms.mazeGenerators;

import javafx.geometry.Pos;

/**
 * An abstract class which represents a generator of maze.
 * implements an interface of maze generator.
 */

public abstract class AMazeGenerator implements IMazeGenerator
{
	/**
	 * Default constructor.
	 */

	public AMazeGenerator(){
	}


	/**
	 * An abstract method of creating a new maze, implemented by the children classes.
	 * @param numOfRows - the required number of rows in the maze.
	 * @param numOfColumns - the required number of columns in the maze.
	 * @return - default return - null.
	 */
	public Maze generate(int numOfRows, int numOfColumns) {
		return null;
	}

	/**
	 * Measures the operating time of the 'generate' method.
	 * @param numOfRows - the required number of rows in the maze.
	 * @param numOfColumns - the required number of columns in the maze.
	 * @return the delta of times before and after operating the 'generate' method.
	 */
	
	public long measureAlgorithmTimeMillis(int numOfRows, int numOfColumns) {

		long beforeGen, afterGen;

		//check validity of measures of the maze
		if(numOfRows < 0 || numOfColumns < 0)
			throw new IllegalArgumentException("illegal number of rows or columns");

		if(numOfRows == 0 && numOfColumns == 0) //there is no maze to generate
			return 0;

		beforeGen = System.currentTimeMillis();
		generate(numOfRows,numOfColumns);
		afterGen = System.currentTimeMillis();

		return (afterGen - beforeGen);
	}

	/**
	 *
	 * @param isStartPosition - true := start. false := end
	 * @param mazeMap - the map to find positions on.
	 * @return - a start position closest to the top, left. Or an end position closest to the bottom, right.
	 */

	protected Position getStartOrEndPosition(boolean isStartPosition, int[][] mazeMap){
		Position result = null;
		int numOfRows = mazeMap.length;
		int numOfColumns = mazeMap[0].length;
		boolean stop = false;
		if (isStartPosition){
			for(int i=0; !stop && i<numOfRows ; i++){ //will succeed as long as wall density is not very close to 1
				for(int j=0; !stop && j<numOfColumns ; j++){
					if (0 == mazeMap[i][j]){
						result = new Position(i, j);
						stop = true;
					}
				}
			}
		}
		else {
			for(int i=numOfRows-1; !stop && i>0 ; i--){
				for(int j=numOfColumns-1; !stop && j>0 ; j--){
					if (0 == mazeMap[i][j]){
						result = new Position(i, j);
						stop = true;
					}
				}
			}
		}

		return result;

	}

}

