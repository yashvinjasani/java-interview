//https://www.hackerrank.com/challenges/3d-surface-area/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'surfaceArea' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY A as parameter.
     */

    public static int surfaceArea(List<List<Integer>> A) {
    // Write your code here
        int H = A.size();
    int W = A.get(0).size();
    
    // Start with the top and bottom faces
    int totalArea = 2 * H * W;
    
    for (int i = 0; i < H; i++) {
        for (int j = 0; j < W; j++) {
            int currentHeight = A.get(i).get(j);
            
            // Check Up (North)
            int upHeight = (i == 0) ? 0 : A.get(i - 1).get(j);
            totalArea += Math.max(0, currentHeight - upHeight);
            
            // Check Down (South)
            int downHeight = (i == H - 1) ? 0 : A.get(i + 1).get(j);
            totalArea += Math.max(0, currentHeight - downHeight);
            
            // Check Left (West)
            int leftHeight = (j == 0) ? 0 : A.get(i).get(j - 1);
            totalArea += Math.max(0, currentHeight - leftHeight);
            
            // Check Right (East)
            int rightHeight = (j == W - 1) ? 0 : A.get(i).get(j + 1);
            totalArea += Math.max(0, currentHeight - rightHeight);
        }
    }
    
    return totalArea;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int H = Integer.parseInt(firstMultipleInput[0]);

        int W = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> A = new ArrayList<>();

        for (int i = 0; i < H; i++) {
            String[] ARowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> ARowItems = new ArrayList<>();

            for (int j = 0; j < W; j++) {
                int AItem = Integer.parseInt(ARowTempItems[j]);
                ARowItems.add(AItem);
            }

            A.add(ARowItems);
        }

        int result = Result.surfaceArea(A);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
