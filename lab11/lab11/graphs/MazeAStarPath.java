package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX;
    private int targetY;
    private MinPQ<Node> fringe = new MinPQ<>();
    int totalMoves = 0;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int x = maze.toX(v);
        int y = maze.toY(v);
        return Math.abs(x - targetX) + Math.abs(y - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        Node firstV = new Node(s, null, 0);
        fringe.insert(firstV);

        while (fringe.min().oneDCoor != t) {
            Node smallestV = fringe.delMin();
            for (int v: maze.adj(smallestV.oneDCoor)) {
                if (smallestV.prevNode == null || v != smallestV.prevNode.oneDCoor) {
                    distTo[v] = smallestV.moveSoFar + 1;
                    fringe.insert(new Node(v, smallestV, smallestV.moveSoFar + 1));
                }
            }
        }

        Node lastNode = fringe.min();
        while (lastNode != null) {
            totalMoves += 1;
            lastNode = lastNode.prevNode;
        }

        Node lastN = fringe.min();
        while (lastN.prevNode != null && totalMoves >= 0) {
            marked[lastN.oneDCoor] = true;
            edgeTo[lastN.oneDCoor] = lastN.prevNode.oneDCoor;
            lastN = lastN.prevNode;
            totalMoves -= 1;
            announce();
        }

        marked[s] = true;
        announce();
    }

    @Override
    public void solve() {
        astar(s);
    }

    private class Node implements Comparable<Node>{
        private int oneDCoor;
        private Node prevNode;
        private int moveSoFar;
        Node(int d, Node prev, int moves) {
            oneDCoor = d;
            prevNode = prev;
            moveSoFar = moves;
        }

        @Override
        public int compareTo(Node o) {
            return distTo[this.oneDCoor] + h(this.oneDCoor) - distTo[o.oneDCoor] - h(o.oneDCoor);
        }
    }
}

