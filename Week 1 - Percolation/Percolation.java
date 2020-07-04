import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] square;
    private int count;
    private int size = 0;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF full;

    public int encode(int i, int j) {
        return size * i + j + 1;
    }

    public Percolation(int N) {
        if (N <= 0) throw new IndexOutOfBoundsException("N can not be equal to 0");
        square = new boolean[N][N];
        size = N;
        uf = new WeightedQuickUnionUF(N ^ 2 + 2);
        //full = new WeightedQuickUnionUF(N ^ 2 + 1);
        virtualTop = 0;
        virtualBottom = N * N + 1;
    }

    public void open(int i, int j) {
        //if (i > size - 1 || j > square[i].length) throw new IndexOutOfBoundsException("Out of range");
        if (isOpen(i, j)) return;
        square[i][j] = true;
        count += 1;

        if (i == 0) {
            uf.union(encode(i, j), virtualTop);
            //full.union(encode(i, j), virtualTop);
        }
        if (i == size - 1) {
            uf.union(encode(i, j), virtualBottom);
        }

        //checks for union
        checkNeighbors(i, j);

    }

    public boolean isOpen(int i, int j) {
        //throw new IllegalArgumentException("HI JOHN!!!!!!!!!!!!!!!!!!!!!!!!");
        if (i > size || j > square[i].length) throw new IndexOutOfBoundsException("Out of range");
        if (square[i][j]) return true;
        else return false;
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean isFull(int i, int j) {
        //if (uf.connected(encode(i, j), virtualTop)) return true;
        //else return false;

        if (isOpen(i, j) && isValid(i, j) && uf.find(virtualTop) == uf.find(encode(i, j))) {
            return true;
        } else return false;

    }


    public boolean percolates() {
        if (uf.find(virtualBottom) == uf.find(virtualTop)) {
            return true;
        } else return false;
    }

    public void checkNeighbors(int i, int j) {

        if (isValid(i + 1, j) && isOpen(i + 1, j)) {
            uf.union(encode(i + 1, j), encode(i, j));
            //full.union(encode(i + 1, j), encode(i, j));
        }
        if (isValid(i - 1, j) && isOpen(i - 1, j)) {
            uf.union(encode(i - 1, j), encode(i, j));
            //full.union(encode(i - 1, j), encode(i, j));
        }
        if (isValid(i, j + 1) && isOpen(i, j + 1)) {
            uf.union(encode(i, j + 1), encode(i, j));
            //full.union(encode(i, j + 1), encode(i, j));
        }
        if (isValid(i, j - 1) && isOpen(i, j - 1)) {
            uf.union(encode(i, j - 1), encode(i, j));
            //full.union(encode(i, j - 1), encode(i, j));
        }
    }

    public boolean isValid(int i, int j) {
        if (i >= size || j >= size || i < 0 || j < 0)
            return false;
        else
            return true;
    }


    public static void main(String[] args) {

    }

}
