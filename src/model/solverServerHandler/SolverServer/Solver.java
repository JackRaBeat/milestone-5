package model.solverServerHandler.SolverServer;

public interface Solver<Problem,Sol> {
	public Sol solve(Problem p);
	
}
