package model.solverServerHandler.SolverServer;
//TODO:rewrite this class.
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

public class FileCacheManager<T> implements CacheManager<Searchable<T>,String> {

	private Hashtable<Searchable<T>,String> cm;
	private static int solutionCount;//every instance needs to know and maintain the amount of 
	//the saved solutions.
	
	public FileCacheManager()//cash manager initialization
	{
		cm=new Hashtable<Searchable<T>,String>();//allocation.
		//check if the solution file exists. 
		File f=new File("Solutions.sol");
		if(!f.exists()) return;
			
		//the file exists, now reading the existing solutions from it..
		ObjectInputStream in; 
		try {
			in = new ObjectInputStream(new FileInputStream("Solution.txt"));
			try {
			for(int i=0;i<FileCacheManager.solutionCount;i++)
			{			
			SerializeFormatted sf=(SerializeSearcher<T>)in.readObject();
			cm.put((Searchable<T>)sf.deserializeProb(),(String)sf.deserializeSol());
			}
			}catch(ClassNotFoundException e) {}
	}catch(IOException e) {e.printStackTrace();}
		
	}	
	@Override
	public String getSolution(Searchable<T> problem) {
		
		//check if the solution file exists. 
		File f=new File("Solutions.sol");
		if(!f.exists())
			return null;//indicates the solution hasn't been found;
		
		String sol=cm.get(problem);//return if found;
			return sol;				
	}
	@Override
	public void saveSolution(Searchable<T> problem, String solution) {//increases the solution amount
		//every time it approaches a new one.
	
		ObjectOutputStream out;
		
        	//check if the solution file exists. 
				File f=new File("Solutions.sol");
				if(!f.exists()) FileCacheManager.solutionCount=0;
				//save in the cache manager for current iteration and reading.
				cm.put(problem,solution);					
			//save to the file- for next iterations after closing.	
		try {
			 out=new ObjectOutputStream(new FileOutputStream("Solution.txt"));//open the solutions file.
			 //create it in case it doesn't exist.
			 //create the to be saved serialized object.
			SerializeFormatted s=new SerializeSearcher<T>(problem,solution);
			out.writeObject(s);//write the object;		
			out.close();//close the file 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileCacheManager.solutionCount++;
	}
}
