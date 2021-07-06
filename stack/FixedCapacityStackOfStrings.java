package stack;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/******************************************************************************
 *  Compilation:  javac FixedCapacityStackOfStrings.java
 *  Execution:    java FixedCapacityStackOfStrings
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Stack of strings implementation with a fixed-size array.
 *
 *  % more tobe.txt
 *  to be or not to - be - - that - - - is
 *
 *  % java FixedCapacityStackOfStrings 5 < tobe.txt
 *  to be not that or be
 *
 *  Remark:  bare-bones implementation. Does not do repeated
 *  doubling or null out empty array entries to avoid loitering.
 *
 ******************************************************************************/
public class FixedCapacityStackOfStrings implements Iterable<String> {

    private final String[] array;
    private int count;

    public FixedCapacityStackOfStrings(int capacity) {
        array = new String[capacity];
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void push(String item) {
        array[count++] = item;
    }

    public String pop() {
        return array[--count];
    }

    @Override
    public Iterator<String> iterator() {
        return new ReverseArrayIterator();
    }

    public class ReverseArrayIterator implements Iterator<String> {
        private int current = count - 1;

        @Override
        public boolean hasNext() {
            return current >= 0;
        }

        @Override
        public String next() {
            return array[current--];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);
        FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(max);
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
