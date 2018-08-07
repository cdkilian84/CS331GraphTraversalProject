//Christopher Kilian
//CS 331 - Winter 2017
//Project 2

package edu.cpp.cs331.graphs.cdkilian;

import edu.cpp.cs331.graphs.*;
import java.util.*;

//Implementation of Dijkstras algorithm based on the pseudocode for the algorithm found on Wikipedia
//Offers O(n^2) runtime through the use of Hashmaps to find neighboring edges as well as the maintenance of several arrays of values for which the indices correspond to vertex values
public class DijkstrasSP implements ShortestPath{
	Graph theGraph;
	Map<Vertex, List<Edge>> neighborhood; //keys are the vertices from which an edge starts, value is list of associated edges which leave that vertex
	Map<Vertex, Integer> vertexID;
	
	//interface implementation
	public List<Edge> genShortestPath(Graph G, Vertex source, Vertex goal){
		theGraph = G;
		List<Edge> shortestPath = runDijkstras(source, goal);
		return shortestPath;
	}

	
	//Running of Dijkstras algorithm --> Offers O(n^2) time 
	public List<Edge> runDijkstras(Vertex source, Vertex goal){
		vertexIDMap();
		mapNeighborhood();
		int graphSize = theGraph.getvList().size();
		Vertex currentVertex; //holds the currently examined vertex (ID corresponds to index values of arrays)
		int[] cost = new int[graphSize]; //indices for these arrays correspond to vertex id values (aka vertex id 1 is index 1 in these arrays)
		Edge[] shortPath = new Edge[graphSize]; //store edges that form the shortest path --> indices correspond to vertices, edge stored at vertex i is the edge that led to that vertex
		boolean[] visited = new boolean[graphSize]; //for use in the inner "for" loop --> mirrors those values removed from "vertices" list, without need to run O(n) "contains" method
		List<Vertex> vertices = new ArrayList<Vertex>(theGraph.getvList());
		
		//fill arrays with initial default values (Integer.MAX_VALUE stands in for infinite distance)
		Arrays.fill(cost, Integer.MAX_VALUE);
		cost[vertexID.get(source)] = 0;
		
		while(!vertices.isEmpty()){
			currentVertex = getMinDistanceV(cost, vertices);
			
			if(currentVertex.equals(goal)){ //stop searching if we've reached the "goal" node, as that path will already have been discovered
				break;
			}
			
			vertices.remove(currentVertex); 
			int currentIndex = vertexID.get(currentVertex);//save calling multiple times
			visited[currentIndex] = true;
			List<Edge> edgesToNeighbors = neighborhood.get(currentVertex);
			
			for(Edge e : edgesToNeighbors){ //O(n-1)
				int nextVertexIndex = vertexID.get(e.getTwo());
				if(visited[nextVertexIndex]){
					continue;
				}
				int newCost = cost[currentIndex] + e.getWeight();
				if(newCost < cost[nextVertexIndex]){
					cost[nextVertexIndex] = newCost;
					shortPath[nextVertexIndex] = e; //edge saved at a vertex index i is the shortest path edge that leads to that vertex
				}
			}
		}
		
		return buildEdgePath(source, goal, shortPath);
	}
	//end method runDijkstras
	
	
	//Build the ID mapping for vertices --> Since vertex ID values can be anything, create a mapping for each vertex to a value in range 0 to n-1
	public void vertexIDMap(){
		List<Vertex> vertices = theGraph.getvList();
		vertexID = new HashMap<>();
		
		for(int i = 0; i < vertices.size(); i++){
			vertexID.put(vertices.get(i), i);
		}
	}
	//end method vertexIDMap
	
	
	//Build edge path backwards from the goal to the source --> Returns the requested shortest path
	public List<Edge> buildEdgePath(Vertex source, Vertex goal, Edge[] path){
		Vertex workingVertex = goal;
		List<Edge> shortestPath = new ArrayList<Edge>();

		while(!workingVertex.equals(source)){
			shortestPath.add(path[vertexID.get(workingVertex)]);
			workingVertex = path[vertexID.get(workingVertex)].getOne(); //get source of current edge --> the previous vertex in the path
		}
		
		Collections.reverse(shortestPath); //since path is put in list in reverse order, reverse it here to get proper order
		return shortestPath;
	}
	//end method buildEdgePath
	
	
	//build map which associates each vertex with the edges leaving it
	public void mapNeighborhood(){
		neighborhood = new HashMap<Vertex, List<Edge>>();
		List<Edge> edges = theGraph.geteList();
		
		for(Edge e : edges){
			Vertex edgeStart = e.getOne();
			if(neighborhood.containsKey(edgeStart)){
				neighborhood.get(edgeStart).add(e); //add edge to list of edges associated with this vertex if the vertex already exists as a key in the map
			}else{ //else create new mapping for this vertex along with associated edge list
				List<Edge> edgeList = new ArrayList<Edge>();
				edgeList.add(e);
				neighborhood.put(edgeStart, edgeList);
			}	
		}
	}
	//end method mapNeighborhood
	
	
    //find the index value which corresponds to the next closest neighboring vertex which has not been "visited" yet (node for which all )
	public Vertex getMinDistanceV(int[] cost, List<Vertex> vertices){
		Vertex smallestV = null;
		int smallestVal = Integer.MAX_VALUE;
		
		for(Vertex v : vertices){
			int vertexIndex = vertexID.get(v);
			if(cost[vertexIndex] < smallestVal){
				smallestV = v;
				smallestVal = cost[vertexIndex];
			}
		}

		return smallestV;
	}
	//end method getMinDistanceV
}
//end class DijkstrasSP