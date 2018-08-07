package edu.cpp.cs331.graphs;

import java.util.List;

public interface ShortestPath {
	List<Edge> genShortestPath(Graph G, Vertex source, Vertex goal);
}
