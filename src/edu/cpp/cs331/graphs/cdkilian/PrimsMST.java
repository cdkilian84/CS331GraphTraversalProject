//Christopher Kilian
//CS 331 - Winter 2017
//Project 2

package edu.cpp.cs331.graphs.cdkilian;

import edu.cpp.cs331.graphs.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//Implementation of Prims Algorithm for finding an MST, based on the Pseudocode provided during class lectures for this algorithm
//Offers O(n^2) runtime through the use of hashsets and their accompanying constant time operations (put, get, contains, etc), as well as a mapping of the frontier that maps those vertices to their cheapest potential 
//edges connecting back to the MST. Pre-processing occurs in the form of the building of a "neighborhood" mapping of each vertex to all of its connected edges.
public class PrimsMST implements MinimumSpanningTree{
    Graph theGraph;
	int graphSize;
	Map<Vertex, HashSet<Edge>> neighborhood; //keys are the vertices, and values are a set of edges which touch it
	
	//interface implementation
	public Graph genMST(Graph g){
		theGraph = g;
		graphSize = theGraph.getvList().size();
		return runPrims();
	}
	
	
	//Primary running of Prims Algorithm --> Runs in O(n^2) time
	public Graph runPrims(){
		buildNeighborhood(); //initialize the neighborhood mapping
		HashSet<Edge> mstEdges = new HashSet<>();
		HashSet<Vertex> seenVertices = new HashSet<>();
		Vertex initial = theGraph.getvList().get(0); //get vertex at index 0 as the default starting vertex
		seenVertices.add(initial); 
		Map<Vertex, Edge> adjacentVertices = new HashMap<Vertex, Edge>(); //adjacentVertices holds all vertices which are adjacent to the set of seen vertices (the frontier) --> This is a disjoint set from the set of "seen" vertices that are already in the MST
		initAdjacentVertices(adjacentVertices, initial); 
		
		while(seenVertices.size() != graphSize){
			Edge shortestEdge = findCheapest(adjacentVertices, seenVertices);
			Vertex workingV;
			
			if(!seenVertices.contains(shortestEdge.getOne())){
				workingV = shortestEdge.getOne();
			}else{
				workingV = shortestEdge.getTwo();
			}
			seenVertices.add(workingV);
			adjacentVertices.remove(workingV); //always remove vertices newly added to the MST from the "adjacent" vertex set
			for(Edge e : neighborhood.get(workingV)){ //build/update the "frontier" of adjacent vertices and their cheapest mapped edges
				Vertex newVert = e.getOne();
				if(newVert.equals(workingV)){
					newVert = e.getTwo(); //make sure to get the vertex that e connects to that ISN'T in seenVertices
				}
				if(!seenVertices.contains(newVert)){
					if(adjacentVertices.containsKey(newVert)){
						if(e.getWeight() < adjacentVertices.get(newVert).getWeight()){
							adjacentVertices.put(newVert, e);
						}
					}else{
						adjacentVertices.put(newVert, e);
					}
				}
			}
			
			mstEdges.add(shortestEdge);
		}
		
		return buildMSTGraph(seenVertices, mstEdges);
	}
	//end method runPrims
	
	
	//Method to initialize the HashMap which contains the vertices on the "frontier" of the MST --> Also ensures that each of these vertices is mapped to its corresponding cheapest edge
	public void initAdjacentVertices(Map<Vertex, Edge> adjacentVertices, Vertex initial){
		HashSet<Edge> adjacentEdges = neighborhood.get(initial);
		Edge cheapestEdge = null;
		int lowestCost = Integer.MAX_VALUE;
		
		for(Edge e : adjacentEdges){
			Vertex newVert = e.getOne();
			if(newVert.equals(initial)){
				newVert = e.getTwo(); //make sure to get the vertex that e connects to that ISN'T in seenVertices
			}
			if(adjacentVertices.containsKey(newVert)){
				if(e.getWeight() < adjacentVertices.get(newVert).getWeight()){
					adjacentVertices.put(newVert, e);
				}
			}else{
				adjacentVertices.put(newVert, e);
			}
		}
	}
	//end method initAdjacentVertices
	
	
	//This method finds the cheapest edge on the "frontier" of the MST which should be the next edge and connected vertex added to the MST
	public Edge findCheapest(Map<Vertex, Edge> adjacentVertices, HashSet<Vertex> seenVertices){
		Edge cheapestEdge = null;
		int lowestCost = Integer.MAX_VALUE;
		
		for(Vertex v : adjacentVertices.keySet()){
			Edge examinedEdge = adjacentVertices.get(v);
			Vertex v1 = examinedEdge.getOne();
			Vertex v2 = examinedEdge.getTwo();
			if((examinedEdge.getWeight() < lowestCost) && (seenVertices.contains(v1) || seenVertices.contains(v2))){
				cheapestEdge = examinedEdge;
				lowestCost = cheapestEdge.getWeight();
			}
		}
		
		return cheapestEdge;
	}
	//end method findCheapest
	

	//create the neighborhood mapping vertices to the edges that connect them
	public void buildNeighborhood(){
		List<Edge> edges = theGraph.geteList();
		neighborhood = new HashMap<Vertex, HashSet<Edge>>();
		
		for(Edge e : edges){
			//build the layout mapping vertices to their neighbors
			//Note edges are undirected, so must map 1 to 2 AND 2 to 1
			if(neighborhood.containsKey(e.getOne())){
				neighborhood.get(e.getOne()).add(e);
			}else{
				HashSet<Edge> temp = new HashSet<Edge>();
				temp.add(e);
				neighborhood.put(e.getOne(), temp);
			}
			if(neighborhood.containsKey(e.getTwo())){
				neighborhood.get(e.getTwo()).add(e);
			}else{
				HashSet<Edge> temp2 = new HashSet<Edge>();
				temp2.add(e);
				neighborhood.put(e.getTwo(), temp2);
			}
		}
	}
	//end method buildNeighborhood
	
	
	//method to build the MST graph which is to be returned --> This method is only to be called after Prims algorithm has run and the edges/vertices for the MST have been collected
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
//end class PrimsMST