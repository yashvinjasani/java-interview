//https://www.hackerrank.com/challenges/sherlock-and-anagrams/problem?isFullScreen=true
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
     * Complete the 'sherlockAndAnagrams' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int sherlockAndAnagrams(String s) {
    Map<String, Integer> signatureMap = new HashMap<>();
    int totalPairs = 0;

    // Iterate through all possible starting points
    for (int i = 0; i < s.length(); i++) {
        // Iterate through all possible ending points
        for (int j = i + 1; j <= s.length(); j++) {
            // 1. Extract the substring
            String currentSubstring = s.substring(i, j);
            
            // 2. Sort the characters to create the signature
            char[] chars = currentSubstring.toCharArray();
            Arrays.sort(chars);
            String signature = new String(chars);
            
            // 3. Check how many times we've seen this signature
            int previousOccurrences = signatureMap.getOrDefault(signature, 0);
            
            // 4. Add previous occurrences to total pairs
            totalPairs += previousOccurrences;
            
            // 5. Update the map with the new occurrence
            signatureMap.put(signature, previousOccurrences + 1);
        }
    }
    
    return totalPairs;
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

                int result = Result.sherlockAndAnagrams(s);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
