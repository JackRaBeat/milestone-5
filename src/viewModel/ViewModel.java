package viewModel;


import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.StringJoiner;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import model.Model;

public class ViewModel extends Observable implements Observer {
	public StringProperty commandLineText, printAreaText; // these are observable values
	public DoubleProperty throttleVal, rudderVal, planeXCord, planeYCord, aileronVal, elevatorVal;
	public DoubleProperty heading;
	volatile boolean dataServAvailable;
	public BooleanProperty serverUp;
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
		serverUp = new SimpleBooleanProperty(false);
		dataServAvailable = false;
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
	
	public boolean interpreterBusy()
	{
		return model.interpreterBusy();
	}
	
	public void stop()
	{
		model.stop();
	}
	
	public void updateInterpreter(boolean state)
	{
		model.updateIntepreter(state);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		String []data=arg.toString().split(" ");
		String action=data[0];
		StringJoiner sj=new StringJoiner(" ");
		for(int i=1;i<data.length;i++) sj.add(data[i]);	
		String value= sj.toString();
       switch(action)
       {
       case("print"):
    	   String existing_print=this.printAreaText.get();
           if(existing_print==null) existing_print="";
    	   this.printAreaText.set(existing_print+value+"\n");
         break;
       case("DataServerAvailable"):
    	   serverUp.setValue(true);
    	   dataServAvailable = true;
       //inform Eli we added those 2 variables to generic_small
    	   System.out.println("hey im here!!!");
    	  new Thread(()->{
    		   while(dataServAvailable) {
    			   planeXCord.set(model.getPlaneXCord());
    			   planeYCord.set(model.getPlaneYCord());
    			   heading.set(model.getHeading());
    			   try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {e.printStackTrace();}
    		   }
    		   }).start();
       }
	}

	public void connectToSolver(String ip, int port) {
		File planeImageFile = new File("resources/solver.exe");
		Runtime runTime = Runtime.getRuntime();
		try {
			runTime.exec("java -classpath " + planeImageFile.toURI().getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.connectToSolver(ip, port);
	}
}
