//https://www.hackerrank.com/challenges/funny-string/problem?isFullScreen=true
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
     * Complete the 'funnyString' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String funnyString(String s) {
        int n = s.length();
    
    // Loop through the string starting from the second character
    for (int i = 1; i < n; i++) {
        // Calculate the difference from the front
        int diffForward = Math.abs(s.charAt(i) - s.charAt(i - 1));
        
        // Calculate the difference from the back
        int diffBackward = Math.abs(s.charAt(n - i -1) - s.charAt(n - i));
        
        // If they don't match, it's not a funny string
        if (diffForward != diffBackward) {
            return "Not Funny";
        }
    }
    
    // If the loop finishes without returning, all differences matched
    return "Funny";
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

                String result = Result.funnyString(s);

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
