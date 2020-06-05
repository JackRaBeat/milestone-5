package model.solverServerHandler.SolverServer;

public interface CacheManager<Problem,Sol> {
	public Sol getSolution(Problem P);
	public void saveSolution(Problem P,Sol S);
	
}
