//https://www.hackerrank.com/challenges/append-and-delete/problem?isFullScreen=true
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
     * Complete the 'appendAndDelete' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. STRING t
     *  3. INTEGER k
     */

    public static String appendAndDelete(String s, String t, int k) {
    // Step 1: Find the length of the longest common prefix
    int commonLength = 0;
    int minLen = Math.min(s.length(), t.length());
    
    for (int i = 0; i < minLen; i++) {
        if (s.charAt(i) == t.charAt(i)) {
            commonLength++;
        } else {
            break;
        }
    }
    
    // Step 2: Calculate the absolute minimum operations required
    int minOps = (s.length() - commonLength) + (t.length() - commonLength);
    
    // Step 3: If k is less than the minimum required moves, it's impossible
    if (k < minOps) {
        return "No";
    }
    
    // Step 4: Handle excess moves
    // Case A: The leftover moves are an even number (can waste moves by deleting and re-appending)
    if ((k - minOps) % 2 == 0) {
        return "Yes";
    }
    
    // Case B: The total moves k are enough to completely delete the initial string 
    // down to an empty string, and then build the new string.
    if (k >= (s.length() + t.length())) {
        return "Yes";
    }
    
    // If none of the valid conditions are met
    return "No";
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String t = bufferedReader.readLine();

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.appendAndDelete(s, t, k);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
