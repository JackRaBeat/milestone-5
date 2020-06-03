package commands;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ConnectCommand implements Command {
	public static Socket connection;// we only open one session towards the simulator.

	@Override
	public int getArguments(String[] tokens, int idx, List<Object> emptyList) {
		return StringToArgumentParser.parse(tokens, idx, 2, emptyList, "String", "Integer");
	}

	
	
	@Override
	public void doCommand(List<Object> args) {

		String ip = args.get(0).toString();
		int port = (int) args.get(1);
		try {
			connection = new Socket(ip, port);
		} catch (IOException e) {e.printStackTrace();}

	}

}
