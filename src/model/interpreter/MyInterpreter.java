package model.interpreter;

import java.util.*;

import model.interpreter.Variable.Var;

public class MyInterpreter {

	// it should be here since various command use the hashmap.
	public static HashMap<String, Var> SymbolTable = new HashMap<String, Var>();
	public static double returnValue = 0;
	public static Thread interpretation_thread = null;
	public static boolean stop=true;

	public void interpret(String text) {
		if(interpreterBusy())//to make sure the previous existing 
			try {//thread is done running.
				interpretation_thread.join();
			    } catch (InterruptedException e) {}
		
		stop=false;
		interpretation_thread = new Thread(() -> {
			SymbolTable.clear();// clears the variable context
			String[] lines = text.split("\n");
			Lexer lexer = Lexer.getInstance();
			Parser parser = Parser.getInstance();
			StringJoiner sj = new StringJoiner("\n");
			for (String line : lines)
				sj.add(line);
			String code = sj.toString();
			String[] tokens = lexer.lexer(code);
			parser.parse(tokens);
			interpretation_thread = null;// stress the thread is done running
		});
		interpretation_thread.start();
	}

	public boolean interpreterBusy() {
		return (interpretation_thread != null);
	}
	
	public void stop()
	{
		stop=true;
	}

}