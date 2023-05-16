import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset. //
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void TestEqualChars() {
        boolean rst1 = offByOne.equalChars('a', 'b');
        boolean rst2 = offByOne.equalChars('a', 'c');
        boolean rst3 = offByOne.equalChars('a', 'a');
        assertTrue(rst1);
        assertFalse(rst2);
        assertFalse(rst3);
    }


    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
}
