package org.raden;

import org.raden.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;

        System.out.println("PORT: " + port);

        Server server = new Server(port);

        try {
            server.start();
        } catch (IOException e) {
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }
}