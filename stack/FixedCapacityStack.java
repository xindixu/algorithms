package stack;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class FixedCapacityStack<Item> implements Iterable<Item> {

    private final Item[] array;
    private int count;

    public FixedCapacityStack(int capacity) {
        // no generic array creation; had to use this ugly casting
        array = (Item[]) new Object[capacity];
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void push(Item item) {
        array[count++] = item;
    }

    public Item pop() {
        return array[--count];
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public class ReverseArrayIterator implements Iterator<Item> {
        private int current = count - 1;

        public boolean hasNext() {
            return current >= 0;
        }

        public Item next() {
            return array[current--];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);
        FixedCapacityStack<String> stack = new FixedCapacityStack<>(max);
        StdOut.println(max);

        // Hint: use command + D to send EOF in the command line
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (stack.isEmpty()) StdOut.println("BAD INPUT");
            else StdOut.print(stack.pop() + " ");
        }
        StdOut.println();

        // print what's left on the stack
        StdOut.println("Left on stack: ");
        for (String s : stack) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}
