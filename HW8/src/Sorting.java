import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either array or comparator is null.");
        }

        int length = arr.length;
        for (int i = 1; i < length - 1; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either array or comparator is null.");
        }
        int start = 0;
        int end = arr.length - 1;
        while (end > start) {
            int swapped = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                    swapped = i;
                }
            }
            end = swapped;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    T temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;
                    swapped = i;
                }
            }
            start = swapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either array or comparator is null.");
        }
        if (arr.length == 1) {
            return;
        }
        int length = arr.length;
        int mid = length / 2;
        T[] left = (T[]) new Object[mid];
        T[] right = (T[]) new Object[length - mid];
        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }
        for (int i = mid; i < length; i++) {
            right[i - mid] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int leftIdx = 0;
        int rightIdx = 0;
        int currIdx = 0;

        while (leftIdx < mid && rightIdx < length - mid) {
            if (comparator.compare(left[leftIdx], right[rightIdx]) <= 0) {
                arr[currIdx] = left[leftIdx];
                leftIdx++;
            } else {
                arr[currIdx] = right[rightIdx];
                rightIdx++;
            }
            currIdx++;
        }

        while (leftIdx < mid) {
            arr[currIdx] = left[leftIdx];
            currIdx++;
            leftIdx++;
        }

        while (rightIdx < length - mid) {
            arr[currIdx] = right[rightIdx];
            currIdx++;
            rightIdx++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
            Random rand) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either array or comparator is null.");
        }

        quickSortHelper(arr, 0, arr.length, comparator, rand);
    }

    /**
     * Recursive helper method for quicksort.
     * 
     * @param <T>        data type to sort
     * @param arr        array to be sorted
     * @param left       start index
     * @param right      end index
     * @param comparator comparator to compare the data
     * @param rand       random object used to select pivot
     */
    private static <T> void quickSortHelper(T[] arr, int left, int right, Comparator<T> comparator, Random rand) {
        if (right - left < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(right - left) + left;
        T pivot = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = pivot;
        int i = left + 1;
        int j = right - 1;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        T temp = arr[left];
        arr[left] = arr[j];
        arr[j] = temp;
        quickSortHelper(arr, left, j, comparator, rand);
        quickSortHelper(arr, j + 1, right, comparator, rand);

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Inputted array is null.");
        }
        int length = arr.length;
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }

        int maxNum = 0;
        for (int number : arr) {
            if (Math.abs(number) > maxNum) {
                maxNum = number;
            }
        }

        int iterations = 0;
        while (maxNum != 0) {
            maxNum = maxNum / 10;
            iterations++;
        }

        int powerOfTen = 1;
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < length; j++) {
                int digit = (arr[j] / powerOfTen) % 10;
                buckets[digit + 9].add(arr[j]);
            }
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[index] = bucket.removeFirst();
                    index++;
                }
            }
            powerOfTen = powerOfTen * 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     *         holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Inputted data list is null.");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = heap.remove();
        }
        return sorted;
    }
}
