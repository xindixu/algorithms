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

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
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
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException();
        Key x = floor(key, root, null);
        if (x == null) throw new NoSuchElementException();
        return x;
    }

    private Key floor(Key key, Node cur, Key best) {
        if (cur == null) return best;
        int cmp = key.compareTo(cur.key);
        // key < cur.key -> cur key is too big, go to left to find something smaller and keep cur best
        if (cmp < 0) return floor(key, cur.left, best);
        // key > cur.key -> cur key is small enough, go to right for potentially bigger keys and update cur best
        if (cmp > 0) return floor(key, cur.right, cur.key);
        return cur.key;
    }


    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException();
        Key x = ceiling(key, root, null);
        if (x == null) throw new NoSuchElementException();
        return x;
    }

    private Key ceiling(Key key, Node cur, Key best) {
        if (cur == null) return best;
        int cmp = key.compareTo(cur.key);
        // key < cur.key -> cur key is big enough, go to left for potentially smaller keys and update cur best
        if (cmp < 0) return ceiling(key, cur.left, cur.key);
        // key > cur.key -> cur key is too small, go to right to find something bigger and keep cur best
        if (cmp > 0) return ceiling(key, cur.right, best);
        return cur.key;
    }

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *                                  <em>n</em>–1
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) throw new IllegalArgumentException();
        return select(root, rank);
    }

    private Key select(Node cur, int rank) {
        if (cur == null) return null;
        // rank of cur item is the size of left subtree
        int rankOfCur = size(cur.left);
        // rankOfCur is too small, go to right subtree and rank for that should exclude current rank and the current
        // node
        if (rankOfCur < rank) return select(cur.right, rank - rankOfCur - 1);
        // rankOfCur is too big, go to left subtree and keep the rank
        if (rankOfCur > rank) return select(cur.left, rank);
        return cur.key;
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
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException();
        return rank(root, key);
    }

    private int rank(Node cur, Key key) {
        if (cur == null) return 0;
        int cmp = key.compareTo(cur.key);

        // key < cur.key -> go to left subtree to find that node and its rank
        if (cmp < 0) return rank(cur.left, key);
        // key > cur.key -> go to right subtree to find that node and its rank
        // also need to add the cur node (1) and the size of left subtree
        if (cmp > 0) return size(cur.left) + rank(cur.right, key) + 1;
        // key == cur.key -> rank = size of left subtree
        return size(cur.left);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public int size(Key lo, Key hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        int cmp = lo.compareTo(hi);
        if (cmp > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        return rank(hi) - rank(lo);
    }

    /***************************************************************************
     *  Deletions
     ***************************************************************************/

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node deleteMin(Node cur) {
        if (cur.left == null) return cur.right;
        cur.left = deleteMin(cur.left);
        cur.size = size(cur.left) + size(cur.right) + 1;
        return cur;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMax(root);
    }

    private Node deleteMax(Node cur) {
        if (cur.right == null) return cur.left;
        cur.right = deleteMax(cur.right);
        cur.size = size(cur.left) + size(cur.right) + 1;
        return cur;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException();
        root = delete(key, root);
    }

    private Node delete(Key key, Node cur) {
        if (cur == null) return null;

        int cmp = key.compareTo(cur.key);
        // cur key > key -> go to left subtree to find the matching key
        if (cmp < 0) cur.left = delete(key, cur.left);
            // cur key < key -> go to right subtree to find the matching key
        else if (cmp > 0) cur.right = delete(key, cur.right);
            // found the matching key
        else {
            // cases where cur has at most one child
            if (cur.right == null) return cur.left;
            if (cur.left == null) return cur.right;

            // save cur node reference
            Node temp = cur;
            // find the successor (go right and continue going left to find a node without left child)
            cur = min(temp.right);
            // remove successor from right subtree
            cur.right = deleteMin(temp.right);
            // link left subtree with successor
            cur.left = temp.left;
        }

        cur.size = size(cur.left) + size(cur.right) + 1;
        return cur;
    }


    /***************************************************************************
     *  Debugging
     ***************************************************************************/


    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node cur) {
        if (root == null) return -1;
        return 1 + Math.max(height(cur.left), height(cur.right));
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
