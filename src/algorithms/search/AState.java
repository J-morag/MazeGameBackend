package algorithms.search;

import java.util.List;

public abstract class AState {


    public abstract List<AState> getSuccessors();
    public abstract boolean equals(AState otherState);

}
