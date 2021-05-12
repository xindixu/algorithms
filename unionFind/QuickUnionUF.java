package unionFind;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  Compilation:  javac QuickUnionUF.java
 *  Execution:  java QuickUnionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                https://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                https://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Quick-find algorithm.
 *
 ******************************************************************************/

public class QuickUnionUF {
    private final int[] roots;
    private int count; // number of components

    public QuickUnionUF(int n) {
        count = n;
        roots = new int[count];
        for (int i = 0; i < count; i++) {
            roots[i] = i;
        }
    }

    public int count() {
        return count;
    }

    private void validate(int p) {
        int n = roots.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException(
                    String.format("Index %d is not between 0 and %d", p, n));
        }
    }

    /**
     * Find the root of the given element
     *
     * @param p - element to find
     * @return p's root
     */
    private int find(int p) {
        validate(p);
        int key = p;
        while (key != roots[key]) {
            key = roots[key];
        }
        return key;
    }

    /**
     * Test if the given elements are in the same component
     *
     * @param p - element 1
     * @param q - element 2
     * @return - whether or not these two elements are connected
     */
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return find(p) == find(q);
    }

    private void union(int p, int q) {
        validate(p);
        validate(q);

        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }

        roots[qRoot] = pRoot;
        count--;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickUnionUF uf = new QuickUnionUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
