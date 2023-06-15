package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private MinPQ<SearchNode> mPQSearchNodes = new MinPQ<>();
    private int ultimateMoves = -1;
    private List<WorldState> ultimateSequence = new ArrayList<>();
    private List<WorldState> ultimateSequenceHelper = new ArrayList<>();

    /**Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        //construct the smallest SearchNode and insert it into the MinPQ;
        SearchNode firstNode = new SearchNode(0, null, initial);
        mPQSearchNodes.insert(firstNode);

        //check if the first item of MinPQ is the goal;
        while (!mPQSearchNodes.min().worldState.isGoal()) {

            // get the smallest SearchNode and put it into the ultimateSequence;
            SearchNode smallesSearchNode = mPQSearchNodes.delMin();

            //put every one of its neighbor into the mPQSearchNodes except its "grandparent";
            for (WorldState ws : smallesSearchNode.worldState.neighbors()) {
                if (smallesSearchNode.prevSearchNode == null ||
                        !ws.equals(smallesSearchNode.prevSearchNode.worldState)) {
                    mPQSearchNodes.insert(
                            new SearchNode(smallesSearchNode.moveSoFar + 1, smallesSearchNode, ws));
                }
            }
        }

        SearchNode sn = mPQSearchNodes.delMin();
        while (sn != null) {
            ultimateMoves += 1;
            ultimateSequenceHelper.add(sn.worldState);
            sn = sn.prevSearchNode;
        }
    }

    /**Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState.*/
    public int moves() {
        return ultimateMoves;
    }

    /**Returns a sequence of WorldStates from the initial WorldState
     to the solution.*/
    public Iterable<WorldState> solution() {
        for (int i = ultimateMoves; i >= 0; i -= 1) {
            ultimateSequence.add(ultimateSequenceHelper.get(i));
        }
        return ultimateSequence;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private int moveSoFar;
        private SearchNode prevSearchNode;
        private WorldState worldState;

        SearchNode(int m, SearchNode s, WorldState ws) {
            moveSoFar = m;
            prevSearchNode = s;
            worldState = ws;
        }

        public WorldState getWorldState() {
            return worldState;
        }

        public SearchNode getPrevSearchNode() {
            return prevSearchNode;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.moveSoFar + this.worldState.estimatedDistanceToGoal() -
                    o.moveSoFar - o.worldState.estimatedDistanceToGoal();
        }
    }
}
