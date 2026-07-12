//https://www.hackerrank.com/challenges/quicksort1/problem?isFullScreen=true
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
     * Complete the 'quickSort' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static List<Integer> quickSort(List<Integer> arr) {
        // Initialize the three partition lists
    List<Integer> left = new ArrayList<>();
    List<Integer> equal = new ArrayList<>();
    List<Integer> right = new ArrayList<>();
    
    // The pivot is the first element
    int pivot = arr.get(0);
    
    // Iterate through the array and partition the elements
    for (int num : arr) {
        if (num < pivot) {
            left.add(num);
        } else if (num > pivot) {
            right.add(num);
        } else {
            equal.add(num);
        }
    }
    
    // Combine the lists: left + equal + right
    List<Integer> result = new ArrayList<>();
    result.addAll(left);
    result.addAll(equal);
    result.addAll(right);
    
    return result;
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

        List<Integer> result = Result.quickSort(arr);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
