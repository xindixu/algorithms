package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Compilation:  javac Bag.java
 * Execution:    java Bag < input.txt
 * Dependencies: StdIn.java StdOut.java
 *
 * A generic bag or multiset, implemented using a singly linked list.
 */
public class Bag<Item> implements Iterable<Item> {

    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    private Node<Item> first;
    private int count;

    public Bag() {
        first = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        count++;
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
        Bag<Double> numbers = new Bag<>();
        while (!StdIn.isEmpty()) numbers.add(StdIn.readDouble());
        int N = numbers.size();
        StdOut.println(N);
        double sum = 0.0;
        for (double x : numbers) {
            sum += x;
        }
        double mean = sum / N;
        sum = 0.0;
        for (double x : numbers) sum += (x - mean) * (x - mean);
        double std = Math.sqrt(sum / (N - 1));
        StdOut.printf("Mean: %.2f\n", mean);
        StdOut.printf("Std dev: %.2f\n", std);
    }
}
