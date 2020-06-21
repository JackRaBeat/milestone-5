package model;

import java.util.Observable;

import model.flightGearServerHandler.FlightGearServerHandler;
import model.interpreter.MyInterpreter;


public class Model extends Observable {

	MyInterpreter interpreter;
	FlightGearServerHandler fliGearServerHandler;

	private static class ModelHolder {
		public static final Model model = new Model();
	}
	
	private Model() {
		this.interpreter = new MyInterpreter();
		this.fliGearServerHandler = new FlightGearServerHandler();
	}

	public static Model getInstance() {
		return ModelHolder.model;
	}
	
	public void connectToSimulator(String ip, int port) {
		fliGearServerHandler.connect(ip, port);
	}

	public void setVar(String path, double value) {
		fliGearServerHandler.dc.set(path, value);
	}

	public void interpretText(String code) {
		interpreter.interpret(code);
	}
	
	public void printOutput(String output)
	{
		String data="print "+output;
		setChanged();
		notifyObservers(data);
	}
	

}
