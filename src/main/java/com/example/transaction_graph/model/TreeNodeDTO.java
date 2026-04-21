package com.example.transaction_graph.model;

import java.util.List;

public class TreeNodeDTO {

	public String id;
	public String name;
	public String accountNumber;

	public List<TreeNodeDTO> children;

	public TreeNodeDTO(String id, String name, String accountNumber) {
		this.id = id;
		this.name = name;
		this.accountNumber = accountNumber;
	}
}