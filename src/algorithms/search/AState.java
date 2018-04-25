package algorithms.search;

import java.util.ArrayList;
import java.util.Objects;

public abstract class AState implements Comparable{

    protected double heuristicDistance;
    protected ArrayList<AState> successors;

    public AState(double heuristicDistance){
        this.heuristicDistance = heuristicDistance;
        successors = new ArrayList();
    }

    public AState(double heuristicDistance, ArrayList<AState> successors){
        this.heuristicDistance = heuristicDistance;
        successors = new ArrayList();
    }

    /**
     *
     * @return a new ArrayList<AState> of successors (states you can get to from this state).
     */
    public ArrayList<AState> getSuccessors(){
        return new ArrayList<>(successors);
    }

    /**
     *
     * @param successors - a list of successors to this state. Will save a copy.
     */
    public void setSuccessors(ArrayList<AState> successors) {
        this.successors = new ArrayList<>(successors);
    }

    /**
     * uses the heuristicDistance value to compare two states.
     * @param other - the AState to compare to
     * @return - 0:= both objects are of equal heuristicDistance. positive integer:= this.heuristicDistance > other.heuristicDistance
     *          negative integer:= this.heuristicDistance < other.heuristicDistance
     * @throws NullPointerException - if the specified object is null
     * @throws ClassCastException - if the specified object's type prevents it from being compared to this object.
     */
    @Override
    public int compareTo(Object other) {
        if (other == null) throw new NullPointerException();
        if(!(other instanceof AState)) throw new ClassCastException();

        if(this.heuristicDistance == ((AState)other).heuristicDistance) return 0;
        else return this.heuristicDistance > ((AState)other).heuristicDistance ? 1 : -1 ;
    }

    @Override
    public abstract boolean equals(Object otherState);

    @Override
    public abstract int hashCode() ;

    @Override
    public abstract String toString() ;
}
