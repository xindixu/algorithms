package sorting;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

@SuppressWarnings("rawtypes")
public class TraceInsertion {
    private final static double X_POSITION_I = -2.50;
    private final static double X_POSITION_J = -1.25;

    // This class should not be instantiated.
    private TraceInsertion() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param array the array to be sorted
     */
    public static void sort(Comparable[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int j;
            // exchange array[i] with each larger entry on the left
            for (j = i; j > 0; j--) {
                if (less(array[j], array[j - 1])) {
                    exch(array, j, j - 1);
                } else {
                    break;
                }
            }
            draw(array, i, i, j);
            assert isSorted(array, 0, i);
        }
        assert isSorted(array);
    }

    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/

    // is the array a[] sorted?
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (Comparable objectComparable : a) {
            StdOut.println(objectComparable);
        }
    }

    private static void header(String[] array) {
        int n = array.length;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(n / 2.0, -3, "a[ ]");
        // numbers
        for (int i = 0; i < n; i++)
            StdDraw.text(i, -2, i + "");
        StdDraw.text(X_POSITION_I, -2, "i");
        StdDraw.text(X_POSITION_J, -2, "j");
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(-3, -1.5, n - 0.5, -1.5);
        StdDraw.setPenColor(StdDraw.BLACK);

        // elements
        for (int i = 0; i < n; i++)
            StdDraw.text(i, -1, array[i]);
    }

    public static void draw(Comparable[] array, int row, int i, int j) {
        int n = array.length;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(X_POSITION_I, row, i + "");
        StdDraw.text(X_POSITION_J, row, j + "");
        // elements
        for (int k = 0; k < n; k++) {
            if (k == j) StdDraw.setPenColor(StdDraw.BOOK_RED);
            else if (k < j) StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else if (k > i) StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(k, row, array[k] + "");
        }
    }

    private static void footer(String[] array) {
        int n = array.length;
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < n; i++)
            StdDraw.text(i, n, array[i]);
    }

    /**
     * Reads in a sequence of strings from standard input; selection sorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;

        // set canvas size
        StdDraw.setCanvasSize(30 * (n + 3), 30 * (n + 3));
        StdDraw.setXscale(-4, n + 1);
        StdDraw.setYscale(n + 1, -4);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));

        header(a);
        sort(a);
        footer(a);
        show(a);
    }

}
