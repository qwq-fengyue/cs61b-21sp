package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        AListNoResizing aList = new AListNoResizing<>();
        for (int i = 0; i < 3; i++) {
            buggyAList.addLast(i);
            aList.addLast(i);
        }
        for (int i = 0; i < 3; i++) {
            assertEquals(buggyAList.removeLast(), aList.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        AListNoResizing<Integer> L = new AListNoResizing<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyAList.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                assertEquals(L.size(), buggyAList.size());
            } else if (operationNumber == 2 && L.size() > 0){
                assertEquals(L.getLast(), buggyAList.getLast());
            } else if (L.size() > 0) {
                assertEquals(L.removeLast(), buggyAList.removeLast());
            }
        }
    }
}
