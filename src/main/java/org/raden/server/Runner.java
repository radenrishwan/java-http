package org.raden.server;

import org.raden.http.Request;
import org.raden.http.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class Runner implements Runnable {
    private final Socket socket;

    public Runner(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)
        ) {
            StringBuilder rawRequest = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                rawRequest.append(line).append("\r\n");
            }

            Request request = Request.parse(rawRequest.toString());
            Response response = new Response();
            response.body = Preview.html();

            out.println(response.parse());

        } catch (IOException e) {
            System.err.println("An error occurred with client " + socket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                if (!socket.isClosed()) {
                    System.out.println("Closing connection with client: " + socket.getInetAddress());
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}