package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    MinPQ<SearchNode> MPQSearchNodes = new MinPQ<>();
    int ultimateMoves = -1;
    List<WorldState> ultimateSequence = new ArrayList<>();
    List<WorldState> ultimateSequenceHelper = new ArrayList<>();

    /**Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        //construct the smallest SearchNode and insert it into the MinPQ;
        SearchNode firstNode = new SearchNode(0, null, initial);
        MPQSearchNodes.insert(firstNode);

        //check if the first item of MinPQ is the goal;
        while (!MPQSearchNodes.min().WorldState.isGoal()) {

            // get the smallest SearchNode and put it into the ultimateSequence;
            SearchNode smallesSearchNode = MPQSearchNodes.delMin();

            //put every one of its neighbor into the MPQSearchNodes except its "grandparent";
            for (WorldState ws : smallesSearchNode.WorldState.neighbors()) {
                if (smallesSearchNode.prevSearchNode == null || !ws.equals(smallesSearchNode.prevSearchNode.WorldState)) {
                    MPQSearchNodes.insert(new SearchNode(smallesSearchNode.moveSoFar + 1, smallesSearchNode, ws));
                }
            }
        }

        SearchNode sn = MPQSearchNodes.delMin();
        while (sn != null) {
            ultimateMoves += 1;
            ultimateSequenceHelper.add(sn.WorldState);
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

    private class SearchNode implements Comparable<SearchNode>{
        private int moveSoFar;
        private SearchNode prevSearchNode;
        private WorldState WorldState;

        public SearchNode(int m, SearchNode s, WorldState ws) {
            moveSoFar = m;
            prevSearchNode = s;
            WorldState = ws;
        }

        public WorldState getWorldState() {
            return WorldState;
        }

        public SearchNode getPrevSearchNode() {
            return prevSearchNode;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.moveSoFar + this.WorldState.estimatedDistanceToGoal() - o.moveSoFar - o.WorldState.estimatedDistanceToGoal();
        }
    }
}
