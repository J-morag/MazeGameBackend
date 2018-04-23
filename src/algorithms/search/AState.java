package algorithms.search;

import java.util.List;

public abstract class AState {


    public abstract List<AState> getSuccessors();
    @Override
    public abstract boolean equals(Object otherState);


}
