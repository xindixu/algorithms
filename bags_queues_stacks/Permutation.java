package bags_queues_stacks;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

import static java.lang.Integer.parseInt;

public class Permutation {
    public static void main(String[] args) {
        int k = parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        // save input into the queue
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randomizedQueue.enqueue(item);
        }

        // print out k items, generated randomly
        // each item is printed out at least once
        Iterator<String> randomizedQueueIterator = randomizedQueue.iterator();
        int count = 0;
        while (randomizedQueueIterator.hasNext() && count < k) {
            StdOut.println(randomizedQueueIterator.next());
            count++;
        }
    }
}
