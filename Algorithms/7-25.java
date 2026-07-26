//https://www.hackerrank.com/challenges/countingsort4/problem?isFullScreen=true
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
     * Complete the 'countSort' function below.
     *
     * The function accepts 2D_STRING_ARRAY arr as parameter.
     */

    public static void countSort(List<List<String>> arr) {
        int n = arr.size();
        
        // The problem constrains the maximum integer to 100
        StringBuilder[] buckets = new StringBuilder[100];
        
        // Initialize the StringBuilders to avoid NullPointerExceptions
        for (int i = 0; i < 100; i++) {
            buckets[i] = new StringBuilder();
        }
        
        // Iterate through the original array
        for (int i = 0; i < n; i++) {
            int key = Integer.parseInt(arr.get(i).get(0));
            String value = arr.get(i).get(1);
            
            // Apply the first-half dash rule
            if (i < n / 2) {
                value = "-";
            }
            
            // Append the value and a space to the corresponding bucket
            buckets[key].append(value).append(" ");
        }
        
        // Combine all buckets into a final result
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            result.append(buckets[i]);
        }
        
        // Print the result, trimming the trailing space at the very end
        System.out.println(result.toString().trim());
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> arr = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                arr.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Result.countSort(arr);

        bufferedReader.close();
    }
}
