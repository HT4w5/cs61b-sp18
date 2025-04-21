import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {

        // #1 Add / Remove test.
        StudentArrayDeque<Integer> sad0 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads0 = new ArrayDequeSolution<>();
        // for (int j = 0; j < 10; ++j) {

        // Random add.
        String opRecord = "";
        for (int i = 0; i < 10; ++i) {
            double factor = StdRandom.uniform();
            if (factor > 0.5) {
                sad0.addFirst(i);
                ads0.addFirst(i);
                opRecord += "addFirst(" + i + ")\n";
            } else {
                sad0.addLast(i);
                ads0.addLast(i);
                opRecord += "addLast(" + i + ")\n";
            }
        }

        // Random removal.
        Integer expected, actual;
        for (int i = 0; i < 10; ++i) {
            double factor = StdRandom.uniform();
            if (factor > 0.5) {
                actual = sad0.removeFirst();
                expected = ads0.removeFirst();
                assertNotNull(opRecord, actual);
                opRecord += "removeFirst(): " + actual + "\n";
                /*
                 * String errMsg = "Error occured in add / remove test:" +
                 * " removeFirst() returned Integer with value "
                 * + actual + "while actual value is " + expected + "\n";
                 */
                assertEquals(opRecord, expected, actual);
            } else {
                actual = sad0.removeLast();
                expected = ads0.removeLast();
                assertNotNull(opRecord, actual);
                opRecord += "removeLast(): " + actual + "\n";
                /*
                 * String errMsg = "Error occured in add / remove test:" +
                 * " removeLast() returned Integer with value "
                 * + actual + " while actual value is " + expected + "\n";
                 */
                assertEquals(opRecord, expected, actual);
            }
        }
        // }
    }
}
