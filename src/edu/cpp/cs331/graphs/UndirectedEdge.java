package edu.cpp.cs331.graphs;

public class UndirectedEdge extends Edge {

	public UndirectedEdge(Vertex one, Vertex two, int weight) {
		super(one, two, weight);
	}

	@Override
	public boolean equals(Object that) {
		if (!this.getClass().equals(that.getClass()))
			return false;

		Edge other = (Edge) that;

		return (other.one.equals(this.one) && other.two.equals(this.two))
				|| (other.one.equals(this.two) && other.two.equals(this.one));
	}

	public String toString() {
		return this.one + " <-" + this.weight + "-> " + this.two ;
	}


}
