package model.flightGearServerHandler;

import model.dataHandler.*;

public class FlightGearServerHandler {
	DataServer ds;
	DataClient dc;

	public FlightGearServerHandler(int serverPort, String ip) {
		Object lock = new Object();
		String[] paths = null;// TODO: create an instance of an object filled with the relevant paths!
		// figure out either they're constant or not.
		ds.open(serverPort, 4, paths, lock);
		DataSynchronizer.waitForData(lock);// make sure to boot-up our server and read data
		// before trying to present it in the application.
		dc.connect(serverPort, ip);
	}
}
