//https://www.hackerrank.com/challenges/happy-ladybugs/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'happyLadybugs' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING b as parameter.
     */

    public static String happyLadybugs(String b) {
    // Write your code here
        int[] counts = new int[26];
    int underscores = 0;
    
    // Step 1: Count the frequencies of each ladybug color and empty spaces
    for (int i = 0; i < b.length(); i++) {
        char c = b.charAt(i);
        if (c == '_') {
            underscores++;
        } else {
            counts[c - 'A']++;
        }
    }
    
    // Step 2: If any ladybug is the only one of its color, it can never be happy
    for (int count : counts) {
        if (count == 1) {
            return "NO";
        }
    }
    
    // Step 3: If there are no empty spaces, check if the board is already a winning state
    if (underscores == 0) {
        for (int i = 0; i < b.length(); i++) {
            boolean happy = false;
            
            // Check left neighbor
            if (i > 0 && b.charAt(i) == b.charAt(i - 1)) {
                happy = true;
            }
            // Check right neighbor
            if (i < b.length() - 1 && b.charAt(i) == b.charAt(i + 1)) {
                happy = true;
            }
            
            // If this ladybug has no matching neighbors, they are stuck unhappy
            if (!happy) {
                return "NO";
            }
        }
    }
    
    // Step 4: If all checks pass, the ladybugs are (or can be made) happy
    return "YES";
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int g = Integer.parseInt(bufferedReader.readLine().trim());

        for (int gItr = 0; gItr < g; gItr++) {
            int n = Integer.parseInt(bufferedReader.readLine().trim());

            String b = bufferedReader.readLine();

            String result = Result.happyLadybugs(b);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
