//https://www.hackerrank.com/challenges/determining-dna-health/problem?isFullScreen=true
import java.io.*;
import java.util.*;

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    TrieNode fail;
    TrieNode outputLink;
    List<Integer> geneIndices = new ArrayList<>();
    List<Long> healthPrefixSums = new ArrayList<>();

    public void addGene(int index, long health) {
        geneIndices.add(index);
        long currentSum = healthPrefixSums.isEmpty() ? 0 : healthPrefixSums.get(healthPrefixSums.size() - 1);
        healthPrefixSums.add(currentSum + health);
    }
}

public class Solution {

    static void insert(TrieNode root, String gene, int index, long health) {
        TrieNode current = root;
        for (int i = 0; i < gene.length(); i++) {
            int charIdx = gene.charAt(i) - 'a';
            if (current.children[charIdx] == null) {
                current.children[charIdx] = new TrieNode();
            }
            current = current.children[charIdx];
        }
        current.addGene(index, health);
    }

    static void buildFailureLinks(TrieNode root) {
        Queue<TrieNode> queue = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) {
                root.children[i].fail = root;
                queue.add(root.children[i]);
            }
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for (int i = 0; i < 26; i++) {
                TrieNode child = current.children[i];
                if (child != null) {
                    TrieNode fallback = current.fail;
                    while (fallback != root && fallback != null && fallback.children[i] == null) {
                        fallback = fallback.fail;
                    }
                    
                    child.fail = (fallback != null && fallback.children[i] != null) ? fallback.children[i] : root;
                    
                    if (!child.fail.geneIndices.isEmpty()) {
                        child.outputLink = child.fail;
                    } else {
                        child.outputLink = child.fail.outputLink;
                    }
                    queue.add(child);
                }
            }
        }
    }

    static long getHealth(TrieNode node, int start, int end) {
        if (node.geneIndices.isEmpty()) return 0;

        int left = Collections.binarySearch(node.geneIndices, start);
        if (left < 0) left = -(left + 1);

        int right = Collections.binarySearch(node.geneIndices, end);
        if (right < 0) right = -(right + 1) - 1;

        if (left > right) return 0;

        long sum = node.healthPrefixSums.get(right);
        if (left > 0) {
            sum -= node.healthPrefixSums.get(left - 1);
        }
        return sum;
    }

    static long processDNA(TrieNode root, String dna, int start, int end) {
        long totalHealth = 0;
        TrieNode current = root;

        for (int i = 0; i < dna.length(); i++) {
            int charIdx = dna.charAt(i) - 'a';

            while (current != root && current.children[charIdx] == null) {
                current = current.fail;
            }

            if (current.children[charIdx] != null) {
                current = current.children[charIdx];
            } else {
                current = root;
            }

            TrieNode temp = current;
            while (temp != root && temp != null) {
                totalHealth += getHealth(temp, start, end);
                temp = temp.outputLink;
            }
        }
        return totalHealth;
    }

    // Fast I/O is required to prevent Time Limit Exceeded (TLE) on large test cases
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();

        String[] genes = new String[n];
        for (int i = 0; i < n; i++) {
            genes[i] = scanner.next();
        }

        long[] healths = new long[n];
        for (int i = 0; i < n; i++) {
            healths[i] = scanner.nextLong();
        }

        TrieNode root = new TrieNode();
        for (int i = 0; i < n; i++) {
            insert(root, genes[i], i, healths[i]);
        }

        buildFailureLinks(root);

        int s = scanner.nextInt();
        long minHealth = Long.MAX_VALUE;
        long maxHealth = Long.MIN_VALUE;

        for (int i = 0; i < s; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            String dna = scanner.next();

            long health = processDNA(root, dna, start, end);
            minHealth = Math.min(minHealth, health);
            maxHealth = Math.max(maxHealth, health);
        }

        System.out.println(minHealth + " " + maxHealth);
    }
}
