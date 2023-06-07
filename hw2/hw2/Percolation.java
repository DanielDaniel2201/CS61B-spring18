package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] world;
    private int N;
    private WeightedQuickUnionUF UF;
    private int numOpen = 0;
    private int topVirtual1;
    private int topVirtual2;

    public Percolation(int N) {     // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N must be larger than 0!");
        }
        this.N = N;
        topVirtual1 = N * N;
        topVirtual2 = N * N + 1;
        world = new boolean[N][N];
        UF = new WeightedQuickUnionUF(N * N + 2);

        for (int i = 0; i < N; i += 1) {
            int tmpIndex = xyTo1D(0, i);
            UF.union(topVirtual1, tmpIndex);
        }

        for (int i = 0; i < N; i += 1) {
            int tmpIndex = xyTo1D(N - 1, i);
            UF.union(topVirtual2, tmpIndex);
        }
    }

    public void open(int row, int col) {      // open the site (row, col) if it is not open already
        validityCheck(row, col);
        if (world[row][col]) {
            return;
        }
        world[row][col] = true;
        numOpen += 1;

        int index = xyTo1D(row, col);
        if (row > 0 && world[row - 1][col]) {
            int tmpIndex = xyTo1D(row - 1, col);
            UF.union(tmpIndex, index);
        }
        if (row < N - 1 && world[row + 1][col]) {
            int tmpIndex = xyTo1D(row + 1, col);
            UF.union(tmpIndex, index);
        }
        if (col > 0 && world[row][col - 1]) {
            int tmpIndex = xyTo1D(row, col - 1);
            UF.union(tmpIndex, index);
        }
        if (col < N - 1 && world[row][col + 1]) {
            int tmpIndex = xyTo1D(row, col + 1);
            UF.union(tmpIndex, index);
        }
    }
    public boolean isOpen(int row, int col) {    // is the site (row, col) open?
        validityCheck(row, col);
        return world[row][col];
    }

    public boolean isFull(int row, int col) {  // is the site (row, col) full?
        validityCheck(row, col);
        int tmpIndex = xyTo1D(row, col);
        return isOpen(row, col) && UF.connected(tmpIndex, topVirtual1);
    }

    public int numberOfOpenSites() {          // number of open sites
        return numOpen;
    }

    public boolean percolates() {              // does the system percolate?
        return numOpen != 0 && UF.connected(topVirtual1, topVirtual2);
    }

    private int xyTo1D(int x, int y) {   // convert 2d coordinates to 1d index
        return x * N + y;
    }

    private void validityCheck(int row, int col) {
        if (row < 0 || col < 0 || row > N - 1 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
    }
    public static void main(String[] args) {
    }
}
