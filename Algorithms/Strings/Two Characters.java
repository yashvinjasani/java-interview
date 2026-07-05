//https://www.hackerrank.com/challenges/two-characters/problem?isFullScreen=true
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
     * Complete the 'alternate' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int alternate(String s) {
        int maxLength = 0;
    
    // Find all unique characters in the string
    StringBuilder uniqueChars = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (uniqueChars.indexOf(String.valueOf(c)) == -1) {
            uniqueChars.append(c);
        }
    }
    
    // Iterate through all possible pairs of unique characters
    for (int i = 0; i < uniqueChars.length(); i++) {
        for (int j = i + 1; j < uniqueChars.length(); j++) {
            char char1 = uniqueChars.charAt(i);
            char char2 = uniqueChars.charAt(j);
            
            int currentLength = 0;
            char lastSeen = '\0';
            boolean isValid = true;
            
            // Iterate through the original string to check the pair
            for (int k = 0; k < s.length(); k++) {
                char current = s.charAt(k);
                
                if (current == char1 || current == char2) {
                    if (current == lastSeen) {
                        // Consecutive characters found, invalid alternating string
                        isValid = false;
                        break;
                    }
                    
                    // Update tracking variables
                    currentLength++;
                    lastSeen = current;
                }
            }
            
            // If the string was valid, update the maximum length
            if (isValid) {
                maxLength = Math.max(maxLength, currentLength);
            }
        }
    }
    
    return maxLength;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int l = Integer.parseInt(bufferedReader.readLine().trim());

        String s = bufferedReader.readLine();

        int result = Result.alternate(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
