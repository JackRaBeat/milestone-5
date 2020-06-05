package model.solverServerHandler.SolverServer;

public class SearcherAdapter<T> implements Solver<Searchable<T>,PathSolution<T>> {//adaption.

	private Searcher<T> s;
	
	public SearcherAdapter(Searcher<T> s)
	{
		this.s=s;
	}
	@Override
	public PathSolution<T> solve(Searchable<T> p) {
		return s.search(p);
	}

}
