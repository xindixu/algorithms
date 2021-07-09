package sorting;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

@SuppressWarnings("rawtypes")
public class TraceShell {
    private final static double X_POSITION_H = -3.75;
    private final static double X_POSITION_I = -2.50;
    private final static double X_POSITION_J = -1.25;
    private static int posY = 0;

    // This class should not be instantiated.
    private TraceShell() {
    }

    /**
     * Computes the max value of h for (3x+1) h increments
     *
     * @param n length of the array
     */
    private static int getMaxH(int n) {
        int h = 1;
        while (h < n / 3) h = 3 * h + 1;
        return h;
    }

    /**
     * h sort the array, implemented with insertion sort with a stride length h, using the natural order.
     *
     * @param array the array to be sorted
     * @param h     stride length
     * @param n     length of the array
     */
    private static void hSort(Comparable[] array, int h, int n) {
        for (int i = h; i < n; i++) {
            int j;
            // exchange array[i] with each larger entry on the left
            for (j = i; j >= h; j -= h) {
                if (less(array[j], array[j - h])) {
                    exch(array, j, j - h);
                } else {
                    break;
                }
            }
            draw(array, posY, i, j, h);
            posY++;
        }
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param array the array to be sorted
     */
    public static void sort(Comparable[] array) {
        int n = array.length;

        // 3x + 1
        for (int h = getMaxH(n); h >= 1; h /= 3) {
            hSort(array, h, n);
            footer(array, posY);
            posY++;
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

        StdDraw.text(X_POSITION_H, -2, "h");
        StdDraw.text(X_POSITION_I, -2, "i");
        StdDraw.text(X_POSITION_J, -2, "j");
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(-4, -1.5, n - 0.5, -1.5);
        StdDraw.setPenColor(StdDraw.BLACK);

        // elements
        for (int i = 0; i < n; i++)
            StdDraw.text(i, -1, array[i]);
    }

    public static void draw(Comparable[] array, int row, int i, int j, int h) {
        int n = array.length;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(X_POSITION_H, row, h + "");
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

    private static void footer(Comparable[] array, int row) {
        int n = array.length;
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < n; i++)
            StdDraw.text(i, row, array[i] + "");
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

        // number of rows needed
        int rows = 0;
        int h = 1;
        while (h < n / 3) {
            rows += (n - h + 1);
            h = 3 * h + 1;
        }
        rows += (n - h + 1);

        // set canvas size
        StdDraw.setCanvasSize(30 * (n + 3), 30 * (rows + 3));
        StdDraw.setXscale(-5, n + 1);
        StdDraw.setYscale(rows + 1, -4);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));

        header(a);
        sort(a);
        show(a);
    }
}
