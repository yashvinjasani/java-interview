//https://www.hackerrank.com/challenges/morgan-and-a-string/problem?isFullScreen=true
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
     * Complete the 'morganAndString' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING a
     *  2. STRING b
     */

public static String morganAndString(String a, String b) {
        // Append a lexicographically larger character ('z') to serve as a boundary sentinel.
        // This avoids complex out-of-bounds checks and forces the algorithm to prioritize valid uppercase letters.
        a += 'z';
        b += 'z';
        
        // Convert to char arrays for O(1) index access time, preventing substring TLEs
        char[] arrA = a.toCharArray();
        char[] arrB = b.toCharArray();
        int lenA = arrA.length;
        int lenB = arrB.length;
        
        StringBuilder result = new StringBuilder(lenA + lenB);
        int i = 0;
        int j = 0;
        
        while (i < lenA - 1 && j < lenB - 1) {
            if (arrA[i] < arrB[j]) {
                result.append(arrA[i++]);
            } else if (arrA[i] > arrB[j]) {
                result.append(arrB[j++]);
            } else {
                // Tie-breaker: Use temporary pointers to look ahead until characters differ
                int x = i;
                int y = j;
                
                while (arrA[x] == arrB[y] && arrA[x] != 'z') {
                    x++;
                    y++;
                }
                
                // Compare the differing characters to decide which string's current character to consume
                if (arrA[x] <= arrB[y]) {
                    result.append(arrA[i++]);
                } else {
                    result.append(arrB[j++]);
                }
            }
        }
        
        // Append any remaining characters from 'a' (excluding the 'z')
        while (i < lenA - 1) {
            result.append(arrA[i++]);
        }
        
        // Append any remaining characters from 'b' (excluding the 'z')
        while (j < lenB - 1) {
            result.append(arrB[j++]);
        }
        
        return result.toString();
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

                String result = Result.morganAndString(a, b);

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
