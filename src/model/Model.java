package model;

import java.util.Observable;

import model.flightGearServerHandler.FlightGearServerHandler;
import model.interpreter.MyInterpreter;

public class Model extends Observable {
	MyInterpreter interpreter;
	FlightGearServerHandler fliGearServerHandler;

	public Model() {
		this.interpreter = new MyInterpreter();
		this.fliGearServerHandler = new FlightGearServerHandler();
	}

	public void connectToSimulator(String ip, int port) {
		fliGearServerHandler.connect(ip, port);
	}

	public void setVar(String path, double value) {
		fliGearServerHandler.dc.set(path, value);
	}
	public void interpretText(String code)
	{
	 interpreter.interpret(code);
	}
 
	
	
}
