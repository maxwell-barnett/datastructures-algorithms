import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Maxwell Barnett
 * @version 1.0
 * @userid mbarnett42
 * @GTID 903683864
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("List or at least one item in the list given is null.");
        }

        backingArray = (T[]) new Comparable[2 * (data.size()) + 1];
        size = data.size();
        for (int i = 1; i <= data.size(); i++) {
            backingArray[i] = data.get(i - 1);
        }

        int start = size / 2;

        for (int i = start; i >= 1; i--) {
            heapify(backingArray, i);
        }

    }

    /**
     * Recursive heapify helper method to build heap.
     * 
     * @param arr  array to modify
     * @param root root of subtree
     */
    private void heapify(T[] arr, int root) {
        int minIndex = root;
        int l = 2 * minIndex;
        int r = (2 * minIndex) + 1;

        if (l <= size && arr[l].compareTo(arr[minIndex]) < 0) {
            minIndex = l;
        }
        if (r <= size && arr[r].compareTo(arr[minIndex]) < 0) {
            minIndex = r;
        }

        if (minIndex != root) {
            T temp = arr[root];
            arr[root] = arr[minIndex];
            arr[minIndex] = temp;
            heapify(arr, minIndex);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data given is null.");
        }
        if (size + 1 > backingArray.length) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < size; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        backingArray[size + 1] = data;
        size++;
        if (size == 0) {
            backingArray[1] = data;
        } else {
            int i = size;
            while (i > 1 && backingArray[i].compareTo(backingArray[i / 2]) < 0) {
                T temp = backingArray[i / 2];
                backingArray[i / 2] = backingArray[i];
                backingArray[i] = temp;
                i /= 2;
            }
        }

    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty.");
        }
        T holder = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = holder;
        backingArray[size] = null;
        int i = 1;
        T minChild;
        while (backingArray[i * 2] != null && backingArray[i * 2].compareTo(backingArray[i]) < 0) {
            if (backingArray[(i * 2) + 1] != null && backingArray[(i * 2) + 1].compareTo(backingArray[i * 2]) < 0) {
                minChild = backingArray[(i * 2) + 1];
                backingArray[(i * 2) + 1] = backingArray[i];
                backingArray[i] = minChild;
                i = (i * 2) + 1;
            } else {
                minChild = backingArray[i * 2];
                backingArray[i * 2] = backingArray[i];
                backingArray[i] = minChild;
                i *= 2;
            }

        }
        size--;
        return holder;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
