//https://www.hackerrank.com/challenges/lilys-homework/problem?isFullScreen=true
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
     * Complete the 'lilysHomework' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static int lilysHomework(List<Integer> arr) {
        int n = arr.size();
        
        // Arrays for the ascending target
        int[] originalAsc = new int[n];
        int[] targetAsc = new int[n];
        
        // Arrays for the descending target
        int[] originalDesc = new int[n];
        int[] targetDesc = new int[n];
        
        // Populate the arrays
        for (int i = 0; i < n; i++) {
            originalAsc[i] = arr.get(i);
            targetAsc[i] = arr.get(i);
            
            originalDesc[i] = arr.get(i);
        }
        
        // Sort for ascending target
        Arrays.sort(targetAsc);
        
        // Populate and sort for descending target
        for (int i = 0; i < n; i++) {
            targetDesc[i] = targetAsc[n - 1 - i];
        }
        
        // Calculate minimum swaps for both scenarios
        int swapsAsc = getMinSwaps(originalAsc, targetAsc);
        int swapsDesc = getMinSwaps(originalDesc, targetDesc);
        
        // Return the minimum of the two
        return Math.min(swapsAsc, swapsDesc);
    }
    
    // Helper method to calculate the exact number of swaps needed to reach the target
    private static int getMinSwaps(int[] original, int[] target) {
        int swaps = 0;
        Map<Integer, Integer> indexMap = new HashMap<>();
        
        // Map each value to its current index
        for (int i = 0; i < original.length; i++) {
            indexMap.put(original[i], i);
        }
        
        for (int i = 0; i < original.length; i++) {
            // If the current element is not what it should be
            if (original[i] != target[i]) {
                swaps++;
                
                int currentValue = original[i];
                int expectedValue = target[i];
                
                // Find where the expected value currently is
                int expectedValueIndex = indexMap.get(expectedValue);
                
                // Swap the values in the array
                original[i] = expectedValue;
                original[expectedValueIndex] = currentValue;
                
                // Update the map to reflect the new position of the swapped value
                indexMap.put(currentValue, expectedValueIndex);
            }
        }
        
        return swaps;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.lilysHomework(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
