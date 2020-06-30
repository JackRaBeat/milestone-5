package model;

import java.util.Observable;

import model.flightGearServerHandler.FlightGearServerHandler;
import model.interpreter.MyInterpreter;
import model.solverServerHandler.SolverServerHandler;


public class Model extends Observable {

	public MyInterpreter interpreter;
	public FlightGearServerHandler fliGearServerHandler;
	public SolverServerHandler solvServerHandler;
	private static class ModelHolder {
		public static final Model model = new Model();
	}
	
	private Model() {
		this.interpreter = new MyInterpreter();
		this.fliGearServerHandler = new FlightGearServerHandler();
		this.solvServerHandler=new SolverServerHandler();
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
	
	public boolean interpreterBusy()
	{
		return interpreter.interpreterBusy();
	}
	
	public void stop()
	{
		interpreter.stop();
	}
	
	public void updateIntepreter(boolean state)
	{
		MyInterpreter.enabled=state;
	}

	public void connectToSolver(String ip, int port) {
		System.out.println("ip: "+ip+" port: "+port);
		solvServerHandler.connect(ip,port);	
	}
	
	public boolean isConnectedToSolver()
	{
		return  (SolverServerHandler.connection!=null);
	}
	
	public void solveProblem(int[][] mapGrid, int currentX, int currentY, int xDest, int yDest)
	{
		String sol = solvServerHandler.solveProblem(mapGrid,currentX,currentY,xDest, yDest);
		if(sol == "") {System.out.println("ERROR ON READING LINE");}
		passSolution(sol);
	}

	public void notifyDataServerAvailable() {
		String data="DataServerAvailable";
		setChanged();
		notifyObservers(data);
	}
	
	public void passSolution(String solution)
	{
		String data="gotSolution "+solution;
		setChanged();
		notifyObservers(data);
	}
	
	public double getHeading()
	{
		return fliGearServerHandler.ds.get("/instrumentation/magnetic-compass/indicated-heading-deg");
	}
	public double getPlaneLatCord()
	{
		return fliGearServerHandler.ds.get("position/latitude-deg");
	}
	public double getPlaneLongCord()
	{
		return fliGearServerHandler.ds.get("position/longitude-deg");
	}
	
//SCRIPT:
/*openDataServer 5400 10
connect 127.0.0.1 5402
var breaks = bind "/controls/flight/speedbrake"
var throttle = bind "/controls/engines/current-engine/throttle"
var heading = bind "/instrumentation/heading-indicator/indicated-heading-deg"
var airspeed = bind "/instrumentation/airspeed-indicator/indicated-speed-kt"
var roll= bind "/instrumentation/attitude-indicator/indicated-roll-deg"
var pitch = bind "/instrumentation/attitude-indicator/internal-pitch-deg"
var rudder = bind "/controls/flight/rudder"
var aileron = bind "/controls/flight/aileron"
var elevator = bind "/controls/flight/elevator"
var alt = bind "/instrumentation/altimeter/indicated-altitude-ft"
breaks = 0
throttle = 1
var h0 = heading
while alt < 500 {
rudder = (h0 -heading)/80
aileron = -roll / 70
elevator = pitch / 50
print alt
sleep 100
}
print "done"*/

}
	