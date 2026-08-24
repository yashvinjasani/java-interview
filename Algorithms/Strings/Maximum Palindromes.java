//https://www.hackerrank.com/challenges/maximum-palindromes/problem?isFullScreen=true
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
    static int MOD = 1000000007;
    static int[][] freq;
    static long[] fact;
    static long[] invFact;

    /*
     * Complete the 'initialize' function below.
     *
     * The function accepts STRING s as parameter.
     */
    public static void initialize(String s) {
        int n = s.length();
        freq = new int[n + 1][26];
        fact = new long[n + 1];
        invFact = new long[n + 1];

        fact[0] = 1;
        invFact[0] = 1;

        // Build prefix sum array for frequencies and compute factorials
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                freq[i + 1][j] = freq[i][j];
            }
            freq[i + 1][s.charAt(i) - 'a']++;
            fact[i + 1] = (fact[i] * (i + 1)) % MOD;
        }

        // Precompute modular inverses for factorials using Fermat's Little Theorem
        invFact[n] = power(fact[n], MOD - 2);
        for (int i = n - 1; i >= 1; i--) {
            invFact[i] = (invFact[i + 1] * (i + 1)) % MOD;
        }
    }

    /*
     * Complete the 'answerQuery' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER l
     *  2. INTEGER r
     */
    public static int answerQuery(int l, int r) {
        int pairs = 0;
        int odds = 0;
        long denominatorInv = 1;

        // Calculate pairs and odds for the query range
        for (int i = 0; i < 26; i++) {
            int count = freq[r][i] - freq[l - 1][i];
            int p = count / 2;
            pairs += p;
            
            if (count % 2 != 0) {
                odds++;
            }
            
            if (p > 0) {
                denominatorInv = (denominatorInv * invFact[p]) % MOD;
            }
        }

        // Apply permutation formula: (P! / (p1! * p2! ...)) * max(1, odds)
        long ans = fact[pairs];
        ans = (ans * denominatorInv) % MOD;
        
        if (odds > 0) {
            ans = (ans * odds) % MOD;
        }

        return (int) ans;
    }

    // Helper function for modular exponentiation
    private static long power(long base, long exp) {
        long res = 1;
        base %= MOD;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp /= 2;
        }
        return res;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        Result.initialize(s);

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int l = Integer.parseInt(firstMultipleInput[0]);

                int r = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.answerQuery(l, r);

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
