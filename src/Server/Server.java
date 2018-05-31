package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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


    public static class Configurations{

        private HashMap<String, String> configValues;
        private HashMap<String, String[]> acceptedConfigValues;

        public Configurations(){

            configValues = new HashMap<>();
            acceptedConfigValues = new HashMap<>();

            //initialize accepted values
            acceptedConfigValues.put("GeneratorClass", new String[]{"MyMazeGenerator", "SimpleMazeGenerator"});

            //initialize fields with default values
            configValues.put("GeneratorClass", "MyMazeGenerator");


            Scanner input = null;

//            try {

                input = new Scanner("Resources/config.properties");

                // load a properties file
                this.load(input);


//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } finally {
                if (input != null) {
//                    try {
                        input.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }

        public String getProperty(String prop) {
            return configValues.get(prop);
        }

        private void load(Scanner input) {
            while(input.hasNextLine()) {
                final String line = input.nextLine();
                String keyAndVal[] = line.split("-");
                //correct format and configuration exists and value is accepted
                if (2 == keyAndVal.length && configValues.containsKey(keyAndVal[0]) && arrayContainsString(acceptedConfigValues.get(keyAndVal[0]), keyAndVal[1])){
                    configValues.put(keyAndVal[0], keyAndVal[1]);
                }
            }
        }

        private static boolean arrayContainsString(String[] arr, String str){
            for (String s:
                 arr) {
                if (s.equals(str)) return true;
            }
            return false;
        }
    }
}
