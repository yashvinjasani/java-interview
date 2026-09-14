//https://www.hackerrank.com/challenges/gridland-provinces/problem?isFullScreen=true
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
     * Complete the 'gridlandProvinces' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING s1
     *  2. STRING s2
     */

    // Hash configuration: Double Hashing to prevent collisions
    private static final long M1 = 1000000007L;
    private static final long M2 = 1000000009L;
    private static final long B1 = 313L;
    private static final long B2 = 317L;
    
    private static long[] pB1 = new long[1500];
    private static long[] pB2 = new long[1500];
    
    // Precompute the powers for our prime bases for O(1) hash combining
    static {
        pB1[0] = 1; 
        pB2[0] = 1;
        for (int i = 1; i < 1500; i++) {
            pB1[i] = (pB1[i - 1] * B1) % M1;
            pB2[i] = (pB2[i - 1] * B2) % M2;
        }
    }

    public static int gridlandProvinces(String s1, String s2) {
        Set<Long> uniqueHashes = new HashSet<>();
        int n = s1.length();
        
        // 1. Forward sweep
        generatePaths(s1, s2, n, uniqueHashes);
        
        // 2. Backward sweep (process reversed strings)
        String revS1 = new StringBuilder(s1).reverse().toString();
        String revS2 = new StringBuilder(s2).reverse().toString();
        
        generatePaths(revS1, revS2, n, uniqueHashes);
        
        return uniqueHashes.size();
    }

    private static void generatePaths(String s1, String s2, int n, Set<Long> uniqueHashes) {
        // Precalculate all possible Right U-Turn Hashes in O(N^2)
        // rightU[r][j] stores the hash of the right U-turn starting at row 'r', col 'j'
        long[][] rightU = new long[2][n + 1];
        for (int r = 0; r <= 1; r++) {
            for (int j = 0; j <= n; j++) {
                if (j == n) {
                    rightU[r][j] = 0;
                    continue;
                }
                long h1 = 0, h2 = 0;
                // Move Rightward
                for (int k = j; k < n; k++) {
                    char c = (r == 0) ? s1.charAt(k) : s2.charAt(k);
                    h1 = (h1 * B1 + c) % M1;
                    h2 = (h2 * B2 + c) % M2;
                }
                // Drop and move Leftward
                for (int k = n - 1; k >= j; k--) {
                    char c = (r == 0) ? s2.charAt(k) : s1.charAt(k);
                    h1 = (h1 * B1 + c) % M1;
                    h2 = (h2 * B2 + c) % M2;
                }
                // Pack two 32-bit hashes into one 64-bit long
                rightU[r][j] = (h1 << 32) | h2;
            }
        }

        // Generate combinations using the precalculated hashes
        for (int i = 0; i <= n; i++) {
            for (int startRow = 0; startRow <= 1; startRow++) {
                long h1 = 0, h2 = 0;
                
                // 1. Construct the Hash for the Left U-Turn
                if (i > 0) {
                    for (int k = i - 1; k >= 0; k--) {
                        char c = (startRow == 0) ? s1.charAt(k) : s2.charAt(k);
                        h1 = (h1 * B1 + c) % M1;
                        h2 = (h2 * B2 + c) % M2;
                    }
                    for (int k = 0; k < i; k++) {
                        char c = (startRow == 0) ? s2.charAt(k) : s1.charAt(k);
                        h1 = (h1 * B1 + c) % M1;
                        h2 = (h2 * B2 + c) % M2;
                    }
                }
                
                int currRow = (i == 0) ? startRow : (1 - startRow);
                
                // 2. Incrementally advance Zig-Zag and append Right U-Turn
                for (int j = i; j <= n; j++) {
                    
                    // Unpack the precalculated right U-turn hash
                    long rightHash = rightU[currRow][j];
                    long rh1 = rightHash >>> 32;
                    long rh2 = rightHash & 0xFFFFFFFFL;
                    
                    int rightLen = 2 * (n - j);
                    
                    // Combine hashes mathematically in O(1) time
                    long finalH1 = (h1 * pB1[rightLen] + rh1) % M1;
                    long finalH2 = (h2 * pB2[rightLen] + rh2) % M2;
                    
                    uniqueHashes.add((finalH1 << 32) | finalH2);
                    
                    // Advance the Zig-Zag by one column
                    if (j < n) {
                        char c1 = (currRow == 0) ? s1.charAt(j) : s2.charAt(j);
                        h1 = (h1 * B1 + c1) % M1;
                        h2 = (h2 * B2 + c1) % M2;
                        
                        currRow = 1 - currRow;
                        
                        char c2 = (currRow == 0) ? s1.charAt(j) : s2.charAt(j);
                        h1 = (h1 * B1 + c2) % M1;
                        h2 = (h2 * B2 + c2) % M2;
                    }
                }
            }
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int p = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, p).forEach(pItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                String s1 = bufferedReader.readLine();

                String s2 = bufferedReader.readLine();

                int result = Result.gridlandProvinces(s1, s2);

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
