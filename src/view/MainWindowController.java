package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainWindowController implements Initializable {

	@FXML
	JoyStick joystick;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		joystick.redraw();
	}

}
