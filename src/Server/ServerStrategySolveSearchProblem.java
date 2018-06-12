package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import Server.Server.Configurations;


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
            byte[] mazeByteArr = mazeFromClient.toByteArray();
            int hashCodeMaze = Arrays.hashCode(mazeByteArr);
            SearchableMaze searchableMaze = new SearchableMaze(mazeFromClient);

            SearchableMaze searchableProblem = new SearchableMaze(mazeFromClient);

            ISearchingAlgorithm searcher = null;

            Configurations.load("resources/config.properties");

            if (Configurations.searchAlgorithm.getCurrValue() == Configurations.searchAlgorithm.BestFirstSearch)
                searcher = new BestFirstSearch();
            else if (Configurations.searchAlgorithm.getCurrValue() == Configurations.searchAlgorithm.BreadthFirstSearch)
                searcher = new BreadthFirstSearch();
            else if (Configurations.searchAlgorithm.getCurrValue() == Configurations.searchAlgorithm.DepthFirstSearch)
                searcher = new DepthFirstSearch();
            else {
                //if(Configurations.searchAlgorithm.getCurrValue() == Configurations.searchAlgorithm.AStar)
                searcher = new BestFirstSearch();
                searchableProblem = new HeuristicSearchableMaze(mazeFromClient);
            }

            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            try{
                Solution solution = isSolutionExist(tempDirectoryPath,hashCodeMaze);
                if(solution != null){
                    //read the solution from the exiting file
                    toClient.writeObject(solution);
                    toClient.flush();
                }
                else {
                    //solve the maze
                    solution = searcher.solve(searchableMaze);
                    //save solution in the temp directory
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

    /**
     * check if solution is already exists in the given directory (if it has a file).
     * @return the solution if exists, null if not.
     */
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

    /**
     * Create a new file in the given directory, and write the solution to the file.
     */
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
