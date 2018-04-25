package algorithms.search;

import java.util.ArrayList;

/**
 * Represents the path from start position to goal position
 */
public class Solution {

    private ArrayList<AState> pathList;

    public Solution() {
        this.pathList = new ArrayList<AState>() {};
    }

    public void add(AState state){
        pathList.add(state);
    }

    public void add(int index, AState state){
        pathList.add(index, state);
    }

    public AState remove(){
        return pathList.remove(pathList.size()-1);
    }

    public AState remove(int index){
        return pathList.remove(0);
    }

    public ArrayList<AState> getSolutionPath () {
        return pathList;
    }
}
