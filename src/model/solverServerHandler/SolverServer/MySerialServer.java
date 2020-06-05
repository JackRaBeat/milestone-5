package model.solverServerHandler.SolverServer;
import java.net.*; 
import java.io.*; 
public class MySerialServer implements Server {//TODO: handle the timeout with the stop variable.
	private volatile boolean stop;
	private int port;
	public MySerialServer(int port){
		stop = false;
		this.port = port;
	}
	public void start(ClientHandler ch){ 
		new Thread(()->{try {runServer(ch);} catch(Exception e) {e.printStackTrace(); }}).start();
	}
	
	public void runServer(ClientHandler ch) throws Exception {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(3000);
		while(!stop){
			try{
				Socket aClient = server.accept(); 
				try{
					ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
					aClient.close();
					} catch(IOException e) {e.printStackTrace();}
			}catch(SocketTimeoutException e) {e.printStackTrace();}
		}
		server.close();
	}

	@Override
	public void stop() {
		this.stop = true;
	}
}
