package model;

import java.util.Observable;

import model.flightGearServerHandler.FlightGearServerHandler;
import model.interpreter.MyInterpreter;
import model.solverServerHandler.SolverServerHandler;

public class Model extends Observable {
	MyInterpreter interpreter;
	FlightGearServerHandler fliGearServerHandler;
	SolverServerHandler solvServHandler;
}
