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
		if (startPosition.getRowIndex() > numRows-1 || startPosition.getColumnIndex() > numColumns-1
				|| startPosition.getRowIndex() < 0 || startPosition.getColumnIndex() < 0
				|| endPosition.getRowIndex() > numRows-1 || endPosition.getColumnIndex() > numColumns-1
				|| endPosition.getRowIndex() < 0 || endPosition.getColumnIndex() < 0)
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

	public int[][] getMazeMap() {
		int[][] copyMazeMap = new int[mazeMap.length][mazeMap[0].length];
		for(int i=0; i < mazeMap.length; i++){
			for(int j=0; j < mazeMap[0].length; j++){
				copyMazeMap[i][j] = mazeMap[i][j];
			}
		}
		return copyMazeMap;
	}

	/**
	 * prints the maze to the screen.
	 */
	
	public void print() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		String ans = "";
		int sRow = startPosition.getRowIndex();
		int sColumn = startPosition.getColumnIndex();
		int eRow = goalPosition.getRowIndex();
		int eColumn = goalPosition.getColumnIndex();
		for (int j=0; j<mazeMap.length ; j++) System.out.print('_');
		ans += '\n';
		for (int i=0; i<mazeMap.length ; i++){
			ans += '|';
			for (int j=0; j<mazeMap.length ; j++){
				if(i==sRow && j==sColumn) ans += 'S';
				else if(i==eRow && j==eColumn) ans += 'E';
				else if(0 == mazeMap[i][j]) ans += "☐";
				else ans += '■';
			}
			ans += "|\n";
		}
		for (int j=0; j<mazeMap.length ; j++) ans += '_';
		ans += '\n';
		return ans;
	}
}

