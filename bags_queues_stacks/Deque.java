package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Compilation:  javac Deque.java
 * Execution:    java Deque < input.txt
 * Dependencies: StdIn.java StdOut.java
 *
 * A double-ended queue or deque is like a stack or a queue but supports adding and removing items at both ends.
 * Implemented with doubly-linked list
 * Each deque element is of type Item.
 *
 * This version uses a static nested class Node (to save 8 bytes per Node), whereas the version in the textbook uses a
 * non-static nested class (for simplicity).
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> leftEnd;
    private Node<Item> rightEnd;
    private int count;

    private static class Node<Item> {
        Item item;
        Node<Item> left;
        Node<Item> right;

        public Node(Item item, Node<Item> left, Node<Item> right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    public Deque() {
        leftEnd = null;
        rightEnd = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void pushLeft(Item item) {
        Node<Item> oldLeftEnd = leftEnd;
        leftEnd = new Node<>(item, null, oldLeftEnd);
        if (isEmpty()) {
            rightEnd = leftEnd;
        } else {
            oldLeftEnd.left = leftEnd;
        }
        count++;
    }

    public void pushRight(Item item) {
        Node<Item> oldRightEnd = rightEnd;
        rightEnd = new Node<>(item, oldRightEnd, null);
        if (isEmpty()) {
            leftEnd = rightEnd;
        } else {
            oldRightEnd.right = rightEnd;
        }
        count++;
    }

    public Item popLeft() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = leftEnd.item;
        Node<Item> rightOfLeftEnd = leftEnd.right;
        // avoid loitering
        leftEnd.right = null;
        if (rightOfLeftEnd != null) {
            rightOfLeftEnd.left = null;
        }
        leftEnd = rightOfLeftEnd;
        count--;
        if (isEmpty()) {
            rightEnd = null;
        }
        return item;
    }

    public Item popRight() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = rightEnd.item;
        Node<Item> leftOfRightEnd = rightEnd.left;
        // avoid loitering
        rightEnd.left = null;
        if (leftOfRightEnd != null) {
            leftOfRightEnd.right = null;
        }
        rightEnd = leftOfRightEnd;
        count--;
        if (isEmpty()) {
            leftEnd = null;
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    public class LinkedListIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedListIterator() {
            current = leftEnd;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.right;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                if (count % 2 == 0) {
                    deque.pushLeft(item);
                } else {
                    deque.pushRight(item);
                }
                count++;
            } else if (!deque.isEmpty()) {
                if (count % 2 == 0) {
                    StdOut.print(deque.popLeft() + " ");
                } else {
                    StdOut.print(deque.popRight() + " ");
                }
                count++;
            }
        }
        StdOut.println("(" + deque.size() + " left on deque)");

        // print what's left on the deque
        StdOut.println("Left on deque: ");
        for (String s : deque) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}
