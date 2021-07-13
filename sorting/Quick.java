package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("rawtypes")
public class Quick {

    private Quick() {
    }


    private static void shuffle(Comparable[] array) {
        StdRandom.shuffle(array);
    }

    private static int partition(Comparable[] array, int lo, int hi) {
        Comparable pivot = array[lo];
        int i = lo;
        int j = hi + 1;

        while (true) {
            // find item in the left part to swap (anything >= pivot
            while (less(array[++i], pivot)) {
                if (i == hi) {
                    break;
                }
            }

            // find item in the right part to swap (anything <= pivot
            while (less(pivot, array[--j])) {
                if (j == lo) {
                    break;
                }
            }

            // pointer cross over
            if (i >= j) {
                break;
            }

            exch(array, i, j);
        }
        // put pivot in the final sorted position
        exch(array, lo, j);

        // now array[lo...j-1] <= array[j] <= array[j+1...hi]
        return j;
    }

    private static void sort(Comparable[] array, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int pivot = partition(array, lo, hi);
        sort(array, lo, pivot - 1);
        sort(array, pivot + 1, hi);
    }

    public static void sort(Comparable[] array) {
        int n = array.length;
        shuffle(array);
        sort(array, 0, n - 1);
        assert isSorted(array, 0, n - 1);
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

    /**
     * Reads in a sequence of strings from standard input; selection sorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Quick.sort(a);
        Quick.show(a);
    }
}
