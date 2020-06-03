package commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import expressions.DataServer;
import expressions.MyDataServer;

public class DisconnectCommand implements Command {
	@Override
	public int getArguments(String[] tokens, int idx, List<Object> emptyList) {
		return 0;
	}
 
	@Override
	public void doCommand(List<Object> args) {
		Socket connection = ConnectCommand.connection;

		
        Object lock=MyDataServer.lock;
		DataServer ds = MyDataServer.getServer();
		ds.close();
		synchronized(lock)//we ensure the server has received all the relevant changes from the 
        {//simulator client so we need to wait for it to finish reading before the simulator
			//client closes.
        	try {
				lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		
		try {
			OutputStream out = connection.getOutputStream();
			PrintWriter UserOutput = new PrintWriter(out,true);
			UserOutput.println("bye");
			//client and simulator server are now closed.
		} catch (IOException e) {
			e.printStackTrace();
		}
        //is the simulator server closing before the client?
		//assume it isn't  
		
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//the simulator's server needs to send an ack too so we wont close before it
		//and result in timeout in the simulator's side. 
		
	}

}
