package edu.cpp.cs331.graphs;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Graph {
	protected List<Vertex> vList;
	protected List<Edge> eList;

	public Graph() {
		this.vList = new ArrayList<>();
		this.eList = new ArrayList<>();
	}

	public List<Vertex> getvList() {
		return vList;
	}

	public List<Edge> geteList() {
		return eList;
	}

	public boolean removeVertex(Vertex v) {
		return this.vList.remove(v);
	}

	public boolean removeEdge(Edge e) {
		return this.eList.remove(e);
	}

	public void addEdge(Edge e) throws InconsistentEdgeException, DuplicateEdgeException {
		if (!vList.contains(e.getOne()) || !vList.contains(e.getTwo()))
			throw new InconsistentEdgeException();
		if (eList.contains(e))
			throw new DuplicateEdgeException();

		eList.add(e);
	}

	public void addVertex(Vertex v) throws VertexAlreadyExistsException {
		if (vList.contains(v))
			throw new VertexAlreadyExistsException();

		vList.add(v);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("-------------\n");
		sb.append("Vertex Set\n-------------\n");

		for (Vertex i : this.vList)
			sb.append(i + "\n");
		sb.append("-------------\n");

		sb.append("Edge Set\n-------------\n");
		for (Edge i : this.eList)
			sb.append(i + "\n");

		return sb.toString();
	}

	public static void genUndirectedHeavySpine(Graph g, int size) throws VertexAlreadyExistsException, DuplicateEdgeException
	{
		Vertex prev = null;
		for (int i = 0; i < size; i++) {
			Vertex cur = new Vertex(i);

			g.addVertex(cur);

			// Generate a heavy spine to the graph that guarantees the graph is
			// connected.
			if (prev != null)
				try {
					g.addEdge(new UndirectedEdge(prev, cur, Integer.MAX_VALUE / size));
				} catch (InconsistentEdgeException e) {
					e.printStackTrace();
				}
			prev = cur;
		}
		
	}

	public static void genDirectedHeavySpine(Graph g, int size) throws VertexAlreadyExistsException, DuplicateEdgeException
	{
		Vertex prev = null;
		for (int i = 0; i < size; i++) {
			Vertex cur = new Vertex(i);

			g.addVertex(cur);

			// Generate a heavy spine to the graph that guarantees the graph is
			// connected.
			if (prev != null)
				try {
					g.addEdge(new DirectedEdge(prev, cur, Integer.MAX_VALUE / size));
					g.addEdge(new DirectedEdge(cur, prev, Integer.MAX_VALUE / size));
				} catch (InconsistentEdgeException e) {
					e.printStackTrace();
				}
			prev = cur;
		}
		
	}
	// Will generate a random graph with 'size' number of verteces and density
	// value ranging from 0 to 1.
	// 1 contains all possible edges
	// 0 will only contain the minimum number in order to have a connected
	// graph.
	public static Graph genRandomUndirectedGraph(int size, float density)
			throws VertexAlreadyExistsException, DuplicateEdgeException {
		Graph g = new Graph();
		Random gen = new Random(System.currentTimeMillis());
		
		genUndirectedHeavySpine(g, size);

		for (int i = 0; i < size; i++)
			for(int j = i+1; j < size; j++)
			{
				if (gen.nextFloat() <= density)
					try {
						g.addEdge(new UndirectedEdge(g.getvList().get(i), g.getvList().get(j), genRandomEdgeWeight(gen, size)));
					} catch (InconsistentEdgeException e) {
						continue;
					} catch (DuplicateEdgeException e) {
						continue;
					}
			}

		return g;
	}

	public static Graph genRandomDirectedGraph(int size, float density)
			throws VertexAlreadyExistsException, DuplicateEdgeException {
		Graph g = new Graph();
		Random gen = new Random(System.currentTimeMillis());
		
		genDirectedHeavySpine(g, size);

		for (Vertex i : g.vList)
			for (Vertex j : g.vList)
				if (!i.equals(j) && gen.nextFloat() <= density)
					try {
						g.addEdge(new DirectedEdge(i, j, genRandomEdgeWeight(gen, size)));
					} catch (InconsistentEdgeException e) {
						continue;
					} catch (DuplicateEdgeException e) {
						continue;
					}

		return g;
	}

	private static int genRandomEdgeWeight(Random gen, int size) {
		return gen.nextInt(Integer.MAX_VALUE / size);
	}

	public class InconsistentEdgeException extends Throwable {
		private static final long serialVersionUID = -5864389640379020322L;
	}

	public class DuplicateEdgeException extends Throwable {
		private static final long serialVersionUID = 6968422280902254673L;
	}

	public class VertexAlreadyExistsException extends Throwable {
		private static final long serialVersionUID = -3414361468093067837L;
	}
}
