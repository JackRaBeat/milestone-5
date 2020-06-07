package model;

import java.util.Observable;

import model.flightGearServerHandler.FlightGearServerHandler;
import model.interpreter.MyInterpreter;
import model.solverServerHandler.SolverServerHandler;

public class Model extends Observable {
	MyInterpreter interpreter;
	FlightGearServerHandler fliGearServerHandler;
	SolverServerHandler solvServHandler;
 
	public void setVar(String name,double value)
	{
		fliGearServerHandler.dc.set(name, value);
	}
	
	public void connectToSimulator(String ip,int port)
	{
		fliGearServerHandler.dc.connect(port, ip);
	}
	
	
	
	
	
}
