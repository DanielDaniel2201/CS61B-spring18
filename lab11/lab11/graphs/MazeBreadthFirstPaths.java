package lab11.graphs;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */


    private static final int INFINITY = Integer.MAX_VALUE;
    /**
     * one dimensional coordinate of the starting vertex
     */
    private int s;

    /**
     * one dimensional coordinate of the ending vertex
     */
    private int t;

    /** whether the target vertex is reached*/
    private boolean targetFound = false;

    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        edgeTo[s] = s;
        announce();
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int w) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> fringe = new LinkedList<>();

        for (int v = 0; v < maze.V(); v += 1) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        fringe.add(w);
        marked[s] = true;

        while (!fringe.isEmpty()) {
            int s = fringe.poll();

            if (s == t) {
                targetFound = true;
            }
            if (targetFound) {
                break;
            }

            for (int a: maze.adj(s)) {
                if (!marked[a]) {
                    marked[a] = true;
                    distTo[a] = 1 + distTo[s];
                    edgeTo[a] = s;
                    announce();
                    fringe.add(a);
                }
            }
        }
    }


    @Override
    public void solve() {
         bfs(s);
    }
}

