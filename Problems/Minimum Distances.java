//https://www.hackerrank.com/challenges/minimum-distances/problem?isFullScreen=true
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
     * Complete the 'minimumDistances' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static int minimumDistances(List<Integer> a) {
    // Write your code here
        int minDistance = Integer.MAX_VALUE;
        
        // HashMap to store the last seen index of each value
        Map<Integer, Integer> lastSeen = new HashMap<>();
        
        for (int i = 0; i < a.size(); i++) {
            int currentVal = a.get(i);
            
            // If we've seen this value before, calculate the distance
            if (lastSeen.containsKey(currentVal)) {
                int distance = i - lastSeen.get(currentVal);
                
                // Update minimum distance if this one is smaller
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
            
            // Update the last seen index for the current value
            lastSeen.put(currentVal, i);
        }
        
        // If minDistance hasn't changed, no matching pairs were found
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.minimumDistances(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
