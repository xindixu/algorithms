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

    private Node<Item> first;
    private Node<Item> last;
    private int count;

    private static class Node<Item> {
        Item item;
        Node<Item> prev;
        Node<Item> next;

        public Node(Item item, Node<Item> prev, Node<Item> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> oldFirst = first;
        first = new Node<>(item, null, oldFirst);
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        count++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> oldLast = last;
        last = new Node<>(item, oldLast, null);
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        count++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        Node<Item> nextOfFirst = first.next;
        // avoid loitering
        first.next = null;
        if (nextOfFirst != null) {
            nextOfFirst.prev = null;
        }
        first = nextOfFirst;
        count--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        Node<Item> prevOfLast = last.prev;
        // avoid loitering
        last.prev = null;
        if (prevOfLast != null) {
            prevOfLast.next = null;
        }
        last = prevOfLast;
        count--;
        if (isEmpty()) {
            first = null;
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
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
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
                    deque.addFirst(item);
                } else {
                    deque.addLast(item);
                }
                count++;
            } else if (!deque.isEmpty()) {
                if (count % 2 == 0) {
                    StdOut.print(deque.removeFirst() + " ");
                } else {
                    StdOut.print(deque.removeLast() + " ");
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
