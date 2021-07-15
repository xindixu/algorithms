package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("rawtypes")
public class MergeBottomUp {

    // This class should not be instantiated.
    private MergeBottomUp() {
    }

    /**
     * Stably merge sorted array[lo...mid] and array[mid+1...hi] into array[lo...hi], using aux[lo...hi]
     *
     * @param array the array to be sorted
     * @param aux   helper array to hold items while merging
     * @param lo    start of the first portion
     * @param mid   end of the first portion
     * @param hi    end of the second portion
     */
    private static void merge(Comparable[] array, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(array, lo, mid);
        assert isSorted(array, mid + 1, hi);
        // copy the sorted portions into the aux array
        if (hi - lo >= 0) System.arraycopy(array, lo, aux, lo, hi - lo + 1);

        int i = lo;
        int j = mid + 1;
        // merge the two sub arrays back to array[]
        for (int k = lo; k <= hi; k++) {
            if (j > hi) array[k] = aux[i++];
            else if (i > mid) array[k] = aux[j++];
            else if (less(aux[i], aux[j])) array[k] = aux[i++];
            else array[k] = aux[j++];
        }
        assert isSorted(array, lo, hi);
    }


    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param array the array to be sorted
     */
    public static void sort(Comparable[] array) {
        int n = array.length;
        Comparable[] aux = new Comparable[n];

        for (int size = 1; size < n; size = size * 2) {
            for (int lo = 0; lo < n - size; lo += size * 2) {
                merge(array, aux, lo, lo + size - 1, Math.min(lo + size * 2 - 1, n - 1));
            }
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
        MergeBottomUp.sort(a);
        MergeBottomUp.show(a);
    }
}
