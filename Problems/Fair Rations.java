//https://www.hackerrank.com/challenges/fair-rations/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'fairRations' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts INTEGER_ARRAY B as parameter.
     */

    public static String fairRations(List<Integer> B) {
    // Write your code here
        int sum = 0;
    
    // Step 1: Calculate the total sum of all loaves
    for (int num : B) {
        sum += num;
    }
    
    // Step 2: If the sum is odd, it's impossible to make everyone even
    if (sum % 2 != 0) {
        return "NO";
    }
    
    int loavesDistributed = 0;
    
    // Step 3: Greedily fix odd numbers from left to right
    for (int i = 0; i < B.size() - 1; i++) {
        if (B.get(i) % 2 != 0) {
            // Give 1 loaf to the current person (making them even)
            B.set(i, B.get(i) + 1);
            // Give 1 loaf to the next person
            B.set(i + 1, B.get(i + 1) + 1);
            
            // We distributed 2 loaves in total
            loavesDistributed += 2;
        }
    }
    
    // Step 4: Return the result as a string
    return String.valueOf(loavesDistributed);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int N = Integer.parseInt(bufferedReader.readLine().trim());

        String[] BTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> B = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int BItem = Integer.parseInt(BTemp[i]);
            B.add(BItem);
        }

        String result = Result.fairRations(B);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
