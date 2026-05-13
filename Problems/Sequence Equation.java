//https://www.hackerrank.com/challenges/permutation-equation/problem?isFullScreen=true
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
     * Complete the 'permutationEquation' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY p as parameter.
     */

    public static List<Integer> permutationEquation(List<Integer> p) {
    // Write your code here
        int n = p.size();
        
        // Array to store the 1-based index of each value in p.
        // Size is n + 1 so we can directly use the values (1 to n) as indices.
        int[] indexOfValue = new int[n + 1];
        for (int i = 0; i < n; i++) {
            // p.get(i) gives the value, i + 1 gives its 1-based index
            indexOfValue[p.get(i)] = i + 1;
        }
        
        List<Integer> result = new ArrayList<>();
        
        // For each x from 1 to n, find y such that p(p(y)) = x
        for (int x = 1; x <= n; x++) {
            int z = indexOfValue[x]; // Find z where p(z) = x
            int y = indexOfValue[z]; // Find y where p(y) = z
            result.add(y);
        }
        
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> p = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> result = Result.permutationEquation(p);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
