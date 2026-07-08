//https://www.hackerrank.com/challenges/caesar-cipher-1/problem?isFullScreen=true
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
     * Complete the 'caesarCipher' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. INTEGER k
     */

    public static String caesarCipher(String s, int k) {
        StringBuilder result = new StringBuilder();
    
    // It's a good practice to mod k by 26 initially in case k > 26
    k = k % 26; 

    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);

        if (Character.isLetter(ch)) {
            // Determine if the character is uppercase or lowercase
            char base = Character.isLowerCase(ch) ? 'a' : 'A';
            
            // Apply the shift and wrap around using modulo 26
            char shiftedChar = (char) (((ch - base + k) % 26) + base);
            result.append(shiftedChar);
        } else {
            // If it's a symbol or number, leave it unencrypted
            result.append(ch);
        }
    }

    return result.toString();
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String s = bufferedReader.readLine();

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.caesarCipher(s, k);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
