package algorithms.mazeGenerators;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    void toByteArray() {
        Random rnd = new Random();
        for (int i = 0; i < 4; i++) {
            AMazeGenerator mazeGenerator = new MyMazeGenerator();

            Maze maze = mazeGenerator.generate(rnd.nextInt(1000), rnd.nextInt(1000));

            long time = System.currentTimeMillis();
            Maze mazeEncodedDecoded = new Maze(maze.toByteArray());
            System.out.println(System.currentTimeMillis() - time);

            assertEquals(maze, mazeEncodedDecoded);


            AMazeGenerator mazeGenerator2 = new SimpleMazeGenerator();

            Maze maze2 = mazeGenerator2.generate(rnd.nextInt(1000), rnd.nextInt(1000));

            long time2 = System.currentTimeMillis();
            Maze mazeEncodedDecoded2 = new Maze(maze2.toByteArray());
            System.out.println(System.currentTimeMillis() - time2);

            assertEquals(maze2, mazeEncodedDecoded2);

        }

    }
}