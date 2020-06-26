package model.solverServerHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import model.Model;

public class SolverServerHandler {
	public static Socket connection;
	Model m=Model.getInstance();

	public void connect(String ip, int port) {
		try {
			connection = new Socket(ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void solveProblem(int[][] mapGrid, double currentX, double currentY, double xDest, double yDest, double w,
			double h) {
		String map[] = formatMap(mapGrid);
		String currentPos = formatLocation(currentX, currentY, w, h);
		String destPos = formatLocation(xDest, yDest, w, h);
		
		for(String line:map) System.out.println(line);
		System.out.println("CURRENT: "+currentPos);
		System.out.println("DEST: "+destPos);
		
		
		try {
			OutputStream out = connection.getOutputStream();
			PrintWriter UserOutput = new PrintWriter(out, true);
			BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			for (String line : map)
				UserOutput.println(line);
			UserOutput.println("end");
			UserOutput.println(currentPos);
			UserOutput.println(destPos);
			String line=in.readLine();
			m.passSolution(line);
		
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}

	private String[] formatMap(int[][] mapGrid) {
		int j;
		String map[] = new String[mapGrid.length];
		
		for (int i = 0; i < mapGrid.length; i++) {
			map[i]="";
			for (j = 0; j < mapGrid[0].length - 1; j++) {			
				map[i] += Integer.toString(mapGrid[i][j]) + ",";
			}
			map[i] += Integer.toString(mapGrid[i][j]);
		}
		return map;
	}
	// converts the actual position on the canvas onto the cell dimensions
	private String formatLocation(double x, double y, double w, double h) {
		String convertedX = (Integer.toString((int) (x / w)));
		String convertedY = (Integer.toString((int) (y / h)));
		return convertedX + "," + convertedY;
	}

	public void close() {

	}

}
