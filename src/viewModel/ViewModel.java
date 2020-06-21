package viewModel;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.StringJoiner;

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
		model.setVar("/controls/engines/current-engine/throttle", throttleVal.get());
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
		
		System.out.println("im arg: "+arg.toString());
		String []data=arg.toString().split(" ");
	//	for(String s:data) System.out.println("in data!!!!: "+s);
		
		String action=data[0];
		StringJoiner sj=new StringJoiner(" ");
		for(int i=1;i<data.length;i++) sj.add(data[i]);	
		String value= sj.toString();
		System.out.println("action: "+action);
		System.out.println("value: "+value);
       switch(action)
       {
       case("print"):
    	   //System.out.println("im invoked!");
    	   String existing_print=this.printAreaText.get();
           if(existing_print==null) existing_print="";
    	   this.printAreaText.set(existing_print+value+"\n");
         break;
       }
	
	}
}
