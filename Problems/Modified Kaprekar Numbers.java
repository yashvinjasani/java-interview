//https://www.hackerrank.com/challenges/kaprekar-numbers/problem?isFullScreen=true
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
     * Complete the 'kaprekarNumbers' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER p
     *  2. INTEGER q
     */
public static void kaprekarNumbers(int p, int q) {
        boolean found = false;
        
        // Loop from p to q inclusive. Use long to prevent integer overflow.
        for (long i = p; i <= q; i++) {
            long square = i * i;
            String sqString = String.valueOf(square);
            
            // 'd' is the number of digits in the original number
            int d = String.valueOf(i).length(); 
            
            // The right side is always the last 'd' characters
            String rightStr = sqString.substring(sqString.length() - d);
            
            // The left side is whatever is left over from the start
            String leftStr = sqString.substring(0, sqString.length() - d);
            
            // Convert strings back to numbers
            long right = Long.parseLong(rightStr);
            
            // Handle edge case where the left string might be empty (e.g., when i = 1, 1^2 = 1)
            long left = leftStr.isEmpty() ? 0 : Long.parseLong(leftStr);
            
            // Check the Kaprekar condition
            if (left + right == i) {
                System.out.print(i + " ");
                found = true;
            }
        }
        
        // If no numbers were printed in the range, print the fallback message
        if (!found) {
            System.out.println("INVALID RANGE");
        } else {
            System.out.println(); // Add a trailing newline for clean console output
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int p = Integer.parseInt(bufferedReader.readLine().trim());

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        Result.kaprekarNumbers(p, q);

        bufferedReader.close();
    }
}
