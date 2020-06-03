package expressions;

import java.util.ArrayList;
import java.util.HashMap;

import Variable.RegularVar;
import Variable.Var;
import commands.CommandsMap;
import interpreter.MyInterpreter;

public class Utilities {
	public static boolean isDouble(String str) {
		try {
			double d = Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// when calculating all kind of variables are only used regulary.
	public static Var getVar(String name) {

		if (MyInterpreter.SymbolTable.containsKey(name)) {
			double value = MyInterpreter.SymbolTable.get(name).calculate();
			//we create a new one since we don't want to harm the one in the symbol-table.
			return new RegularVar(value);
		}
		return null;

	}
	

	
	public static boolean IsVarOrCmd(String str)
	{
		try {
			double d = Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			ArrayList<String> operators=new ArrayList<String>();
			operators.add("+");
			operators.add("-");
			operators.add("/");
			operators.add("*");
			operators.add("(");
			operators.add(")");
			return !(operators.contains(str));			
		}
	}
	
	
}