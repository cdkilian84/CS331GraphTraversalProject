//Christopher Kilian
//CS 331 - Winter 2017
//Project 2

package edu.cpp.cs331.graphs.cdkilian;

import edu.cpp.cs331.graphs.*;
import java.util.*;
import java.util.logging.*;


//Please note that this implementation of Kruskals MST algorithm is based on the version found in "Foundations of Algorithms Fifth Edition" by Richard Neapolitan. It uses an accompanying 
//"Disjoint Set Data Structure" which allows for mlogm runtime (where m=number of edges) by simulating the disjoint sets that the vertices for the graph would fall into without actually needing to maintain those sets.
public class KruskalsMST implements MinimumSpanningTree{
    Graph theGraph;
    int graphSize;
	
	//interface implementation
	public Graph genMST(Graph g){
		theGraph = g;
		graphSize = theGraph.getvList().size();
		return runKruskals();
	}
	
	//Kruskals algorithm --> Runs in (n^2)logn time (mlogm for m=number of vertices)
	public Graph runKruskals(){
		HashSet<Edge> mstEdges = new HashSet<>();
		HashSet<Vertex> mstVertices = new HashSet<>();
		PriorityQueue<Edge> orderedEdges = new PriorityQueue<Edge>(theGraph.geteList()); //O(mlogm)
		DisjointSet theSets = new DisjointSet(theGraph.getvList()); //build the disjoint set data structure which simulates the disjoint sets the vertices would be stored in
		//System.out.println("At the Start, the queue has " + orderedEdges.size() + " values");
		
		while(mstEdges.size() < graphSize-1){ //mstEdges will be 1 smaller than number of vertices when the MST is complete
			Edge e = orderedEdges.poll();
			
			int firstPosIndex = theSets.find(e.getOne());
			int secondPosIndex = theSets.find(e.getTwo());
			
			if(firstPosIndex != secondPosIndex){
				theSets.merge(firstPosIndex, secondPosIndex);
				mstEdges.add(e);
				mstVertices.add(e.getOne()); //since this is a HashSet, no need to check if one or both vertices are already in the set, since copies are not allowed in a HashSet and will simply be discarded
				mstVertices.add(e.getTwo());
			}
		}
		
		//System.out.println("At the end, the queue has " + orderedEdges.size() + " values left");
		return buildMSTGraph(mstVertices, mstEdges);
	}
	//end method runKruskals
	
	
	//method to build the MST graph which is to be returned --> This method is only to be called after Kruskals algorithm has run and the edges/vertices for the MST have been collected
	public Graph buildMSTGraph(HashSet<Vertex> vertices, HashSet<Edge> edges){
		Graph mstGraph = new Graph();
		
		for(Vertex v : vertices){
			try {
				mstGraph.addVertex(v);
			} catch (Graph.VertexAlreadyExistsException ex) {
				Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		for(Edge e : edges){
			try {
				mstGraph.addEdge(e);
			} catch (Graph.InconsistentEdgeException ex) {
				Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Graph.DuplicateEdgeException ex) {
				Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		return mstGraph;
	}
	//end method buildMSTGraph
	
}
//end class KruskalsMST