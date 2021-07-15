package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MaxPriorityQueue<Key> implements Iterable<Key> {

    private Key[] pq;
    private int count;
    private Comparator<Key> comparator;

    public MaxPriorityQueue(int capacity) {
        this.pq = (Key[]) new Object[capacity + 1];
        this.count = 0;
    }

    public MaxPriorityQueue() {
        this(1);
    }

    public MaxPriorityQueue(int capacity, Comparator comparator) {
        this.pq = (Key[]) new Object[capacity + 1];
        this.count = 0;
        this.comparator = comparator;
    }

    public MaxPriorityQueue(Comparator comparator) {
        this(1, comparator);
    }

    private void resize(int capacity) {
        pq = Arrays.copyOf(pq, capacity);
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Add a new key to the priority queue.
     * Add it to the end and swim it up to restore heap invariant
     *
     * @param x new key to add
     */
    private void insert(Key x) {
        if (count + 1 == pq.length) {
            resize(pq.length * 2);
        }

        pq[++count] = x;
        swim(count);
        assert isMaxHeap();
    }

    /**
     * Remove the max key from the priority queue.
     * Exchange the root with the last node and sink root down to restore heap invariant
     *
     * @return the deleted max item in the priority queue
     */
    private Key removeMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Key maxItem = pq[1];
        exch(1, count);
        pq[count] = null;
        count--;
        sink(1);

        if (count + 1 > 0 && count + 1 == pq.length / 4) {
            resize(pq.length / 2);
        }
        assert isMaxHeap();
        return maxItem;
    }


    /***************************************************************************
     * Helper functions to navigate through the heap
     ***************************************************************************/

    private int leftChild(int i) {
        return 2 * i;
    }

    private int rightChild(int i) {
        return 2 * i + 1;
    }

    private int parent(int i) {
        return i / 2;
    }

    /***************************************************************************
     * Helper functions to restore heap invariant
     ***************************************************************************/

    /**
     * When a child's key becomes larger than parent's key
     *
     * @param k index of the child
     */
    private void swim(int k) {
        // break when k doesn't have parents
        while (k > 1) {
            // break when parent's key is not less than k's key
            if (!less(parent(k), k)) {
                break;
            }
            exch(parent(k), k);
            k = parent(k);
        }
    }

    /**
     * When a parent's key becomes less than one or both of its children's keys
     *
     * @param k index of the parent
     */
    private void sink(int k) {
        // break when k doesn't have children
        while (leftChild(k) <= count) {
            // find the largerChild
            int largerChild = leftChild(k);
            if (rightChild(k) <= count && less(leftChild(k), rightChild(k))) {
                largerChild = rightChild(k);
            }
            // break when k's key is not less than larger child's key
            if (!less(k, largerChild)) {
                break;
            }
            exch(k, largerChild);
            k = largerChild;
        }
    }

    /***************************************************************************
     * Helper functions for compares and swaps.
     ***************************************************************************/
    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        } else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..n] a max heap?
    private boolean isMaxHeap() {
        for (int i = 1; i <= count; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = count + 1; i < pq.length; i++) {
            if (pq[i] != null) return false;
        }
        if (pq[0] != null) return false;
        return isMaxHeapOrdered(1);
    }

    // is subtree of pq[1..n] rooted at k a max heap?
    private boolean isMaxHeapOrdered(int k) {
        if (k > count) return true;
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= count && less(k, left)) return false;
        if (right <= count && less(k, right)) return false;
        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
    }


    /***************************************************************************
     * Iterator.
     ***************************************************************************/

    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * in descending order.
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the keys in descending order
     */
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        private MaxPriorityQueue<Key> copy;

        public HeapIterator() {
            copy = comparator == null ? new MaxPriorityQueue<>(size()) : new MaxPriorityQueue<>(size(), comparator);
            for (int i = 1; i <= count; i++) {
                copy.insert(pq[i++]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.removeMax();
        }
    }

    /**
     * Unit tests the {@code MaxPQ} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        MaxPriorityQueue<String> pq = new MaxPriorityQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.removeMax() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");

        // print what's left on the deque
        StdOut.println("Left on pq: ");
        for (String s : pq) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}
