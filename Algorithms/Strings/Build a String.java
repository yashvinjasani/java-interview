//https://www.hackerrank.com/challenges/build-a-string/problem?isFullScreen=true
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
     * Complete the 'buildString' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER a
     *  2. INTEGER b
     *  3. STRING s
     */
    public static int buildString(int a, int b, String s) {
    int n = s.length();
    final int MAXS = n * 2 + 10;
    final int LOG = 16;

    int[][] nxt = new int[MAXS][26];
    int[] link = new int[MAXS];
    int[] slen = new int[MAXS];
    int[] pos = new int[MAXS];
    int[] mp = new int[MAXS];
    int[][] pa = new int[MAXS][LOG];
    int[] rpos = new int[n];
    Arrays.fill(pos, -1);
    slen[0] = -1;

    int sz = 2, last = 1;
    link[1] = 0;
    slen[1] = 0;

    for (int i = 0; i < n; i++) {
        int ch = s.charAt(i) - 'a';
        int p = last, np = last = sz++;
        slen[np] = slen[p] + 1;
        rpos[i] = np;
        pos[np] = i;
        while (p != 0 && nxt[p][ch] == 0) {
            nxt[p][ch] = np;
            p = link[p];
        }
        if (p == 0) {
            link[np] = 1;
        } else {
            int q = nxt[p][ch];
            if (slen[p] + 1 == slen[q]) {
                link[np] = q;
            } else {
                int nq = sz++;
                slen[nq] = slen[p] + 1;
                System.arraycopy(nxt[q], 0, nxt[nq], 0, 26);
                link[nq] = link[q];
                pos[nq] = pos[q];
                link[np] = link[q] = nq;
                while (p != 0 && nxt[p][ch] == q) {
                    nxt[p][ch] = nq;
                    p = link[p];
                }
            }
        }
    }

    List<Integer>[] G = new ArrayList[sz];
    for (int i = 0; i < sz; i++) G[i] = new ArrayList<>();
    for (int i = 2; i < sz; i++) G[link[i]].add(i);

    int[] stack = new int[sz];
    int[] iter = new int[sz];
    int[] retv = new int[sz];
    boolean[] seen = new boolean[sz];
    // iterative dfs: mp[u] = min end-position in the suffix-link subtree
    int top = 0;
    stack[top++] = 1;
    pa[1][0] = 1;
    while (top > 0) {
        int u = stack[top - 1];
        if (!seen[u]) {
            seen[u] = true;
            for (int k = 1; k < LOG; k++) pa[u][k] = pa[pa[u][k - 1]][k - 1];
            retv[u] = (pos[u] == -1 ? 1_000_000_000 : pos[u]);
            iter[u] = 0;
        }
        if (iter[u] < G[u].size()) {
            int v = G[u].get(iter[u]++);
            pa[v][0] = u;
            stack[top++] = v;
        } else {
            for (int v : G[u]) retv[u] = Math.min(retv[u], retv[v]);
            mp[u] = retv[u];
            top--;
        }
    }

    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        dp[i] = dp[i - 1] + a;
        int u = rpos[i - 1];
        for (int k = LOG - 1; k >= 0; k--) {
            int w = pa[u][k];
            if (slen[link[w]] + 1 + mp[w] > i - 1) u = w;
        }
        u = link[u];
        int L = 0;
        if (u > 0) L = Math.max(0, Math.min(slen[u], (i - 1) - mp[u]));
        if (L > 0) dp[i] = Math.min(dp[i], dp[i - L] + b);
    }
    return dp[n];
}
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int a = Integer.parseInt(firstMultipleInput[1]);

                int b = Integer.parseInt(firstMultipleInput[2]);

                String s = bufferedReader.readLine();

                int result = Result.buildString(a, b, s);
                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
