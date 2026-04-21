package com.example.transaction_graph.exception;

public class CycleException extends RuntimeException {
    public CycleException(String message) {
        super(message);
    }
}