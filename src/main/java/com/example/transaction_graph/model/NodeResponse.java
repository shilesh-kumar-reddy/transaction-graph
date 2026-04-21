package com.example.transaction_graph.model;

import java.util.List;

public class NodeResponse {

	public GraphNode node;
	public int level;
	public boolean isRoot;
	public boolean isLeaf;

	public List<GraphNode> parentChain;
	public List<ChildNodeDTO> children;

	public List<NodeTransaction> nodeTransactions;
	public List<NodeTransaction> nextLevelTransactions;

	// NEW
	public List<TreeNodeDTO> childrenTree;
}