# Transaction Graph Node Explorer

## Tech Stack
- Java 17
- Spring Boot 3.x
- Maven

## How to Run
1. Clone project
2. Run:
   mvn spring-boot:run
3. App runs on:
   http://localhost:8080

## API

### Get Node Details
GET /api/graph/nodes/{id}

Example:
http://localhost:8080/api/graph/nodes/N1

## Response Includes
- Node details
- Level in hierarchy
- Parent chain
- Direct children
- Node transactions
- Next level transactions
- isRoot / isLeaf flags

## Assumptions
- Data is loaded from static JSON
- Missing parent treated as root
- Cycles are detected and handled

## Error Handling
- 404 if node not found
- 400 if cycle detected