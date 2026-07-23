//https://www.hackerrank.com/challenges/gem-stones/problem?isFullScreen=true
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
     * Complete the 'gemstones' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY arr as parameter.
     */

    public static int gemstones(List<String> arr) {
        if (arr == null || arr.isEmpty()) return 0;

        int[] mineralCounts = new int[26];
        int numRocks = arr.size();

        for (String rock : arr) {
            // Track minerals found in the current rock to avoid double-counting
            boolean[] foundInRock = new boolean[26];
            for (char mineral : rock.toCharArray()) {
                foundInRock[mineral - 'a'] = true;
            }

            // Increment the global count for any mineral found in this rock
            for (int i = 0; i < 26; i++) {
                if (foundInRock[i]) {
                    mineralCounts[i]++;
                }
            }
        }

        // Count how many minerals appeared in all rocks
        int gemstoneCount = 0;
        for (int count : mineralCounts) {
            if (count == numRocks) {
                gemstoneCount++;
            }
        }

        return gemstoneCount;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> arr = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        int result = Result.gemstones(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
