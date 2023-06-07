package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    int repeatingTime;
    double[] soFarEstimates;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be larger than 0");
        }

        repeatingTime = T;
        soFarEstimates = new double[N];

        int counter = 0;
        while (counter < repeatingTime) {
            Percolation perc = pf.make(N);   //make a new world

            while (!perc.percolates()) {   //make sure open a valid site, which is blocked
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                while (perc.isOpen(row, col)) {
                    row = StdRandom.uniform(0, N);
                    col = StdRandom.uniform(0, N);
                }
                perc.open(row, col);
            }
            int numOpen = perc.numberOfOpenSites();
            double estimate = (double) numOpen / N * N;
            soFarEstimates[counter] = estimate;
            counter += 1;
        }
    }

    public double mean() {    // sample mean of percolation threshold
        return StdStats.mean(soFarEstimates);
    }

    public double stddev() {        // sample standard deviation of percolation threshold
        return StdStats.stddev(soFarEstimates);
    }

    public double confidenceLow() {      // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(repeatingTime);
    }
    public double confidenceHigh() {    // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(repeatingTime);
    }
}
