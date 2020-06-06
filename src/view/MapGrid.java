package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class MapGrid extends Canvas {

	int[][] mapData;
	double initialX, initialY;
	double area;
	Image plane;

//TODO: figure out how the other data members are necessary.
//TODO: add the plane to the component.	

	public void setMapData(int[][] mapData, double initialX, double initialY, double area) {
		this.mapData = mapData;
		this.initialX = initialX;
		this.initialY = initialY;
		this.area = area;
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

	public void redraw() {
		if (mapData != null) {

			double scale = calcColorScale();
			double W = getWidth();
			double H = getHeight();

			double w = W / mapData[0].length;
			double h = H / mapData.length;

			GraphicsContext gc = getGraphicsContext2D();

			for (int i = 0; i < mapData.length; i++) {
				for (int j = 0; j < mapData[0].length; j++) {
					String color = calcCellColor(mapData[i][j], scale);
					gc.setFill(Paint.valueOf(color));
					gc.fillRect(j * w, i * h, w, h);

				}
			}

		}

	}

}