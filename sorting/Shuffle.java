package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Shuffle {
    private Shuffle() {
    }

    private static class Item implements Comparable<Item> {
        private double randomKey;
        private Comparable<Item> value;

        public Item(double randomKey, Comparable value) {
            this.randomKey = randomKey;
            this.value = value;
        }

        @Override
        public int compareTo(Item o) {
            double result = o.randomKey - this.randomKey;
            if (result < 0) {
                return -1;
            } else if (result == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * Shuffle the array using sort shuffle, using insertion sort
     *
     * @param array the array to be shuffled
     */
    private static void sortShuffle(Comparable[] array) {
        int n = array.length;
        Item[] items = new Item[n];
        // generate random numbers for each array entry
        for (int i = 0; i < n; i++) {
            items[i] = new Item(StdRandom.uniform(0.0, 1.0), array[i]);
        }

        Insertion.sort(items);

        // update the array based on the sorted random numbers (shuffle)
        for (int i = 0; i < n; i++) {
            array[i] = items[i].value;
        }
    }

    /**
     * Shuffle th array using knuth shuffle
     *
     * @param array the array to be shuffled
     */
    private static void knuthShuffle(Comparable[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int r = StdRandom.uniform(0, i + 1);
            exch(array, i, r);
        }
    }

    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
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
        Shuffle.knuthShuffle(a);
        Shuffle.show(a);
    }
}
