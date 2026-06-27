//https://www.hackerrank.com/challenges/almost-sorted/problem?isFullScreen=true
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
     * Complete the 'almostSorted' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void almostSorted(List<Integer> arr) {
    // Write your code here
        // 1. Create a sorted copy of the array
    List<Integer> sortedArr = new ArrayList<>(arr);
    Collections.sort(sortedArr);
    
    // 2. Find all indices where the original and sorted arrays differ
    List<Integer> diff = new ArrayList<>();
    for (int i = 0; i < arr.size(); i++) {
        if (!arr.get(i).equals(sortedArr.get(i))) {
            diff.add(i);
        }
    }
    
    // 3. Analyze the differences
    if (diff.isEmpty()) {
        System.out.println("yes");
        return;
    }
    
    if (diff.size() == 2) {
        System.out.println("yes");
        // +1 for 1-based indexing required by the output format
        System.out.println("swap " + (diff.get(0) + 1) + " " + (diff.get(1) + 1));
        return;
    }
    
    // Check if reversing the sub-segment fixes the array
    int left = diff.get(0);
    int right = diff.get(diff.size() - 1);
    
    boolean canReverse = true;
    for (int i = left, j = right; i <= right; i++, j--) {
        if (!arr.get(i).equals(sortedArr.get(j))) {
            canReverse = false;
            break;
        }
    }
    
    if (canReverse) {
        System.out.println("yes");
        System.out.println("reverse " + (left + 1) + " " + (right + 1));
    } else {
        System.out.println("no");
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

        Result.almostSorted(arr);

        bufferedReader.close();
    }
}
