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
		System.out.println("waiting for simulator...");
		Object lock = new Object();
		String[] paths =this.definePaths();		
		ds.open(5400, 4, paths, lock);
		DataSynchronizer.waitForData(lock);// make sure to boot-up our server and read data (simulator client connected)
		System.out.println("CONNECTED!");
		// before trying to present it in the application.
	}

	public void connect(String ip, int serverPort) {
		
		dc.connect(serverPort, ip);
	}
	
	public String[] definePaths()
	{
		String[] paths=new String[23];
		paths[0]="/instrumentation/airspeed-indicator/indicated-speed-kt";
		paths[1]="/instrumentation/altimeter/indicated-altitude-ft";
		paths[2]="/instrumentation/altimeter/pressure-alt-ft";
		paths[3]="/instrumentation/attitude-indicator/indicated-pitch-deg";
		paths[4]="/instrumentation/attitude-indicator/indicated-roll-deg";
		paths[5]="/instrumentation/attitude-indicator/internal-pitch-deg";
		paths[6]="/instrumentation/attitude-indicator/internal-roll-deg";
		paths[7]="/instrumentation/encoder/indicated-altitude-ft";
		paths[8]="/instrumentation/encoder/pressure-alt-ft";
		paths[9]="/instrumentation/gps/indicated-altitude-ft";
		paths[10]="/instrumentation/gps/indicated-ground-speed-kt";
		paths[11]="/instrumentation/gps/indicated-vertical-speed";
		paths[12]="/instrumentation/heading-indicator/indicated-heading-deg";
		paths[13]="/instrumentation/magnetic-compass/indicated-heading-deg";
		paths[14]="/instrumentation/slip-skid-ball/indicated-slip-skid";
		paths[15]="/instrumentation/turn-indicator/indicated-turn-rate";
		paths[16]="/instrumentation/vertical-speed-indicator/indicated-speed-fpm";
		paths[17]="/controls/flight/aileron";
		paths[18]="/controls/flight/elevator";
		paths[19]="/controls/flight/rudder";
		paths[20]="/controls/flight/flaps";
		paths[21]="/controls/engines/engine/throttle";
		paths[22]="/engines/engine/rpm";
		return paths;
	}	
}
