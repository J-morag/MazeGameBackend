package algorithms.mazeGenerators;


/**
 * Represents a 2D maze.
 */

public class Maze
{
	/**
	 * Start position. Represented by 'S' when maze is printed.
	 */
	
	private Position startPosition;

	/**
	 * Goal (end) position. Represented by 'E' when maze is printed.
	 */
	
	private Position goalPosition;

	/**
	 * int matrix representing the body of the maze. 0 := hallway, 1 := wall.
	 */
	
	private int[][] mazeMap;

	/**
	 * default constructor.
	 * Default: Maze will be 10*10. No Walls. Start(0,0), End(9,9);
	 */
	public Maze(){
		this(new int[][]{
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0}	}, new Position(0,0), new Position(9,9));
	}

	/**
	 * constructor.
	 * Does not check that the maze is solvable.
	 * @param mazeMap - 2D int array that represents the body of the map. must not be jagged.
	 * @param startPosition - start position. must fall withing the boundary of the maze. deep copied.
	 * @param endPosition - end position. must fall withing the boundary of the maze. deep copied.
	 * @throws IllegalArgumentException - if the given array is jagged, empty, or null.
	 * @throws IllegalArgumentException - if either the start or end positions are outside the map, or null, or  they overlap.
	 */

	public Maze(int[][] mazeMap, Position startPosition, Position endPosition) {
		//check nulls
		if (null==mazeMap || null==startPosition || null==endPosition) throw new IllegalArgumentException("null argument.");
		//check map validity
		int numRows = mazeMap.length;
		if (0==numRows) throw new IllegalArgumentException("empty map");
		int numColumns = mazeMap[0].length;
		if (0==numColumns) throw new IllegalArgumentException("empty map");
		for (int[] row : mazeMap) {
			if (row.length != numColumns) throw new IllegalArgumentException("jagged map");
		}
		//check that start and end positions are within the map.
		if (startPosition.getRowIndex() > numRows-1 || startPosition.getColomnIndex() > numColumns-1
				|| startPosition.getRowIndex() < 0 || startPosition.getColomnIndex() < 0
				|| endPosition.getRowIndex() > numRows-1 || endPosition.getColomnIndex() > numColumns-1
				|| endPosition.getRowIndex() < 0 || endPosition.getColomnIndex() < 0)
			throw new IllegalArgumentException("position out of bounds");
		//check that the start and end positions don't overlap
		if (startPosition.equals(endPosition)) throw new IllegalArgumentException("overlapping start and end positions");

		this.mazeMap = mazeMap;
		this.startPosition = new Position(startPosition);
		this.goalPosition = new Position(endPosition);
	}

	/**
	 * @return a copy of the start position
	 */
	
	public Position getStartPosition() {
		return new Position(startPosition);
	}

	/**
	 * @return a copy of the goal position
	 */
	
	public Position getGoalPosition() {
		return new Position(goalPosition);
	}

	/**
	 * prints the maze to the screen.
	 */
	
	public void print() {
		int sRow = startPosition.getRowIndex();
		int sColumn = startPosition.getColomnIndex();
		int eRow = goalPosition.getRowIndex();
		int eColumn = goalPosition.getColomnIndex();
		for (int j=0; j<mazeMap.length ; j++) System.out.print('_');
		System.out.println("");
		for (int i=0; i<mazeMap.length ; i++){
			System.out.print("|");
			for (int j=0; j<mazeMap.length ; j++){
				if(i==sRow && j==sColumn) System.out.print('S');
				else if(i==eRow && j==eColumn) System.out.print('E');
				//else if(0 == mazeMap[i][j]) System.out.print('░');
				else if(0 == mazeMap[i][j]) System.out.print("☐");
				//else System.out.print('█');
				else System.out.print('■');
			}
			System.out.println("|");
		}
		for (int j=0; j<mazeMap.length ; j++) System.out.print('_');
		System.out.println("");
	}



}

