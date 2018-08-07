package edu.cpp.cs331.graphs;

public abstract class Edge implements Comparable<Edge>{
	protected Vertex one;
	protected Vertex two;
	protected int weight;

	public Edge(Vertex one, Vertex two, int weight) {
		this.one = one;
		this.two = two;
		this.weight = weight;
	}

	public Vertex getOne() {
		return one;
	}

	public void setOne(Vertex one) {
		this.one = one;
	}

	public Vertex getTwo() {
		return two;
	}

	public void setTwo(Vertex two) {
		this.two = two;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge that) {
		return this.weight - that.weight;
	}
}
