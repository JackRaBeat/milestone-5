package model.flightGearServerHandler;

import java.util.Observable;
import model.dataHandler.*;

public class FlightGearServerHandler extends Observable {
	public DataServer ds;
	public DataClient dc;
	
	public FlightGearServerHandler()
	{		
		ds=MyDataServer.getServer();
		dc=new MyDataClient();
	}
//TODO: inform the client about the need to boot up the simulator software.
	public void connect(String ip, int serverPort) {
		System.out.println("waiting for simulator...");
		Object lock = new Object();
		ds.open(5400, 4, lock);
		DataSynchronizer.waitForData(lock);// make sure to boot-up our server and read data (simulator client connected)
		System.out.println("CONNECTED!");
		// before trying to present it in the application.	
		dc.connect(serverPort, ip);
	
}
}