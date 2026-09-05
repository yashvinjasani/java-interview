//https://www.hackerrank.com/challenges/string-function-calculation/problem?isFullScreen=true
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
     * Complete the 'maxValue' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING t as parameter.
     */

    // Node representation for the Suffix Automaton
    static class State {
        int len, link;
        int[] next = new int[26];
        long cnt;
    }

    /*
     * Complete the 'maxValue' function below.
     */
    public static int maxValue(String t) {
        int n = t.length();
        State[] st = new State[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            st[i] = new State();
        }
        
        st[0].len = 0;
        st[0].link = -1;
        st[0].cnt = 0;
        
        int sz = 1;
        int last = 0;
        
        // 1. Build the Suffix Automaton
        for (int i = 0; i < n; i++) {
            int cIdx = t.charAt(i) - 'a';
            int cur = sz++;
            st[cur].len = st[last].len + 1;
            st[cur].cnt = 1;
            
            int p = last;
            while (p != -1 && st[p].next[cIdx] == 0) {
                st[p].next[cIdx] = cur;
                p = st[p].link;
            }
            
            if (p == -1) {
                st[cur].link = 0;
            } else {
                int q = st[p].next[cIdx];
                if (st[p].len + 1 == st[q].len) {
                    st[cur].link = q;
                } else {
                    int clone = sz++;
                    st[clone].len = st[p].len + 1;
                    System.arraycopy(st[q].next, 0, st[clone].next, 0, 26);
                    st[clone].link = st[q].link;
                    while (p != -1 && st[p].next[cIdx] == q) {
                        st[p].next[cIdx] = clone;
                        p = st[p].link;
                    }
                    st[q].link = st[cur].link = clone;
                }
            }
            last = cur;
        }
        
        // 2. Sort states by length (Bucket Sort for O(N) performance)
        int[] c = new int[n + 1];
        for (int i = 0; i < sz; i++) c[st[i].len]++;
        for (int i = 1; i <= n; i++) c[i] += c[i - 1];
        
        int[] order = new int[sz];
        for (int i = 0; i < sz; i++) order[--c[st[i].len]] = i;
        
        // 3. Propagate frequency counts via suffix links
        long maxVal = 0;
        for (int i = sz - 1; i > 0; i--) {
            int v = order[i];
            if (st[v].link != -1) {
                st[st[v].link].cnt += st[v].cnt;
            }
            
            // Calculate f(s) = length * occurrences
            long val = (long) st[v].len * st[v].cnt;
            if (val > maxVal) {
                maxVal = val;
            }
        }
        
        // The maximum value can technically exceed standard 32-bit limits,
        // but since HackerRank's template requires an `int` return type, 
        // we cast it here to compile. 
        return (int) maxVal; 
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String t = bufferedReader.readLine();

        int result = Result.maxValue(t);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
