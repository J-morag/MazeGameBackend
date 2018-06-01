package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import Server.Server.Configurations;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();

            int[] arrFromClient = (int[])(fromClient.readObject());
            int rows = (arrFromClient)[0];
            int columns = (arrFromClient)[1];


            Configurations.load("Resources/config.properties");
            IMazeGenerator mazeGenerator = null;
//            try{
//                mazeGenerator = (IMazeGenerator)Class.forName(Configurations.generatorClass.getCurrValue().toString()).getDeclaredConstructor().newInstance();
//            }
//            catch (Exception e){
//                mazeGenerator = new MyMazeGenerator();
//            }

            if (Configurations.generatorClass.getCurrValue() == Configurations.generatorClass.MyMazeGenerator) {
                mazeGenerator = new MyMazeGenerator();
            }
            else //if (Configurations.generatorClass.getCurrValue() == Configurations.generatorClass.SimpleMazeGenerator)
                mazeGenerator = new SimpleMazeGenerator();


            Maze maze = mazeGenerator.generate(rows,columns);
            byte[] mazeByteArr = maze.toByteArray();
            ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
            byteArrOut.flush();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteArrOut);
            try{
                compressor.write(mazeByteArr);
                toClient.writeObject(byteArrOut.toByteArray());
                toClient.flush();
                toClient.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
