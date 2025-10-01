package org.raden.http;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public String method;
    public String path;
    public String version;
    public Map<String, String> headers;
    public String body;

    public static Request parse(String raw) {
        if (raw == null || raw.isBlank()) {
            System.err.println("Received an empty request.");
            return null;
        }

        Request request = new Request();
        request.headers = new HashMap<>();

        final String[] headAndBody = raw.split("\r\n\r\n", 2);
        String head = headAndBody[0];
        request.body = headAndBody.length > 1 ? headAndBody[1] : "";

        String[] headLines = head.split("\r\n");

        final String[] firstLineSplit = headLines[0].split(" ");

        if (firstLineSplit.length != 3) {
            System.err.println("Invalid request line: " + headLines[0]);
            return null;
        }

        request.method = firstLineSplit[0];
        request.path = firstLineSplit[1];
        request.version = firstLineSplit[2];

        for (int i = 1; i < headLines.length; i++) {
            String headerLine = headLines[i];
            String[] headerParts = headerLine.split(": ", 2);
            if (headerParts.length == 2) {
                request.headers.put(headerParts[0], headerParts[1]);
            }
        }

        return request;
    }

    @Override
    public String toString() {
        return "Request [\n" +
                "  method=" + method + ",\n" +
                "  path=" + path + ",\n" +
                "  version=" + version + ",\n" +
                "  headers=" + headers + ",\n" +
                "  body='" + body + "'\n" +
                "]";
    }
}