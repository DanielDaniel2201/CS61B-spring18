package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] world;
    private int worldBreadth;
    private WeightedQuickUnionUF UF;
    private int numOpen = 0;
    private int topVirtual1;
    private int topVirtual2;

    public Percolation(int N) {     // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N must be larger than 0!");
        }
        worldBreadth = N;
        world = new boolean[worldBreadth][worldBreadth];
        UF = new WeightedQuickUnionUF(worldBreadth * worldBreadth + 2);
        topVirtual1 = worldBreadth * worldBreadth;
        topVirtual2 = worldBreadth * worldBreadth + 1;
    }
    public void open(int row, int col) {      // open the site (row, col) if it is not open already
        if (row < 0 || col < 0 || row > worldBreadth - 1 || col > worldBreadth - 1) {
            throw new IndexOutOfBoundsException(
                    "the index should be in the range from 0 to N - 1, inclusively");
        }

        if (!world[row][col]) {
            world[row][col] = true;
            numOpen += 1;
            int index = xyTo1D(row, col);
            if (row > 0 && world[row - 1][col]) {
                int tmpIndex = xyTo1D(row - 1, col);
                UF.union(tmpIndex, index);
            }
            if (row < worldBreadth - 1 && world[row + 1][col]) {
                int tmpIndex = xyTo1D(row + 1, col);
                UF.union(tmpIndex, index);
            }
            if (col > 0 && world[row][col - 1]) {
                int tmpIndex = xyTo1D(row, col - 1);
                UF.union(tmpIndex, index);
            }
            if (col < worldBreadth - 1 && world[row][col + 1]) {
                int tmpIndex = xyTo1D(row, col + 1);
                UF.union(tmpIndex, index);
            }
        }

    }
    public boolean isOpen(int row, int col) {    // is the site (row, col) open?
        if (row < 0 || col < 0
                || row > worldBreadth - 1 || col > worldBreadth - 1) {
            throw new IndexOutOfBoundsException(
                    "the index should be in the range from 0 to N - 1, inclusively");
        }
        return world[row][col];
    }

    public boolean isFull(int row, int col) {  // is the site (row, col) full?
        if (row < 0 || col < 0 || row > worldBreadth - 1 || col > worldBreadth - 1) {
            throw new IndexOutOfBoundsException(
                    "the index should be in the range from 0 to N - 1, inclusively");
        }
        isPercolateHelper();
        int tmpIndex = xyTo1D(row, col);
        return UF.connected(tmpIndex, topVirtual1) && isOpen(row, col);
    }

    public int numberOfOpenSites() {          // number of open sites
        return numOpen;
    }

    public boolean percolates() {              // does the system percolate?
        isPercolateHelper();
        return UF.connected(topVirtual1, topVirtual2);
    }
//    public static void main(String[] args)   // use for unit testing (not required)

    private int xyTo1D(int x, int y) {   // convert 2d coordinates to 1d index
        return x * worldBreadth + y;
    }

    private void isPercolateHelper() {
        for (int i = 0; i < worldBreadth; i += 1) {
            int tmpIndex = xyTo1D(0, i);
            UF.union(topVirtual1, tmpIndex);
        }

        for (int i = 0; i < worldBreadth; i += 1) {
            int tmpIndex = xyTo1D(worldBreadth - 1, i);
            UF.union(topVirtual2, tmpIndex);
        }
    }

    public static void main(String[] args) {
    }
}
