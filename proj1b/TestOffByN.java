import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void testEqualChars() {
        CharacterComparator cc;

        for (int i = 0; i < 6; ++i) {
            cc = new OffByN(i);
            // Equals.
            for (char c = 0; c < 65535 - i; ++c) {
                assertTrue(cc.equalChars(c, (char) (c + i)));
                assertTrue(cc.equalChars((char) (c + i), c));
            }

            // Not equals.
            for (char c = 0; c < 65535 - i - 1; ++c) {
                assertFalse(cc.equalChars(c, (char) (c + i + 1)));
            }
        }
    }
}
