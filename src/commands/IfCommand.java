package commands;

import java.util.List;

import interpreter.Parser;

public class IfCommand extends ConditionParser {

	@Override
	public void doCommand(List<Object> args) {
		Parser parser=Parser.getInstance();
		if(state()) 
			parser.parse(block);	
	}

}
