//https://www.hackerrank.com/challenges/repeated-string/problem?isFullScreen=true
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
     * Complete the 'repeatedString' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. LONG_INTEGER n
     */

    public static long repeatedString(String s, long n) {
    long countInS = 0;
    int length = s.length();
    
    // 1. Count the number of 'a's in the given string 's'
    for (int i = 0; i < length; i++) {
        if (s.charAt(i) == 'a') {
            countInS++;
        }
    }
    
    // 2. Calculate full repetitions of 's' and the remaining characters
    long fullRepeats = n / length;
    long remainder = n % length;
    
    // 3. Calculate total 'a's in the fully repeated parts
    long totalCount = fullRepeats * countInS;
    
    // 4. Add the 'a's found in the remaining characters
    for (int i = 0; i < remainder; i++) {
        if (s.charAt(i) == 'a') {
            totalCount++;
        }
    }
    
    return totalCount;
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        long n = Long.parseLong(bufferedReader.readLine().trim());

        long result = Result.repeatedString(s, n);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
