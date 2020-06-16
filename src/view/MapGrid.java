package view;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class MapGrid extends Canvas {

	public int[][] mapData;
	double area;
	Image planeImage;
	Image destinationImage;
	Image arrowImage;
	public DoubleProperty destinationXcord, destinationYcord;
	public DoubleProperty planeXcord, planeYcord;
	public IntegerProperty heading;
	public StringProperty solution;
	
//TODO: figure out how the other data members are necessary.
//TODO: add the plane to the component.	

	public void setImages(Image planeImage, Image destinationImage, Image arrowImage) {
		this.planeImage = planeImage;
		this.destinationImage = destinationImage;
		this.arrowImage = arrowImage;
	}

	public void setMapData(int[][] mapData, double initialX, double initialY, double area) {
		this.mapData = mapData;
		this.planeXcord = new SimpleDoubleProperty();
		this.planeYcord = new SimpleDoubleProperty();
		this.destinationXcord = new SimpleDoubleProperty();
		this.destinationYcord = new SimpleDoubleProperty();
		this.solution = new SimpleStringProperty();
		StringBuilder sb = new StringBuilder("");
		for(int i = 0 ; i < 100; i++) {
			if(i % 2 == 0) sb.append("right,");
			else sb.append("down,");
		}
		for(int i = 0 ; i < 30; i++) {
			if(i % 2 == 0) sb.append("right,");
			else sb.append("up,");
		}

		this.solution.set(sb.toString());
		this.planeXcord.set(initialX + this.getWidth()/2);
		this.planeYcord.set(initialY + this.getHeight()/2);
		this.destinationXcord.set(this.getWidth()/2 + this.getWidth()/5);
		this.destinationYcord.set(this.getHeight()/2 + this.getHeight()/5);
		this.area = area;
		this.setOnMouseClicked((e)->{
			destinationXcord.set(e.getX());
			destinationYcord.set(e.getY());
			redraw();
		});
		redraw();
	}

	private double calcColorScale() {
		int max = 0;// finding max height to define our heights scale.
		for (int i = 0; i < mapData.length; i++) {
			for (int j = 0; j < mapData[0].length; j++) {
				int value = mapData[i][j];
				if (value > max) {
					max = value;
				}
			}
		}
		return ((double) max / 255);
	}

	// normalizes cell color given the scale range and the cell height.
	private String calcCellColor(int height, double scale) {
		int normalized = (int) (height / scale);
		String red = Integer.toHexString(255 - normalized);
		String green = Integer.toHexString(normalized);
		if (red.length() < 2)
			red = "0" + red;
		if (green.length() < 2)
			green = "0" + green;

		String color = "#" + red + green + "00";
		return color;
	}
	public void drawImage(GraphicsContext gc, Image im, double x, double y ,double w ,double h ,int r) {
		gc.save();
		gc.translate(x, y);
		gc.rotate(r);
		gc.translate(-x, -y);
		gc.drawImage(im, x - w/2, y - h/2, w, h);
		gc.restore();
	}
	 

	public void redraw() {
		if (mapData != null) {

			double scale = calcColorScale();
			double W = getWidth();
			double H = getHeight();

			double w = W / mapData[0].length;
			double h = H / mapData.length;

			GraphicsContext gc = getGraphicsContext2D();
			gc.clearRect(0, 0, this.getWidth(), this.getHeight());
			for (int i = 0; i < mapData.length; i++) {
				for (int j = 0; j < mapData[0].length; j++) {
					String color = calcCellColor(mapData[i][j], scale);
					gc.setFill(Paint.valueOf(color));
					gc.fillRect(j * w, i * h, w, h);
				}
			}
			drawImage(gc,planeImage ,planeXcord.get(), planeYcord.get(), W / mapData[0].length * 10 , H  / mapData[0].length  * 10, 130 /*header*/);
			drawImage(gc,destinationImage ,destinationXcord.doubleValue(), destinationYcord.doubleValue() /*destinationY*/, W / mapData[0].length * 10 , H  / mapData[0].length  * 10, 360);
			String sol = solution.get();
			if(sol != " ") {
				int desXDataCord = (int) (planeXcord.get() / w);
				int desYDataCord = (int) (planeYcord.get() / h);
				String n[] = sol.split(",");
				int numsToAvg = mapData.length / 10;
				double avg = 0;
				for(int i = 0; i <= n.length ; i++)
				{
					switch(n[i]) {
					  case "up":
					    avg +=0;
					    desYDataCord--;
					    break;
					  case "right":
						  avg +=90;
						  desXDataCord++;
					    break;
					  case "down":
						  avg += 180;
						  desYDataCord++;
						    break;
					  case "left":
						  avg += 270;
						  desXDataCord--;
						break;
					}
					if(i % numsToAvg == numsToAvg - 1) {
						drawImage(gc,arrowImage,w * desXDataCord, h * desYDataCord, w * 2, h * 2,(int) avg/numsToAvg);
						avg = 0;
					}
				}
				
			}
			}
		}


}