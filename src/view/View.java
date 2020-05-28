package view;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;

public class View implements Initializable {

	@FXML JoyStick JoyStickCanvas;
	@FXML Button LoadDataButton;
	@FXML Button ConnectButton;
	@FXML Button CalculatePathButton;
	@FXML Canvas GridCanvas;
	@FXML RadioButton AutoPilotButton;
	@FXML TextArea CommandLine;
	@FXML Button ExecuteButton;
	@FXML TextArea PrintTextArea;
	@FXML RadioButton ManualButton;
	@FXML Slider ThrottleSlider;
	@FXML Slider RudderSlider;
	
	@FXML public void onRudderSliderChanged() {
		System.out.println(RudderSlider.getValue());
		PrintTextArea.appendText(RudderSlider.getValue() + "\n");
	}
	@FXML public void onThrottleSliderChanged() {
		System.out.println(ThrottleSlider.getValue());
		PrintTextArea.appendText(ThrottleSlider.getValue() + "\n");
	}
	@FXML public void ConnectPressed(){
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("FlightGear Server connection");
		dialog.setHeaderText("Please insert the ip and port of FlightGear server");

		ButtonType loginButtonType = new ButtonType("connect", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField ip = new TextField();
		ip.setPromptText("IP");
		TextField port = new TextField();
		port.setPromptText("port");

		grid.add(new Label("IP:"), 0, 0);
		grid.add(ip, 1, 0);
		grid.add(new Label("Port:"), 0, 1);
		grid.add(port, 1, 1);


		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> ip.requestFocus());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(ip.getText(), port.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(serverInfo -> {
		    System.out.println("IP=" + serverInfo.getKey() + ", Port=" + serverInfo.getValue());
		    PrintTextArea.appendText("IP=" + serverInfo.getKey() + ", Port=" + serverInfo.getValue() + "\n");
		});
	}
	@FXML public void LoadDataPressed() {
		FileChooser fc = new FileChooser();
		fc.setTitle("load csv File");
		fc.setInitialDirectory(new File("./csv_maps"));
		File chosen = fc.showOpenDialog(null);
		if(chosen != null) {
			System.out.println(chosen.getName());
			 PrintTextArea.appendText(chosen.getName() + "\n");
		}
	}
	@FXML public void calculatePathPressed() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Solver Server connection");
		dialog.setHeaderText("Please insert the ip and port of Solver server");

		ButtonType loginButtonType = new ButtonType("Connect and solve", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField ip = new TextField();
		ip.setPromptText("IP");
		TextField port = new TextField();
		port.setPromptText("port");

		grid.add(new Label("IP:"), 0, 0);
		grid.add(ip, 1, 0);
		grid.add(new Label("Port:"), 0, 1);
		grid.add(port, 1, 1);


		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> ip.requestFocus());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(ip.getText(), port.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(serverInfo -> {
		    System.out.println("IP=" + serverInfo.getKey() + ", Port=" + serverInfo.getValue());
		    PrintTextArea.appendText("IP=" + serverInfo.getKey() + ", Port=" + serverInfo.getValue() + "\n");
		});
	}
	@FXML public void ExecutePressed() {
	    System.out.println(CommandLine.getText());
	    PrintTextArea.appendText(CommandLine.getText() + "\n");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		JoyStickCanvas.redraw();
		PrintTextArea.setEditable(false);
		ToggleGroup buttonGroup = new ToggleGroup();
		AutoPilotButton.setToggleGroup(buttonGroup);
		ManualButton.setToggleGroup(buttonGroup);
	}
}
