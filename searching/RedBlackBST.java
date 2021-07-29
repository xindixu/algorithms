package searching;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private boolean color; // color of the link to the parent
        private int size;

        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackBST() {
    }

    /***************************************************************************
     *  Node helper methods.
     ***************************************************************************/

    private boolean isRed(Node cur) {
        if (cur == null) return false;
        return cur.color == RED;
    }

    private int size(Node cur) {
        if (cur == null) return 0;
        return cur.size;
    }

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /***************************************************************************
     *  Standard BST search.
     ***************************************************************************/

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

    /***************************************************************************
     *  Red-black tree insertion.
     ***************************************************************************/

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
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node cur, Key key, Value value) {
        if (cur == null) return new Node(key, value, RED, 1);

        int cmp = key.compareTo(cur.key);
        if (cmp < 0) cur.left = put(cur.left, key, value);
        else if (cmp > 0) cur.right = put(cur.right, key, value);
        else cur.value = value;

        // fix invalid colors
        if (isRed(cur.right) && !isRed(cur.left)) cur = rotateLeft(cur);
        if (isRed(cur.left) && isRed(cur.left.left)) cur = rotateRight(cur);
        if (isRed(cur.left) && isRed(cur.right)) flipColors(cur);

        cur.size = size(cur.left) + size(cur.right) + 1;
        return cur;
    }

    /***************************************************************************
     *  Red-black tree helper functions.
     ***************************************************************************/

    // orient a left-leaning red link to (temporarily) lean right
    // the larger node here is also the parent node
    private Node rotateRight(Node larger) {
        assert larger != null && isRed(larger.left);

        // at first, larger is the parent of smaller
        Node smaller = larger.left;

        // larger takes over smaller's right subtree
        larger.left = smaller.right;
        // larger becomes the right subtree of smaller
        smaller.right = larger;

        // smaller is now linked to parent, so copy over the color
        smaller.color = larger.color;
        // since we are rotating right, larger node color should be red
        larger.color = RED;

        // smaller is now linked to parent, so copy over the size
        smaller.size = larger.size;
        // need to recompute size for large, since large takes over smaller's right subtree
        larger.size = size(larger.left) + size(larger.right) + 1;

        // let smaller be the parent
        return smaller;
    }

    // orient a (temporarily) right-leaning red link to lean left
    // the smaller node here is also the parent node
    private Node rotateLeft(Node smaller) {
        assert smaller != null && isRed(smaller.right);

        // at first, smaller is the parent of larger
        Node larger = smaller.right;

        // smaller takes over larger's left subtree
        smaller.right = larger.left;
        // smaller becomes the left subtree of larger
        larger.left = smaller;

        // larger is now linked to the parent, so copy over the color
        larger.color = smaller.color;
        // since we are rotating left, smaller color should be rd
        smaller.color = RED;

        // larger is now linked to the parent, so copy over the size
        larger.size = smaller.size;
        // need to recompute for smaller, since smaller takes over larger's left subtree
        smaller.size = size(smaller.left) + size(smaller.right) + 1;

        // let larger be the parent
        return larger;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node parent) {
        assert parent != null && isRed(parent.left) && isRed(parent.right);

        parent.left.color = !parent.left.color;
        parent.right.color = !parent.right.color;
        parent.color = !parent.color;
    }

    /**
     * Unit tests the {@code RedBlackBST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        RedBlackBST<String, Integer> st = new RedBlackBST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        StdOut.println();
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
        StdOut.println();
    }
}
