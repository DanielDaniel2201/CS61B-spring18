import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /** You must use this palindrome, and not instantiate
     new Palindromes, or the autograder might be upset.*/
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome() {
        boolean rst1 = palindrome.isPalindrome("");
        boolean rst2 = palindrome.isPalindrome("a");
        boolean rst3 = palindrome.isPalindrome("wow");
        boolean rst4 = palindrome.isPalindrome("noon");
        boolean rst5 = palindrome.isPalindrome("madam");
        boolean rst6 = palindrome.isPalindrome("redder");
        assertTrue(rst1);
        assertTrue(rst2);
        assertTrue(rst3);
        assertTrue(rst4);
        assertTrue(rst5);
        assertTrue(rst6);
        boolean rst7 = palindrome.isPalindrome("magnificent");
        boolean rst8 = palindrome.isPalindrome("mediocre");
        assertFalse(rst7);
        assertFalse(rst8);
    }

    @Test
    public void testOverloadedIsPalindrome() {
        CharacterComparator cc = new OffByOne();
        boolean rst1 = palindrome.isPalindrome("flake", cc);
        boolean rst2 = palindrome.isPalindrome("madam", cc);
        assertTrue(rst1);
        assertFalse(rst2);
    }
}
