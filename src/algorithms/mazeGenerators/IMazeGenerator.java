package algorithms.mazeGenerators;


/**
 * An interface of maze generator.
 */
public  interface IMazeGenerator
{
	/**
	 * Creates a new maze.
	 * @param numOfRows - the required number of rows in the maze.
	 * @param numOfColumns - the required number of columns in the maze.
	 * @return - not implemented in the interface - no return.
	 */
	
	public Maze generate(int numOfRows, int numOfColumns) ;

	/**
	 * Measures the operating time of the 'generate' method.
	 * @param numOfRows - the required number of rows in the maze.
	 * @param numOfColumns - the required number of columns in the maze.
	 * @return - not implemented in the interface - no return.
	 */
	
	public long measureAlgorithmTimeMillis(int numOfRows, int numOfColumns) ;


}

