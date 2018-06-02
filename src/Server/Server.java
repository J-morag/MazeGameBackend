package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;


public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService TPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        TPool = Executors.newCachedThreadPool();
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    TPool.execute(() -> {handleClient(clientSocket);});
                } catch (SocketTimeoutException e) {
                }
            }
            TPool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public void stop() {
        stop = true;
    }


    /**
     * This static class handles configurations.
     * Different configuration fields are defined by the enums(static) contained within.
     * The constructor is private, and all fields are static.
     * Adding new fields is done by adding a new enum to code as a member, adding it to the switch case in setProperty, and to store method.
     * adding new options to an existing field (enum) is done by adding the new option to the enum.
     * I'am pretty sure this class is thread safe.
     */
    public static class Configurations{

        public enum generatorClass {
            MyMazeGenerator, SimpleMazeGenerator;
            static generatorClass currValue = MyMazeGenerator;
            public static void setCurrValue(generatorClass value) {currValue = value;}
            public static generatorClass getCurrValue(){return currValue;}
        }
        public enum searchAlgorithm {
            BestFirstSearch, BreadthFirstSearch, DepthFirstSearch, AStar;
            static searchAlgorithm currValue = BestFirstSearch;
            public static void setCurrValue(searchAlgorithm value) {currValue = value;}
            public static searchAlgorithm getCurrValue(){return currValue;}
        }

        private Configurations(){

        }

        public static void setProperty(String prop, String value) {
            try{
//                switch (prop){
//                    case "generatorClass":
//                        generatorClass.setCurrValue(generatorClass.valueOf(value));
//                    case "searchAlgorithm":
//                        searchAlgorithm.setCurrValue(searchAlgorithm.valueOf(value));
//                }
                if (prop.equals("generatorClass"))
                    generatorClass.setCurrValue(generatorClass.valueOf(value));
                else if (prop.equals("searchAlgorithm"))
                    searchAlgorithm.setCurrValue(searchAlgorithm.valueOf(value));
            }
            catch (IllegalArgumentException e){
                System.out.println("attempted to set unrecognized config value " + value + " to field " + prop + ". default value used instead.");
            }
        }

        public static void load(String filePath) {
            Scanner input = null;

            try {

                input = new Scanner(new File(filePath));

                while(input.hasNextLine()) {
                    final String line = input.nextLine();
                    String keyAndVal[] = line.split("-");

                    //if correct format
                    if (2 == keyAndVal.length){
                        setProperty(keyAndVal[0], keyAndVal[1]);
                    }
                }


            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        }

        public static void store(String filePath){
            BufferedWriter output = null;

            try {

                output = new BufferedWriter(new FileWriter(filePath));

                output.write("generatorClass"+"-"+generatorClass.getCurrValue()+'\n');
                output.write("searchAlgorithm"+"-"+searchAlgorithm.getCurrValue()+'\n');


            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
