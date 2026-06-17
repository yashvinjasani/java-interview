//https://www.hackerrank.com/challenges/cavity-map/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'cavityMap' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    public static List<String> cavityMap(List<String> grid) {
    // Write your code here
    int n = grid.size();
    
    // 1. Convert the immutable String list into a mutable 2D char array
    char[][] map = new char[n][n];
    for (int i = 0; i < n; i++) {
        map[i] = grid.get(i).toCharArray();
    }

    // 2. Iterate through only the inner cells (skipping the border)
    for (int i = 1; i < n - 1; i++) {
        for (int j = 1; j < n - 1; j++) {
            
            char currentDepth = map[i][j];
            
            // 3. Check if strictly deeper than all 4 adjacent cells
            if (currentDepth > map[i - 1][j] && // Top
                currentDepth > map[i + 1][j] && // Bottom
                currentDepth > map[i][j - 1] && // Left
                currentDepth > map[i][j + 1]) { // Right
                
                // Mark as cavity
                map[i][j] = 'X';
            }
        }
    }

    // 4. Convert the modified char array back into a List<String>
    List<String> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        result.add(new String(map[i]));
    }
    
    return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> grid = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String gridItem = bufferedReader.readLine();
            grid.add(gridItem);
        }

        List<String> result = Result.cavityMap(grid);

        for (int i = 0; i < result.size(); i++) {
            bufferedWriter.write(result.get(i));

            if (i != result.size() - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
