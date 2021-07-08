package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayDeque<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;
    private Item[] array;
    private int count;
    private int leftEnd;
    private int rightEnd;

    public ResizingArrayDeque() {
        array = (Item[]) new Object[INIT_CAPACITY];
        count = 0;
        leftEnd = 0;
        rightEnd = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < count; i++) {
            copy[i] = array[(leftEnd + i) % array.length];
        }
        array = copy;
        // update pointers
        leftEnd = array.length - 1;
        rightEnd = count;
    }

    private void updateLeft(boolean isExpanding) {
        if (isExpanding) {
            leftEnd--;
        } else {
            leftEnd++;
        }
        if (leftEnd < 0) {
            leftEnd = array.length - 1;
        }
        if (leftEnd > array.length - 1) {
            leftEnd = 0;
        }
    }

    private void updateRight(boolean isExpanding) {
        if (isExpanding) {
            rightEnd++;
        } else {
            rightEnd--;
        }
        if (rightEnd < 0) {
            rightEnd = array.length - 1;
        }
        if (rightEnd > array.length - 1) {
            rightEnd = 0;
        }
    }

    public void pushLeft(Item item) {
        // expand the array
        if (count == array.length) {
            resize(array.length * 2);
        }
        // add item
        array[leftEnd] = item;
        // update pointers
        if (isEmpty()) {
            updateRight(true);
        }
        updateLeft(true);
        count++;
    }

    public void pushRight(Item item) {
        // expand the array
        if (count == array.length) {
            resize(array.length * 2);
        }
        // add item
        array[rightEnd] = item;
        // update pointers
        if (isEmpty()) {
            updateLeft(true);
        }
        updateRight(true);
        count++;
    }

    public Item popLeft() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // update pointers
        count--;
        updateLeft(false);
        if (isEmpty()) {
            updateRight(false);
        }
        Item item = array[leftEnd];

        // shrink the array
        if (count == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    public Item popRight() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // update pointers
        count--;
        updateRight(false);
        if (isEmpty()) {
            updateLeft(false);
        }
        Item item = array[rightEnd];

        // shrink the array
        if (count == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
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
            Item item = array[(leftEnd + current + 1) % array.length];
            current++;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();
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
