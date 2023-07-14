public class HuffmanDecoder {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        ObjectReader or = new ObjectReader(inputFileName);
        BinaryTrie trie = (BinaryTrie) or.readObject();
        int oneHugeBitSeqLength = (int) or.readObject();
        char[] symbols = new char[oneHugeBitSeqLength];
        BitSequence remainingSeq = (BitSequence) or.readObject();
        for (int i = 0; i < oneHugeBitSeqLength; i += 1) {
            Match match = trie.longestPrefixMatch(remainingSeq);
            symbols[i] = match.getSymbol();
            remainingSeq = remainingSeq.allButFirstNBits(match.getSequence().length());
        }
        FileUtils.writeCharArray(outputFileName, symbols);

    }

}
