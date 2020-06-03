package interpreter;

import java.util.*;
import Variable.Var;

public class MyInterpreter {

	// it should be here since various command use the hashmap.
	public static HashMap<String, Var> SymbolTable = new HashMap<String, Var>();
	public static double returnValue = 0;

	public void interpret(String[] lines) {
		Lexer lexer = Lexer.getInstance();
		Parser parser = Parser.getInstance();
		StringJoiner sj = new StringJoiner("\n");
		for(String line:lines) sj.add(line);
		String code=sj.toString();
		String[] tokens = lexer.lexer(code);
		parser.parse(tokens);
	}
	


}