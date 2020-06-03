package Variable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import commands.ConnectCommand;
import expressions.DataGetter;

public class BoundRemoteVar implements Var{

	String path;
	DataGetter getter;
	
	public BoundRemoteVar(String path,DataGetter getter) {
		this.path=path;
		this.getter=getter;
	}
	

	@Override
	public void set(double value) {
	    Socket connection=ConnectCommand.connection;
	    
	    try {
			OutputStream out= connection.getOutputStream();
			PrintWriter UserOutput=new PrintWriter(out,true);
		    UserOutput.println("set"+" "+path+" "+value);
			//after i send the value to the server it will make my local symbol-table change as well
		    //because of the server thread I'm running.
		} catch (IOException e) {e.printStackTrace();}	
	}
	

	@Override
	public double calculate() {
	return getter.get(path);
	}
	
}
