# DESIGN.md

## Overview
This service loads a static JSON file representing a graph of nodes and transactions. It provides APIs to explore node hierarchy and transactions.

## Data Loading
- JSON loaded at startup using Jackson
- Stored in memory using:
  - Map<String, GraphNode> nodeById
  - Map<String, List<GraphNode>> childrenMap

## Core Logic

### Level Calculation
- Traverse parent chain until root
- Level = number of hops

### Parent Chain
- Built by traversing parentId
- Stored in correct order (root → parent)

### Children Fetching
- Direct children fetched from childrenMap

### Transactions
- nodeTransactions → transactions of current node
- nextLevelTransactions → aggregated from children

## Edge Cases

### Missing Parent
- Treated as root node
- Level = 0

### Cycle Detection
- Used HashSet to detect loops
- Throws error if cycle found

## API Design
- Used DTO for children to reduce payload
- Separated transactions to avoid duplication

## Scalability
- In-memory maps give O(1) lookup
- Can be extended to DB if needed