//https://www.hackerrank.com/challenges/insertionsort1/problem?isFullScreen=true
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
     * Complete the 'insertionSort1' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */

    public static void insertionSort1(int n, List<Integer> arr) {
    // 1. Store the rightmost value
    int target = arr.get(n - 1);
    int i = n - 2;
    
    // 2. Loop backwards while the current element is greater than the target
    while (i >= 0 && arr.get(i) > target) {
        // Shift the larger element to the right
        arr.set(i + 1, arr.get(i));
        
        // Print the array state
        printArray(arr);
        
        // Move to the next element on the left
        i--;
    }
    
    // 3. Insert the target into its correct position
    arr.set(i + 1, target);
    
    // 4. Print the final array state
    printArray(arr);
}

// Helper method to print the array as space-separated integers
private static void printArray(List<Integer> arr) {
    for (int num : arr) {
        System.out.print(num + " ");
    }
    System.out.println();
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        Result.insertionSort1(n, arr);

        bufferedReader.close();
    }
}
