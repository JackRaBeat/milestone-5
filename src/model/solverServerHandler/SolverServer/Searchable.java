package model.solverServerHandler.SolverServer;

import java.io.Serializable;
import java.util.*;

public interface Searchable<T> extends Serializable {
State<T> getInitialState();
boolean isGoalState(State<T> s);
List<State<T>> getAllPossibleStates(State<T> s);//successors

}