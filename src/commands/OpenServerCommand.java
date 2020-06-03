package commands;
import java.util.*;

import expressions.*;
public class OpenServerCommand implements Command {
private DataServer server;
	@Override
	public int getArguments(String[] tokens, int idx, List<Object> emptyList) {
		return StringToArgumentParser.parse(tokens, idx, 2, emptyList, "Integer","Integer");
	}

	@Override
	public void doCommand(List<Object> args) {
		int port=(int)args.get(0);
		int freq=(int)args.get(1);
		
		this.server=MyDataServer.getServer();//object created for the first time 
		String []paths=new String[3];
		paths[0]="simX";
		paths[1]="simY";
		paths[2]="simZ";
		Object lock=new Object();//passed to the server to make this thread to wake up when needed 
		server.open(port,freq,paths,lock);
	 	try {
	 		synchronized(lock)
	 		{
			lock.wait();
	 		}
	    	} catch (InterruptedException e) { }
	}
	
}
