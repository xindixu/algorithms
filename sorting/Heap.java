package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Heap {


    private Heap() {
    }

    private static void heapify(Comparable[] array) {
        int n = array.length;
        for (int i = rootOfLastHeap(n); i >= 1; i--) {
            sink(array, i, n);
        }
    }

    private static void sortdown(Comparable[] array) {
        int end = array.length;
        while (end > 1) {
            exch(array, 1, end--);
            sink(array, 1, end);
        }
    }

    public static void sort(Comparable[] array) {
        heapify(array);
        sortdown(array);
    }

    /***************************************************************************
     * Helper functions to navigate through the heap
     ***************************************************************************/

    private static int leftChild(int i) {
        return 2 * i;
    }

    private static int rightChild(int i) {
        return 2 * i + 1;
    }

    private static int rootOfLastHeap(int n) {
        return n / 2;
    }

    /***************************************************************************
     * Helper functions to restore heap invariant
     ***************************************************************************/

    /**
     * When a parent's key is less than one or both of its children's keys
     *
     * @param a heap array
     * @param k parent node
     * @param n length of the heap array
     */
    private static void sink(Comparable[] a, int k, int n) {
        while (leftChild(k) <= n) {
            int largerChild = leftChild(k);
            if (rightChild(k) < n && less(a, largerChild, rightChild(k))) {
                largerChild = rightChild(k);
            }
            if (!less(a, k, largerChild)) {
                break;
            }
            exch(a, k, largerChild);
            k = largerChild;
        }
    }

    /***************************************************************************
     *  Helper sorting functions, with off-by-one support
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable[] a, int v, int w) {
        return a[v - 1].compareTo(a[w - 1]) < 0;
    }


    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = swap;
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
        Heap.sort(a);
        Heap.show(a);
    }
}
