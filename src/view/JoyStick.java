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
		super();
		this.radius_small = radius_small;
		this.radius_big = radius_big;
		this.base_paint="#E3E0E2";
		this.ball_paint="#515151";
	}
	
	//the methods are capable of returning the set dimensions only after invoking the constructor.
	private void setDimensions()
	{
	    this.width = getWidth(); 
		this.height = getHeight();
	}
	
	
	public void redraw() {		
		setDimensions();
		GraphicsContext gc = getGraphicsContext2D();


		gc.setFill(Paint.valueOf(base_paint));
		gc.fillOval((width-radius_big)/2, (height-radius_big)/2, radius_big, radius_big);
		gc.setFill(Paint.valueOf("#000000"));
		gc.strokeOval((width-radius_big)/2, (height-radius_big)/2, radius_big, radius_big);
		
		gc.setFill(Paint.valueOf(ball_paint));
		gc.fillOval((width-radius_small)/2,(height-radius_small)/2, radius_small, radius_small);
		gc.setFill(Paint.valueOf("#000000"));
		gc.strokeOval((width-radius_small)/2,(height-radius_small)/2, radius_small, radius_small);
				
	}

}
