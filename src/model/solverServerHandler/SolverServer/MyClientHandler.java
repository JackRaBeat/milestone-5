//TODO:interpret this backtracking.
//TODO:check if the pathsolution injection is okay
//TODO: complete the algorithm
//TODO: add cache functionality 

package model.solverServerHandler.SolverServer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.awt.Point;

public class MyClientHandler implements ClientHandler {

		CacheManager<Searchable<Point>,String> cm;
		Solver<Searchable<Point>, PathSolution<Point>> solver;//TODO: confirm.
		
		
		public MyClientHandler() {
			cm=new FileCacheManager<Point>();
			solver=new SearcherAdapter<Point>(new BestFirstSearch<Point>());//TODO:inject here the best algorithm.
		}
		
		
		@Override
		public void handleClient(InputStream in, OutputStream out) {
			try{
				BufferedReader inFromClient=new BufferedReader(new InputStreamReader(in));
				PrintWriter outToClient=new PrintWriter(out);
									
				Double[][] mat=readMatrix(inFromClient);
				
				State<Point> initial=readLocation(inFromClient);
				State<Point> goal=readLocation(inFromClient);
								
     			Searchable<Point> problem =new Matrix(mat,initial,goal);
								  		
				String sol=cm.getSolution(problem);
				
				if(sol==null){// check if exists in the cash manager
					
					PathSolution<Point> ret=solver.solve(problem);//TODO: add caching functionality after im done with the algoritm
	     			sol=interpretSolution(ret); //solve the problem
					
					cm.saveSolution(problem, sol);//save it 
				}
			
				outToClient.println(sol);//instructions string
				outToClient.flush();
				
				inFromClient.close();
				outToClient.close();
				
				}catch(IOException e){}
		}
		
		private Double[][] readMatrix(BufferedReader inFromClient) throws IOException
		{
			String line;
			Double[] d;
			List<Double []> mat=new LinkedList<Double[]>();	
			
			while(!(line=inFromClient.readLine()).equals("end")){//scan the whole problem and then start managing the solution
				String[] split=line.split(",");
				d = Arrays.stream(split).map(x -> Double.parseDouble(x)).toArray(size->new Double[size]);
				mat.add(d);
			}
			Double[][] ret=mat.toArray(new Double[mat.size()][]);
		
						
			return ret;
		}
		
		private State<Point> readLocation(BufferedReader inFromClient) throws IOException
		{
			String[] point=inFromClient.readLine().split(",");
			State<Point> state=new State<Point>(new Point(Integer.parseInt(point[0]),Integer.parseInt(point[1])));
		
			return state;
				
		}
		
		private String interpretSolution(PathSolution<Point> states)
		{			
		 StringJoiner joiner=new StringJoiner(",");		 
		 List<State<Point>> sts = new ArrayList<>(states.getPath());
		 Collections.reverse(sts);		 
		 Iterator<State<Point>> it=sts.iterator();		 	 
		 if(!it.hasNext()) return "";	 
		 State<Point> current=it.next();
		 State<Point> next;
		 
		 while(it.hasNext())
		 {
	    	next=it.next();
		  if(current.getState().x>next.getState().x)
		  {
			  joiner.add("Up");
		  }
		  if(current.getState().x<next.getState().x)
		  {
			  joiner.add("Down");
		  }
		  if(current.getState().y<next.getState().y)//right
		  {
			  joiner.add("Right");
		  }
		  if(current.getState().y>next.getState().y)//left
		  {
			  joiner.add("Left");
		  }
		 current=next;
		 }
		
		 return joiner.toString();
		 
		}
		
			
			

}
