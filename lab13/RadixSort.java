import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int singleStringLength = greatestLength(asciis);
        String[] sorted = new String[asciis.length];
        System.arraycopy(asciis, 0, sorted, 0, asciis.length);

        for (int i = singleStringLength; i >= 0; i -= 1) {
            sortHelperLSD(sorted, i);
        }

        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] alphabet = new int[256];
        String[] sortedTmp = new String[asciis.length];

        for (String str : asciis) {
            char charAtIndex = charAtString(str, index);
            alphabet[charAtIndex] += 1;
        }

        for (int i = 1; i < 256; i += 1) {
            alphabet[i] = alphabet[i] + alphabet[i - 1];
        }

        for (int i = asciis.length - 1; i >= 0; i -= 1) {
            String str = asciis[i];
            char c = charAtString(str, index);
            int a = alphabet[c];
            sortedTmp[a - 1] = str;
            alphabet[c] -= 1;
        }
        System.arraycopy(sortedTmp, 0, asciis, 0, asciis.length);
    }

    private static int greatestLength(String[] arr) {
        int length = arr[0].length();
        for (String str : arr) {
            if (str.length() > length) {
                length = str.length();
            }
        }
        return length;
    }

    private static char charAtString(String str, int index) {
        if (str.length() >= index + 1) {
            return str.charAt(index);
        } else {
            return '_';
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
