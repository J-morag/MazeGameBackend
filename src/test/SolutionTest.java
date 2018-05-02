package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import java.util.ArrayList;


public class SolutionTest {


        public static void main(String[] args) {
//        MazeState m = new MazeState(0,null,5,0);
//        MazeState k = new MazeState(0,null,5,0);
//        System.out.println(k.equals(m));
//        System.out.println(k.hashCode());
//        System.out.println(m.hashCode());

            IMazeGenerator mg = new MyMazeGenerator();
            Maze maze = mg.generate(50, 50);

            //maze.print();
            System.out.format("Start State : %s || Goal State : %s", maze.getStartPosition(), maze.getGoalPosition());

            SearchableMaze searchableMaze = new SearchableMaze(maze);
//        SearchableMaze searchableMaze2 = new SearchableMaze(maze);
            Maze maze1=maze;
            maze =putSolutionToMaze(solveProblem(searchableMaze,new DepthFirstSearch()),maze);
//         maze1 =putSolutionToMaze(solveProblem(searchableMaze2,new BestFirstSearch()),maze);
//                solveProblem(searchableMaze, new BreadthFirstSearch());
//        solveProblem(searchableMaze, new DepthFirstSearch());
//        solveProblem(searchableMaze, new BestFirstSearch());
            System.out.println("Printing Maze Solution");
            print(maze);
//        print(maze1);

        }


        private static Solution solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
            //Solve a searching problem with a searcher
            Solution solution = searcher.solve(domain);
            System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
            return solution;
            //Printing Solution Path
//        System.out.println("Solution path:");
//        ArrayList<AState> solutionPath = solution.getSolutionPath();
//        for (int i = 0; i < solutionPath.size(); i++) {
//            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
//        }
        }


    private static Maze putSolutionToMaze(Solution sol, Maze maze) {
        for (int i = 0; i < sol.getSolutionPath().size(); i++) {
            MazeState current = (MazeState) sol.getSolutionPath().get(i);
            maze.getMazeMap()[current.getPosition().getRowIndex()][current.getPosition().getColumnIndex()] = 5;
        }
        return maze;
    }
        public static void print(Maze maze) {
            for (int i = 0; i < maze.getMazeMap().length ; i++) {
                for (int j = 0; j < maze.getMazeMap()[0].length ; j++) {
                    if(i==maze.getStartPosition().getRowIndex() && j==maze.getStartPosition().getColumnIndex()){
                        System.out.print("S");
                    }
                    else if(i==maze.getGoalPosition().getRowIndex() && j==maze.getGoalPosition().getColumnIndex()){
                        System.out.print("E");
                    }
                    else if(maze.getMazeMap()[i][j]==1){
                        System.out.print("▓");
                    }
                    else if(maze.getMazeMap()[i][j]==0) {
                        System.out.print("░");
                    }
                    else if(maze.getMazeMap()[i][j]==5){
                        System.out.print("X");
                    }
                }
                System.out.println("");
            }

        }
    }






