package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.LogManager;
import java.util.logging.Logger;


//import Server.Strategies.IServerStrategy;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    //private static final Logger LOG = LogManager.getLogger();

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
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
                    new Thread(() -> {handleClient(clientSocket);}).start();
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
}
