package stack;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class ResizingArrayStack<Item> {

    private static final int INIT_CAPACITY = 8;
    private Item[] array;
    private int count;

    public ResizingArrayStack() {
        array = (Item[]) new Object[INIT_CAPACITY];
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(array, 0, copy, 0, count);
        array = copy;
    }

    public void push(Item item) {
        // expand the array
        if (count == array.length) {
            resize(array.length * 2);
        }
        array[count++] = item;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        count -= 1;
        Item item = array[count];
        // avoid loitering
        array[count] = null;
        // shrink the array
        if (count > 0 && count == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    public static void main(String[] args) {

        ResizingArrayStack<String> stack = new ResizingArrayStack<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item);
            else if (!stack.isEmpty()) StdOut.print(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
