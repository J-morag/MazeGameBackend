package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBreadthFirstSearch {

    SimpleMazeGenerator smg = new SimpleMazeGenerator();
    MyMazeGenerator mmg = new MyMazeGenerator();
    Maze maze = mmg.generate(1000, 1000);
    SearchableMaze searchableMaze = new SearchableMaze(maze);
    BreadthFirstSearch bfs = new BreadthFirstSearch();

    @Test
    void getName() {
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        assertEquals("BreadthFirstSearch", bfs.getName());
    }

    @Test
    void solve() {

        long time = System.currentTimeMillis();
        bfs.solve(searchableMaze);
        time = System.currentTimeMillis() - time ;
        assertTrue(time<20000);


    }

//    @Test
//    void testNullArgument() {
//        assertThrows(IllegalArgumentException.class, searchableMaze -> bfs.solve(searchableMaze));
//    }
}