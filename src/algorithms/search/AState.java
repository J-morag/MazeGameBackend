package algorithms.search;

import java.util.List;

public abstract class AState implements Comparable{

    private double heuristicDistance;

    public AState(){
        heuristicDistance = 0.0;
    }

    public abstract List<AState> getSuccessors();

    @Override
    public abstract boolean equals(Object otherState);

    /**
     * uses the heuristicDistance value to compare two states.
     * @param other - the AState to compare to
     * @return - 0:= both objects are of equal heuristicDistance. positive integer:= this.heuristicDistance > other.heuristicDistance
     *          negative integer:= this.heuristicDistance < other.heuristicDistance
     */
    @Override
    public int compareTo(Object other) {
        // TODO NullPointerException
        if(!(other instanceof AState)) throw new ClassCastException();

        if(this.heuristicDistance == ((AState)other).heuristicDistance) return 0;
        else return this.heuristicDistance > ((AState)other).heuristicDistance ? 1 : -1 ;
    }

    @Override
    public abstract String toString() ;
}
