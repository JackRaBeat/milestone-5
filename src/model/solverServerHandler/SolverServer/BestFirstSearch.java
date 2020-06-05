package model.solverServerHandler.SolverServer;
import java.util.*;

public class BestFirstSearch<T> extends CommonSearcher<T> {
	
	@Override
	public PathSolution<T> search(Searchable<T> s) {
		//allocating with necessary comparator.
		openList=new PriorityQueue<State<T>>((s1,s2)->(Double.compare(s1.getCost(),s2.getCost())));
		
		addToOpenList(s.getInitialState());
		HashSet<State<T>> closedSet= new HashSet<State<T>>();//visited nodes.
		
		while (openList.size()>0)
		{
			State<T>n=popOpenList();
			closedSet.add(n);			
				
		if(s.isGoalState(n))
			{
			PathSolution<T> sol=backTrace(n,s.getInitialState());//TODO: understand how to implement the backTracing function. 
			return sol;
			}
		
		List <State<T>> successors=s.getAllPossibleStates(n);
		
		for(State<T> state:successors ) {
			if(!closedSet.contains(state)&&!openList.contains(state)) {	//first time reaching 					
				state.setCameFrom(n);
				addToOpenList(state);
			}
			
			else if(n.getPathCost()+state.getCost()<state.getPathCost()){//current price is better than the previous one 
				if(openList.contains(state))//it has been already visited and 
				{
					openList.remove(state);
				}										
				//there is a way to improve the price.			
				state.setCameFrom(n);			
				addToOpenList(state);				
			}
			
		}
		
		}
	
		return null;
	}
	

}