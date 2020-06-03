package viewModel;

import java.util.Observable;
import java.util.Observer;

import flightGearServerHandler.FlightGearServerHandler;
import interpreter.MyInterpreter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel  extends Observable implements Observer{
	public StringProperty commandLineText, printAreaText; // these are observable values
	public DoubleProperty throttleVal, rudderVal, planeXCord, planeYCord;
	MyInterpreter interp;
	FlightGearServerHandler fliGearServerHandler;
	SolverServerHandler solvServHandler;
	
	public ViewModel() {
		commandLineText = new SimpleStringProperty();
		printAreaText = new SimpleStringProperty();
		throttleVal = new SimpleDoubleProperty();
		rudderVal = new SimpleDoubleProperty();
		planeXCord = new SimpleDoubleProperty();
		planeYCord = new SimpleDoubleProperty();
	}

	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}



	public void RudderSend() {

		throttleVal.set(throttleVal.get() + (rudderVal.get() * 10));
		
	}



	public void throttleSend() {
		// TODO Auto-generated method stub
		
	}
}
