package test;

import algorithms.mazeGenerators.*;

import java.util.List;

public class TestMazeAndPositionClasses {
    public static void main(String[] args){
        Position pos1 = new Position(1,1);
        Position pos2 = new Position(2,3);
        Position pos3 = new Position(8,3);
        Position pos4 = new Position(8,-1);
        int[][] map1 = new int[][] { {1,1,1,1,1},
                                    {1,0,0,0,1},
                                    {1,1,0,0,1},
                                    {1,1,1,1,1}
                                                    };
        int[][] emptyMap = new int[][] { {}
        };

        Maze maze = null;

        try {
            maze = new Maze(map1, pos1, pos2);
        }
        catch( Exception e){
            System.out.println("exception thrown with parameters: "+ map1 +"  "+ pos1 + "  " + pos2);
            System.out.println("message: " + e.getMessage());
        }

        try {
            maze = new Maze(map1, pos1, pos1);
        }
        catch( Exception e){
            System.out.println("exception thrown with parameters: "+ map1 +"  "+ pos1 + "  " + pos1);
            System.out.println("message: " + e.getMessage());
        }

        try {
            maze = new Maze(map1, pos1, pos3);
        }
        catch( Exception e){
            System.out.println("exception thrown with parameters: "+ map1 +"  "+ pos1 + "  " + pos3);
            System.out.println("message: " + e.getMessage());
        }

        try {
            maze = new Maze(emptyMap, pos1, pos2);
        }
        catch( Exception e){
            System.out.println("exception thrown with parameters: "+ emptyMap +"  "+ pos1 + "  " + pos2);
            System.out.println("message: " + e.getMessage());
        }

        try {
            maze = new Maze(map1, pos1, pos4);
        }
        catch( Exception e){
            System.out.println("exception thrown with parameters: "+ map1 +"  "+ pos1 + "  " + pos4);
            System.out.println("message: " + e.getMessage());
        }

        maze.print();

        try {
            maze = new Maze();
        }
        catch( Exception e){
            System.out.println("exception thrown with parameters: ");
            System.out.println("message: " + e.getMessage());
        }

        maze.print();
    }
}
