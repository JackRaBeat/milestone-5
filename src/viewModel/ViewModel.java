package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

public class ViewModel extends Observable implements Observer {
	public StringProperty commandLineText, printAreaText; // these are observable values
	public DoubleProperty throttleVal, rudderVal, planeXCord, planeYCord, aileronVal, elevatorVal;
	public DoubleProperty heading;
	Model model;

	public ViewModel(Model model) {
		this.model = model;
		commandLineText = new SimpleStringProperty();
		printAreaText = new SimpleStringProperty();
		throttleVal = new SimpleDoubleProperty();
		rudderVal = new SimpleDoubleProperty();
		planeXCord = new SimpleDoubleProperty();
		planeYCord = new SimpleDoubleProperty();
		aileronVal = new SimpleDoubleProperty();
		elevatorVal = new SimpleDoubleProperty();
		heading = new SimpleDoubleProperty();
	}

	public void RudderSend() {
		model.setVar("/controls/flight/rudder", rudderVal.get());
	}

	public void throttleSend() {
		model.setVar("/controls/engines/engine/throttle", throttleVal.get());
	}
	
	public void aileronSend() {
		model.setVar("/controls/flight/aileron", aileronVal.get());
	}
	
	public void elevatorSend() {
		model.setVar("/controls/flight/elevator", elevatorVal.get());
	}
	

	public void connectToSimulator(String ip, int port) {
		model.connectToSimulator(ip, port);
	}

	public void interpretText() {
		model.interpretText(this.commandLineText.get());
	}

	@Override
	public void update(Observable o, Object arg) {

	}

}
