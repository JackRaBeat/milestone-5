package flightGearServerHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class FlightGearServerHandler {
	private int myPort, serverPort;
	private volatile boolean stop;
	private String ip;
	
	public FlightGearServerHandler(int serverPort,String ip) {
		this.serverPort = serverPort;
		this.ip = ip;
		new Thread(()->runServer()).start();
		new Thread(()->runClient()).start();
	}
	
	private void runClient(){
		while(!stop){
			try {
				Socket interpreter=new Socket(ip, serverPort);
				PrintWriter out=new PrintWriter(interpreter.getOutputStream());
				while(!stop){
					out.println(/*???*/);
					out.flush();
					try {Thread.sleep(100);} catch (InterruptedException e1) {e1.printStackTrace();}
				}
				out.close();
				interpreter.close();
			} catch (IOException e) {
				try {Thread.sleep(1000);} catch (InterruptedException e1) {e1.printStackTrace();}
			}
		}
	}
	
	private void runServer(){
		try {
			ServerSocket server=new ServerSocket(serverPort);
			server.setSoTimeout(1000);
			while(!stop){
				try{
					Socket client=server.accept();
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String line=null;
					while(!(line=in.readLine()).equals("bye")){
						try{
						}catch(NumberFormatException e){e.printStackTrace();}
					}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			server.close();
		} catch (IOException e) {e.printStackTrace();}
	}

	public void close() {
		stop=true;
	}
}
