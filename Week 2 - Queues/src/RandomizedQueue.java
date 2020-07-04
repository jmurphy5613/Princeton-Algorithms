import java.util.Iterator;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] Q;
    Random rand = new Random();

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        Q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = Q[i];
        }
        Q = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (size == Q.length) resize(Q.length * 2);
        Q[size++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        int index = rand.nextInt(size);
        Item itemtoreturn = Q[index];
        Q[index] = Q[size - 1];
        Q[size - 1] = null;
        size--;
        if (size > 0 && size == Q.length / 4) resize(Q.length / 2);
        return itemtoreturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        Item item = Q[rand.nextInt(size)];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIteriator();
    }

    private class ArrayIteriator implements Iterator<Item> {

        private int sizeCopy;
        private Item[] itemCopy;


        public ArrayIteriator() {
            int sizeCopy = size;
            Item[] itemCopy = (Item[]) new Object[sizeCopy];

            for (int i = 0; i < sizeCopy; i++) {
                itemCopy[i] = Q[i];
            }
        }

        @Override
        public boolean hasNext() {
            return sizeCopy > 0;
        }

        @Override
        public Item next() {
            int index = rand.nextInt(sizeCopy);
            Item item = itemCopy[index];
            itemCopy[index] = itemCopy[sizeCopy - 1];
            itemCopy[sizeCopy - 1] = null;
            sizeCopy--;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
    }

}
