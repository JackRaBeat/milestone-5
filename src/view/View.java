package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import viewModel.ViewModel;

public class View implements Initializable, Observer {

	ViewModel vm;

	@FXML
	Button LoadDataButton;
	@FXML
	Button ConnectButton;
	@FXML
	Button ExecuteButton;
	@FXML
	Button CalculatePathButton;
	@FXML
	RadioButton AutoPilotButton;
	@FXML
	RadioButton ManualButton;

	@FXML
	JoyStick JoyStickCanvas;
	@FXML
	MapGrid GridCanvas;

	@FXML
	TextArea CommandLineTextArea;
	@FXML
	TextArea PrintTextArea;
	@FXML
	Slider ThrottleSlider;
	@FXML
	Slider RudderSlider;

	public void setViewModel(ViewModel vm) {
		this.vm = vm;
		this.vm.rudderVal.bind(RudderSlider.valueProperty());
		this.vm.throttleVal.bind(ThrottleSlider.valueProperty());
		this.vm.commandLineText.bind(CommandLineTextArea.textProperty());
		this.PrintTextArea.textProperty().bind(vm.printAreaText);
		this.vm.aileronVal.bind(JoyStickCanvas.aileron);
		this.vm.elevatorVal.bind(JoyStickCanvas.elevator);
		this.GridCanvas.planeXcord.bind((Bindings.createDoubleBinding(
				() -> (((vm.planeXCord.doubleValue()-GridCanvas.initialX) / Math.sqrt(GridCanvas.area)) * GridCanvas.getWidth()),
				vm.planeXCord)));
		this.GridCanvas.planeYcord.bind((Bindings.createDoubleBinding(
				() -> (((vm.planeYCord.doubleValue()-GridCanvas.initialY) / Math.sqrt(GridCanvas.area)) * GridCanvas.getHeight()),
				vm.planeYCord)));
		
		this.GridCanvas.heading.bind(this.vm.heading);
		
		JoyStickCanvas.aileron.addListener(new ChangeListener<Number>() {		
			  public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			  if (!ManualButton.isSelected()) return;
				vm.aileronSend();
			  }});
		
		
		JoyStickCanvas.elevator.addListener(new ChangeListener<Number>() {
			  public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			  if (!ManualButton.isSelected()) return;
				vm.elevatorSend();
		}});
		
	}

	public void onRudderSliderChanged() {
		if (!ManualButton.isSelected()) return;
		vm.RudderSend();
	}

	@FXML
	public void onThrottleSliderChanged() {
		if (!ManualButton.isSelected()) return;
		vm.throttleSend();
	}
	
	//TODO: add a listener for the binding of those two.
	public void onAileronChange()
	{
	if (ManualButton.isSelected() == false) return;
	  		
	}
	
	public void onElevatorChange()
	{
	if (!ManualButton.isSelected()) return;
	  		
	}
	
	
	@FXML
	public void ConnectPressed() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("FlightGear Server connection");
		dialog.setHeaderText("Please insert the ip and port of the FlightGear server");

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

			vm.connectToSimulator(serverInfo.getKey(), Integer.parseInt(serverInfo.getValue()));
		});
	}

	@FXML
	public void LoadDataPressed() {

		FileChooser fc = new FileChooser();
		fc.setTitle("load csv File");
		fc.setInitialDirectory(new File("./resources"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {

			List<String> list = new LinkedList<String>();
			Scanner scanner = null;
			try {
				scanner = new Scanner(chosen);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (scanner.hasNext()) {
				list.add(scanner.next());
			}
			scanner.close();

			// scanning initial coordinates and the area of each cell (km^2)
			String[] coordinates = list.get(0).split(",");
			double initialX = Double.parseDouble(coordinates[0]);
			double initialY = Double.parseDouble(coordinates[1]);
			double area = Double.parseDouble(list.get(1).split(",")[0]);
			
			// Scanning the heights matrix. Each cell is measured by meters.
			int row = list.size();
			int col = list.get(2).split(",").length;
			int[][] mapData = new int[row][col];
			for (int i = 2; i < row; i++) {
				String[] data = list.get(i).split(",");
				for (int j = 0; j < col; j++) {

					mapData[i - 2][j] = Integer.parseInt(data[j]);
				}
			}
			
      
         
            
			GridCanvas.setMapData(mapData, area,initialX,initialY);
			
			GridCanvas.setOnMouseClicked((e) -> {
				GridCanvas.destinationXcord.set(e.getX());
				GridCanvas.destinationYcord.set(e.getY());
				GridCanvas.redraw();
			});
		}
	}

	@FXML
	public void calculatePathPressed() {

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

	@FXML
	public void ExecutePressed() {
		if (!AutoPilotButton.isSelected()) return;
		vm.interpretText();	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		PrintTextArea.setEditable(false);
		RudderSlider.setShowTickLabels(true);
		RudderSlider.setShowTickMarks(true);
		ThrottleSlider.setShowTickLabels(true);
		ThrottleSlider.setShowTickMarks(true);
		RudderSlider.setMajorTickUnit(0.5f);
		
		 
		File planeImageFile = new File("resources/airplane-icon.png");
		Image planeImage = new Image("file:" + planeImageFile.toURI().getPath());
		File destinationImageFile = new File("resources/destination-icon.png");
		Image destinationImage = new Image("file:" + destinationImageFile.toURI().getPath());
		File arrowImageFile = new File("resources/arrow-icon.png");
		Image arrowImage = new Image("file:" + arrowImageFile.toURI().getPath());
		GridCanvas.setImages(planeImage, destinationImage, arrowImage);
		PrintTextArea.setEditable(false);
		ToggleGroup buttonGroup = new ToggleGroup();
		AutoPilotButton.setToggleGroup(buttonGroup);
		ManualButton.setToggleGroup(buttonGroup);
		JoyStickCanvas.setMouseEventHandlers();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
