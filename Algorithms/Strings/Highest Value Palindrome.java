//https://www.hackerrank.com/challenges/richie-rich/problem?isFullScreen=true
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
     * Complete the 'highestValuePalindrome' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. INTEGER n
     *  3. INTEGER k
     */

    public static String highestValuePalindrome(String s, int n, int k) {
    char[] chars = s.toCharArray();
    boolean[] changed = new boolean[n];
    
    int left = 0;
    int right = n - 1;
    
    // Phase 1: Make it a palindrome with minimum changes
    while (left < right) {
        if (chars[left] != chars[right]) {
            // Change the smaller character to the larger one
            if (chars[left] > chars[right]) {
                chars[right] = chars[left];
            } else {
                chars[left] = chars[right];
            }
            changed[left] = true; // Mark as modified
            k--;
        }
        left++;
        right--;
    }
    
    // If we used more changes than allowed, it's impossible
    if (k < 0) {
        return "-1";
    }
    
    // Phase 2: Maximize the palindrome
    left = 0;
    right = n - 1;
    
    while (left <= right) {
        // If we are at the middle character (odd length)
        if (left == right) {
            if (k > 0 && chars[left] != '9') {
                chars[left] = '9';
            }
            break;
        }
        
        // If they are not already '9'
        if (chars[left] != '9') {
            // If this pair was altered in Phase 1, it costs 1 extra change to make them '9'
            // If it wasn't altered, it costs 2 changes
            int cost = changed[left] ? 1 : 2;
            
            if (k >= cost) {
                chars[left] = '9';
                chars[right] = '9';
                k -= cost;
            }
        }
        
        left++;
        right--;
    }
    
    return new String(chars);
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String s = bufferedReader.readLine();

        String result = Result.highestValuePalindrome(s, n, k);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
