package model.solverServerHandler.SolverServer;

import java.util.*;

public abstract class CommonSearcher<T> implements Searcher<T> {
	protected PriorityQueue<State<T>> openList;
	private int evalutatedNodes;

	public CommonSearcher() {
		// the queue is allocated in each specific algorithm.
		// each algorithm orders the elements due to a different priority
		// ex. BestFirstSearch uses a cmp and BFS uses the natural insertion order.
		this.evalutatedNodes = 0;
	}

	final protected State<T> popOpenList() {
		this.evalutatedNodes++;
		return openList.poll();
	}

	@Override
	public abstract PathSolution<T> search(Searchable<T> s);

	@Override
	public int getNumberOfNodesEvaluated() {
		return this.evalutatedNodes;
	}

	protected void addToOpenList(State<T> state) {
		this.openList.add(state);

	}

	protected PathSolution<T> backTrace(State<T> goal, State<T> initial)//
	{
		PathSolution<T> sol = new PathSolution<T>();

		State<T> it = goal;
		while (!it.equals(initial)) {
			sol.addToSolution(it);
			it = it.getCameFrom();
		}
		sol.addToSolution(initial);
		return sol;
	}

}
