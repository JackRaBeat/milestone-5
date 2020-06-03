package commands;

import java.util.List;
import Variable.RegularVar;
import interpreter.MyInterpreter;

public class AssignmentCommand implements Command {

	@Override
	public int getArguments(String[] tokens, int idx, List<Object> emptyList) {
		emptyList.add(tokens[idx-2]);//add the name first
		//why you're not adding the second argument (the value) to the empty list?? what are you returning here??
		return StringToArgumentParser.parse(tokens, idx, 1, emptyList, "Double");
	}

	@Override
	public void doCommand(List<Object> args) {
		String name=args.get(0).toString();
		double value=(double)args.get(1);
		//value assigned right after creation- regularVar.		
		if(MyInterpreter.SymbolTable.get(name)==null)
		{
			MyInterpreter.SymbolTable.put(name,new RegularVar(value));
		}//already initialized
	    MyInterpreter.SymbolTable.get(name).set(value);
	}

}
