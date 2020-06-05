package model.solverServerHandler.SolverServer;

import java.util.LinkedList;
import java.awt.Point;
import java.util.List;


public class Matrix implements Searchable<Point> {

	State<Point>[][] mat;
	State<Point> initialState;
	State<Point> goalState;
	
	public Matrix()//default CTOR
	{
		
	}
	
	public Matrix(Double[][] mat,State<Point> initial,State<Point> goal)
	{
		
		this.mat=(State<Point>[][]) new State<?>[mat.length][mat[0].length];
	 for(int i=0;i<mat.length;i++)
	 {
		 for(int j=0;j<mat[i].length;j++)
		 {
		this.mat[i][j]=new State<Point>(new Point(i,j));
		this.mat[i][j].setCost(mat[i][j]);
		this.mat[i][j].setCameFrom(null);
	 }
		 
	}
	 	
	 this.initialState=initial;
	 this.goalState=goal;
	}
	
	
	public State<Point>[][] getMat() {//all setters and getters.
		return mat;
	}

	public State<Point> getGoalState() {
		return goalState;
	}
	
	@Override
	public State<Point> getInitialState() {
	return this.initialState;
	}

	public void setMat(State<Point>[][] mat) {
		this.mat = mat;
	}

	public void setInitialState(State<Point> initialState) {
		this.initialState = initialState;
	}

	public void setGoalState(State<Point> goalState) {
		this.goalState = goalState;
	}


	@Override
	public boolean isGoalState(State<Point> s) {
		return (goalState.equals(s));
	}

	@Override
	public List<State<Point>> getAllPossibleStates(State<Point> s) {//?
		List<State<Point>> l=new LinkedList<State<Point>>();
		 int x=s.getState().x;
		 int y=s.getState().y;//we return all the actual possible states
		 //let the algorithm itself distinguish and pick the possible and best state.
		 //boundaries check 
		 if(x-1>=0) l.add(mat[x-1][y]);
		 if(y-1>=0) l.add(mat[x][y-1]);
		 if(x+1<mat.length) l.add(mat[x+1][y]);
		 if(y+1<mat[0].length) l.add(mat[x][y+1]);
		 return l;

	}

}
