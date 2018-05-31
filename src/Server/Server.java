package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Scanner;

//import Server.Strategies.IServerStrategy;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    //private static final Logger LOG = LogManager.getLogger();
    private ExecutorService TPool;
//    private ServerSocket serverSocket;
//    private Socket clientSocket;
//    private  int numOfClients;

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        TPool = Executors.newCachedThreadPool();
//        numOfClients = 0;
//        serverSocket = null;
//        clientSocket = null;
    }

    public void start() throws IOException {
        new Thread(() -> {
            runServer();
        }).start();
    }

    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            //LOG.info(String.format("Server started (port: %s, listening Interval: %s)",port,listeningIntervalMS));
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    //LOG.info(String.format("Client excepted: %s",clientSocket.toString()));
                    //Thread runnable = new Thread(() -> {handleClient(clientSocket);});
                    TPool.execute(() -> {handleClient(clientSocket);});
                } catch (SocketTimeoutException e) {
                    //LOG.debug("Socket Timeout - no Client requests!");
                }
            }
            TPool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    public void stop() {
        //LOG.info("Stopping server..");
        stop = true;
    }


//    private static class ServerThread implements Runnable {
//
//        Server server=null;
//        Socket client=null;
//        BufferedReader cin;
//        PrintStream cout;
//        Scanner sc=new Scanner(System.in);
//        int id;
//        String s;
//
//        ServerThread(Socket client, int count ,Server server ) throws IOException {
//
//            this.client=client;
//            this.server=server;
//            this.id=count;
//            System.out.println("Connection "+id+"established with client "+client);
//            cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
//            cout=new PrintStream(client.getOutputStream());
//        }
//
//        @Override
//        public void run() {
//            int x=1;
//            try {
//                while (true) {
//                    s = cin.readLine();
//                    System.out.print("Client(" + id + ") :" + s + "\n");
//                    System.out.print("Server : ");
//                    //s=stdin.readLine();
//                    s = sc.nextLine();
//                    if (s.equalsIgnoreCase("bye")) {
//                        cout.println("BYE");
//                        x = 0;
//                        System.out.println("Connection ended by server");
//                        break;
//                    }
//                    cout.println(s);
//                }
//                cin.close();
//                client.close();
//                cout.close();
//                if (x == 0) {
//                    System.out.println("Server cleaning up.");
//                    System.exit(0);
//                }
//            }
//            catch(IOException ex) {
//                System.out.println("Error : " + ex);
//            }
//        }
//    }


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
            MYMAZEGENERATOR, SIMPLEMAZEGENERATOR;
            static generatorClass currValue = MYMAZEGENERATOR;
            public static void setCurrValue(generatorClass value) {currValue = value;}
            public static generatorClass getCurrValue(){return currValue;}
        }
        public enum searchAlgorithm {
            BESTFIRSTSEARCH, BREADTHFIRSTSEARCH, DEPTHFIRSTSEARCH, ASTAR;
            static searchAlgorithm currValue = BESTFIRSTSEARCH;
            public static void setCurrValue(searchAlgorithm value) {currValue = value;}
            public static searchAlgorithm getCurrValue(){return currValue;}
        }

        private Configurations(){

//            configFields = new HashMap<>();
//            acceptedConfigValues = new HashMap<>();

//            //initialize accepted values
//            acceptedConfigValues.put("GeneratorClass", new String[]{"MyMazeGenerator", "SimpleMazeGenerator"});
//
//
//            //initialize fields with default values
//            configFields.put("GeneratorClass", "MyMazeGenerator");
//
//            Scanner input = null;
//
//            try {
//
//                input = new Scanner(new File("Resources/config.properties"));
//
//                // load a properties file
//                this.load(input);
//
//
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } finally {
//                if (input != null) {
////                    try {
//                        input.close();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//                }
//            }
        }

//        public Configurations getInstance(){
//            if (null == instance) instance = new Configurations();
//            return instance;
//        }

//        public Enum getProperty(String prop) {
//            switch (prop){
//                case "generatorClass":
//                    return generatorClass.getCurrValue();
//            }
//            return null;
//        }

        public static void setProperty(String prop, String value) {
            try{
                switch (prop){
                    case "generatorClass":
                        generatorClass.setCurrValue(generatorClass.valueOf(value));
                    case "searchAlgorithm":
                        searchAlgorithm.setCurrValue(searchAlgorithm.valueOf(value));
                }
            }
            catch (IllegalArgumentException e){
                System.out.println("IllegalArgumentException: attempted to set unrecognized config value " + value + " to field " + prop);
            }
        }

//        private void load(Scanner input) {
//            while(input.hasNextLine()) {
//                final String line = input.nextLine();
//                String keyAndVal[] = line.split("-");
//
//                //if correct format and configuration exists and value is accepted
//                if (2 == keyAndVal.length && configFields.containsKey(keyAndVal[0]) && arrayContainsString(acceptedConfigValues.get(keyAndVal[0]), keyAndVal[1])){
//                    configFields.put(keyAndVal[0], keyAndVal[1]);
//                }
//            }
//        }
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
//                    try {
                    input.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
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

//        private static boolean arrayContainsString(String[] arr, String str){
//            for (String s:
//                 arr) {
//                if (s.equals(str)) return true;
//            }
//            return false;
//        }

    }
}
