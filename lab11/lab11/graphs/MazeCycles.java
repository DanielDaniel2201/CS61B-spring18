package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    int start;

    int[] edgeToCopy = new int[maze.V()];

    boolean detected;
    public MazeCycles(Maze m) {
        super(m);
        start = maze.xyTo1D(1, 1);
        detected = false;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        cycleDetect(start);
    }

    // Helper methods go here
    private void cycleDetect(int v) {
        marked[v] = true;
        announce();

        for (int w: maze.adj(v)) {
            if (!marked[w]) {
                edgeToCopy[w] = v;
                announce();
                cycleDetect(w);
                if (detected) {
                    return;
                }
            } else {
                if (w != edgeToCopy[v]) {
                    detected = true;
                    edgeTo[w] = v;
                    edgeTo[v] = edgeToCopy[v];
                    while (edgeToCopy[v] != w) {
                        v = edgeToCopy[v];
                        edgeTo[v] = edgeToCopy[v];
                    }
                    announce();
                }
                if (detected) {
                    return;
                }
            }
        }
    }
}

