package viewModel;

import java.util.Observable;
import java.util.Observer;



import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;
//TODO: take care of the data binding deceleration section.
public class ViewModel extends Observable implements Observer {
	public StringProperty commandLineText, printAreaText; // these are observable values
	public DoubleProperty throttleVal, rudderVal, planeXCord, planeYCord,aileronVal,elevatorVal;
	Model model;
		
	public ViewModel(Model model) {
		this.model=model;
		commandLineText = new SimpleStringProperty();
		printAreaText = new SimpleStringProperty();
		throttleVal = new SimpleDoubleProperty();
		rudderVal = new SimpleDoubleProperty();
		planeXCord = new SimpleDoubleProperty();
		planeYCord = new SimpleDoubleProperty();
		aileronVal = new SimpleDoubleProperty();
		elevatorVal = new SimpleDoubleProperty();
	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}

	public void RudderSend(double value) {
		rudderVal.set(value);
	}

	public void throttleSend() {
		// TODO Auto-generated method stub

	}
}
