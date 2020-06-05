package model.solverServerHandler.SolverServer;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class BFS<T> extends CommonSearcher<T> {

	@Override
	public PathSolution<T> search(Searchable<T> s) {
		//no comparator-natural ordering.
		openList=new PriorityQueue<State<T>>();
		
		addToOpenList(s.getInitialState());
		HashSet<State<T>> closedSet= new HashSet<State<T>>();//visited nodes.
		while (openList.size()>0)
		{
			State<T>n=popOpenList();
			closedSet.add(n);//mark as visited.
			
		if(s.isGoalState(n))// if we reached the goal destination.
		{
		PathSolution<T> sol=backTrace(n,s.getInitialState());//TODO: understand how to implement the backTracing function. 
		return sol;
		}
		
	List <State<T>> successors=s.getAllPossibleStates(n);
	
	for(State<T> state:successors ) {
		if(!closedSet.contains(state)) {	//first time reaching 					
			state.setCameFrom(n);
			addToOpenList(state);//to be evaluated. reach its neighbors.
			closedSet.add(state);//visited
		}
		
	}
		}
		
	return null;	
	}
}
