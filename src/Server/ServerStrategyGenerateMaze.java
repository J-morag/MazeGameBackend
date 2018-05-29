package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    //private OutputStream out;
    private IMazeGenerator mazeGenerator;

    public ServerStrategyGenerateMaze(int[] measures) {
        mazeGenerator = new SimpleMazeGenerator();
    }

    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            Object arrFromClient = fromClient.readObject();
            int rows = ((int[])arrFromClient)[0];
            int columns = ((int[])arrFromClient)[1];
            Maze maze = mazeGenerator.generate(rows,columns);
            byte[] mazeByteArr = maze.toByteArray();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(outputStream);
            compressor.write(mazeByteArr);

            toClient.flush();
            toClient.writeObject(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}
