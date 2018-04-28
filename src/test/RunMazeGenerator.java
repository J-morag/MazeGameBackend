package test;

import algorithms.mazeGenerators.*;

public class RunMazeGenerator {

    private static final int sizeOfSides = 30;
    public static void main(String[] args) {
        //testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }

    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s",
                mazeGenerator.measureAlgorithmTimeMillis(sizeOfSides, sizeOfSides)));
        // generate another maze
        Maze maze = mazeGenerator.generate(sizeOfSides, sizeOfSides);
        // prints the maze
        maze.print();
        // get the maze entrance
        Position startPosition = maze.getStartPosition();
        // print the position
        System.out.println(String.format("Start Position: %s",
                startPosition)); // format "{row,column}"
        // prints the maze exit position
        System.out.println(String.format("Goal Position: %s",
                maze.getGoalPosition()));
        System.out.println(((int)-0.1));
    }
}