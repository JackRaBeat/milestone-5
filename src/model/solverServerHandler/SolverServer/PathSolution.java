package model.solverServerHandler.SolverServer;

import java.util.LinkedList;
import java.util.List;

public class PathSolution<T> implements Solution<T> {
	private List<State<T>> trackedStates;
	
	public PathSolution()
	{
		trackedStates=new LinkedList<State<T>>();
	}
	public void addToSolution(State<T> s)
	{
		this.trackedStates.add(s);
	}
	public List<State<T>> getPath()
	{
		return this.trackedStates;
	}
	
	
	

}
