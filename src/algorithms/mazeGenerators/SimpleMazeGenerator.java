package algorithms.mazeGenerators;


import java.util.Random;

/**
 *
 */
public class SimpleMazeGenerator extends AMazeGenerator
{

	private Random rand;
	/**
	 * Default constructor
	 */
	public SimpleMazeGenerator(){
		super();
		rand = new Random();
	}

	/**
	 * generates a maze using my own algorithm, by placing walls randomly, and then creating a path.
	 * @param numOfRows - the required number of rows in the maze.
	 * @param numOfColumns - the required number of columns in the maze.
	 * @return - a randomly generated maze.
	 */
	@Override
	public Maze generate(int numOfRows, int numOfColumns) {
		int[][] mazeMap = getMapWithRandomWalls(numOfRows, numOfColumns, 0.5);
		Position startPos = super.getStartOrEndPosition(true, mazeMap);
		Position endPos = super.getStartOrEndPosition(false, mazeMap);

		makeAPath(mazeMap, startPos, endPos);

		return new Maze(mazeMap, startPos, endPos);
	}

	private int[][] getMapWithRandomWalls(int numOfRows, int numOfColumns, double desiredWallDensity){
		int[][] mazeMap = new int[numOfRows][numOfColumns];
		double wallDensity = 0;
		int numOfWalls = 0;
		//will fill horizontal walls until hitting half density. Will then fill vertical walls until hitting density.
		while (wallDensity<desiredWallDensity){
			if (wallDensity<(desiredWallDensity/2)){ //horizontal walls
				int wallLength = rand.nextInt(numOfColumns-1)+1; //between 1 and full length
				int wallColumnOffset = rand.nextInt(numOfColumns-wallLength +1); //offset from left wall
				int wallRowLoc = rand.nextInt(numOfRows);

				for (int j=wallColumnOffset ; j<wallLength ; j++){ //insert wall
					if (!(mazeMap[wallRowLoc][j] == 1 )){ //not already a wall
						mazeMap[wallRowLoc][j] = 1;
						numOfWalls++;
					}
				}
			}
			else{ //vertical walls
				int wallLength = rand.nextInt(numOfRows-1)+1; //between 1 and full length
				int wallRowOffset = rand.nextInt(numOfRows-wallLength +1); //offset from top wall
				int wallColumnLoc = rand.nextInt(numOfColumns);

				for (int j=wallRowOffset ; j<wallLength ; j++){ //insert wall
					if (!(mazeMap[j][wallColumnLoc] == 1 )){ //not already a wall
						mazeMap[j][wallColumnLoc] = 1;
						numOfWalls++;
					}
				}
			}
			wallDensity = (double)(numOfWalls)/(numOfColumns*numOfRows);
		}
		return mazeMap;
	}

	private void makeAPath(int[][] mazeMap, Position startPos, Position endPos){
		int i = startPos.getRowIndex();
		int j = startPos.getColomnIndex();
		int endRow = endPos.getRowIndex();
		int endColumn = endPos.getColomnIndex();
		while(!(i == endRow && j == endColumn)){
			int dirDelta = rand.nextInt(5); //up to 4 steps
			int dir = rand.nextInt(2); //i or j
			int dirPolarity = rand.nextInt(2) == 1 ? 1 : -1; // back or forth

			if (dir==1){ //change i
				while (!(i == endRow && j == endColumn) && i+1<mazeMap.length && dirDelta>0){
					i++;
					dirDelta--;
					mazeMap[i][j] = 0;
				}
			}
			else{
				while (!(i == endRow && j == endColumn) && j+1<mazeMap[0].length && dirDelta>0){
					j++;
					dirDelta--;
					mazeMap[i][j] = 0;
				}
			}
		}
	}
}

