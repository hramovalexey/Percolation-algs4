import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// creates n-by-n grid, with all sites initially blocked
public class Percolation {
    private final WeightedQuickUnionUF wqu;  // main instance
    private final WeightedQuickUnionUF wqu2; // For backwash prevention
    private boolean[] op; // what sites are open?
    // private int size; // sites number
    private final int dmn; // grid dimension
    private int opens; // number of open sites
    private boolean isPerc = false; // is Percolated? (needed for backwash prevention)


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("value at constructor should be > 0");
        }
        int size = n * n + 2;
        dmn = n;
        op = new boolean[size];
        opens = 0;
        wqu = new WeightedQuickUnionUF(size);
        wqu2 = new WeightedQuickUnionUF(size);
        for (int i = 0; i < size; i++) {
            op[i] = false;
        }
        op[0] = true;
        op[size - 1] = true;
    }

    // Percolated state setter
    private void setPerc() {
        isPerc = true;
    }

    // Calculates position (n) at grid
    private int number(int row,
                       int col) {
        return ((row - 1) * dmn + col);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (row > dmn || row < 1 || col > dmn || col < 1) {
            throw new IllegalArgumentException("value at open method must be between 1 and " + dmn);
        }
        if (isOpen(row, col)) return;
        int current = number(row, col); // current site id
        int upper = current - dmn;
        int lower = current + dmn;
        int left = current - 1;
        int right = current + 1;
        op[current] = true;
        opens = opens + 1;
        if (row == 1) {
            wqu.union(0, current);
            wqu2.union(0, current);
            if (dmn == 1 || isOpen(row + 1, col)) {
                wqu.union(lower, current);
                wqu2.union(lower, current);
            }
        }

        if (row == dmn) {
            wqu.union(op.length - 1, current);
            if (dmn == 1 || isOpen(row - 1, col)) {
                wqu.union(upper, current);
                wqu2.union(upper, current);
            }
        }

        if (row > 1 && row < dmn) {
            if (isOpen(row + 1, col)) {
                wqu.union(lower, current);
                wqu2.union(lower, current);
            }
            if (isOpen(row - 1, col)) {
                wqu.union(upper, current);
                wqu2.union(upper, current);
            }
        }

        if (col > 1 && col < dmn) {
            if (isOpen(row, col + 1)) {
                wqu.union(right, current);
                wqu2.union(right, current);
            }
            if (isOpen(row, col - 1)) {
                wqu.union(left, current);
                wqu2.union(left, current);
            }
        }

        if ((dmn != 1 && col == 1) && (isOpen(row, col + 1))) {
            wqu.union(right, current);
            wqu2.union(right, current);
        }

        if ((dmn != 1 && col == dmn) && (isOpen(row, col - 1))) {
            wqu.union(left, current);
            wqu2.union(left, current);
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > dmn || row < 1 || col > dmn || col < 1) {
            throw new IllegalArgumentException(
                    "value at isOpen method must be between 1 and " + dmn);
        }
        boolean isOp = false;
        if (op[number(row, col)]) {
            isOp = true;
        }
        return isOp;
    }

    // is the site (row, col) full? (also has side effect: sets system as percolated if place from last row is full)
    public boolean isFull(int row, int col) {
        if (row > dmn || row < 1 || col > dmn || col < 1) {
            throw new IllegalArgumentException(
                    "value at isFull method must be between 1 and " + dmn);
        }
        boolean isFu = false;
        if (isOpen(row, col) && wqu2.connected(0, (number(row, col)))) {
            isFu = true;
            if (row == dmn) setPerc();
        }
        return isFu;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opens;
    }

    // does the system percolate?
    public boolean percolates() {
        if (isPerc) return true;
        if (wqu.connected(op.length - 1, 0)) {
            setPerc();
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        Percolation perc = new Percolation(3);
        perc.open(1, 3);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(3, 1);
        System.out.println(perc.isFull(3, 1));
        System.out.println(perc.numberOfOpenSites());
        System.out.println(perc.percolates());
    }
}


