//https://www.hackerrank.com/challenges/countingsort2/problem?isFullScreen=true
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
     * Complete the 'countingSort' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static List<Integer> countingSort(List<Integer> arr) {
    // 1. Find the maximum value to size the counting array appropriately
    int max = 0;
    for (int num : arr) {
        if (num > max) {
            max = num;
        }
    }

    // 2. Create the counting array (size max + 1 to include the 0 index)
    // Note: If the problem explicitly limits values to 100, you can use: new int[100];
    int[] count = new int[max + 1];

    // 3. Tally the frequencies of each element
    for (int num : arr) {
        count[num]++;
    }

    // 4. Reconstruct and populate the final sorted list
    List<Integer> sortedList = new ArrayList<>();
    for (int i = 0; i < count.length; i++) {
        // While the count at this index is greater than 0, add the index to the list
        while (count[i] > 0) {
            sortedList.add(i);
            count[i]--;
        }
    }

    return sortedList;
    
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

        List<Integer> result = Result.countingSort(arr);

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
