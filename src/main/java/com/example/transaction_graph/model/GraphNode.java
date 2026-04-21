package com.example.transaction_graph.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GraphNode {

	public String id;
	public String parentId;
	public String name;
	public String accountNumber;

	@JsonProperty("transactions")
	public List<NodeTransaction> transactions;

}
