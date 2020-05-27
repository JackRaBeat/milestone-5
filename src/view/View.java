package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

public class View implements Initializable {

	@FXML
	JoyStick joystick;
	@FXML
	Slider throttle;
	@FXML
	Slider rudder;
	@FXML
	
	
	public void onSliderChanged() {
		System.out.println(throttle.getValue());
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		joystick.redraw();
	}

}
