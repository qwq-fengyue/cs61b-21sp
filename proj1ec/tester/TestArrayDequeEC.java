package tester;

import static org.junit.Assert.*;
import org.junit.Test;
import student.StudentArrayDeque;
import edu.princeton.cs.algs4.StdRandom;

public class TestArrayDequeEC {
    @Test
    public void TestDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();
        int count = 1000;
        String sequence = "\n";
        for (int i = 0; i < count; i++) {
            int opt = StdRandom.uniform(0, 4);
            switch (opt) {
                case 0:
                    int num = StdRandom.uniform(0, 100);
                    stu.addFirst(num);
                    sol.addFirst(num);
                    sequence += "addFirst(" + num + ")\n";
                    break;
                case 1:
                    num = StdRandom.uniform(0, 100);
                    stu.addLast(num);
                    sol.addLast(num);
                    sequence += "addLast(" + num + ")\n";
                    break;
                case 2:
                    if (sol.isEmpty()) {
                        break;
                    }
                    sequence += "removeFirst()\n";
                    assertEquals(sequence, stu.removeFirst(), sol.removeFirst());
                    break;
                case 3:
                    if (sol.isEmpty()) {
                        break;
                    }
                    sequence += "removeLast()\n";
                    assertEquals(sequence, stu.removeLast(), sol.removeLast());
                    break;
                default:
                    break;
            }
        }
    }
}
