//https://www.hackerrank.com/challenges/queens-attack-2/problem?isFullScreen=true
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
     * Complete the 'queensAttack' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. INTEGER r_q
     *  4. INTEGER c_q
     *  5. 2D_INTEGER_ARRAY obstacles
     */

    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
    // 1. Initialize max travel distances to the edges of the board
    int up = n - r_q;
    int down = r_q - 1;
    int right = n - c_q;
    int left = c_q - 1;
    
    // Diagonals calculated by taking the minimum of their respective straight edges
    int up_left = Math.min(up, left);
    int up_right = Math.min(up, right);
    int down_left = Math.min(down, left);
    int down_right = Math.min(down, right);

    // 2. Scan through each obstacle and shrink the distance if it blocks the path
    for (List<Integer> obstacle : obstacles) {
        int r_o = obstacle.get(0);
        int c_o = obstacle.get(1);

        // Straight Lines (Vertical & Horizontal)
        if (c_o == c_q) { // Same column
            if (r_o > r_q) up = Math.min(up, r_o - r_q - 1);
            else down = Math.min(down, r_q - r_o - 1);
        } 
        else if (r_o == r_q) { // Same row
            if (c_o > c_q) right = Math.min(right, c_o - c_q - 1);
            else left = Math.min(left, c_q - c_o - 1);
        }
        // Diagonals (An obstacle is on a diagonal if |row_diff| == |col_diff|)
        else if (Math.abs(r_o - r_q) == Math.abs(c_o - c_q)) {
            if (r_o > r_q && c_o < c_q) up_left = Math.min(up_left, r_o - r_q - 1);
            else if (r_o > r_q && c_o > c_q) up_right = Math.min(up_right, r_o - r_q - 1);
            else if (r_o < r_q && c_o < c_q) down_left = Math.min(down_left, r_q - r_o - 1);
            else if (r_o < r_q && c_o > c_q) down_right = Math.min(down_right, r_q - r_o - 1);
        }
    }

    // 3. Total sum of all valid fields the queen can reach
    return up + down + right + left + up_left + up_right + down_left + down_right;
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r_q = Integer.parseInt(secondMultipleInput[0]);

        int c_q = Integer.parseInt(secondMultipleInput[1]);

        List<List<Integer>> obstacles = new ArrayList<>();

        IntStream.range(0, k).forEach(i -> {
            try {
                obstacles.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.queensAttack(n, k, r_q, c_q, obstacles);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
