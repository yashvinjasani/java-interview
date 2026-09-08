//https://www.hackerrank.com/challenges/challenging-palindromes/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'buildPalindrome' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING a
     *  2. STRING b
     */

static class Candidate {
        int i, j;
        String A;
        int[] P;

        public Candidate(int i, int j, String A, int[] P) {
            this.i = i;
            this.j = j;
            this.A = A;
            this.P = P;
        }

        public int length() {
            return 2 * (j - i) + P[j];
        }

        public char charAt(int idx) {
            int uLen = j - i;
            int vLen = P[j];
            if (idx < uLen) return A.charAt(i + idx);
            if (idx < uLen + vLen) return A.charAt(j + (idx - uLen));
            int u_idx = idx - (uLen + vLen);
            return A.charAt(j - 1 - u_idx);
        }
    }

    static class SuffixArray {
        int[] sa, lcp, rank;
        String s;
        int n;

        public SuffixArray(String s) {
            this.s = s;
            this.n = s.length();
            sa = new int[n];
            rank = new int[n];
            lcp = new int[n];
            buildSA();
            buildLCP();
        }

        void buildSA() {
            Integer[] SA = new Integer[n];
            for (int i = 0; i < n; i++) SA[i] = i;
            for (int i = 0; i < n; i++) rank[i] = s.charAt(i);

            for (int k = 1; k < n; k *= 2) {
                final int fk = k;
                Arrays.sort(SA, (a, b) -> {
                    if (rank[a] != rank[b]) return rank[a] - rank[b];
                    int rankA = a + fk < n ? rank[a + fk] : -1;
                    int rankB = b + fk < n ? rank[b + fk] : -1;
                    return rankA - rankB;
                });
                int[] tmp = new int[n];
                tmp[SA[0]] = 0;
                for (int i = 1; i < n; i++) {
                    int a = SA[i - 1], b = SA[i];
                    int rankA1 = rank[a], rankB1 = rank[b];
                    int rankA2 = a + fk < n ? rank[a + fk] : -1;
                    int rankB2 = b + fk < n ? rank[b + fk] : -1;
                    tmp[b] = tmp[a] + ((rankA1 == rankB1 && rankA2 == rankB2) ? 0 : 1);
                }
                rank = tmp;
                if (rank[SA[n - 1]] == n - 1) break;
            }
            for (int i = 0; i < n; i++) sa[i] = SA[i];
        }

        void buildLCP() {
            for (int i = 0; i < n; i++) rank[sa[i]] = i;
            int h = 0;
            for (int i = 0; i < n; i++) {
                if (rank[i] > 0) {
                    int j = sa[rank[i] - 1];
                    while (i + h < n && j + h < n && s.charAt(i + h) == s.charAt(j + h)) h++;
                    lcp[rank[i]] = h;
                    if (h > 0) h--;
                }
            }
        }
    }

    static class SparseTable {
        int[][] st;
        int[] log;
        int[] V;

        public SparseTable(int[] V) {
            this.V = V;
            int n = V.length;
            log = new int[n + 1];
            for (int i = 2; i <= n; i++) log[i] = log[i / 2] + 1;
            int K = log[n] + 1;
            st = new int[n][K];
            for (int i = 0; i < n; i++) st[i][0] = i;

            for (int j = 1; j < K; j++) {
                for (int i = 0; i + (1 << j) <= n; i++) {
                    int left = st[i][j - 1];
                    int right = st[i + (1 << (j - 1))][j - 1];
                    st[i][j] = V[left] >= V[right] ? left : right;
                }
            }
        }

        public int query(int L, int R) {
            if (L > R) return L;
            int j = log[R - L + 1];
            int left = st[L][j];
            int right = st[R - (1 << j) + 1][j];
            return V[left] >= V[right] ? left : right;
        }
    }

    private static List<Candidate> findCandidates(String A, String B) {
        int n = A.length();
        int m = B.length();
        String B_rev = new StringBuilder(B).reverse().toString();
        String S = A + "#" + B_rev + "$";

        SuffixArray sa = new SuffixArray(S);
        int[] L = new int[n];
        
        int currentLcp = 0;
        boolean seenB = false;
        for (int i = 0; i < S.length(); i++) {
            if (i > 0) currentLcp = Math.min(currentLcp, sa.lcp[i]);
            int idx = sa.sa[i];
            if (idx >= n + 1 && idx < n + 1 + m) {
                seenB = true;
                currentLcp = Integer.MAX_VALUE;
            } else if (idx < n) {
                if (seenB) L[idx] = Math.max(L[idx], currentLcp);
            }
        }

        currentLcp = 0;
        seenB = false;
        for (int i = S.length() - 1; i >= 0; i--) {
            int idx = sa.sa[i];
            if (idx >= n + 1 && idx < n + 1 + m) {
                seenB = true;
                currentLcp = Integer.MAX_VALUE;
            } else if (idx < n) {
                if (seenB) L[idx] = Math.max(L[idx], currentLcp);
            }
            if (i > 0) currentLcp = Math.min(currentLcp, sa.lcp[i]);
        }

        int[] P = new int[n + 1];
        int[] rad = new int[2 * n + 1];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append('#').append(A.charAt(i));
        sb.append('#');
        String ma = sb.toString();
        
        int c = 0, r = 0;
        for (int i = 0; i < ma.length(); i++) {
            int iMir = 2 * c - i;
            rad[i] = (i < r) ? Math.min(r - i, rad[iMir]) : 0;
            while (i - 1 - rad[i] >= 0 && i + 1 + rad[i] < ma.length() && 
                   ma.charAt(i - 1 - rad[i]) == ma.charAt(i + 1 + rad[i])) {
                rad[i]++;
            }
            if (i + rad[i] > r) {
                c = i;
                r = i + rad[i];
            }
        }

        int[] max_c = new int[2 * n + 1];
        for (int i = 0; i < ma.length(); i++) {
            int start = i - rad[i];
            if (start >= 0 && start <= 2 * n) {
                max_c[start] = Math.max(max_c[start], i);
            }
        }
        for (int i = 1; i <= 2 * n; i++) {
            max_c[i] = Math.max(max_c[i], max_c[i - 1]);
        }
        for (int j = 0; j <= n; j++) {
            P[j] = max_c[2 * j] - 2 * j;
        }

        int[] V = new int[n + 1];
        for (int j = 0; j <= n; j++) V[j] = 2 * j + P[j];
        SparseTable st = new SparseTable(V);

        List<Candidate> candidates = new ArrayList<>();
        int maxLen = -1;

        for (int i = 0; i < n; i++) {
            if (L[i] >= 1) {
                int max_j = st.query(i + 1, i + L[i]);
                int len = V[max_j] - 2 * i;
                if (len > maxLen) {
                    maxLen = len;
                    candidates.clear();
                    candidates.add(new Candidate(i, max_j, A, P));
                } else if (len == maxLen) {
                    candidates.add(new Candidate(i, max_j, A, P));
                }
            }
        }
        return candidates;
    }

    public static String buildPalindrome(String a, String b) {
        List<Candidate> cand1 = findCandidates(a, b);
        
        String bRev = new StringBuilder(b).reverse().toString();
        String aRev = new StringBuilder(a).reverse().toString();
        List<Candidate> cand2 = findCandidates(bRev, aRev);
        
        List<Candidate> all = new ArrayList<>();
        all.addAll(cand1);
        all.addAll(cand2);
        
        if (all.isEmpty()) return "-1";
        
        int maxL = -1;
        for (Candidate c : all) maxL = Math.max(maxL, c.length());
        
        Candidate best = null;
        for (Candidate c : all) {
            if (c.length() < maxL) continue;
            if (best == null) {
                best = c;
                continue;
            }
            boolean isBetter = false;
            for (int k = 0; k < maxL; k++) {
                char c1 = c.charAt(k);
                char c2 = best.charAt(k);
                if (c1 < c2) {
                    isBetter = true;
                    break;
                } else if (c1 > c2) {
                    break;
                }
            }
            if (isBetter) best = c;
        }
        
        StringBuilder res = new StringBuilder();
        for (int k = 0; k < maxL; k++) {
            res.append(best.charAt(k));
        }
        return res.toString();
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String a = bufferedReader.readLine();

                String b = bufferedReader.readLine();

                String result = Result.buildPalindrome(a, b);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
