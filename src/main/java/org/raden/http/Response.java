package org.raden.http;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public String version;
    public Code code;
    public Map<String, String> headers;
    public String body;

    public Response() {
        this.version = "HTTP/1.1";
        this.code = Code.OK;
        this.headers = new HashMap<>();

        this.headers.put("Content-Type", "text/html");
        this.headers.put("Content-Length", "0");
        this.body = "";
    }

    public String parse() {
        StringBuilder responseStringBuilder = new StringBuilder();

        responseStringBuilder.append(this.version + " " + this.code.toString() + "\r\n");

        this.headers.put("Content-Length", String.valueOf(body.length()));

        responseStringBuilder.append(this.parseHeader());
        responseStringBuilder.append("\r\n");
        responseStringBuilder.append(this.body);

        return responseStringBuilder.toString();
    }

    private String parseHeader() {
        StringBuilder responseStringBuilder = new StringBuilder();

        for (String key : this.headers.keySet()) {
            responseStringBuilder
                    .append(key)
                    .append(": ")
                    .append(this.headers.get(key))
                    .append("\r\n");
        }

        return responseStringBuilder.toString();
    }
}
