package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {

    SimpleMazeGenerator smg = new SimpleMazeGenerator();
    MyMazeGenerator mmg = new MyMazeGenerator();
    Maze maze = mmg.generate(1000, 1000);

    @Test
    void getName() {
        BestFirstSearch bfs = new BestFirstSearch();
        assertEquals("BestFirstSearch", bfs.getName());
    }

    @Test
    void runAlgorithm() {

    }

    @Test
    void getNumberOfNodesEvaluated() {
    }

    @Test
    void solve() {
    }
}