package stack;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/******************************************************************************
 *  Compilation:  javac FixedCapacityStack.java
 *  Execution:    java FixedCapacityStack
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Generic stack implementation with a fixed-size array.
 *
 *  % more tobe.txt
 *  to be or not to - be - - that - - - is
 *
 *  % java FixedCapacityStack 5 < tobe.txt
 *  to be not that or be
 *
 *  Remark:  bare-bones implementation. Does not do repeated
 *  doubling or null out empty array entries to avoid loitering.
 *
 ******************************************************************************/
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
        // assign first then increment
        array[count++] = item;
    }

    public Item pop() {
        // decrement first then access element
        return array[--count];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public class ReverseArrayIterator implements Iterator<Item> {
        private int current = count - 1;

        @Override
        public boolean hasNext() {
            return current >= 0;
        }

        @Override
        public Item next() {
            return array[current--];
        }

        @Override
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
