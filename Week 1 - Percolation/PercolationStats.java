import java.util.Random;

public class PercolationStats {
    private int count;
    Random rand = new Random();

    PercolationStats(int N, int T) {
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            while (!percolation.percolates()) {
                percolation.open(rand.nextInt(N - 1), rand.nextInt(N - 1));
            }
        }
    }

    public double mean() {
        return 0;
    }

    public double stddev() {
        return 0;
    }

    public double confidenceLow() {
        return 0;
    }


    public double confidenceHigh() {
        return 0;
    }
}
