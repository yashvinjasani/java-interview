//https://www.hackerrank.com/challenges/reduced-string/problem?isFullScreen=true
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
     * Complete the 'superReducedString' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String superReducedString(String s) {
    // Write your code here
        StringBuilder stack = new StringBuilder();
    
    for (char c : s.toCharArray()) {
        int length = stack.length();
        
        // If stack is not empty and top element matches current character
        if (length > 0 && stack.charAt(length - 1) == c) {
            // Pop the top element (remove it)
            stack.deleteCharAt(length - 1);
        } else {
            // Push the current character (add it)
            stack.append(c);
        }
    }
    
    // Check if the stack is empty after the loop
    if (stack.length() == 0) {
        return "Empty String";
    } else {
        return stack.toString();
    }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.superReducedString(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
