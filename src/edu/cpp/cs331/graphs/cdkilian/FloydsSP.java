//Christopher Kilian
//CS 331 - Winter 2017
//Project 2

package edu.cpp.cs331.graphs.cdkilian;

import edu.cpp.cs331.graphs.*;
import java.util.*;


//Implementation of Floyds algorithm based on the pseudocode for the algorithm discussed in class and found in "Foundations of Algorithms Fifth Edition" by Richard Neapolitan. 
//Offers O(n^3) runtime and uses two matrices of size n x n, one storing total cost weight values, and the other initially storing the adjacency matrix of edges but eventually storing
//all of the shortest paths. Once Floyds has run, this path matrix can easily be accessed with each row representing a "from this index vertex" to any other vertex in its row. All rows then represent all
//possible shortest paths.
public class FloydsSP implements ShortestPath{
    Graph theGraph;
	int graphSize;
	Edge[][] pathMat;
	int[][] cost;
	Map<Vertex, Integer> vertexID;
	
    
	//interface implementation
    public List<Edge> genShortestPath(Graph G, Vertex source, Vertex goal){
		theGraph = G;
		graphSize = theGraph.getvList().size();
		List<Edge> shortestPath = runFloyds(source, goal);
		return shortestPath;
	}
	
	//Main running of Floyds algorithm --> always runs in O(n^3) time
	public List<Edge> runFloyds(Vertex source, Vertex goal){
		vertexIDMap();
		initHelperMatrices(); //create the pathMat matrix which represents the connections in the graph and will be altered to represent the shortest paths, along with the cost matrix
		
		for(int k = 0; k < graphSize; k++){
			for(int i = 0; i < graphSize; i++){
				for(int j = 0; j < graphSize; j++){
					if((cost[i][k] != Integer.MAX_VALUE) && (cost[k][j] != Integer.MAX_VALUE)){
						if(cost[i][k] + cost[k][j] < cost[i][j]){
							cost[i][j] = cost[i][k] + cost[k][j];
							pathMat[i][j] = pathMat[k][j];
						}
					}
				}
			}
		}
		
		return buildEdgePath(source, goal);
	}
	//end method runFloyds
	
	
	//Build the ID mapping for vertices --> Since vertex ID values can be anything, create a mapping for each vertex to a value in range 0 to n-1
	public void vertexIDMap(){
		List<Vertex> vertices = theGraph.getvList();
		vertexID = new HashMap<>();
		
		for(int i = 0; i < vertices.size(); i++){
			vertexID.put(vertices.get(i), i);
		}
	}
	//end method vertexIDMap
	
	
	//Method to extract the specific shortest path requested from the path matrix after Floyds has been run
	public List<Edge> buildEdgePath(Vertex source, Vertex goal){
		int sourceId = vertexID.get(source);
		Vertex end = goal;
		List<Edge> shortestPath = new ArrayList<Edge>();
		
		while(!end.equals(source)){
			shortestPath.add(pathMat[sourceId][vertexID.get(end)]); 
			end = pathMat[sourceId][vertexID.get(end)].getOne(); //get source of current edge --> the previous vertex in the path
		}
		
		Collections.reverse(shortestPath); //since path is put in list in reverse order, reverse it here to get proper order
		
		return shortestPath;
	}
	//end method buildEdgePath
	
	
	//initialize the pathMat matrix (which at the start is an adjacency matrix representing the graph) and cost matrix to same weight values as the pathMat matrix
	public void initHelperMatrices(){
		List<Edge> edges = theGraph.geteList();
		cost = new int[graphSize][graphSize];
		pathMat = new Edge[graphSize][graphSize];
		
		for(Edge e : edges){
			int vertexOneID = vertexID.get(e.getOne());
			int vertexTwoID = vertexID.get(e.getTwo());

			pathMat[vertexOneID][vertexTwoID] = e;
		}
		
		for(int i = 0; i < graphSize; i++){
			for(int j = 0; j < graphSize; j++){
				if(i != j){ //values at i = j should be 0 for cost matrix
					if(pathMat[i][j] != null){
						cost[i][j] = pathMat[i][j].getWeight();
					}else{
						cost[i][j] = Integer.MAX_VALUE;
					}
				}
			}
		}
	}
	//end method initHelperMatrices
}
//end class FloydsSP