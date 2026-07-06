//https://www.hackerrank.com/challenges/insertionsort2/problem?isFullScreen=true
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
     * Complete the 'insertionSort2' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */

    public static void insertionSort2(int n, List<Integer> arr) {
        // Start from the second element (index 1) since a single element is already sorted
    for (int i = 1; i < n; i++) {
        int key = arr.get(i);
        int j = i - 1;

        // Shift elements of the sorted segment that are greater than the key to the right
        while (j >= 0 && arr.get(j) > key) {
            arr.set(j + 1, arr.get(j));
            j--;
        }
        
        // Insert the key into its correct position
        arr.set(j + 1, key);

        // Print the array after each insertion iteration
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println(); // Move to the next line for the next iteration
    }

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        Result.insertionSort2(n, arr);

        bufferedReader.close();
    }
}
                        
