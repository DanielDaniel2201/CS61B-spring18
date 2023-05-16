import org.hamcrest.Condition;

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        //create am empty deque;
        Deque<Character> rst = new LinkedListDeque<>();
        //get each i-th letter do addLast (for or while);
        for (int i = 0; i < word.length(); i += 1) {
            rst.addLast(word.charAt(i));
        }
        return rst;
        //return the created deque.
    }

    private boolean isPalindromeHelper(Deque<Character> w) {
        if (w.size() <= 1) {
            return true;
        }
        char f = w.removeFirst();
        char l = w.removeLast();
        if (f == l) {
            return isPalindromeHelper(w);
        }
        return false;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> l = wordToDeque(word);
        return isPalindromeHelper(l);
    }

    /**
     * overloading the method isPalindrome
     * */
    private boolean isPalindromeHelper(Deque<Character> w, CharacterComparator cc) {
        if (w.size() <= 1) {
            return true;
        }
        char f = w.removeFirst();
        char l = w.removeLast();
        if (cc.equalChars(f, l)) {
            return isPalindromeHelper(w, cc);
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> l = wordToDeque(word);
        return isPalindromeHelper(l, cc);
    }
}
