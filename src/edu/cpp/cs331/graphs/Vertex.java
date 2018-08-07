package edu.cpp.cs331.graphs;

public class Vertex {
	protected int id;

	public Vertex(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(int id) {
		this.id = id;
	}

	public boolean equals(Vertex that) {
		return this.id == that.id;
	}
	@Override
	public boolean equals(Object that) {
		if (this.getClass().equals(that.getClass()))
			return this.equals((Vertex)that);

		return false;
	}

	public String toString() {
		return "V" + this.id;
	}
}
