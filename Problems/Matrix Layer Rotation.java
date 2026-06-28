//https://www.hackerrank.com/challenges/matrix-rotation-algo/problem?isFullScreen=true
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
     * Complete the 'matrixRotation' function below.
     *
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY matrix
     *  2. INTEGER r
     */

    public static void matrixRotation(List<List<Integer>> matrix, int r) {
        int m = matrix.size();
        int n = matrix.get(0).size();
        
        // Use a standard 2D array to safely reconstruct without modifying the original Lists
        int[][] result = new int[m][n];
                
        // The number of concentric layers
        int numLayers = Math.min(m, n) / 2;
        
        for (int layer = 0; layer < numLayers; layer++) {
            List<Integer> flattenedLayer = new ArrayList<>();

            // 1. Extract Layer (Clockwise: Top -> Right -> Bottom -> Left)
            // Top row
            for (int i = layer; i < n - layer; i++) {
                flattenedLayer.add(matrix.get(layer).get(i));
            }
            // Right column
            for (int i = layer + 1; i < m - layer; i++) {
                flattenedLayer.add(matrix.get(i).get(n - 1 - layer));
            }
            // Bottom row
            for (int i = n - 2 - layer; i >= layer; i--) {
                flattenedLayer.add(matrix.get(m - 1 - layer).get(i));
            }
            // Left column
            for (int i = m - 2 - layer; i > layer; i--) {
                flattenedLayer.add(matrix.get(i).get(layer));
            }

            // 2. Calculate Effective Rotations
            int len = flattenedLayer.size();
            int startIndex = r % len;

            // 3. Reconstruct the Layer into the Result Matrix
            // Top row
            for (int i = layer; i < n - layer; i++) {
                result[layer][i] = flattenedLayer.get(startIndex % len);
                startIndex++;
            }
            // Right column
            for (int i = layer + 1; i < m - layer; i++) {
                result[i][n - 1 - layer] = flattenedLayer.get(startIndex % len);
                startIndex++;
            }
            // Bottom row
            for (int i = n - 2 - layer; i >= layer; i--) {
                result[m - 1 - layer][i] = flattenedLayer.get(startIndex % len);
                startIndex++;
            }
            // Left column
            for (int i = m - 2 - layer; i > layer; i--) {
                result[i][layer] = flattenedLayer.get(startIndex % len);
                startIndex++;
            }
        }

        // 4. Print the Result Matrix exactly as requested
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(result[i][j] + (j < n - 1 ? " " : ""));
            }
            System.out.println();
        }
    }
    }


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);

        int n = Integer.parseInt(firstMultipleInput[1]);

        int r = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                matrix.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Result.matrixRotation(matrix, r);

        bufferedReader.close();
    }
}
