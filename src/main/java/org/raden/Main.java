package org.raden;

import org.raden.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server(8080);

        try {
            server.start();
        } catch (IOException e) {
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }
}