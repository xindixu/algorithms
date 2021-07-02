package stack;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FixedCapacityStackOfStrings {

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

    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);
        FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(max);
        StdOut.println(max);

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (stack.isEmpty()) StdOut.println("BAD INPUT");
            else StdOut.print(stack.pop() + " ");
        }
        StdOut.println();

        // print what's left on the stack
//        StdOut.print("Left on stack: ");
//        for (String s : stack) {
//            StdOut.print(s + " ");
//        }
//        StdOut.println();
    }
}
