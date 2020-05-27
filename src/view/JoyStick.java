package view;

import javafx.scene.paint.Paint;
import javafx.beans.NamedArg;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class JoyStick extends Canvas {

	private double width,height;
	private double radius_small,radius_big;
	private String base_paint,ball_paint;



	public JoyStick(@NamedArg("radius_small") Double radius_small, @NamedArg("radius_big") Double radius_big){
		this.width = getWidth();
		System.out.println(getWidth());
		this.height = getHeight();
		this.radius_small = radius_small;
		this.radius_big = radius_big;
		this.base_paint="#E3E0E2";
		this.ball_paint="#515151";
	}
	
	public void redraw() {		
		GraphicsContext gc = getGraphicsContext2D();
		double x=radius_big;
		double y=radius_big;
	 
		gc.setFill(Paint.valueOf(base_paint));
		gc.fillOval((width-radius_big)/2+x, (height-radius_big)/2+x, radius_big, radius_big);
		gc.setFill(Paint.valueOf("#000000"));
		gc.strokeOval((width-radius_big)/2+x, (height-radius_big)/2+x, radius_big, radius_big);
		
		gc.setFill(Paint.valueOf(ball_paint));
		gc.fillOval((width-radius_small)/2+y,(height-radius_small)/2+y, radius_small, radius_small);
		gc.setFill(Paint.valueOf("#000000"));
		gc.strokeOval((width-radius_small)/2+y,(height-radius_small)/2+y, radius_small, radius_small);
				
	}

}
