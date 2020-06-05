package model.solverServerHandler.SolverServer;

public interface Searcher<T> {
public PathSolution<T> search(Searchable<T> s);
public int getNumberOfNodesEvaluated();
}
