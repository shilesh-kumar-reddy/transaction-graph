package com.example.transaction_graph.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.transaction_graph.exception.CycleException;
import com.example.transaction_graph.exception.NotFoundException;
import com.example.transaction_graph.model.ChildNodeDTO;
import com.example.transaction_graph.model.GraphNode;
import com.example.transaction_graph.model.NodeResponse;
import com.example.transaction_graph.model.NodeTransaction;
import com.example.transaction_graph.model.TreeNodeDTO;
import com.example.transaction_graph.repository.GraphRepository;

@Service
public class GraphService {

	@Autowired
	private GraphRepository repo;

	public NodeResponse getNodeDetails(String id, int maxDepth) {

		if (maxDepth < 1 || maxDepth > 5) {
			throw new IllegalArgumentException("maxDepth must be between 1 and 5");
		}

		GraphNode node = repo.getNode(id);
		if (node == null) {
			throw new NotFoundException("Graph node " + id + " does not exist");
		}

		NodeResponse res = new NodeResponse();

		GraphNode cleanNode = new GraphNode();
		cleanNode.id = node.id;
		cleanNode.name = node.name;
		cleanNode.parentId = node.parentId;
		cleanNode.accountNumber = node.accountNumber;

		res.node = cleanNode;

		List<GraphNode> parentChain = new ArrayList<>();
		Set<String> visited = new HashSet<>();

		int level = 0;
		String parentId = node.parentId;

		while (parentId != null) {
			if (visited.contains(parentId)) {
				throw new CycleException("Cycle detected at " + parentId);
			}
			visited.add(parentId);

			GraphNode parent = repo.getNode(parentId);
			if (parent == null)
				break;

			parentChain.add(0, parent);
			level++;
			parentId = parent.parentId;
		}

		res.level = level;
		res.parentChain = parentChain;

		res.isRoot = (node.parentId == null || repo.getNode(node.parentId) == null);

		List<GraphNode> children = repo.getChildren(node.id);

		List<ChildNodeDTO> childDTOs = new ArrayList<>();
		for (GraphNode child : children) {
			childDTOs.add(new ChildNodeDTO(child.id, child.name, child.accountNumber));
		}

		res.children = childDTOs;
		res.isLeaf = children.isEmpty();

		res.nodeTransactions = node.transactions != null ? node.transactions : new ArrayList<>();

		List<NodeTransaction> nextLevelTxns = new ArrayList<>();
		for (GraphNode child : children) {
			if (child.transactions != null) {
				nextLevelTxns.addAll(child.transactions);
			}
		}
		res.nextLevelTransactions = nextLevelTxns;

		// NEW TREE LOGIC
		if (maxDepth > 1) {
			res.childrenTree = buildTree(node.id, maxDepth, new HashSet<>());
		}

		return res;
	}

	private List<TreeNodeDTO> buildTree(String nodeId, int depth, Set<String> visited) {

		if (depth == 0)
			return new ArrayList<>();

		List<TreeNodeDTO> result = new ArrayList<>();
		List<GraphNode> children = repo.getChildren(nodeId);

		for (GraphNode child : children) {

			if (visited.contains(child.id))
				continue;
			visited.add(child.id);

			TreeNodeDTO dto = new TreeNodeDTO(child.id, child.name, child.accountNumber);

			dto.children = buildTree(child.id, depth - 1, visited);

			result.add(dto);
		}

		return result;
	}
}