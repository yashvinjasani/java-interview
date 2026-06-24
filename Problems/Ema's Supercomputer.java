//https://www.hackerrank.com/challenges/two-pluses/problem?isFullScreen=true
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

    // Helper class to represent a Plus and its grid coordinates
    static class Plus {
        int area;
        Set<Integer> cells;

        public Plus(int r, int c, int d, int m) {
            this.area = 4 * d + 1;
            this.cells = new HashSet<>();
            
            // Convert 2D coordinates (row, col) to a 1D index (r * cols + c)
            this.cells.add(r * m + c); // Center cell
            
            // Add radiating cells
            for (int i = 1; i <= d; i++) {
                this.cells.add((r - i) * m + c); // Top
                this.cells.add((r + i) * m + c); // Bottom
                this.cells.add(r * m + (c - i)); // Left
                this.cells.add(r * m + (c + i)); // Right
            }
        }

        // Checks if this plus shares any coordinates with another plus
        public boolean overlaps(Plus other) {
            for (int cell : this.cells) {
                if (other.cells.contains(cell)) {
                    return true;
                }
            }
            return false;
        }
    }

    /*
     * Complete the 'twoPluses' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY grid as parameter.
     */
    public static int twoPluses(List<String> grid) {
        int n = grid.size();
        int m = grid.get(0).length();
        List<Plus> pluses = new ArrayList<>();

        // Step 1: Discover all possible valid pluses of all sizes
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                
                // If the center is 'G', we have at least a plus of radius 0
                if (grid.get(r).charAt(c) == 'G') {
                    int d = 0;
                    
                    // Keep expanding the radius (d) outward safely within bounds
                    while (r - d >= 0 && r + d < n && c - d >= 0 && c + d < m
                            && grid.get(r - d).charAt(c) == 'G'
                            && grid.get(r + d).charAt(c) == 'G'
                            && grid.get(r).charAt(c - d) == 'G'
                            && grid.get(r).charAt(c + d) == 'G') {
                        
                        // Save the plus (Important: we save every valid size found)
                        pluses.add(new Plus(r, c, d, m));
                        d++; // Try to grow it by 1 more block
                    }
                }
            }
        }

        // Step 2: Compare every plus against every other plus
        int maxProduct = 0;
        for (int i = 0; i < pluses.size(); i++) {
            for (int j = i + 1; j < pluses.size(); j++) {
                Plus p1 = pluses.get(i);
                Plus p2 = pluses.get(j);
                
                // If they don't share any cells, calculate their area product
                if (!p1.overlaps(p2)) {
                    maxProduct = Math.max(maxProduct, p1.area * p2.area);
                }
            }
        }

        return maxProduct;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<String> grid = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        int result = Result.twoPluses(grid);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
