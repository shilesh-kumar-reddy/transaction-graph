package com.example.transaction_graph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction_graph.model.NodeResponse;
import com.example.transaction_graph.service.GraphService;

@RestController
@RequestMapping("/api/graph")
public class GraphController {

	@Autowired
	private GraphService service;

	@GetMapping("/nodes/{id}")
	public NodeResponse getNode(@PathVariable String id, @RequestParam(defaultValue = "1") int maxDepth) {
		return service.getNodeDetails(id, maxDepth);
	}
	
}