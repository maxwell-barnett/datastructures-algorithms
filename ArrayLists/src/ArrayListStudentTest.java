import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * This is a basic set of unit tests for ArrayList.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class ArrayListStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
        list.addAtIndex(0, "0a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("4a");  // 4a
        list.addToFront("3a");  // 3a, 4a
        list.addToFront("2a");  // 2a, 3a, 4a
        list.addToFront("1a");  // 1a, 2a, 3a, 4a
        list.addToFront("0a");  // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("0a");   // 0a
        list.addToBack("1a");   // 0a, 1a
        list.addToBack("2a");   // 0a, 1a, 2a
        list.addToBack("3a");   // 0a, 1a, 2a, 3a
        list.addToBack("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        String temp = "2a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, temp);   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(2));    // 0a, 1a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String temp = "0a"; // For equality checking.

        list.addAtIndex(0, temp);   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromFront());   // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String temp = "5a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, temp);   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromBack());    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertEquals("0a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("2a", list.get(2));
        assertEquals("3a", list.get(3));
        assertEquals("4a", list.get(4));
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        // Should be empty at initialization
        assertTrue(list.isEmpty());

        // Should not be empty after adding elements
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        // Clearing the list should empty the array and reset size
        list.clear();

        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testExtension() {
        list = new ArrayList<>();

        list.addToFront("0a"); // 0a
        list.addToFront("1a"); // 0a, 1a
        list.addToFront("2a"); // 0a, 1a, 2a
        list.addToFront("3a"); // 0a, 1a, 2a, 3a
        list.addToFront("4a"); // 0a, 1a, 2a, 3a, 4a
        list.addToFront("5a");
        list.addToFront("6a");
        list.addToFront("7a");
        list.addToFront("8a");
        list.addToFront("9a");
        list.addToFront("10a");

        Object[] expected = new Object[] {"10a", "9a", "8a", "7a", "6a", "5a", "4a", "3a", "2a",
                "1a", "0a", null, null, null, null, null, null, null};
        assertArrayEquals(expected, list.getBackingArray());
    }

    // the following tests check the `throws` conditions are right
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexLowerBound() {
        new ArrayList<>().addAtIndex(-1, 0);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexUpperBound() {
        new ArrayList<>().addAtIndex(1, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddAtIndexNullData() {
        new ArrayList<>().addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontNullData() {
        new ArrayList<>().addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackNullData() {
        new ArrayList<>().addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexLowerBound() {
        new ArrayList<>().removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexUpperBound() {
        new ArrayList<>().removeAtIndex(0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromFrontEmpty() {
        new ArrayList<>().removeFromFront();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromBackEmpty() {
        new ArrayList<>().removeFromBack();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetLowerBound() {
        new ArrayList<>().get(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetUpperBound() {
        new ArrayList<>().get(0);
    }

    // some exhaustive checks now
    @Test(timeout = TIMEOUT)
    public void testUnmodifiedInitialCapacity() {
        assertEquals(9, ArrayList.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testInitialCapacityIsNotCopied() {
        assertEquals(ArrayList.INITIAL_CAPACITY, new ArrayList<>().getBackingArray().length);
        var a1 = new ArrayList<>();
        a1.addToBack("a");
        a1.clear();
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);

        // originally I had some wild reflection stuff here, but it doesn't work on Java 11 :(
    }

    @Test(timeout = TIMEOUT)
    public void testBackingArraySizeIsReset() {
        var a1 = new ArrayList<>();
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);

        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack("a");
        }

        assertNotEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);
        a1.clear();
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void testAddThenRemove() {
        var a1 = new ArrayList<>();
        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack(i);
        }

        for (int i = ArrayList.INITIAL_CAPACITY; i >= 0; i--) {
            assertEquals(i, a1.removeFromBack());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexOnBoundary() {
        var a1 = new ArrayList<>();
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack(i);
        }

        // I expect that the backing array can be completely saturated
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.size());
        assertEquals(a1.getBackingArray()[ArrayList.INITIAL_CAPACITY - 1], ArrayList.INITIAL_CAPACITY - 1);
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);

        a1.removeFromBack();
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexOnBoundary() {
        var a1 = new ArrayList<>();
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY - 1; i++) {
            a1.addToBack(i);
        }

        assertEquals(ArrayList.INITIAL_CAPACITY - 1, a1.size());

        a1.addAtIndex(a1.size() - 1, 42);
        a1.addAtIndex(a1.size() - 1, 43);
    }

    @Test(timeout = TIMEOUT)
    public void testCapacityIncreaseTimes() {
        var a1 = new ArrayList<>();
        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack(i);
        }

        assertEquals(ArrayList.INITIAL_CAPACITY * 2, a1.getBackingArray().length);
    }
}