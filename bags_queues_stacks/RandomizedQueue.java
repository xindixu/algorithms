package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int count;

    private static class Node<Item> {
        Item item;
        Node<Item> next;

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }

    public RandomizedQueue() {
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

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> oldLast = last;
        last = new Node<>(item, null);
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
        if (isEmpty()) {
            // avoid loitering
            last = null;
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(count);
        Node<Item> current = first;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        return current.item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new RandomLinkedListIterator();
    }

    private class RandomLinkedListIterator implements Iterator<Item> {

        private Item[] randomizedArray;
        private int current;

        public RandomLinkedListIterator() {
            generateRandomizedArray();
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
            return randomizedArray[current++];
        }

        private void generateRandomizedArray() {
            // create an array from the linked list
            randomizedArray = (Item[]) new Object[count];
            Node<Item> currentNode = first;
            for (int i = 0; i < count; i++) {
                randomizedArray[i] = currentNode.item;
                currentNode = currentNode.next;
            }

            // shuffle the array list -> randomize
            for (int i = 0; i < count; i++) {
                int j = StdRandom.uniform(count);
                Item temp = randomizedArray[i];
                randomizedArray[i] = randomizedArray[j];
                randomizedArray[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) randomizedQueue.enqueue(item);
            else if (!randomizedQueue.isEmpty()) {
                StdOut.println("Sample: " + randomizedQueue.sample());
                StdOut.println("Deque: " + randomizedQueue.dequeue() + " ");
            }
        }
        StdOut.println("(" + randomizedQueue.size() + " left on randomizedQueue)");

        // print what's left on the randomizedQueue
        StdOut.println("Left on randomizedQueue: ");
        for (String q : randomizedQueue) {
            StdOut.print(q + " ");
        }
        StdOut.println();
    }
}
