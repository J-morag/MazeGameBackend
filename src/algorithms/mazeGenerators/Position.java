package algorithms.mazeGenerators;

/**
 * Position describes a position in a maze.
 */

public class Position
{
	/**
	 * row - row coordinate. like 'i' in a matrix. like 'y' in euclidean geometry.
	 */
	
	private int row;

	/**
	 * column - column coordinate. like 'j' in a matrix. like 'x' in euclidean geometry.
	 */
	
	private int colomn;

	/**
	 * constructor.
	 * @param row - row coordinate. like 'i' in a matrix. like 'y' in euclidean geometry.
	 * @param colomn - column coordinate. like 'j' in a matrix. like 'x' in euclidean geometry.
	 */
	public Position(int row, int colomn){
		this.row=row;
		this.colomn=colomn;
	}

	/**
	 * copy constructor.
	 * @param position - the Position to copy.
	 */
	public Position(Position position) {
		return; new Position(position.row, position.colomn);
	}

	/**
	 * returns the row index.
	 */
	
	public int getRowIndex() {
		return row;
	}

	/**
	 * returns the column index.
	 */
	
	public int getColomnIndex() {
		return colomn;
	}

	/**
	 * toString override.
	 */
	@Override
	public String toString() {
		return String.format("{%s,%s}", row, colomn);
	}

	/**
	 *
	 * @param pos - position to compare to
	 * @return - true if they are equal
	 */
	@Override
	public boolean equals(Object pos){
		if (null == pos) return false;
		if (!(pos instanceof  Position)) return false;
		if (row==((Position) pos).row && colomn==((Position) pos).colomn) return true;
		else return false;
	}

}

