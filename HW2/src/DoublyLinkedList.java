import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Maxwell Barnett
 * @version 1.0
 * @userid mbarnett42
 * @GTID 903683864
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new IllegalArgumentException();
        }

        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data);

        if (head == null) {
            head = temp;
            tail = temp;
            size++;
            return;
        } else if (index == 0) {
            temp.setNext(head);
            head.setPrevious(temp);
            head = temp;
            size++;
            return;
        } else if (index == size) {
            temp.setPrevious(tail);
            tail.setNext(temp);
            tail = temp;
            size++;
            return;
        }

        if (index < size / 2) {
            DoublyLinkedListNode<T> current = head;
            int i = 0;
            while (i < index) {
                current = current.getNext();
                i++;
            }

            temp.setPrevious(current);
            temp.setNext(current.getNext());
            current.getNext().setPrevious(temp);
            current.setNext(temp);
            size++;
        } else {
            DoublyLinkedListNode<T> current = tail;
            int i = size - 1;
            while (i > index) {
                current = current.getPrevious();
                i--;
            }

            temp.setNext(current);
            temp.setPrevious(current.getPrevious());
            current.getPrevious().setNext(temp);
            current.setPrevious(temp);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            T temp = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp;
        }
        if (index == size - 1) {
            T temp = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp;
        }

        if (index < size / 2) {
            DoublyLinkedListNode<T> current = head;
            int i = 0;
            while (i < index - 1) {
                current = current.getNext();
                i++;
            }
            T temp = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            current.getNext().setPrevious(current);
            size--;
            return temp;
        } else {
            DoublyLinkedListNode<T> current = tail;
            int i = size - 1;
            while (i > index + 1) {
                current = current.getPrevious();
                i--;
            }
            T temp = current.getPrevious().getData();
            current.setPrevious(current.getPrevious().getPrevious());
            current.getPrevious().setNext(current);
            size--;
            return temp;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        }

        if (index < size / 2) {
            int i = 0;
            DoublyLinkedListNode<T> current = head;
            while (i < index) {
                current = current.getNext();
                i++;
            }
            return current.getData();
        } else {
            int i = size - 1;
            DoublyLinkedListNode<T> current = tail;
            while (i > index) {
                current = current.getPrevious();
                i--;
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return getHead() == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        DoublyLinkedListNode<T> current = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (current.getData().equals(data)) {
                return removeAtIndex(i);
            }
            current = current.getPrevious();
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        if (size == 0) {
            return array;
        }
        DoublyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
