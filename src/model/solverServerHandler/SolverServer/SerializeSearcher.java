package model.solverServerHandler.SolverServer;

public class SerializeSearcher<T> implements SerializeFormatted<Searchable<T>,String> {
 Searchable<T> prob;
 String sol;


SerializeSearcher()//default CTOR
{
	
}


public SerializeSearcher(Searchable<T> problem, String solution) {
	this.prob=problem;
	this.sol=solution;
}



public Searchable<T> getProb() {//all setters and getters;
	return prob;
}
public String getSol() {
	return sol;
}
public void setProb(Searchable<T> prob) {
	this.prob = prob;
}
public void setSol(String sol) {
	this.sol = sol;
}


@Override
public Searchable<T> deserializeProb() {
	return this.prob;
}

@Override
public String deserializeSol() {
	return this.sol;
}





	
}
