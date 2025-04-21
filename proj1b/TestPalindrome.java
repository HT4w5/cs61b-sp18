import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    private static String reverse(String str) {
        String temp = "";
        for (int i = 0; i < str.length(); ++i) {
            temp = str.charAt(i) + temp;
        }

        return temp;
    }

    @Test
    public void testReverse() {
        assertEquals("", reverse(""));
        assertEquals("e", reverse("e"));
        assertEquals("ab", reverse("ba"));
        assertEquals("lol", reverse("lol"));
        assertEquals("real piece of shit", reverse("tihs fo eceip laer"));
    }

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindromeBasic() {
        // Unicode.
        assertTrue(palindrome.isPalindrome("" + (char) 1145 + (char) 1145));

        // Basic tests.
        assertTrue(palindrome.isPalindrome("")); // Empty string.
        assertTrue(palindrome.isPalindrome("e")); // Length-1.
        assertTrue(palindrome.isPalindrome("ee")); // Length-2.
        assertFalse(palindrome.isPalindrome("ab"));
        assertTrue(palindrome.isPalindrome("oio")); // Length-3.
        assertFalse(palindrome.isPalindrome("oii"));
        assertFalse(palindrome.isPalindrome("Racecar")); // Case-sensitive.

        // Unicode characters.
        for (char c = 0; c < 129; ++c) {
            System.out.println("Test number " + (int) c + c + "e" + c);
            palindrome.isPalindrome(c + "e" + c);
            assertTrue(palindrome.isPalindrome(c + "" + c));
        }

        // Leading and trailing spaces.
        assertTrue(palindrome.isPalindrome(" lol "));
        assertFalse(palindrome.isPalindrome(" lo l"));

        // Stress test.
        String tempString = "";
        for (char c = 0; c < 1145; ++c) {
            tempString += c;
        }

        // Even length.
        assertTrue(palindrome.isPalindrome(tempString + reverse(tempString)));
        assertFalse(palindrome.isPalindrome(tempString + tempString));

        // Odd length.
        assertTrue(palindrome.isPalindrome(tempString + "e" + reverse(tempString)));
        assertFalse(palindrome.isPalindrome(tempString + "e" + tempString));

    }

    @Test
    public void testIsPalindromeCC() {
        CharacterComparator cc = new OffByOne();

        // OffByOne.

        // Basic tests.
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("e", cc));
        assertTrue(palindrome.isPalindrome("abab", cc));
        assertTrue(palindrome.isPalindrome("abb", cc));

        // Case sensitive.
        assertFalse(palindrome.isPalindrome("aBab", cc));
        assertFalse(palindrome.isPalindrome("abB", cc));

        // True.
        String oB1Pal = "";
        for (char c = 0; c < 65535; ++c) {
            oB1Pal = "";
            oB1Pal = c + oB1Pal + (char) (c + 1);
            assertTrue(palindrome.isPalindrome(oB1Pal, cc));
        }

        // False.
        for (char c = 0; c < 65535; ++c) {
            oB1Pal = "";
            oB1Pal = c + oB1Pal + (char) (c);
            assertFalse(palindrome.isPalindrome(oB1Pal, cc));
        }

        // OffByN.

        for (int i = 0; i < 5; ++i) {
            cc = new OffByN(i);

            // True.
            String oBNPal = "";
            for (char c = 0; c < 65535 - i; ++c) {
                oBNPal = "";
                oBNPal = c + oBNPal + (char) (c + i);
                assertTrue(palindrome.isPalindrome(oBNPal, cc));
            }

            // False.
            for (char c = 0; c < 65535 - i - 1; ++c) {
                oBNPal = "";
                oBNPal = c + oBNPal + (char) (c + i + 1);
                assertFalse(palindrome.isPalindrome(oBNPal, cc));
            }

            // Basic tests.
            assertTrue(palindrome.isPalindrome("", cc));
            assertTrue(palindrome.isPalindrome("e", cc));
            assertTrue(palindrome.isPalindrome("ab" + (char) ('b' + i) + (char) ('a' - i), cc));
            assertTrue(palindrome.isPalindrome("ab" + (char) ('a' + i), cc));

            // Case sensitive.
            assertFalse(palindrome.isPalindrome("a" + (char) ('A' + i), cc));
        }

    }
}
