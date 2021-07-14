package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("rawtypes")
public class Quick3Way {

    // This class should not be instantiated.
    private Quick3Way() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param array the array to be sorted
     */
    public static void sort(Comparable[] array) {
        StdRandom.shuffle(array);
        sort(array, 0, array.length - 1);
        assert isSorted(array);
    }

    // quicksort the subarray a[lo .. hi] using 3-way partitioning
    private static void sort(Comparable[] array, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        Comparable pivot = array[lo];
        int lt = lo;
        int i = lo + 1;
        int gt = hi;

        while (i <= gt) {
            if (less(array[i], pivot)) {
                exch(array, i++, lt++);
            } else if (less(pivot, array[i])) {
                exch(array, i, gt--);
            } else {
                i++;
            }
        }
        // a[lo..lt-1] < pivot = a[lt..gt] < a[gt+1..hi].
        sort(array, lo, lt - 1);
        sort(array, gt + 1, hi);
        assert isSorted(array, lo, hi);
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
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }


    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; 3-way
     * quicksorts them; and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Quick3Way.sort(a);
        show(a);
    }

}