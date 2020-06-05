package model.solverServerHandler.SolverServer;

import java.io.Serializable;

public interface SerializeFormatted<Prob,Sol> extends Serializable {	
	public Prob deserializeProb();
	public  Sol deserializeSol();
	
}
