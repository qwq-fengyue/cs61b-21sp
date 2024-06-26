package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    /* check get and getRecursive method. */
    public void getTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        assertEquals(null, lld1.get(0));
        assertEquals(null, lld1.getRecursive(0));

        lld1.addFirst(42);
        lld1.addLast(0);
        assertEquals(null, lld1.get(-1));
        assertEquals(42, (double) lld1.get(0), 0.0);
        assertEquals(0, (double) lld1.get(1), 0.0);
        assertEquals(null, lld1.get(2));

        assertEquals(null, lld1.getRecursive(-1));
        assertEquals(42, (double) lld1.getRecursive(0), 0.0);
        assertEquals(0, (double) lld1.getRecursive(1), 0.0);
        assertEquals(null, lld1.getRecursive(2));
    }

    @Test
    /* check equals method.
    * Excerpt from Shakespeare's Sonnet 18:
    * Shall I compare thee to a summer's day?
    * Thou art more lovely and more temperate.
    * ...
    * */
    public  void equalsTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        lld1.addLast("Shall");
        lld1.addLast("I");
        lld1.addLast("compare");
        lld1.addLast("thee");
        lld1.addLast("to");
        lld1.addLast("a");
        lld1.addLast("summer's");
        lld1.addLast("day");

        int i = 0;
        assertFalse("Different type(int and LinkedListDeque) should return false.", lld1.equals(i));

        assertFalse("Null should return false.", lld1.equals(null));

        LinkedListDeque<String> lld2 = lld1;
        assertTrue("Same pointer should return true.", lld1.equals(lld2));

        LinkedListDeque<String> lld3 = new LinkedListDeque<String>();
        lld3.addLast("Shall");
        lld3.addLast("I");
        lld3.addLast("compare");
        lld3.addLast("thee");
        lld3.addLast("to");
        lld3.addLast("a");
        lld3.addLast("summer's");
        lld3.addLast("day");
        assertTrue("A Deque contains the same contents should return true.", lld1.equals(lld3));

        LinkedListDeque<String> lld4 = new LinkedListDeque<String>();
        lld4.addLast("Thou");
        lld4.addLast("art");
        lld4.addLast("more");
        lld4.addLast("lovely");
        lld4.addLast("and");
        lld4.addLast("more");
        lld4.addLast("temperate");
        assertFalse("A Deque contains different contents should return false.", lld1.equals(lld4));
    }
}
