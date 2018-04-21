package algorithms.mazeGenerators;

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

}

