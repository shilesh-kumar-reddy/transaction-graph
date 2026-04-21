package com.example.transaction_graph.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.transaction_graph.model.GraphNode;

@Repository
public class GraphRepository {

	public Map<String, GraphNode> nodeById = new HashMap<>();
	public Map<String, List<GraphNode>> childrenMap = new HashMap<>();

	public GraphNode getNode(String id) {
		return nodeById.get(id);
	}

	public List<GraphNode> getChildren(String parentId) {
		return childrenMap.getOrDefault(parentId, new ArrayList<>());
	}
}
