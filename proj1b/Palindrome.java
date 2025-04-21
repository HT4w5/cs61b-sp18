public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); ++i) {
            result.addLast(word.charAt(i));
        }

        return result;
    }

    public boolean isPalindrome(String word) {
        // Create deque of word.
        Deque<Character> wordDeque = wordToDeque(word);
        // Call helper.
        return isPalindromeRec(wordDeque);
    }

    private boolean isPalindromeRec(Deque<Character> word) {
        // Base case: length < 2.
        if (word.size() < 2) {
            return true;
        } else {
            if (word.removeFirst().charValue() == word.removeLast().charValue()) {
                return isPalindromeRec(word);
            } else {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        // Create deque of word.
        Deque<Character> wordDeque = wordToDeque(word);
        // Call helper.
        return isPalindromeRec(wordDeque, cc);
    }

    private boolean isPalindromeRec(Deque<Character> word, CharacterComparator cc) {
        // Base case: length < 2.
        if (word.size() < 2) {
            return true;
        } else {
            if (cc.equalChars(word.removeFirst(), word.removeLast())) {
                return isPalindromeRec(word, cc);
            } else {
                return false;
            }
        }
    }
}
