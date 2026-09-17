//https://www.hackerrank.com/challenges/cards-permutation/problem?isFullScreen=true
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
     * Complete the 'solve' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts INTEGER_ARRAY x as parameter.
     */

    static final long MOD = 1000000007;

    // Helper method for modular exponentiation
    static long power(long base, long exp) {
        long res = 1;
        base %= MOD;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp /= 2;
        }
        return res;
    }

    // Helper method to find modular inverse
    static long modInverse(long n) {
        return power(n, MOD - 2);
    }

    // Fenwick Tree (Binary Indexed Tree) add operation
    static void add(long[] bit, int idx, long val, int n) {
        for (; idx <= n; idx += idx & -idx) {
            bit[idx] = (bit[idx] + val) % MOD;
        }
    }

    // Fenwick Tree query operation
    static long query(long[] bit, int idx) {
        long sum = 0;
        for (; idx > 0; idx -= idx & -idx) {
            sum = (sum + bit[idx]) % MOD;
        }
        return sum;
    }

    public static long solve(List<Integer> x) {
        int n = x.size();
        
        // 1. Precompute Factorials
        long[] fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        // 2. Identify missing numbers
        boolean[] present = new boolean[n + 1];
        int K = 0; // Total number of 0s
        for (int i = 0; i < n; i++) {
            int val = x.get(i);
            if (val > 0) {
                present[val] = true;
            } else {
                K++;
            }
        }

        // 3. Count missing numbers smaller than or equal to each value
        int[] missing_count = new int[n + 1];
        int current_missing = 0;
        for (int i = 1; i <= n; i++) {
            if (!present[i]) {
                current_missing++;
            }
            missing_count[i] = current_missing;
        }

        long ans = 0;
        long[] bit = new long[n + 1];
        long inv2 = modInverse(2);
        
        long sum_missing_greater_seen = 0;
        long unknown_to_right = 0;

        // 4. Right-to-Left Traversal
        for (int i = n - 1; i >= 0; i--) {
            int val = x.get(i);
            long T_i = 0;

            if (val > 0) {
                // CASE A: The value is known
                long smaller_known = query(bit, val - 1);
                long smaller_missing = missing_count[val - 1];

                long term1 = (smaller_known * fact[K]) % MOD;
                long term2 = 0;
                
                if (K > 0) {
                    term2 = (smaller_missing * unknown_to_right) % MOD;
                    term2 = (term2 * fact[K - 1]) % MOD;
                }

                T_i = (term1 + term2) % MOD;

                // Update data structures for knowns to the right
                add(bit, val, 1, n);
                long missing_greater = K - missing_count[val];
                sum_missing_greater_seen = (sum_missing_greater_seen + missing_greater) % MOD;
                
            } else {
                // CASE B: The value is unknown (0)
                long term1 = 0;
                if (K > 0) {
                    term1 = (sum_missing_greater_seen * fact[K - 1]) % MOD;
                }
                
                long term2 = 0;
                if (K > 1) {
                    term2 = (unknown_to_right * fact[K]) % MOD;
                    term2 = (term2 * inv2) % MOD;
                }

                T_i = (term1 + term2) % MOD;
                unknown_to_right++;
            }

            // Multiply T_i by the number of permutations of the remaining suffix elements
            long contribution = (T_i * fact[n - 1 - i]) % MOD;
            ans = (ans + contribution) % MOD;
        }

        // 5. Add K! for the 1-based indexing of the permutations
        ans = (ans + fact[K]) % MOD;
        
        return ans;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.solve(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
