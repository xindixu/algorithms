package searching;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.NoSuchElementException;

@SuppressWarnings({"rawtypes", "unchecked"})
public class BinarySearchSymbolTable<Key extends Comparable<Key>, Value> {

    private static final int INITIAL_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    private int size = 0;

    public BinarySearchSymbolTable() {
        this(INITIAL_CAPACITY);
    }

    public BinarySearchSymbolTable(int capacity) {
        this.keys = (Key[]) new Comparable[capacity];
        this.values = (Value[]) new Object[capacity];
    }

    private void resize(int capacity) {
        keys = Arrays.copyOf(keys, capacity);
        values = Arrays.copyOf(values, capacity);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return true if this symbol table contains key and
     * false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        int i = rank(key);
        if (i < size && key.compareTo(keys[i]) == 0) return values[i];

        return null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is null.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if key is null
     */
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException();

        if (value == null) {
            delete(key);
            return;
        }

        int i = rank(key);
        // key is already in table
        if (i < size && key.compareTo(keys[i]) == 0) {
            values[i] = value;
            return;
        }

        // insert the new key-value pair
        if (size == keys.length) {
            resize(size * 2);
        }

        // move all items after i backward
        for (int j = size; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[i] = key;
        values[i] = value;
        size++;
    }

    /**
     * Removes the specified key and associated value from this symbol table
     * (if the key is in the symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if key is null
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException();

        int i = rank(key);
        // key is not in table
        if (i >= size || key.compareTo(keys[i]) != 0) {
            return;
        }

        // move all items after i forward
        for (int j = i; j < size - 1; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }

        size--;
        // void loitering
        keys[size] = null;
        values[size] = null;

        if (size == keys.length / 4) {
            resize(size / 2);
        }
    }

    /**
     * Returns the number of keys in this symbol table strictly less than key.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than key
     * @throws IllegalArgumentException if key is null
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException();

        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = keys[mid].compareTo(key);
            if (cmp == 0) return mid;
            if (cmp < 0) lo = mid + 1;
            else hi = mid - 1;
        }
        return lo;
    }
    
    /***************************************************************************
     *  Ordered symbol table methods.
     ***************************************************************************/
    /**
     * Returns the smallest key in this symbol table.
     *
     * @return the smallest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException();
        return keys[0];
    }

    /**
     * Returns the largest key in this symbol table.
     *
     * @return the largest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException();
        return keys[size - 1];
    }

    /**
     * Return the kth smallest key in this symbol table.
     *
     * @param k the order statistic
     * @return the kth smallest key in this symbol table
     * @throws IllegalArgumentException unless k is between 0 and n–1
     */
    public Key select(int k) {
        if (k < 0 || k >= size) throw new IllegalArgumentException();
        return keys[k];
    }

    /**
     * Returns the largest key in this symbol table less than or equal to key.
     *
     * @param key the key
     * @return the largest key in this symbol table less than or equal to key
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException();
        int i = rank(key);
        if (i < size && key.compareTo(keys[i]) == 0) return keys[i];
        if (i == 0) throw new NoSuchElementException();
        return keys[i - 1];
    }

    /**
     * Returns the smallest key in this symbol table greater than or equal to key.
     *
     * @param key the key
     * @return the smallest key in this symbol table greater than or equal to key
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException();
        int i = rank(key);
        if (i < size && key.compareTo(keys[i]) == 0) return keys[i];
        if (i == size) throw new NoSuchElementException();
        return keys[i];
    }

    /**
     * Returns the number of keys in this symbol table in the specified range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in this symbol table between lo
     * (inclusive) and hi (inclusive)
     * @throws IllegalArgumentException if either lo or hi
     *                                  is null
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException();
        if (hi == null) throw new IllegalArgumentException();

        if (lo.compareTo(hi) > 0) return 0;
        return rank(hi) - rank(lo) + (contains(hi) ? 1 : 0);
    }

    /**
     * Returns all keys in this symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st,
     * use the foreach notation: for (Key key : st.keys()).
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * Returns all keys in this symbol table in the given range,
     * as an Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in this symbol table between lo
     * (inclusive) and hi (inclusive)
     * @throws IllegalArgumentException if either lo or hi
     *                                  is null
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException();
        if (hi == null) throw new IllegalArgumentException();

        Queue<Key> queue = new Queue<>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(hi)) {
            queue.enqueue(keys[rank(hi)]);
        }
        return queue;
    }

    /**
     * Unit tests the BinarySearchST data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        BinarySearchSymbolTable<String, Integer> st = new BinarySearchSymbolTable<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
