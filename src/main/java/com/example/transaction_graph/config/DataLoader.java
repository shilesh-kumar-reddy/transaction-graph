package com.example.transaction_graph.config;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.transaction_graph.model.GraphNode;
import com.example.transaction_graph.model.GraphWrapper;
import com.example.transaction_graph.repository.GraphRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    @Autowired
    private GraphRepository repository;

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("transactions-graph-nodes.json").getInputStream();

            GraphWrapper wrapper = mapper.readValue(is, GraphWrapper.class);
            List<GraphNode> nodes = wrapper.nodes;

            for (GraphNode node : nodes) {
                repository.nodeById.put(node.id, node);
            }

            for (GraphNode node : nodes) {
                if (node.parentId != null) {
                    repository.childrenMap
                            .computeIfAbsent(node.parentId, k -> new java.util.ArrayList<>())
                            .add(node);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON", e);
        }
    }
}