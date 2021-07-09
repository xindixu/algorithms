package sorting;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;
import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class TraceSelection {
    private final static double X_POSITION_I = -2.50;
    private final static double X_POSITION_MIN = -1.25;

    // This class should not be instantiated.
    private TraceSelection() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param array the array to be sorted
     */
    public static void sort(Comparable[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(array[j], array[min])) {
                    min = j;
                }
            }
            assert isSorted(array, 0, i);
            draw(array, i, i, min);
            exch(array, i, min);
        }
        assert isSorted(array);
    }

    /**
     * Rearranges the array in ascending order, using a comparator.
     *
     * @param array      the array
     * @param comparator the comparator specifying the order
     */
    public static void sort(Comparable[] array, Comparator comparator) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(comparator, array[j], array[min])) {
                    min = j;
                }
            }
            assert isSorted(array, comparator, 0, i);
            exch(array, i, min);
        }
        assert isSorted(array, comparator);
    }

    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
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

    // is the array a[] sorted?
    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(comparator, a[i], a[i - 1])) return false;
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
        StdDraw.text(X_POSITION_MIN, -2, "min");
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(-3, -1.5, n - 0.5, -1.5);
        StdDraw.setPenColor(StdDraw.BLACK);

        // elements
        for (int i = 0; i < n; i++)
            StdDraw.text(i, -1, array[i]);
    }

    public static void draw(Comparable[] array, int row, int i, int min) {
        int n = array.length;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(X_POSITION_I, row, i + "");
        StdDraw.text(X_POSITION_MIN, row, min + "");
        // elements
        for (int k = 0; k < n; k++) {
            if (k == min) StdDraw.setPenColor(StdDraw.BOOK_RED);
            else if (k < i) StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
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
        StdDraw.setXscale(-3, n + 1);
        StdDraw.setYscale(n + 1, -3);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));

        header(a);
        sort(a);
        footer(a);
        show(a);
    }

}
