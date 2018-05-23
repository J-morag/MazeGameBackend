package test;

import algorithms.mazeGenerators.*;

import java.util.Random;

import static java.lang.System.exit;

public class AutomatedTests {

    public static void main(String[] args) {
        testXTimes(true, 20, new RandomizedMazeGeneratorTest(500, 500, new MyMazeGenerator()));
        testXTimes(true, 20, new RandomizedMazeGeneratorTest(500, 500, new SimpleMazeGenerator()));
    }

    private static void testXTimes(boolean catchExceptions, int iterations, TestStrategy testStrategy){

        for (int i=0 ;i<iterations; i++){
            if (catchExceptions){
                try{
                    testStrategy.test();
                }
                catch(Exception e){
                    System.out.println("\nexception: " +e.getClass()+". message:"+ e.getMessage());
                    System.out.println("on iteration "+(i+1)+" of "+iterations+" with "+testStrategy.getDescription());
                    System.out.println("stack trace:");;
                    for (StackTraceElement s:
                            e.getStackTrace()) {
                        System.out.println(s);
                    }
                    exit(1);
                }
            }
            else testStrategy.test();
        }
    }
}

/**
 * simple interface for a test.
 */
interface TestStrategy{
    /**
     * runs the test
     */
    void test();

    /**
     * @return a short description of the test, including the values of any variables used.
     */
    String getDescription();
}

class RandomizedMazeGeneratorTest implements TestStrategy{
    private int rowsUpperBound, columnsUpperBound;
    private int rows, columns;
    private IMazeGenerator mazeGenerator;

    public RandomizedMazeGeneratorTest(int rowsUpperBound, int columnsUpperBound, IMazeGenerator mazeGenerator) {
        this.rowsUpperBound = rowsUpperBound;
        this.columnsUpperBound = columnsUpperBound;
        this.mazeGenerator = mazeGenerator;
    }

    @Override
    public void test() {
        Random rnd = new Random();
        columns = rnd.nextInt(columnsUpperBound);
        rows = rnd.nextInt(rowsUpperBound);
        smallTest(mazeGenerator, rows, columns);
    }

    @Override
    public String getDescription() {
        return mazeGenerator.getClass()+" "+rows+", "+columns;
    }

    private static void smallTest(IMazeGenerator mazeGenerator, int rows, int columns){
        Maze maze = mazeGenerator.generate(rows, columns);
        maze.print();
        // get the maze entrance
        Position startPosition = maze.getStartPosition();
        // print the position
        System.out.println(String.format("Start Position: %s",
                startPosition)); // format "{row,column}"
        // prints the maze exit position
        System.out.println(String.format("Goal Position: %s",
                maze.getGoalPosition()));
        //System.out.println("iteration "+i+" successful!");
    }
}


