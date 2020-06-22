package model.interpreter.commands;

import java.util.List;

import model.Model;
import model.interpreter.expressions.ExpressionCalculate;


public class PrintCommand implements Command {//TODO: provide option to print a longer stream
	//with spaces.
	Model m=Model.getInstance();
	@Override
	public int getArguments(String[] tokens, int idx, List<Object> emptyList) {
		return StringToArgumentParser.parse(tokens, idx, 1, emptyList, "String");
	}

	@Override
	public void doCommand(List<Object> args) {
		String str = args.get(0).toString();
		if (str.charAt(0) != '"')// meaning, its a variable
		{
			str = Double.toString((ExpressionCalculate.invoke(str)));
		}
		else {
			str = str.substring(1, str.length()-1);
		}		
	  m.printOutput(str);
	}

}


/*var throttle = bind "/controls/engines/current-engine/throttle"
throttle = 0*/

//SCRIPT:
/*openDataServer 5400 10
connect 127.0.0.1 5402
var breaks = bind "/controls/flight/speedbrake"
var throttle = bind "/controls/engines/current-engine/throttle"
var heading = bind "/instrumentation/heading-indicator/indicated-heading-deg"
var airspeed = bind "/instrumentation/airspeed-indicator/indicated-speed-kt"
var roll= bind "/instrumentation/attitude-indicator/indicated-roll-deg"
var pitch = bind "/instrumentation/attitude-indicator/internal-pitch-deg"
var rudder = bind "/controls/flight/rudder"
var aileron = bind "/controls/flight/aileron"
var elevator = bind "/controls/flight/elevator"
var alt = bind "/instrumentation/altimeter/indicated-altitude-ft"
breaks = 0
throttle = 1
var h0 = heading
while alt < 500 {
rudder = (h0 -heading)/20
aileron = -roll / 70
elevator = pitch / 50
print alt
sleep 100
}
print "done"*/
