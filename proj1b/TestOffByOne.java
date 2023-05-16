import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        boolean rst1 = offByOne.equalChars('a', 'b');
        boolean rst2 = offByOne.equalChars('a', 'c');
        boolean rst3 = offByOne.equalChars('a', 'a');
        boolean rst4 = offByOne.equalChars('a', 'B');
        boolean rst5 = offByOne.equalChars('a', 'C');
        boolean rst6 = offByOne.equalChars('&', '%');
        assertTrue(rst1);
        assertFalse(rst2);
        assertFalse(rst3);
        assertFalse(rst4);
        assertFalse(rst5);
        assertTrue(rst6);
    }
}
