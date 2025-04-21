import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        // Equals.
        for (char c = 0; c < 65535; ++c) {
            assertTrue(offByOne.equalChars(c, (char) (c + 1)));
            assertTrue(offByOne.equalChars((char) (c + 1), c));
        }

        // Not equals.
        for (char c = 0; c < 65535; ++c) {
            assertFalse(offByOne.equalChars(c, c));
        }
    }
}
