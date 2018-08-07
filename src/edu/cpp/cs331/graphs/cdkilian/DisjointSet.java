//Christopher Kilian
//CS 331 - Winter 2017
//Project 2

package edu.cpp.cs331.graphs.cdkilian;

import edu.cpp.cs331.graphs.*;
import java.util.*;


//This is an implementation of the "Disjoint Set Data Structure II" as found in "Foundations of Algorithms Fifth Edition" by Richard Neapolitan, in Appendix C of the textbook.
//This data structure is to be used by the implementation of KruskalsMST as also described in that textbook. Use of this data structure allows for mlogm runtime of Kruskals, where m = the number of edges in the graph.
public class DisjointSet {
    int size;
	Map<Vertex, Integer> vertexID;
	Node[] contents;
	
	//constructor
	public DisjointSet(List<Vertex> vertices){
		vertexIDMap(vertices);
		contents = new Node[vertices.size()];
		initialize(vertices);
	}
	
	
	//build the data structure by initially making each node of the tree disjoint
	public void initialize(List<Vertex> vertices){
		for(int i = 0; i < contents.length; i++){
			makeSet(i);
		}
	}
	//end method initialize
	
	//Create the "sets" of the data structure
	public void makeSet(int i){
		contents[i] = new Node();
		contents[i].parent = i;
		contents[i].depth = 0;
	}
	//end method makeSet
	
	//get the index value for which "set" a vertex belongs to
	public int find(Vertex v){
		int j = vertexID.get(v);//v.getId();
		
		while(contents[j].parent != j){
			j = contents[j].parent;
		}
		
		return j;
	}
	//end method find
	
	//combines two "sets" so that they all belong to the same group
	public void merge(int p, int q){
		if(contents[p].depth == contents[q].depth){
			contents[p].depth++;
			contents[q].parent = p;
		}else if(contents[p].depth < contents[q].depth){
			contents[p].parent = q;
		}else{
			contents[q].parent = p;
		}
	}
	//end method merge
	
	//Build the ID mapping for vertices --> Since vertex ID values can be anything, create a mapping for each vertex to a value in range 0 to n-1
	public void vertexIDMap(List<Vertex> vertices){
		vertexID = new HashMap<>();
		
		for(int i = 0; i < vertices.size(); i++){
			vertexID.put(vertices.get(i), i);
		}
	}
	//end method vertexIDMap
	
	//the values stored in the sets themselves are "nodes", each containing info about its parent (the "set" it's part of) and its depth in the tree structure
	private class Node{
		int parent;
		int depth;
	}
	
}
//end class DisjointSet