package searching;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SequentialSearchSymbolTable<Key, Value> {
    private int size;
    private Node first;

    private class Node {
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public SequentialSearchSymbolTable() {
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param key the key
     * @return true if this symbol table contains key;
     * false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key in this symbol table.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and null if the key is not in the symbol table
     * @throws IllegalArgumentException if key is null
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
        for (Node cur = first; cur != null; cur = cur.next) {
            if (key.equals(cur.key)) {
                return cur.value;
            }
        }
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

        for (Node cur = first; cur != null; cur = cur.next) {
            if (key.equals(cur.key)) {
                cur.value = value;
                return;
            }
        }

        first = new Node(key, value, first);
        size++;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if key is null
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException();
        first = delete(first, key);

    }

    private Node delete(Node node, Key key) {
        if (node == null) throw new IllegalArgumentException();
        if (key.equals(node.key)) {
            size--;
            return node.next;
        }
        node.next = delete(node.next, key);
        return node;
    }

    /**
     * Returns all keys in the symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st,
     * use the foreach notation: for (Key key : st.keys()).
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (Node cur = first; cur != null; cur = cur.next) {
            queue.enqueue(cur.key);
        }
        return queue;
    }

    /**
     * Unit tests the {@code SequentialSearchST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SequentialSearchSymbolTable<String, Integer> st = new SequentialSearchSymbolTable<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
