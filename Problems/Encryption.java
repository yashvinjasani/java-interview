//https://www.hackerrank.com/challenges/encryption/problem?isFullScreen=true
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
     * Complete the 'encryption' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String encryption(String s) {
    // Write your code here
        String filtered = s.replaceAll("\\s+", "");
        int L=filtered.length();
        double sq=Math.sqrt(L);
        
         int r=(int)Math.floor(sq);
         int c=(int)Math.ceil(sq);
         
         if (r * c < L) {
            r++;
        }
        
        // Create an array of strings to act as your grid rows
String[] grid = new String[r];

for (int i = 0; i < r; i++) {
    int start = i * c;
    int end = Math.min(start + c, L);
    
    if (start < L) {
        grid[i] = filtered.substring(start, end);
    } else {
        grid[i] = ""; // Handing trailing empty rows safely
    }
}

StringBuilder encrypted = new StringBuilder();
        
        for (int col = 0; col < c; col++) {
            for (int row = 0; row < r; row++) {
                // Check if the current row actually has a character at this column index
                if (col < grid[row].length()) {
                    encrypted.append(grid[row].charAt(col));
                }
            }
            // Append a space between column strings, except after the last column
            if (col < c - 1) {
                encrypted.append(" ");
            }
        }
        
        return encrypted.toString();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.encryption(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
