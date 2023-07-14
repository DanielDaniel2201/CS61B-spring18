import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        HashMap<Character,Integer> map = new HashMap<>();
        for (char c : inputSymbols) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }
    public static void main(String[] args) {
        String fileName = args[0];
        char[] inputSymbols = FileUtils.readFile(fileName);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie binaryTrie = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(binaryTrie);
        ow.writeObject(inputSymbols.length);
        Map<Character, BitSequence> lookupTable = binaryTrie.buildLookupTable();
        List<BitSequence> bitSequences = new ArrayList<>(lookupTable.values());
        BitSequence oneHugeBitSeq = BitSequence.assemble(bitSequences);
        ow.writeObject(oneHugeBitSeq);
    }
}
