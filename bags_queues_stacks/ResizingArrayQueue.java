package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Compilation:  javac ResizingArrayQueue.java
 * Execution:    java ResizingArrayQueue < input.txt
 * Dependencies: StdIn.java StdOut.java
 * Data files:   https://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 * Queue implementation with a resizing array.
 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;
    private Item[] array;
    private int first;
    private int last;
    private int count;

    public ResizingArrayQueue() {
        array = (Item[]) new Object[INIT_CAPACITY];
        count = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(array, first, copy, 0, count);
        array = copy;
        first = 0;
        last = count;
    }

    public void enqueue(Item item) {
        // expand the array
        if (first == array.length) {
            resize(array.length * 2);
        }
        array[last++] = item;
        count++;
        // wrap around
        if (last == array.length) {
            last = 0;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = array[first];
        // avoid loitering
        array[first] = null;
        count--;
        first++;

        // wrap around
        if (first == array.length) {
            first = 0;
        }

        // shrink the array
        if (count >= 0 && count == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<Item> {
        private int current;

        public ArrayIterator() {
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < count;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = array[(first + current) % array.length];
            current++;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<>();
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
