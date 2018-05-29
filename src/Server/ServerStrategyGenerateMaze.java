package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    //private int rows;
    //private int columns;
    private OutputStream out;
    private IMazeGenerator mazeGenerator;

    public ServerStrategyGenerateMaze(int[] measures) {
        mazeGenerator = new SimpleMazeGenerator();
        //this.out = new OutputStream;
    }

    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            //Maze maze = mazeGenerator.generate(rows,columns);
            //byte[] mazeByteArr = maze.toByteArray();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(out);
            //compressor.write(mazeByteArr);

            toClient.flush();
            toClient.writeObject(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
