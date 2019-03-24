package ua.procamp;

/**
 * {@link LinkedQueue} implements FIFO {@link Queue}, using singly linked nodes. Nodes are stores in instances of nested
 * class Node. In order to perform operations {@link LinkedQueue#add(Object)} and {@link LinkedQueue#poll()}
 * in a constant time, it keeps to references to the head and tail of the queue.
 *
 * @param <T> a generic parameter
 */
public class LinkedQueue<T> implements Queue<T> {

    private Node<T> head;
    private Node<T> last;
    private int size;

    /**
     * Adds an element to the end of the queue.
     *
     * @param element the element to add
     */
    public void add(T element) {
        Node<T> nodeForInsert = new Node<>(element);
        if (head == null) {
            head = nodeForInsert;
            last = nodeForInsert;
        } else {
            last.next = nodeForInsert;
            last = nodeForInsert;
        }
        size++;
    }

    /**
     * Retrieves and removes queue head.
     *
     * @return an element that was retrieved from the head or null if queue is empty
     */
    public T poll() {
        if (head != null) {
            T returnValue = head.value;
            head = head.next;
            if (head == null) {
                last = null;
            }
            size--;
            return returnValue;
        } else {
            return null;
        }
    }

    /**
     * Returns a size of the queue.
     *
     * @return an integer value that is a size of queue
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return {@code true} if the queue is empty, returns {@code false} if it's not
     */
    public boolean isEmpty() {
        return head == null;
    }

    class Node<T> {

        private T value;

        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
