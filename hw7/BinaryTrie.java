import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    Map<Character, Integer> frequencyTable;
    Node trieNode;

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.frequencyTable = frequencyTable;
        MinPQ<Node> pq = new MinPQ<>();
        for (char c : frequencyTable.keySet()) {
            pq.insert(new Node(c, frequencyTable.get(c), null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        trieNode = pq.delMin();
    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node node = trieNode;

        for (int i = 0; i < querySequence.length(); i += 1) {
            if (node.isLeaf()) {
                return new Match(querySequence.firstNBits(i), node.c);
            } else {
                if (querySequence.bitAt(i) == 0) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
        }

        return new Match(querySequence, node.c);
    }

    private BitSequence prefixMatchSeqHelper(BitSequence sequence, Node node) {
        if (node.isLeaf()) {
            return new BitSequence("");
        } else if (sequence.bitAt(0) == 0) {
            String str = sequence.toString().substring(1);
            BitSequence seq = new BitSequence(str);
            String str1 = sequence.toString().substring(0, 1);
            String str2 = prefixMatchSeqHelper(seq, node.left).toString();
            return new BitSequence(str1 + str2);
        } else {
            String str = sequence.toString().substring(1);
            BitSequence seq = new BitSequence(str);
            String str1 = sequence.toString().substring(0, 1);
            String str2 = prefixMatchSeqHelper(seq, node.right).toString();
            return new BitSequence(str1 + str2);
        }

    }

    private Character prefixMatchCharHelper(BitSequence remainingSequence, Node node) {
        if (node.isLeaf()) {
            return node.c;
        } else if (remainingSequence.bitAt(0) == 0) {
            String str = remainingSequence.toString().substring(1);
            BitSequence seq = new BitSequence(str);
            return prefixMatchCharHelper(seq, node.left);
        } else {
            String str = remainingSequence.toString().substring(1);
            BitSequence seq = new BitSequence(str);
            return prefixMatchCharHelper(seq, node.right);
        }
    }

    public Map<Character, BitSequence> buildLookupTable() {
        HashMap<Character, BitSequence> expected = new HashMap<Character, BitSequence>();
        lookupTableHelper (trieNode, expected, new BitSequence());
        return expected;
    }

    private void lookupTableHelper(Node node, HashMap<Character, BitSequence> map, BitSequence sequence) {
        if (node.left != null) {
            lookupTableHelper(node.left, map, sequence.appended(0));
        }
        if (node.right != null) {
            lookupTableHelper(node.right, map, sequence.appended(1));
        }
        if (node.c != '\0'){
            map.put(node.c, sequence);
        }
    }

    private static class Node implements Comparable<Node>, Serializable{
        private final char c;
        private final int freq;
        private final Node left, right;

        Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return left == null && right ==null;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
}
