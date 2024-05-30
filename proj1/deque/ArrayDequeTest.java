package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized adeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());

    }

    @Test
    /* check get and getRecursive method. */
    public void getTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();

        assertEquals(null, ad1.get(0));
        ad1.addFirst(42);
        ad1.addLast(0);
        assertEquals(null, ad1.get(-1));
        assertEquals(42, (double) ad1.get(0), 0.0);
        assertEquals(0, (double) ad1.get(1), 0.0);
        assertEquals(null, ad1.get(2));
    }

    @Test
    /* check equals method.
     * Excerpt from Shakespeare's Sonnet 18:
     * Shall I compare thee to a summer's day?
     * Thou art more lovely and more temperate.
     * ...
     * */
    public void equalsTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<String>();
        ad1.addLast("Shall");
        ad1.addLast("I");
        ad1.addLast("compare");
        ad1.addLast("thee");
        ad1.addLast("to");
        ad1.addLast("a");
        ad1.addLast("summer's");
        ad1.addLast("day");

        int i = 0;
        assertFalse("Different type(int and ArrayDeque) should return false.", ad1.equals(i));

        assertFalse("Null should return false.", ad1.equals(null));

        ArrayDeque<String> ad2 = ad1;
        assertTrue("Same reference should return true.", ad1.equals(ad2));

        ArrayDeque<String> ad3 = new ArrayDeque<String>();
        ad3.addLast("Shall");
        ad3.addLast("I");
        ad3.addLast("compare");
        ad3.addLast("thee");
        ad3.addLast("to");
        ad3.addLast("a");
        ad3.addLast("summer's");
        ad3.addLast("day");
        assertTrue("A Deque contains the same contents should return true.", ad1.equals(ad3));

        ArrayDeque<String> ad4 = new ArrayDeque<String>();
        ad4.addLast("Thou");
        ad4.addLast("art");
        ad4.addLast("more");
        ad4.addLast("lovely");
        ad4.addLast("and");
        ad4.addLast("more");
        ad4.addLast("temperate");
        assertFalse("A Deque contains different contents should return false.", ad1.equals(ad4));
    }
}

