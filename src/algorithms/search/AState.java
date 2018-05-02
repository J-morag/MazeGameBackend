package algorithms.search;

import java.util.ArrayList;
import java.util.Objects;

public abstract class AState implements Comparable{

    protected double cost;
    //protected ArrayList<AState> successors;
    protected AState parent;

    public AState(double cost){
        this.cost = cost;
        //successors = new ArrayList();
        this.parent = null;
    }

//    public AState(double heuristicDistance, ArrayList<AState> successors){
//        this.heuristicDistance = heuristicDistance;
//        successors = new ArrayList();
//    }

//    /**
//     *
//     * @return ArrayList<AState> of successors (states you can get to from this state).
//     */
//    public ArrayList<AState> getSuccessors(){
//        return successors;
//    }

    public double getCost() {
        return cost;
    }

    public AState getParent() {
        return parent;
    }

    public void setParent(AState parent) {
        this.parent = parent;
    }

    //    /**
//     *
//     * @param successors - a list of successors to this state. Will save a copy.
//     */
//
//
//    public void setSuccessors(ArrayList<AState> successors) {
//        this.successors = new ArrayList<>(successors);
//    }
//
//    /**
//     * uses the heuristicDistance value to compare two states.
//     * @param other - the AState to compare to
//     * @return - 0:= both objects are of equal heuristicDistance. positive integer:= this.heuristicDistance > other.heuristicDistance
//     *          negative integer:= this.heuristicDistance < other.heuristicDistance
//     * @throws NullPointerException - if the specified object is null
//     * @throws ClassCastException - if the specified object's type prevents it from being compared to this object.
//     */
    @Override
    public int compareTo(Object other) {
        if (other == null) throw new NullPointerException();
        if(!(other instanceof AState)) throw new ClassCastException();

        if(this.cost == ((AState)other).cost) return 0;
        else return this.cost > ((AState)other).cost ? 1 : -1 ;
    }

    @Override
    public abstract boolean equals(Object otherState);

    @Override
    public abstract int hashCode() ;

    @Override
    public abstract String toString() ;
}
