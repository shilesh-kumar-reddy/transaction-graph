package com.example.transaction_graph.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFound(NotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("error", "NODE_NOT_FOUND", "message", ex.getMessage()));
	}

	@ExceptionHandler(CycleException.class)
	public ResponseEntity<?> handleCycle(CycleException ex) {
		return ResponseEntity.badRequest().body(Map.of("error", "CYCLE_DETECTED", "message", ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(Map.of("error", "INVALID_INPUT", "message", ex.getMessage()));
	}

}
