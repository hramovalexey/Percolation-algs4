import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int dmn; // structure dimension
    private int row; // Row number
    private int col; // Column number
    private final double[] pThrd; // Percolation threshold array
    private final int trialsN; // trials number
    private double mn, sdev, coLo, coHi; // mean, stddev, confLiHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("value must be > 0: " + n);
        }
        mn = -1;
        sdev = -1;
        coLo = -1;
        coHi = -1;
        dmn = n;
        pThrd = new double[trials];
        trialsN = trials;
        for (int i = 0; i < trials; i++) {

            Percolation ps = new Percolation(n);
            while (!ps.percolates()) {
                sto(ps);
                ps.open(row, col);
            }
            pThrd[i] = (double) ps.numberOfOpenSites() / (dmn * dmn);
        }
    }

    // random row|col number?
    private int rand() {
        return StdRandom.uniform(dmn) + 1;
    }

    // What site to open?
    private void sto(Percolation ps) {
        row = rand();
        col = rand();
        while (ps.isOpen(row, col)) {
            row = rand();
            col = rand();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mn == -1) mn = StdStats.mean(pThrd);
        return mn;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (sdev == -1) sdev = StdStats.stddev(pThrd);
        return sdev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        if (coLo == -1) coLo = mn - (1.96 * sdev) / Math.sqrt(trialsN);
        return coLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (coHi == -1) coHi = mn + (1.96 * sdev) / Math.sqrt(trialsN);
        return coHi;
    }

    public static void main(String[] args) {
        try {
            PercolationStats myObj = new PercolationStats(Integer.parseInt(args[0]),
                                                          Integer.parseInt(args[1]));
            StdOut.print("mean = " + myObj.mean() + "\n");
            StdOut.print("stddev = " + myObj.stddev() + "\n");
            StdOut.print(
                    "95% confidence interval = [" + myObj.confidenceLo() + ", " + myObj
                            .confidenceHi()
                            + "]");
        }

        catch (IllegalArgumentException e) {
            StdOut.print(e.getMessage());
        }

    }
}
