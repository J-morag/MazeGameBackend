package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public synchronized void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            Maze mazeFromClient = (Maze)(fromClient.readObject());
            //int hashCodeMaze = mazeFromClient.hashCode();
            byte[] mazeByteArr = mazeFromClient.toByteArray();
            int hashCodeMaze = Arrays.hashCode(mazeByteArr);
            SearchableMaze searchableMaze = new SearchableMaze(mazeFromClient);
            ISearchingAlgorithm searcher = new BreadthFirstSearch();
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            try{
                Solution solution = isSolutionExist(tempDirectoryPath,hashCodeMaze);
                if(solution != null){
                    toClient.writeObject(solution);
                    toClient.flush();
                }
                else {
                    solution = searcher.solve(searchableMaze);
                    writeSolutionToFile(tempDirectoryPath,hashCodeMaze,solution);
                    toClient.writeObject(solution);
                    toClient.flush();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Solution isSolutionExist (String directoryPath, int hashCode){

        Solution solution = null;
        Path path = Paths.get(directoryPath + hashCode);
        if(Files.exists(path)){
            try{
                FileInputStream inputFile = new FileInputStream(directoryPath + hashCode);
                ObjectInputStream inFileObj = new ObjectInputStream(inputFile);
                solution = (Solution)inFileObj.readObject();
                inputFile.close();
                inFileObj.close();
            }catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return solution;
    }

    private void writeSolutionToFile (String directoryPath, int hashCode, Solution solution){

        try{
            FileOutputStream outFile = new FileOutputStream(directoryPath + hashCode);
            ObjectOutputStream outFileObj = new ObjectOutputStream(outFile);
            outFileObj.writeObject(solution);

            outFile.flush();
            outFileObj.flush();
            outFile.close();
            outFileObj.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
