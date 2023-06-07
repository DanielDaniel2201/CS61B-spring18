package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    int repeatingTime;
    int worldBreadth;
    PercolationFactory PF;
    public PercolationStats(int N, int T, PercolationFactory pf) {   // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be larger than 0");
        }

        repeatingTime = T;
        worldBreadth = N;
        PF = pf;
    }

    public double mean() {                                           // sample mean of percolation threshold
        int counter = 0;
        double[] soFarEstimates = new double[repeatingTime];    //an array of doubles used to store the estimate every time

        while (counter < repeatingTime) {
            Percolation perc = PF.make(worldBreadth);   //make a new world

            while (perc.percolates()) {   //make sure open a valid site, which is blocked
                int row = StdRandom.uniform(0, worldBreadth);
                int col = StdRandom.uniform(0, worldBreadth);
                while (perc.world[row][col]) {
                    row = StdRandom.uniform(0, worldBreadth);
                    col = StdRandom.uniform(0, worldBreadth);
                }
                perc.open(row, col);
            }

            double estimate = (double) perc.numOpen / worldBreadth * worldBreadth;
            soFarEstimates[counter] = estimate;
            counter += 1;
        }

        return StdStats.mean(soFarEstimates);
    }

    public double stddev() {                                         // sample standard deviation of percolation threshold
        double MEAN = mean();
        double[] prevX = stddevHelper();
        double[] tmp = new double[repeatingTime];
        int counter = 0;

        while (counter < repeatingTime) {
            double tmpItem = (prevX[counter] - MEAN) * (prevX[counter] - MEAN);
            tmp[counter] = tmpItem;
            counter += 1;
        }

        return StdStats.mean(tmp) * repeatingTime / (repeatingTime - 1);
    }

    public double confidenceLow() {                                  // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(repeatingTime);
    }
    public double confidenceHigh() {                                 // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(repeatingTime);
    }
    private double[] stddevHelper() {
        int counter = 0;
        double[] soFarEstimates = new double[repeatingTime];    //an array of doubles used to store the estimate every time

        while (counter < repeatingTime) {
            Percolation perc = PF.make(worldBreadth);   //make a new world

            while (perc.percolates()) {   //make sure open a valid site, which is blocked
                int row = StdRandom.uniform(0, worldBreadth);
                int col = StdRandom.uniform(0, worldBreadth);
                while (perc.world[row][col]) {
                    row = StdRandom.uniform(0, worldBreadth);
                    col = StdRandom.uniform(0, worldBreadth);
                }
                perc.open(row, col);
            }

            double estimate = (double) perc.numOpen / worldBreadth * worldBreadth;
            soFarEstimates[counter] = estimate;
            counter += 1;
        }
        return soFarEstimates;
    }
}
