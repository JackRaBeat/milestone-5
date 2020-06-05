package model.solverServerHandler.SolverServer;

import java.io.*;

public class  MyTestClientHandler implements ClientHandler {
	Solver<String,String> solver;
	CacheManager<String,String> cm;
	
	
	
	 public MyTestClientHandler() {
		solver=str->new StringBuilder(str).reverse().toString();//here goes the lambda expression!
		try {cm= new FileCacheManager();}
		catch(Exception e) {}
	 }
	 
	public void handleClient(InputStream in, OutputStream out) throws Exception {
		BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(in));
		PrintWriter UserOutPut=new PrintWriter(out);
		String line;
		try {
			while(!(line = inputFromClient.readLine()).equals("end")) {
			 if(cm.getSolution(line) == null) {
				 cm.saveSolution(line,solver.solve(line));
			 }
			 UserOutPut.println(cm.getSolution(line));
			// UserOutPut.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
