//https://www.hackerrank.com/challenges/manasa-and-stones/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'stones' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER a
     *  3. INTEGER b
     */

    public static List<Integer> stones(int n, int a, int b) {
    // Write your code here
        List<Integer> result = new ArrayList<>();
        
        // Handle the edge case where both differences are the same
        if (a == b) {
            result.add((n - 1) * a);
            return result;
        }
        
        // Find the minimum and maximum step values to ensure ascending order
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        
        // Calculate all possible values for the last stone
        for (int i = 0; i < n; i++) {
            int lastStoneValue = (n - 1 - i) * min + (i * max);
            result.add(lastStoneValue);
        }
        
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        for (int TItr = 0; TItr < T; TItr++) {
            int n = Integer.parseInt(bufferedReader.readLine().trim());

            int a = Integer.parseInt(bufferedReader.readLine().trim());

            int b = Integer.parseInt(bufferedReader.readLine().trim());

            List<Integer> result = Result.stones(n, a, b);

            for (int i = 0; i < result.size(); i++) {
                bufferedWriter.write(String.valueOf(result.get(i)));

                if (i != result.size() - 1) {
                    bufferedWriter.write(" ");
                }
            }

            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
