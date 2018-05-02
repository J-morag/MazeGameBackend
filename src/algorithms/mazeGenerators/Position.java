package algorithms.mazeGenerators;

import java.util.Objects;

/**
 * Position describes a position in a maze.
 * Is immutable.
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
	
	private int column;

	/**
	 * constructor.
	 * @param row - row coordinate. like 'i' in a matrix. like 'y' in euclidean geometry.
	 * @param column - column coordinate. like 'j' in a matrix. like 'x' in euclidean geometry.
	 */
	public Position(int row, int column){
		this.row=row;
		this.column = column;
	}

	/**
	 * copy constructor.
	 * @param position - the Position to copy.
	 */
	public Position(Position position) {
		this.row=position.row;
		this.column =position.column;
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
	
	public int getColumnIndex() {
		return column;
	}

	/**
	 * toString override.
	 */
	@Override
	public String toString() {
		return "{"+row+","+column+"}";
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
		if (row==((Position) pos).row && column ==((Position) pos).column) return true;
		else return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash*31 + row;
		hash = hash*31 + column;
		return hash;
	}
}

