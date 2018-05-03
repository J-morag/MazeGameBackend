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
        BestFirstSearch best = new BestFirstSearch();
        assertEquals("BestFirstSearch", best.getName());
    }

    @Test
    void getNumberOfNodesEvaluated() {
        BestFirstSearch best = new BestFirstSearch();
        assertTrue(best.getNumberOfNodesEvaluated() > 0);
    }

    @Test
    void solve() {
        BestFirstSearch best = new BestFirstSearch();
        ISearchable searchProblem = null;
        assertEquals(null, best.solve(searchProblem));
    }

    @Test
    void sameSolutionLength() {
        MyMazeGenerator mmg = new MyMazeGenerator();
        Maze maze = mmg.generate(1000, 1000);
        ISearchable searchProblem = new SearchableMaze(maze);
        BestFirstSearch best = new BestFirstSearch();
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        for (int i = 0; i < 2 ; i++) {
            Solution bestSolution = best.solve(searchProblem);
            Solution bfsSolution = bfs.solve(searchProblem);
            assertEquals(bestSolution.getSolutionPath().size(), bfsSolution.getSolutionPath().size());
        }
    }
}