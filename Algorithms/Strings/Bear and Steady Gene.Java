//https://www.hackerrank.com/challenges/bear-and-steady-gene/problem?isFullScreen=true
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
     * Complete the 'steadyGene' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING gene as parameter.
     */

    public static int steadyGene(String gene) {
    int n = gene.length();
    int target = n / 4;
    int[] count = new int[26];
    
    // Initial frequency count
    for (char c : gene.toCharArray()) {
        count[c - 'A']++;
    }
    
    // If already steady
    if (isValid(count, target)) {
        return 0;
    }
    
    int minLen = n;
    int left = 0;
    
    // Sliding window
    for (int right = 0; right < n; right++) {
        // Character is inside the window, so remove it from the outside count
        count[gene.charAt(right) - 'A']--;
        
        // While the outside characters are valid, calculate length and shrink window
        while (isValid(count, target) && left <= right) {
            minLen = Math.min(minLen, right - left + 1);
            
            // Put the left character back into the outside count and move pointer
            count[gene.charAt(left) - 'A']++;
            left++;
        }
    }
    
    return minLen;
}

// Helper function to check if outside character counts are within the target limit
private static boolean isValid(int[] count, int target) {
    return count['A' - 'A'] <= target && 
           count['C' - 'A'] <= target && 
           count['T' - 'A'] <= target && 
           count['G' - 'A'] <= target;
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String gene = bufferedReader.readLine();

        int result = Result.steadyGene(gene);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
