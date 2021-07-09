package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Shell {
    // This class should not be instantiated.
    private Shell() {
    }

    /**
     * Computes the max value of x for (3x+1) h increments
     *
     * @param n length of the array
     */
    private static int getMaxX(int n) {
        return (n - 1) / 3;
    }

    /**
     * h sort the array, implemented with insertion sort with a stride length h, using the natural order.
     *
     * @param array the array to be sorted
     * @param h     stride length
     * @param n     length of the array
     */
    private static void hSort(Comparable[] array, int h, int n) {
        for (int i = h; i < n; i += h) {
            // exchange array[i] with each larger entry on the left
            for (int j = i; j >= h; j -= h) {
                if (less(array[j], array[j - h])) {
                    exch(array, j, j - h);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * h sort the array, implemented with insertion sort with a stride length h, using a comparator.
     *
     * @param array      the array to be sorted
     * @param comparator the comparator specifying the order
     * @param h          stride length
     * @param n          length of the array
     */
    private static void hSort(Comparable[] array, Comparator comparator, int h, int n) {
        for (int i = h; i < n; i += h) {
            // exchange array[i] with each larger entry on the left
            for (int j = i; j >= h; j -= h) {
                if (less(comparator, array[j], array[j - h])) {
                    exch(array, j, j - h);
                } else {
                    break;
                }
            }
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
        for (int x = getMaxX(n); x >= 0; x--) {
            int h = 3 * x + 1;
            hSort(array, h, n);
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

        for (int x = getMaxX(n); x >= 0; x--) {
            int h = 3 * x + 1;
            hSort(array, comparator, h, n);
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
            StdOut.print(objectComparable + " ");
        }
    }

    /**
     * Reads in a sequence of strings from standard input; selection sorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Shell.sort(a);
        Shell.show(a);
    }
}

