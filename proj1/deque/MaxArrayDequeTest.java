package deque;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {

    private static class LenComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.length() - str2.length();
        }
    }

    @Test
   public void MaxTest() {
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(new LenComparator());
        mad.addFirst("one");
        mad.addFirst("two");
        mad.addFirst("three");
        mad.addFirst("four");
        assertEquals("three", mad.max());
        assertEquals("two", mad.max(Comparator.naturalOrder()));
        assertEquals("four", mad.max(Comparator.reverseOrder()));
    }
}
