package sorting;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MinPriorityQueue<Key> implements Iterable<Key> {
    private Key[] pq;
    private int count;
    private Comparator<Key> comparator;

    public MinPriorityQueue(int capacity) {
        this.pq = (Key[]) new Object[capacity + 1];
        this.count = 0;
    }

    public MinPriorityQueue() {
        this(1);
    }

    public MinPriorityQueue(int capacity, Comparator comparator) {
        this.pq = (Key[]) new Object[capacity + 1];
        this.count = 0;
        this.comparator = comparator;
    }

    public MinPriorityQueue(Comparator comparator) {
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
    public void insert(Key x) {
        if (count + 1 == pq.length) {
            resize(pq.length * 2);
        }
        pq[++count] = x;
        swim(count);
        assert isMinHeap();
    }

    /**
     * Remove the min key from the priority queue.
     * Exchange the root with the last node and sink root down to restore heap invariant
     *
     * @return the deleted min item in the priority queue
     */
    public Key removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Key minItem = pq[1];
        exch(1, count);
        count--;
        sink(1);

        if (count + 1 > 0 && count + 1 == pq.length / 4) {
            resize(pq.length / 2);
        }
        assert isMinHeap();
        return minItem;
    }

    /***************************************************************************
     * Helper functions to restore heap invariant
     ***************************************************************************/

    /**
     * When a child's key becomes less than parent's key
     *
     * @param k index of the child
     */
    private void swim(int k) {
        while (k > 1) {
            if (!more(parent(k), k)) {
                break;
            }
            exch(parent(k), k);
            k = parent(k);
        }
    }

    /**
     * When a parent's key becomes more than one or both of its children's keys
     *
     * @param k index of the parent
     */
    private void sink(int k) {
        while (leftChild(k) <= count) {
            int smallerChild = leftChild(k);
            if (rightChild(k) <= count && more(smallerChild, rightChild(k))) {
                smallerChild = rightChild(k);
            }
            if (!more(k, smallerChild)) {
                break;
            }
            exch(k, smallerChild);
            k = smallerChild;
        }
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
     * Helper functions for compares and swaps.
     ***************************************************************************/
    private boolean more(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        } else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..n] a min heap?
    private boolean isMinHeap() {
        for (int i = 1; i <= count; i++) {
            if (pq[i] == null) return false;
        }
        for (int i = count + 1; i < pq.length; i++) {
            if (pq[i] != null) return false;
        }
        if (pq[0] != null) return false;
        return isMinHeapOrdered(1);
    }

    // is subtree of pq[1..n] rooted at k a max heap?
    private boolean isMinHeapOrdered(int k) {
        if (k > count) return true;
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= count && more(k, left)) return false;
        if (right <= count && more(k, right)) return false;
        return isMinHeapOrdered(left) && isMinHeapOrdered(right);
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
        private final MinPriorityQueue<Key> copy;

        public HeapIterator() {
            copy = comparator == null ? new MinPriorityQueue<>(size()) : new MinPriorityQueue<>(size(), comparator);
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
            return copy.removeMin();
        }
    }

    /**
     * Unit tests the {@code MaxPQ} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        MinPriorityQueue<String> pq = new MinPriorityQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.removeMin() + " ");
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
