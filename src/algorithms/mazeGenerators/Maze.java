package algorithms.mazeGenerators;


import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a 2D maze.
 */

public class Maze implements Serializable
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
     * //@throws IllegalArgumentException - if the given array is jagged, empty, or null, or smaller than 5 in any direction.
     * //@throws IllegalArgumentException - if either the start or end positions are outside the map, or null, or  they overlap.
     */

    public Maze(int[][] mazeMap, Position startPosition, Position endPosition) {
        final int minVectorLength = 5;
//		int numRows = mazeMap.length;
//		int numColumns = mazeMap[0].length;
//		//check nulls TODO re-enable exceptions in part C
//		if (null==mazeMap || null==startPosition || null==endPosition) throw new IllegalArgumentException("null argument.");
//		//check map validity
//		if (numRows < minVectorLength) throw new IllegalArgumentException("Number of rows must be larger than " + minVectorLength);
//		if (0==numRows) throw new IllegalArgumentException("empty map");
//		if (numColumns< minVectorLength) throw new IllegalArgumentException("Number of columns must be larger than " + minVectorLength);
//		if (0==numColumns) throw new IllegalArgumentException("empty map");
//		for (int[] row : mazeMap) {
//			if (row.length != numColumns) throw new IllegalArgumentException("jagged map");
//		}
//		//check that start and end positions are within the map.
//		if (startPosition.getRowIndex() > numRows-1 || startPosition.getColumnIndex() > numColumns-1
//				|| startPosition.getRowIndex() < 0 || startPosition.getColumnIndex() < 0
//				|| endPosition.getRowIndex() > numRows-1 || endPosition.getColumnIndex() > numColumns-1
//				|| endPosition.getRowIndex() < 0 || endPosition.getColumnIndex() < 0)
//			throw new IllegalArgumentException("position out of bounds");
//		//check that the start and end positions don't overlap
//		if (startPosition.equals(endPosition)) throw new IllegalArgumentException("overlapping start and end positions");

        this.mazeMap = mazeMap;
        this.startPosition = new Position(startPosition);
        this.goalPosition = new Position(endPosition);
    }

    public Maze(byte[] byteEncoding) {
        // TODO add validity check
        int srcPos = 0;
        byte[] bytesToBecomeInt = new byte[4];
        System.arraycopy(byteEncoding, srcPos, bytesToBecomeInt, 0, 4);
        srcPos += 4;
        int numRows = byteArrayToInt(bytesToBecomeInt);
        System.arraycopy(byteEncoding, srcPos, bytesToBecomeInt, 0, 4);
        srcPos += 4;
        int numColumns = byteArrayToInt(bytesToBecomeInt);
        System.arraycopy(byteEncoding, srcPos, bytesToBecomeInt, 0, 4);
        srcPos += 4;

        int startRow = byteArrayToInt(bytesToBecomeInt);
        System.arraycopy(byteEncoding, srcPos, bytesToBecomeInt, 0, 4);
        srcPos += 4;
        int startCol = byteArrayToInt(bytesToBecomeInt);
        startPosition = new Position(startRow, startCol);

        System.arraycopy(byteEncoding, srcPos, bytesToBecomeInt, 0, 4);
        srcPos += 4;
        int endRow = byteArrayToInt(bytesToBecomeInt);
        System.arraycopy(byteEncoding, srcPos, bytesToBecomeInt, 0, 4);
        srcPos += 4;
        int endcol = byteArrayToInt(bytesToBecomeInt);
        goalPosition = new Position(endRow, endcol);

        mazeMap = buildMazeMapFromByteArr(byteEncoding, srcPos, numRows, numColumns);
    }

    /**
     *
     * @param byteEncoding encoding of Maze in bytes.
     * @param bytesArrayIndex the index where the maze map encoding starts
     * @param numRows number of rows
     * @param numColumns number of columns
     * @return a decoded maze map (int[][])
     */
    private int[][] buildMazeMapFromByteArr(byte[] byteEncoding,int bytesArrayIndex, int numRows, int numColumns){
//        int[][] mazeMap = new int[numRows][numColumns];
//        for (int i = 0; i < numRows ; i++) {
//            for (int j = 0; j < numColumns;) {
//                boolean MSB_isOn = ( byteEncoding[bytesArrayIndex] & (byte)-128 ) != 0;
//                if (MSB_isOn) mazeMap[i][j] = 1;
//                j++;
//
//                //now go over the next 7 maze locations
//                int divMeByTwo = 64; // 01000000 is 64, 00100000 is 32...
//                for (; j%8 != 0 && j < mazeMap[0].length; j++){
//                    boolean bitIsOn = ( byteEncoding[bytesArrayIndex] & (byte)divMeByTwo ) != 0;
//                    if (bitIsOn) mazeMap[i][j] = 1;
//                    divMeByTwo /= 2;
//                }
//
//                bytesArrayIndex++;
//            }
//
//        }
        int[][] mazeMap = new int[numRows][numColumns];
        for (int i = 0; i < numRows && bytesArrayIndex<byteEncoding.length ; i++) {
            for (int j = 0; j < numColumns && bytesArrayIndex<byteEncoding.length; j++) {
                mazeMap[i][j] = byteEncoding[bytesArrayIndex];
                bytesArrayIndex++;
            }
        }

        return mazeMap;
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
        return mazeMap;
    }

    /**
     * encodes the Maze as a byte[].
     * first set of 4 bytes represent the number of rows (int), next set is columns, start row, start column,
     * end row, end column.
     *
     * index 24 onwards represent the contents of the maze.
     * if the (number of columns % 8 != 0) , the byte at the end of each row will end in (#columns % 8) 0's.
     * @return a byte[] encoding of the maze
     */
    public byte[] toByteArray(){
        int numRows = mazeMap.length, numColumns=mazeMap[0].length;
        int arraySize = 4 /*num rows*/ + 4 /*num columns*/ + 4 /*start row*/ + 4 /*start column*/ + 4 /*end row*/ + 4 /*end column*/;
        int numBytesToRepresentMazeContent = getNumBytesToRepresentMazeContent(numRows, numColumns);
        arraySize += numBytesToRepresentMazeContent;
        byte[] mazeAsByteArray = new byte[arraySize];
        int index = 0;
        System.arraycopy(intToByteArray(numRows), 0, mazeAsByteArray, index, 4);
        index += 4;
        System.arraycopy(intToByteArray(numColumns), 0, mazeAsByteArray, index, 4);
        index += 4;
        System.arraycopy(intToByteArray(startPosition.getRowIndex()), 0, mazeAsByteArray, index, 4);
        index += 4;
        System.arraycopy(intToByteArray(startPosition.getColumnIndex()), 0, mazeAsByteArray, index, 4);
        index += 4;
        System.arraycopy(intToByteArray(goalPosition.getRowIndex()), 0, mazeAsByteArray, index, 4);
        index += 4;
        System.arraycopy(intToByteArray(goalPosition.getColumnIndex()), 0, mazeAsByteArray, index, 4);
        index += 4;
//        byte[] mazeMapAsByteArr = mazeMapToByteArray();
        byte[] mazeMapAsByteArr = mazeMapToSimpleByteArray();
        System.arraycopy(mazeMapAsByteArr, 0, mazeAsByteArray, index, mazeMapAsByteArr.length);
        return mazeAsByteArray;
    }

    private int getNumBytesToRepresentMazeContent(int numRows, int numColumns) {
//        numRows * (int)Math.ceil(numColumns/8.0);
         return numRows * numColumns;
    }

    /**
     * converts integer to 2's complement array of bytes. byte[1] contains the MSB.
     * @param integer - integer to convert.
     * @return byte array size 4 representing the integer.
     */
    public static byte[] intToByteArray(int integer){
        return ByteBuffer.allocate(4).putInt(integer).array();
    }

    /**
     * converts byte[] to integer. bytes beyond 4 (index 3) will be cut.
     * @param bytes byte array to convert to integer
     * @return integer representation
     */
    public static int byteArrayToInt(byte[] bytes){
        return ByteBuffer.wrap(bytes).getInt();
    }

    /**
     * converts the the maze map to an array of bytes, where every cell is represented by a single bit within a byte.
     * if the (number of columns % 8 != 0) , the byte at the end of each row will end in (#columns % 8) 0's.
     * @return byte[] representing the maze map.
     */
    private byte[] mazeMapToByteArray(){
        byte[] byteArr = new byte[mazeMap.length * (int)Math.ceil(mazeMap[0].length/8.0)];
        int byteArrIndex = 0;
        byte nextByte = 0x00;
        for (int i = 0; i < mazeMap.length; i++) { //rows
            for (int j = 0; j < mazeMap[0].length;) { //inside row (columns)
                // 10000000 is -124
                if (1 == mazeMap[i][j]) nextByte += (byte)-128;
                j++;

                //now go over the next 7 maze locations
                int divByTwo = 64; // 01000000 is 64, 00100000 is 32...
                for (; j%8 != 0 && j < mazeMap[0].length; j++){
                    if (1 == mazeMap[i][j]) nextByte += divByTwo;
                    divByTwo /= 2;
                }

                byteArr[byteArrIndex] = nextByte;
                byteArrIndex++;
                nextByte = 0x00;
            }

        }
        return byteArr;
    }

    private byte[] mazeMapToSimpleByteArray(){
        byte[] byteArr = new byte[mazeMap.length * mazeMap[0].length];
        int byteArrIndex = 0;
        for (int i = 0; i < mazeMap.length; i++) { //rows
            for (int j = 0; j < mazeMap[0].length; j++) { //inside row (columns)
                byteArr[byteArrIndex] = (mazeMap[i][j] == 0) ? (byte)0 : (byte)1;
                byteArrIndex++;
            }
        }
        return byteArr;
    }

    /**
     * prints the maze to the screen.
     */

    public void print() {

        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int sRow = startPosition.getRowIndex();
        int sColumn = startPosition.getColumnIndex();
        int eRow = goalPosition.getRowIndex();
        int eColumn = goalPosition.getColumnIndex();
        for (int j=0; j<mazeMap.length+1 ; j++) sb.append("~ ");
        sb.append('\n');
        for (int i=0; i<mazeMap.length ; i++){
            sb.append("| ");
            for (int j=0; j<mazeMap[0].length ; j++){
                if(i==sRow && j==sColumn) sb.append('S');
                else if(i==eRow && j==eColumn) sb.append('E');
                else if(0 == mazeMap[i][j]) sb.append(' ');
                else sb.append('+');
                sb.append(' ');
            }
            sb.append("|\n");
        }
        for (int j=0; j<mazeMap.length+1 ; j++) sb.append("~ ");
        sb.append('\n');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;
        boolean b1 = startPosition.equals(maze.startPosition);
        boolean b2 = goalPosition.equals(maze.goalPosition);
        boolean b3 = true;
        for(int i=0; i<mazeMap.length; i++){
            b3 = b3 && Arrays.equals(mazeMap[i], maze.mazeMap[i]);
        }
        return b1 && b2 && b3;
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(startPosition, goalPosition);
        for (int[] row:
             mazeMap) {
            result = 31 * result + Arrays.hashCode(row);
        }
        return result;
    }
}

