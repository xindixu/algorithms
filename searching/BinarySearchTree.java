package searching;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public BinarySearchTree() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node cur) {
        if (cur == null) return 0;
        return cur.size;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        root = put(root, key, value);
    }

    private Node put(Node cur, Key key, Value value) {
        if (cur == null) return new Node(key, value, 1);
        int cmp = key.compareTo(cur.key);
        if (cmp < 0) cur.left = put(cur.left, key, value);
        else if (cmp > 0) cur.right = put(cur.right, key, value);
        else cur.value = value;
        cur.size = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
        return get(root, key);
    }

    private Value get(Node cur, Key key) {
        if (cur == null) return null;
        int cmp = key.compareTo(cur.key);
        if (cmp < 0) return get(cur.left, key);
        if (cmp > 0) return get(cur.right, key);
        return cur.value;
    }

    /***************************************************************************
     *  Ordered BST methods.
     ***************************************************************************/

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (root == null) throw new NoSuchElementException();
        return min(root).key;
    }

    private Node min(Node cur) {
        if (cur.left == null) return cur;
        return min(cur.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (root == null) throw new NoSuchElementException();
        return max(root).key;
    }

    private Node max(Node cur) {
        if (cur.right == null) return cur;
        return max(cur.right);
    }


    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys() {
        if (root == null) return new Queue<>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node cur, Queue<Key> queue, Key lo, Key hi) {
        if (cur == null) return;

        int cmpLo = lo.compareTo(cur.key);
        int cmpHi = hi.compareTo(cur.key);

        // current key > lo -> current key is in range
        // before enqueuing the current key, check if the left subtree has any keys in range
        if (cmpLo < 0) keys(cur.left, queue, lo, hi);
        // lo <= current key <= hi
        if (cmpLo <= 0 && cmpHi >= 0) queue.enqueue(cur.key);
        // current key < hi -> current key is in range
        // after enqueuing the current key, check if the right subtree has any keys in range
        if (cmpHi > 0) keys(cur.right, queue, lo, hi);
    }


    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    public Iterable<Key> levelOrder() {
        Queue<Key> results = new Queue<>();
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            Node cur = queue.dequeue();
            if (cur == null) continue;
            results.enqueue(cur.key);
            queue.enqueue(cur.left);
            queue.enqueue(cur.right);
        }
        return results;
    }

    /**
     * Unit tests the {@code BST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        BinarySearchTree<String, Integer> st = new BinarySearchTree<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        StdOut.println(st.size());

        for (String s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));

        StdOut.println();

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
