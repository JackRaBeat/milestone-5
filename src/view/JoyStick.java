package view;

import javafx.scene.paint.Paint;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class JoyStick extends Canvas {

	private double width,height;
	private double centerX, centerY;
	private double radius_small,radius_big;
	private String base_paint,ball_paint;
	private double anchorX, anchorY;
	private double smallX, smallY;
	public JoyStick(@NamedArg("radius_small") Double radius_small, @NamedArg("radius_big") Double radius_big){
		this.radius_small = radius_small;
		this.radius_big = radius_big;
		this.base_paint="#E3E0E2";
		this.ball_paint="#515151";
		smallX = 0;
		smallY = 0;
		Platform.runLater(()->{
		    this.width = getWidth(); 
			this.height = getHeight();
			redraw();
		});
	}
	
	//the methods are capable of returning the set dimensions only after invoking the constructor.
	private void setDimensions()
	{
		//took the method off cause not needed (can use the "runLater" function instead)
	}
	
	public void drawCenteredCircle(GraphicsContext g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.fillOval(x,y,r,r);
		}
	public void redraw() {		
		
		setDimensions();
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);
		drawCenteredCircle();
		gc.setFill(Paint.valueOf(base_paint));
		gc.fillOval((width-radius_big)/2, (height-radius_big)/2, radius_big, radius_big);
		gc.setFill(Paint.valueOf("#000000"));
		gc.strokeOval((width-radius_big)/2, (height-radius_big)/2, radius_big, radius_big);
		gc.setFill(Paint.valueOf(ball_paint));
		drawCenteredCircle(gc, smallX, smallY,radius_small);
		gc.fillOval(smallX,smallY, radius_small, radius_small);
		gc.setFill(Paint.valueOf("#000000"));
		gc.strokeOval((width-radius_small)/2 + smallX,(height-radius_small)/2 + smallY, radius_small, radius_small);
				
	}
	
	public void setMouseEventHandlers(){
		this.setOnMousePressed((e)->
		{
			anchorX = e.getSceneX();
			anchorY = e.getSceneY();
			System.out.println(anchorX + '\n');
		});
		this.setOnMouseDragged((e)->
		{
			smallX = e.getSceneX() - anchorX;
			smallY = e.getSceneY() - anchorY;
			redraw();
			System.out.println("x: " +  e.getSceneX() + " y: " + e.getSceneY());
			});
		this.setOnMouseReleased((e)->{
			
			smallX = 0;
			smallY = 0;
			redraw();
		});
	}
}
