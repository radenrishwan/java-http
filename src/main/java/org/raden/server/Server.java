package org.raden.server;

import org.raden.http.Response;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    private Thread serverThread;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.bind(new InetSocketAddress("0.0.0.0", port));
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = this.serverSocket.accept();
            System.out.printf("New client connected: %s\n", socket.getRemoteSocketAddress());

            Runnable r = new Runner(socket);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
