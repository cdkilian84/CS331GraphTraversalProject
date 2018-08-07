package edu.cpp.cs331.graphs.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.cpp.cs331.graphs.DirectedEdge;
import edu.cpp.cs331.graphs.Edge;
import edu.cpp.cs331.graphs.Graph;
import edu.cpp.cs331.graphs.Graph.DuplicateEdgeException;
import edu.cpp.cs331.graphs.Graph.InconsistentEdgeException;

import edu.cpp.cs331.graphs.Graph.VertexAlreadyExistsException;
import edu.cpp.cs331.graphs.Vertex;

public class GraphTest {

	@Test
	public void testGraph() {
		// Graph g2 = Graph.genRandomGraph(5, 0.5f);

		// System.out.println(g2);

		Graph g = new Graph();

		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v3_dup = new Vertex(3);

		Edge e1 = new DirectedEdge(v1, v2, 5);
		Edge e1_dup = new DirectedEdge(v1, v2, 9);
		Edge e2 = new DirectedEdge(v2, v3, 1);
		Edge e3 = new DirectedEdge(new Vertex(4), v3, 2);

		assertTrue(successAddVertex(g, v1));
		assertTrue(successAddVertex(g, v2));
		assertTrue(successAddVertex(g, v3));
		assertFalse(successAddVertex(g, v3_dup));

		assertTrue(successAddEdge(g, e1));
		assertFalse(successAddEdge(g, e1_dup));
		assertTrue(successAddEdge(g, e2));
		assertFalse(successAddEdge(g, e3));
	}

	@Test
	public void randomUndirectedGraphTest() throws VertexAlreadyExistsException, DuplicateEdgeException {
		System.out.println("------------------Undirected-------------------");
		Graph g = Graph.genRandomUndirectedGraph(5, 0.25f);

		System.out.println(g);
		System.out.println("-------------------------------------");
	}

	@Test
	public void randomDirectedGraphTest() throws VertexAlreadyExistsException, DuplicateEdgeException {
		System.out.println("------------------Directed-------------------");
		Graph g = Graph.genRandomDirectedGraph(5, 0.25f);

		System.out.println(g);
		System.out.println("-------------------------------------");
	}

	private boolean successAddVertex(Graph g, Vertex v) {
		try {
			g.addVertex(v);
		} catch (VertexAlreadyExistsException e1) {
			return false;
		}
		return true;
	}

	private boolean successAddEdge(Graph g, Edge e) {
		try {
			g.addEdge(e);
		} catch (InconsistentEdgeException e1) {
			return false;
		} catch (DuplicateEdgeException e1) {
			return false;
		}
		return true;
	}
}
