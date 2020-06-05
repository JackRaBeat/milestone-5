package model.solverServerHandler.SolverServer;

import java.io.Serializable;

public class State<T> implements Serializable {
	private T state;
	private double cost;
	private double pathCost;
	private State<T> cameFrom;

	public State(T state) {
		this.state = state;
		this.pathCost = Double.POSITIVE_INFINITY;
	}

	public T getState() {// all setters and getters
		return state;
	}

	public double getCost() {
		return cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public double getPathCost() {
		return this.pathCost;
	}

	public void setState(T state) {
		this.state = state;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
		this.pathCost = (cameFrom == null) ? Double.POSITIVE_INFINITY : cameFrom.pathCost + this.cost;
	}

	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}

	public boolean equals(State<T> s) {
		return state.equals(s.state);
	}

}
