//https://www.hackerrank.com/challenges/bigger-is-greater/problem?isFullScreen=true
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
     * Complete the 'biggerIsGreater' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING w as parameter.
     */

    public static String biggerIsGreater(String w) {
    char[] arr = w.toCharArray();
    int n = arr.length;
    
    // Step 1: Find the longest non-increasing suffix
    int i = n - 2;
    while (i >= 0 && arr[i] >= arr[i + 1]) {
        i--;
    }
    
    // If no pivot is found, it's the highest permutation
    if (i < 0) {
        return "no answer";
    }
    
    // Step 2: Find the rightmost successor to the pivot
    int j = n - 1;
    while (arr[j] <= arr[i]) {
        j--;
    }
    
    // Step 3: Swap the pivot with its successor
    swap(arr, i, j);
    
    // Step 4: Reverse the suffix
    reverse(arr, i + 1, n - 1);
    
    return new String(arr);
}

private static void swap(char[] arr, int i, int j) {
    char temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}

private static void reverse(char[] arr, int start, int end) {
    while (start < end) {
        swap(arr, start, end);
        start++;
        end--;
    }
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, T).forEach(TItr -> {
            try {
                String w = bufferedReader.readLine();

                String result = Result.biggerIsGreater(w);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
