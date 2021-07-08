package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Compilation:  javac Steque.java
 * Execution:    java Steque < input.txt
 * Dependencies: StdIn.java StdOut.java
 *
 * A stack-ended queue or steque is a data type that supports push, pop, and enqueue implemented list.
 * Each steque element is of type Item.
 *
 * This version uses a static nested class Node (to save 8 bytes per Node), whereas the version in the textbook uses a
 * non-static nested class (for simplicity).
 */
public class Steque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int count;

    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    public Steque() {
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

    public void push(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        }
        count++;
    }

    public void enqueue(Item item) {
        if (isEmpty()) {
            last = new Node<>();
            first = last;
        } else {
            last.next = new Node<>();
            last = last.next;
        }
        last.item = item;
        count++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        count--;
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
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Steque<String> steque = new Steque<>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                if (count % 2 == 0) {
                    steque.push(item);
                } else {
                    steque.enqueue(item);
                }
                count++;
            } else if (!steque.isEmpty()) StdOut.print(steque.pop() + " ");
        }
        StdOut.println("(" + steque.size() + " left on stack)");

        // print what's left on the stack
        StdOut.println("Left on steque: ");
        for (String s : steque) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }

}
