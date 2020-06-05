package model.dataHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class MyDataServer implements DataServer {

	private ConcurrentHashMap<String, Double> values;
	private volatile boolean open;
	public static Object lock;
	private static class MyServerHolder {
		public static final MyDataServer ds = new MyDataServer();
	}

	private MyDataServer() {
		values = new ConcurrentHashMap<String, Double>();
		open = false;
	}

	public static DataServer getServer() {
		return MyServerHolder.ds;
	}

	@Override
	public double get(String path) {
		return values.get(path);
	}

	@Override
	public void open(int port, int freq, String[] paths, Object lock) {
		if (open)
			return;
		MyDataServer.lock = lock;
		open = true;
		Thread server_thread = new Thread(() -> {

			// its because of the connection!

			try {
				ServerSocket server = new ServerSocket(port);
				server.setSoTimeout(3000);
				Socket aClient = server.accept();
				InputStream in = aClient.getInputStream();
				BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(in));
				
				//makes sure that the main thread waits for the server to boot-up and function. 
				// causes the main thread to wake up.
				DataSynchronizer.resume(lock);
				
				while (open) {
					String[] new_values = inputFromClient.readLine().split(",");
					for (int i = 0; i < new_values.length; i++) {
						String path = paths[i];
						double value = Double.parseDouble(new_values[i]);
						values.put(path, value);
					}

					Thread.sleep(1000 / freq);

				}

				// since the client doesn't let us know the end of the relevant information we
				// need to keep reading a little more
				for (int x = 0; x < 10; x++) {
					String[] new_values = inputFromClient.readLine().split(",");
					for (int i = 0; i < new_values.length; i++) {
						String path = paths[i];
						double value = Double.parseDouble(new_values[i]);
						values.put(path, value);
					}

					Thread.sleep(1000 / freq);
				}
				
				//causes the main thread to wait for all of the relevant information to arrive and then continue.
				// tell the interpreter thread it's okay to keep going now.
				DataSynchronizer.resume(lock);
								
				aClient.close();
				server.close();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		server_thread.start();

	}

	@Override
	public void close() {
		if (!open)
			return;
		this.open = false;
	}

}
