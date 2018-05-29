package Server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
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

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        TPool = Executors.newCachedThreadPool();
    }

    public void start() {
        new Thread(() -> {runServer();}).start();
    }

    public void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            //LOG.info(String.format("Server started (port: %s, listening Interval: %s)",port,listeningIntervalMS));
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    //LOG.info(String.format("Client excepted: %s",clientSocket.toString()));
                    //Thread runnable = new Thread(() -> {handleClient(clientSocket);}).start();
                    //TPool.execute(runnable);
                } catch (SocketTimeoutException e) {
                    //LOG.debug("Socket Timeout - no Client requests!");
                }
            }
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

            try {

                input = new Scanner(new File("Resources/config.properties"));

                // load a properties file
                this.load(input);


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

        public String getProperty(String prop) {
            return configValues.get(prop);
        }
        public void setProperty(String prop, String value) {
            if (arrayContainsString(acceptedConfigValues.get(prop), value)){
                configValues.put(prop, value);
            }
        }

        private void load(Scanner input) {
            while(input.hasNextLine()) {
                final String line = input.nextLine();
                String keyAndVal[] = line.split("-");

                //if correct format and configuration exists and value is accepted
                if (2 == keyAndVal.length && configValues.containsKey(keyAndVal[0]) && arrayContainsString(acceptedConfigValues.get(keyAndVal[0]), keyAndVal[1])){
                    configValues.put(keyAndVal[0], keyAndVal[1]);
                }
            }
        }

        public void store(){
            BufferedWriter output = null;

            try {

                output = new BufferedWriter(new FileWriter("Resources/config.properties"));

                Iterator it = configValues.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry)it.next();
                    output.write(pair.getKey()+"-"+pair.getValue()+'\n');
                    it.remove(); // avoids a ConcurrentModificationException
                }

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

        private static boolean arrayContainsString(String[] arr, String str){
            for (String s:
                 arr) {
                if (s.equals(str)) return true;
            }
            return false;
        }
    }
}
