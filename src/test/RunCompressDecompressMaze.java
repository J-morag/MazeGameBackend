package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Aviadjo on 3/26/2017.
 */
public class RunCompressDecompressMaze {
    public static void main(String[] args) {

        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(1000, 1000); //Generate new maze
//
//        Maze maze = new Maze(new int[][]{
//                {1,1,0,0,0,0,1,0,0,0},
//                {0,0,0,0,0,1,0,0,0,0},
//                {0,0,0,0,1,0,0,0,0,0},
//                {0,0,0,1,0,1,0,0,0,0},
//                {0,0,0,0,0,0,1,1,1,1},
//                {0,0,0,0,0,0,0,0,1,0},
//                {0,0,1,0,0,0,0,0,0,0},
//                {0,1,0,0,0,0,0,0,0,0},
//                {0,1,0,0,0,0,0,0,0,0},
//                {0,1,0,0,0,0,0,0,0,0},
//                {0,1,1,0,0,1,1,0,1,1}, },
//                new Position(0,1),
//                new Position(8,8)
//        );
//
//
//        long time = System.currentTimeMillis();
//        Maze mazeEncodedDecoded = new Maze(maze.toByteArray());
//        System.out.println(System.currentTimeMillis() - time);
//
//        System.out.println(maze.equals(mazeEncodedDecoded));

//        byte[] byteArr = maze1.mazeMapToByteArray();
//
//        for (int i=0; i< byteArr.length; i++) {
//            System.out.print(String.format("%8s", Integer.toBinaryString(byteArr[i] & 0xFF)).replace(' ', '0'));
//            i++;
//            System.out.println(String.format("%8s", Integer.toBinaryString(byteArr[i] & 0xFF)).replace(' ', '0'));
//        }
//
//        byte[] arr = { (byte)255, (byte)255 , (byte)255, (byte)255, 0x01};
//        System.out.println(Maze.byteArrayToInt(arr));


        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte savedMazeBytes[] = new byte[0];
//        byte savedMazeBytesFirstHalf[] = new byte[maze.toByteArray().length/2];
//        byte savedMazeBytesSecondHalf[] = new byte[(int)Math.ceil(maze.toByteArray().length/2.0)];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
//            in.read(savedMazeBytesFirstHalf);
//            in.read(savedMazeBytesSecondHalf);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.arraycopy(savedMazeBytesFirstHalf, 0, savedMazeBytes, 0, maze.toByteArray().length/2);
//        System.arraycopy(savedMazeBytesSecondHalf, 0, savedMazeBytes, maze.toByteArray().length/2, (int)Math.ceil(maze.toByteArray().length/2.0));

        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals)); //maze should be equal to loadedMaze

        Server.Configurations config = new Server.Configurations();
        System.out.println(config.getProperty("GeneratorClass"));
    }
}
