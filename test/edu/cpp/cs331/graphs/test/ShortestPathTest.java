package edu.cpp.cs331.graphs.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.cpp.cs331.graphs.DirectedEdge;
import edu.cpp.cs331.graphs.Edge;
import edu.cpp.cs331.graphs.Graph;
import edu.cpp.cs331.graphs.Graph.*;
import edu.cpp.cs331.graphs.Vertex;
import edu.cpp.cs331.graphs.cdkilian.DijkstrasSP;
import edu.cpp.cs331.graphs.cdkilian.FloydsSP;

public class ShortestPathTest {
	private Graph testGraph;
	private List<Edge> testGraphShortestPath;
	private Vertex source;
	private Vertex goal;

	@Before
	public void setUp() throws Exception, VertexAlreadyExistsException, InconsistentEdgeException, DuplicateEdgeException {
		// Graph taken from the book
		testGraph = new Graph();
		testGraphShortestPath = new LinkedList<>();

		// Vertices
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);

		// Edges v1 to vX
		DirectedEdge e1to2 = new DirectedEdge(v1, v2, 7);
		DirectedEdge e1to3 = new DirectedEdge(v1, v3, 4);
		DirectedEdge e1to4 = new DirectedEdge(v1, v4, 6);
		DirectedEdge e1to5 = new DirectedEdge(v1, v5, 1);

		// Edges v2 to vX
		// No outgoing edges!

		// Edges v3 to vX
		DirectedEdge e3to2 = new DirectedEdge(v3, v2, 2);
		DirectedEdge e3to4 = new DirectedEdge(v3, v4, 5);

		// Edges v4 to vX
		DirectedEdge e4to2 = new DirectedEdge(v4, v2, 3);

		// Edges v5 to vX
		DirectedEdge e5to4 = new DirectedEdge(v5, v4, 1);

		Vertex[] vertexList = { v1, v2, v3, v4, v5 };
		for(Vertex i : vertexList)
			this.testGraph.addVertex(i);

		Edge[] edgeList = { e1to2, e1to3, e1to4, e1to5, e3to2, e3to4, e4to2, e5to4 };
		for(Edge i : edgeList)
			this.testGraph.addEdge(i);
		
		this.source = v1;
		this.goal = v2;
		
		Edge[] knownShortestPath = { e1to5, e5to4, e4to2 };
		for(Edge i : knownShortestPath)
			this.testGraphShortestPath.add(i);

	}

	@Test
	public void testDijkstras() throws VertexAlreadyExistsException, DuplicateEdgeException, InconsistentEdgeException {

		List<Edge> dijkstrasShortestPath = new DijkstrasSP().genShortestPath(testGraph, source, goal);

		// This example has only one shortest path.
		assertTrue(dijkstrasShortestPath.containsAll(this.testGraphShortestPath));

		assertTrue(dijkstrasShortestPath.size() == testGraphShortestPath.size());
	}

	@Test
	public void testFloyds() throws VertexAlreadyExistsException, DuplicateEdgeException, InconsistentEdgeException {

		List<Edge> floydsShortestPath = new FloydsSP().genShortestPath(testGraph, source, goal);

		// This example has only one shortest path.
		assertTrue(floydsShortestPath.containsAll(this.testGraphShortestPath));

		assertTrue(floydsShortestPath.size() == testGraphShortestPath.size());
	}

}
