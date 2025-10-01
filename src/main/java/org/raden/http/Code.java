package org.raden.http;

// TODO: add more later
public enum Code {
    OK("200 OK"),
    NOT_FOUND("404 Not Found"),
    BAD_REQUEST("400 Bad Request"),;

    private final String representation;

    Code(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}




