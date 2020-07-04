import java.util.Iterator;

//Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
//Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
//Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
//Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }


    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument can not be null");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;

        if (isEmpty()) last = first;
        else oldfirst.prev = first;

        size++;

    }


    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument can not be null");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;

        if (isEmpty()) first = last;
        else oldlast.next = last;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("The que is empty");
        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty()) last = null;
        else first.prev = null;

        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("The que is empty");
        Item item = last.item;
        last = last.prev;
        size--;

        if (isEmpty()) first = null;
        else last.next = null;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("You can not use remove");
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
    }
}
