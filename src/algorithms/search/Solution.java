package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the path from start position to goal position
 */
public class Solution implements Serializable{

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

    @Override
    public String toString() {
        String ans = "";
        for (AState state:
                pathList) {
            ans = ans + state.toString() + ", ";
        }
        return ans;
    }
}
