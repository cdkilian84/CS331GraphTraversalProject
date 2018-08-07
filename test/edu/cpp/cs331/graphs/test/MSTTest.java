package edu.cpp.cs331.graphs.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.cpp.cs331.graphs.Edge;
import edu.cpp.cs331.graphs.Graph;
import edu.cpp.cs331.graphs.Graph.DuplicateEdgeException;
import edu.cpp.cs331.graphs.Graph.InconsistentEdgeException;
import edu.cpp.cs331.graphs.Graph.VertexAlreadyExistsException;
import edu.cpp.cs331.graphs.UndirectedEdge;
import edu.cpp.cs331.graphs.Vertex;
import edu.cpp.cs331.graphs.cdkilian.KruskalsMST;
import edu.cpp.cs331.graphs.cdkilian.PrimsMST;

public class MSTTest {

	private Graph testGraph;
	private Graph testGraphMST;

	@Before
	public void setUp() throws Exception, VertexAlreadyExistsException, InconsistentEdgeException, DuplicateEdgeException {
		testGraph = new Graph();
		testGraphMST = new Graph();
		
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);
		
		Vertex[] vList = {v1,v2,v3,v4,v5};
		for(Vertex i : vList)
			testGraph.addVertex(i);
		
		Edge e1to2 = new UndirectedEdge(v1, v2, 1);
		Edge e1to3 = new UndirectedEdge(v1, v3, 3);
		Edge e2to3 = new UndirectedEdge(v2, v3, 7);
		Edge e2to4 = new UndirectedEdge(v2, v4, 6);
		Edge e3to4 = new UndirectedEdge(v3, v4, 4);
		Edge e3to5 = new UndirectedEdge(v3, v5, 2);
		Edge e4to5 = new UndirectedEdge(v4, v5, 5);
		
		Edge[] eList = {e1to2, e1to3, e2to3, e2to4, e3to4, e3to5, e4to5};
		for(Edge i : eList)
			testGraph.addEdge(i);
		
		Edge[] eListMST= {e1to2, e1to3, e3to4, e3to5};
		
		for(Vertex i : vList)
			testGraphMST.addVertex(i);
		for(Edge i : eListMST)
			testGraphMST.addEdge(i);
	}

	@Test
	public void testPrims() {
		Graph primsMST = new PrimsMST().genMST(testGraph);
		
		assertTrue(primsMST.getvList().containsAll(testGraphMST.getvList()));
		assertTrue(primsMST.getvList().size() == testGraphMST.getvList().size());

		assertTrue(primsMST.geteList().containsAll(testGraphMST.geteList()));
		assertTrue(primsMST.geteList().size() == testGraphMST.geteList().size());
	}
	@Test
	public void testKruskals() {
		Graph kruskalsMST = new KruskalsMST().genMST(testGraph);
		
		assertTrue(kruskalsMST.getvList().containsAll(testGraphMST.getvList()));
		assertTrue(kruskalsMST.getvList().size() == testGraphMST.getvList().size());

		assertTrue(kruskalsMST.geteList().containsAll(testGraphMST.geteList()));
		assertTrue(kruskalsMST.geteList().size() == testGraphMST.geteList().size());
	}
}
