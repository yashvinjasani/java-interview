//https://www.hackerrank.com/challenges/bomber-man/problem?isFullScreen=true
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
     * Complete the 'bomberMan' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING_ARRAY grid
     */

    public static List<String> bomberMan(int n, List<String> grid) {
        // Base case: 0 or 1 seconds means the grid doesn't change
        if (n == 0 || n == 1) {
            return grid;
        }

        // Even seconds: The grid is always completely full of bombs
        if (n % 2 == 0) {
            return getFullGrid(grid.size(), grid.get(0).length());
        }

        // Calculate State A (detonation of initial bombs)
        List<String> state3 = detonate(grid);
        if (n % 4 == 3) {
            return state3;
        }

        // Calculate State B (detonation of State A's bombs)
        List<String> state1 = detonate(state3);
        return state1; // This covers the n % 4 == 1 case
    }

    // Helper to generate a grid completely filled with bombs
    private static List<String> getFullGrid(int rows, int cols) {
        List<String> fullGrid = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < cols; j++) {
            sb.append('O');
        }
        String rowString = sb.toString();
        for (int i = 0; i < rows; i++) {
            fullGrid.add(rowString);
        }
        return fullGrid;
    }

    // Helper to simulate one round of detonations
    private static List<String> detonate(List<String> previousGrid) {
        int rows = previousGrid.size();
        int cols = previousGrid.get(0).length();
        
        // Start with a new grid full of bombs
        char[][] newGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(newGrid[i], 'O');
        }

        // Check the previous grid to see where explosions happen
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (previousGrid.get(i).charAt(j) == 'O') {
                    // Destroy the bomb itself
                    newGrid[i][j] = '.';
                    // Destroy top neighbor
                    if (i > 0) newGrid[i - 1][j] = '.';
                    // Destroy bottom neighbor
                    if (i < rows - 1) newGrid[i + 1][j] = '.';
                    // Destroy left neighbor
                    if (j > 0) newGrid[i][j - 1] = '.';
                    // Destroy right neighbor
                    if (j < cols - 1) newGrid[i][j + 1] = '.';
                }
            }
        }

        // Convert the 2D char array back to a List<String>
        List<String> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            result.add(new String(newGrid[i]));
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r = Integer.parseInt(firstMultipleInput[0]);

        int c = Integer.parseInt(firstMultipleInput[1]);

        int n = Integer.parseInt(firstMultipleInput[2]);

        List<String> grid = IntStream.range(0, r).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        List<String> result = Result.bomberMan(n, grid);

        bufferedWriter.write(
            result.stream()
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
