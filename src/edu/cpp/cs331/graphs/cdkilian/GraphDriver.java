//Christopher Kilian
//CS 331 - Winter 2017
//Project 2

package edu.cpp.cs331.graphs.cdkilian;

import edu.cpp.cs331.graphs.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//Please disregard this class --> It is for testing purposes only and not intended for review or grading.
//It's a mess in here!
public class GraphDriver {
    
	public static void main(String[] args) throws Graph.VertexAlreadyExistsException{
            try {
                new GraphDriver();
            } catch (Graph.InconsistentEdgeException ex) {
                Logger.getLogger(GraphDriver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Graph.DuplicateEdgeException ex) {
                Logger.getLogger(GraphDriver.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	public GraphDriver() throws Graph.InconsistentEdgeException, Graph.DuplicateEdgeException, Graph.VertexAlreadyExistsException{
		Graph myTest = new Graph();
		Graph mstTest = new Graph();
		//DijkstrasSP dijkstra = new DijkstrasSP();
		//FloydsSP floyd = new FloydsSP();
		PrimsMST prims = new PrimsMST();
		KruskalsMST kruskals = new KruskalsMST();
		long startTime;
		long endTime;
		long elapsedTime;
		long timeInMicros;
		
		
		PrintStream ps = null;
                try {
                    ps = new PrintStream("runtimes.csv"); //make CSV file for reading into Excel
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GraphDriver.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("PROBLEM WITH PRINTSTREAM");
                    System.exit(0);
                }
		
		
		Vertex v0 = new Vertex(0);
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);
		
		myTest.addVertex(v0);
		myTest.addVertex(v1);
		myTest.addVertex(v2);
		myTest.addVertex(v3);
		myTest.addVertex(v4);
		myTest.addVertex(v5);
		
		//MST
		mstTest.addVertex(v0);
		mstTest.addVertex(v1);
		mstTest.addVertex(v2);
		mstTest.addVertex(v3);
		mstTest.addVertex(v4);
		mstTest.addVertex(v5);
		
		
		//Edge(Vertex one, Vertex two, int weight)
		try{
			//textbook sample graph
			/*
			myTest.addEdge(new DirectedEdge(v0, v1, 1));
			myTest.addEdge(new DirectedEdge(v0, v3, 1));
			myTest.addEdge(new DirectedEdge(v0, v4, 5));
			myTest.addEdge(new DirectedEdge(v1, v0, 9));
			myTest.addEdge(new DirectedEdge(v1, v2, 3));
			myTest.addEdge(new DirectedEdge(v1, v3, 2));
			myTest.addEdge(new DirectedEdge(v2, v3, 4));
			myTest.addEdge(new DirectedEdge(v3, v2, 2));
			myTest.addEdge(new DirectedEdge(v3, v4, 3));
			myTest.addEdge(new DirectedEdge(v4, v0, 3));
			*/
                        
			myTest.addEdge(new DirectedEdge(v0, v1, 2));
			myTest.addEdge(new DirectedEdge(v0, v2, 4));
			myTest.addEdge(new DirectedEdge(v1, v0, 2));
			myTest.addEdge(new DirectedEdge(v1, v2, 1));
			myTest.addEdge(new DirectedEdge(v1, v3, 10));
			myTest.addEdge(new DirectedEdge(v1, v4, 7));
			myTest.addEdge(new DirectedEdge(v2, v0, 5));
			myTest.addEdge(new DirectedEdge(v2, v1, 2));
			myTest.addEdge(new DirectedEdge(v2, v4, 5));
			myTest.addEdge(new DirectedEdge(v3, v4, 4));
			myTest.addEdge(new DirectedEdge(v3, v5, 1));
			myTest.addEdge(new DirectedEdge(v4, v2, 4));
			myTest.addEdge(new DirectedEdge(v4, v3, 3));
			myTest.addEdge(new DirectedEdge(v4, v5, 8));
			myTest.addEdge(new DirectedEdge(v4, v1, 7));
			myTest.addEdge(new DirectedEdge(v5, v3, 3));
			myTest.addEdge(new DirectedEdge(v5, v4, 6));
			
			//MST - undirected graph from book
			/*
			mstTest.addEdge(new UndirectedEdge(v0, v1, 1));
			mstTest.addEdge(new UndirectedEdge(v0, v2, 3));
			mstTest.addEdge(new UndirectedEdge(v1, v2, 3));
			mstTest.addEdge(new UndirectedEdge(v1, v3, 6));
			mstTest.addEdge(new UndirectedEdge(v2, v3, 4));
			mstTest.addEdge(new UndirectedEdge(v2, v4, 2));
			mstTest.addEdge(new UndirectedEdge(v3, v4, 5));
			*/
			//My Undirected graph
			mstTest.addEdge(new UndirectedEdge(v0, v2, 4));
			mstTest.addEdge(new UndirectedEdge(v1, v0, 2));
			mstTest.addEdge(new UndirectedEdge(v1, v2, 1));
			mstTest.addEdge(new UndirectedEdge(v1, v3, 10)); 
			mstTest.addEdge(new UndirectedEdge(v1, v4, 7));
			mstTest.addEdge(new UndirectedEdge(v4, v2, 4));
			mstTest.addEdge(new UndirectedEdge(v4, v3, 3));
			mstTest.addEdge(new UndirectedEdge(v5, v3, 3));
			mstTest.addEdge(new UndirectedEdge(v5, v4, 6));
			
		}catch(Exception x){
			System.out.println("ARG!");
		}
		
		/*
		for(int k = 0; k < 20; k++){
		//Test Dijkstra vs Floyds:
		long floydsTime = 0;
		long dijkstrasTime = 0;
		int i = 0;
		int j = 1;
		System.out.println("Generating graph...");
		Graph testDvF = new Graph();
		testDvF = testDvF.genRandomDirectedGraph(1000, 0.25f);
		DijkstrasSP dijkstra2 = new DijkstrasSP();
		FloydsSP floyd2 = new FloydsSP();
		startTime = System.nanoTime();
		List<Edge> shortestPathFloyd = floyd2.genShortestPath(testDvF, testDvF.getvList().get(0), testDvF.getvList().get(999));
		endTime = System.nanoTime();
		floydsTime += (endTime - startTime);
		startTime = System.nanoTime();
		List<Edge> shortestPathDijkstra = dijkstra2.genShortestPath(testDvF, testDvF.getvList().get(0), testDvF.getvList().get(999));
		endTime = System.nanoTime();
		dijkstrasTime += (endTime - startTime);
		System.out.println("Dijkstra SP: " + shortestPathDijkstra);
		System.out.println("Floyd SP: " + shortestPathFloyd);
		
		while((dijkstrasTime < floydsTime) && (i < 300)){
			i++;
			if(i%10 == 0){
				j++;
			}
			startTime = System.nanoTime();
			shortestPathFloyd = floyd2.buildEdgePath(testDvF.getvList().get(i), testDvF.getvList().get(999-j));
			endTime = System.nanoTime();
			floydsTime += (endTime - startTime);
			startTime = System.nanoTime();
			shortestPathDijkstra = dijkstra2.genShortestPath(testDvF, testDvF.getvList().get(i), testDvF.getvList().get(999-j));
			endTime = System.nanoTime();
			dijkstrasTime += (endTime - startTime);
			if(!shortestPathFloyd.containsAll(shortestPathDijkstra) || (shortestPathFloyd.size() != shortestPathDijkstra.size())){
				System.out.println("Shortest Path from vertex " + i + " to vertex " + (999-j));
				System.out.println("Dijkstra path contains " + shortestPathDijkstra.size() + " vertices");
				System.out.println("Floyd path contains " + shortestPathFloyd.size() + " vertices");
				int dijkstraCost = 0;
				int floydCost = 0;
				for(Edge e : shortestPathFloyd){
					if(!shortestPathDijkstra.contains(e)){
						System.out.println("Floyds SP contains " + e + " but not Dijkstras!!!");
					}
					floydCost += e.getWeight();
				}
				for(Edge e : shortestPathDijkstra){
					if(!shortestPathFloyd.contains(e)){
						System.out.println("Dijkstras SP contains " + e + " but not Floyds!!!");
					}
					dijkstraCost += e.getWeight();
				}
				System.out.println("Total Dijkstra Cost was:" + dijkstraCost);
				System.out.println("Total Floyd Cost was:" + floydCost);
				break;
			}
		}
		System.out.println("Number of iterations (shortest paths found): " + (i+1));
		System.out.println("Final total runtime for Dijkstras: " + dijkstrasTime);
		System.out.println("Final total runtime for Floyds: " + floydsTime);
		}
		*/
		
		
		/*
		long totalTime = 0;
		Graph randMST = new Graph();
		randMST = randMST.genRandomUndirectedGraph(500, 1.0f);
		startTime = System.nanoTime();
		PriorityQueue<Edge> orderedEdges = new PriorityQueue<Edge>(randMST.geteList());
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Initializing queue with the list takes: " + totalTime);
		startTime = System.nanoTime();
		PriorityQueue<Edge> orderedEdges2 = new PriorityQueue<Edge>();
		for(Edge e : randMST.geteList()){
			orderedEdges2.add(e);
		}
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("Building queue takes: " + totalTime);
		*/
		
		
		
		for(int i = 4; i <= 10; i++){
			long totalTime = 0;
			long totalTime2 = 0;
			int n  = (int) Math.pow(2, i);
			ps.print(n);
			
			/*
			//SPT Testing
			System.out.println("Generating graph of size " + n + "...");
			Graph newTest = new Graph();
			startTime = System.nanoTime();
			newTest = newTest.genRandomDirectedGraph(n, 1.0f);
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			long timeInSeconds = TimeUnit.SECONDS.convert(totalTime, TimeUnit.NANOSECONDS);
			System.out.println("Random graph generated in " + timeInSeconds +" seconds. About to find shortest path...");
			
			
			Graph newTestCOPY = new Graph();
			for(Vertex v : newTest.getvList()){
				try {
					newTestCOPY.addVertex(v);
				} catch (Graph.VertexAlreadyExistsException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding vertex to MSTGraph!! The vertex already exists!");
				}
			}
			
			for(Edge e : newTest.geteList()){
				try {
					newTestCOPY.addEdge(e);
				} catch (Graph.InconsistentEdgeException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding edge to MSTGraph!! The vertices for this edge don't exist!");
				} catch (Graph.DuplicateEdgeException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding edge to MSTGraph!! This edge already exists!");
				}
			}
			
			
			Vertex start = newTest.getvList().get(0);
			Vertex end = newTest.getvList().get((n-5));
			totalTime = 0;
			totalTime2 = 0;
			for(int j = 0; j < 20; j++){
				DijkstrasSP dijkstra = new DijkstrasSP();
				FloydsSP floyd = new FloydsSP();
				System.out.println("Now running Dijkstras...");
				startTime = System.nanoTime();
				List<Edge> shortestPath = dijkstra.genShortestPath(newTest, start, end);
				endTime = System.nanoTime();
				totalTime += (endTime - startTime);
				System.out.println("Now running Floyds...");
				startTime = System.nanoTime();
				List<Edge> shortestPath2 = floyd.genShortestPath(newTestCOPY, start, end);
				endTime = System.nanoTime();
				totalTime2 += (endTime - startTime);
			}
			totalTime = totalTime/20;
			totalTime2 = totalTime2/20;
			//timeInMicros = TimeUnit.MICROSECONDS.convert(totalTime, TimeUnit.NANOSECONDS);
			System.out.println("Average runtime for Dijkstras was " + totalTime);
			ps.print("," + totalTime);
			System.out.println("Average runtime for Floyds was " + totalTime2);
			ps.println("," + totalTime2);
			*/
			
			
			//Random MST testing
			Graph randMST = new Graph();
			System.out.println("Generating graph of size " + n + "...");
			startTime = System.nanoTime();
			randMST = randMST.genRandomUndirectedGraph(n, 1.0f);
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			long timeInSeconds = TimeUnit.SECONDS.convert(totalTime, TimeUnit.NANOSECONDS);
			
			Graph newTestCOPY = new Graph();
			for(Vertex v : randMST.getvList()){
				try {
					newTestCOPY.addVertex(v);
				} catch (Graph.VertexAlreadyExistsException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding vertex to MSTGraph!! The vertex already exists!");
				}
			}
			
			for(Edge e : randMST.geteList()){
				try {
					newTestCOPY.addEdge(e);
				} catch (Graph.InconsistentEdgeException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding edge to MSTGraph!! The vertices for this edge don't exist!");
				} catch (Graph.DuplicateEdgeException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding edge to MSTGraph!! This edge already exists!");
				}
			}
			
			System.out.println("Random graph generated in " + timeInSeconds +" seconds. About to find shortest path...");
			
			long totalTimeP = 0;
			long totalTimeK = 0;
			for(int j = 0; j < 20; j++){
				long startTimeP = 0;
				long endTimeP = 0;
				PrimsMST prims2 = new PrimsMST();
				KruskalsMST kruskals2 = new KruskalsMST();
				System.out.println("Now running Kruskals...");
				startTime = System.nanoTime();
				Graph kruskalsMST = kruskals2.genMST(randMST);
				endTime = System.nanoTime();
				totalTimeK += (endTime - startTime);
				System.out.println("Now running Prims...");
				startTimeP = System.nanoTime();
				Graph primsMST = prims2.genMST(newTestCOPY);
				endTimeP = System.nanoTime();
				totalTimeP += (endTimeP - startTimeP);
			}
			totalTimeP = totalTimeP/20;
			totalTimeK = totalTimeK/20;
			
			System.out.println("Average runtime for Prims was " + totalTimeP);
			ps.print("," + totalTimeP);
			System.out.println("Average runtime for Kruskals was " + totalTimeK);
			ps.println("," + totalTimeK);
			
			
			//ps.println("," + totalTime);
		}
		
		
		
		/*
		System.out.println(myTest.toString());
		System.out.println("Now running Floyds...");
		List<Edge> shortestPathF = floyd.genShortestPath(myTest, new Vertex(3), new Vertex(0));
		
		System.out.println(shortestPathF.toString());
		System.out.println("\n\n");
		
		System.out.println("Now running Dijkstras...");
		List<Edge> shortestPath = dijkstra.genShortestPath(myTest, new Vertex(3), new Vertex(0));
		
		System.out.println(shortestPath.toString());
		System.out.println("\n\n");
		*/
		
		//Prims vs Kruskals comparison testing:
		/*
		boolean exit = false;
		int runs = 1;
		while(!exit && runs < 100){
			System.out.println("Run: " + runs);
			//Random MST testing
			Graph randMST = new Graph();
			System.out.println("Generating graph....");
			randMST = randMST.genRandomUndirectedGraph(500, 0.5f);
			
			//to ensure no crossover
			Graph randMSTCOPY = new Graph();
			for(Vertex v : randMST.getvList()){
				try {
					randMSTCOPY.addVertex(v);
				} catch (Graph.VertexAlreadyExistsException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding vertex to MSTGraph!! The vertex already exists!");
				}
			}
			
			for(Edge e : randMST.geteList()){
				try {
					randMSTCOPY.addEdge(e);
				} catch (Graph.InconsistentEdgeException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding edge to MSTGraph!! The vertices for this edge don't exist!");
				} catch (Graph.DuplicateEdgeException ex) {
					Logger.getLogger(PrimsMST.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("Problem adding edge to MSTGraph!! This edge already exists!");
				}
			}
			System.out.println("Done generating graph!");
			
			
			//System.out.println(randMST.toString());
			System.out.println("About to test Prims...");
			Graph theMST = prims.genMST(randMST);
			//System.out.println(theMST.toString());
			
			System.out.println("About to test Kruskals...");
			Graph theSecondMST = kruskals.genMST(randMSTCOPY);
			//System.out.println(theSecondMST.toString());
			
			System.out.println("Are they equivalent?");
			if(theMST.geteList().containsAll(theSecondMST.geteList()) && theMST.getvList().containsAll(theSecondMST.getvList()) && (theMST.geteList().size() == theSecondMST.geteList().size())){
				System.out.println("YES!!");
			}else{
				System.out.println("NOOOOOOOOOO!!!!!!!");
				System.out.println("Prims vertex size: " + theMST.getvList().size());
				System.out.println("Kruskals vertex size: " + theSecondMST.getvList().size());
				System.out.println("Prims number of edges: " + theMST.geteList().size());
				System.out.println("Kruskals number of edges: " + theSecondMST.geteList().size());
				for(Vertex v : theMST.getvList()){
					if(!theSecondMST.getvList().contains(v)){
						System.out.println("Prims contains Vertex " + v + " and Kruskals doesn't!!");
					}
				}
				for(Edge e : theMST.geteList()){
					if(!theSecondMST.geteList().contains(e)){
						System.out.println("Prims contains edge " + e + " and Kruskals doesn't!!");
					}
				}
				for(Edge e : theSecondMST.geteList()){
					if(!theMST.geteList().contains(e)){
						System.out.println("Kruskals contains edge " + e + " and Prims doesn't!!");
					}
				}
				
				exit = true;
			}
			runs++;
		}
		*/
		
		
		/*
		//for(int i = 0; i < 10; i++){
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		System.out.println("About to generate graph...");
		myTest = myTest.genRandomDirectedGraph(300, 1.0f);
		System.out.println("Random graph generated. About to find shortest path...");
		
		System.out.println("Now running Floyds...");
		startTime = System.nanoTime();
		List<Edge> shortestPath3 = floyd.genShortestPath(myTest, myTest.getvList().get(0), myTest.getvList().get(299));
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		timeInMicros = TimeUnit.MICROSECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
		
		System.out.println("Runtime in microseconds was: " + timeInMicros);
		System.out.println("The shortest path is: ");
		System.out.println(shortestPath3.toString());
		System.out.println("\n");
		
		//System.out.println("Getting another path from Floyds (just retrieval). Path from V0 to V250: \n" + floyd.buildEdgePath(myTest.getvList().get(0), myTest.getvList().get(250)));
		
		
		//System.out.println(myTest.toString());
		System.out.println("Now running Dijkstras...");
		startTime = System.nanoTime();
		List<Edge> shortestPath2 = dijkstra.genShortestPath(myTest, myTest.getvList().get(0), myTest.getvList().get(299));
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		timeInMicros = TimeUnit.MICROSECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
		
		System.out.println("Runtime in microseconds was: " + timeInMicros);
		System.out.println("The shortest path is: ");
		System.out.println(shortestPath2.toString());
		System.out.println("\n");
		}
         */       
	}
	
}
