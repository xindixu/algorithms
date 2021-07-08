package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Compilation:  javac Queue.java
 * Execution:    java Queue < input.txt
 * Dependencies: StdIn.java StdOut.java
 * Data files:   https://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 * A generic queue, implemented using a linked list.
 */
public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int count;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Queue() {
        first = null;
        last = null;
        count = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        count++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        count--;
        // avoid loitering
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {
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
        Queue<String> queue = new Queue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");

        // print what's left on the queue
        StdOut.println("Left on queue: ");
        for (String q : queue) {
            StdOut.print(q + " ");
        }
        StdOut.println();
    }
}
