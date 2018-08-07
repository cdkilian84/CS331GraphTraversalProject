package edu.cpp.cs331.graphs;

public class DirectedEdge extends Edge {

	public DirectedEdge(Vertex one, Vertex two, int weight) {
		super(one, two, weight);
	}
	
	@Override
	public boolean equals(Object that)
	{
		if(!this.getClass().equals(that.getClass()))
			return false;
		
		return ((Edge)that).one.equals(this.one) && ((Edge)that).two.equals(this.two);
	}

	public String toString()
	{
		return this.one + " -" + this.weight + "-> " + this.two ;
	}
}
