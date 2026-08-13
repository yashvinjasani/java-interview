//https://www.hackerrank.com/challenges/anagram/problem?isFullScreen=true
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
     * Complete the 'anagram' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int anagram(String s) {
    // 1. If the length is odd, they cannot be anagrams
    if (s.length() % 2 != 0) {
        return -1;
    }

    int mid = s.length() / 2;
    String s1 = s.substring(0, mid);
    String s2 = s.substring(mid);

    // Array to store character frequencies (for a-z)
    int[] charCounts = new int[26];

    // 2. Count frequencies of characters in the first half
    for (int i = 0; i < mid; i++) {
        charCounts[s1.charAt(i) - 'a']++;
    }

    int changes = 0;
    
    // 3. Check characters in the second half against our counts
    for (int i = 0; i < mid; i++) {
        int charIndex = s2.charAt(i) - 'a';
        
        if (charCounts[charIndex] > 0) {
            // Match found, decrement the available count
            charCounts[charIndex]--;
        } else {
            // No match available in s1, so this character requires a change
            changes++;
        }
    }

    return changes;
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                int result = Result.anagram(s);

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
