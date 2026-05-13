//https://www.hackerrank.com/challenges/jumping-on-the-clouds-revisited/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

static int jumpingOnClouds(int[] c, int k) {
    int n = c.length;
    int e = 100; // initial energy
    int i = 0;   // starting cloud
    
    do {
        // Jump to the next cloud
        i = (i + k) % n;
        
        // Every jump costs 1 energy
        e -= 1;
        
        // If it's a thundercloud, it costs an additional 2 energy
        if (c[i] == 1) {
            e -= 2;
        }
        
    } while (i != 0); // Stop when we return to cloud 0
    
    return e;
}

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        int[] c = new int[n];

        String[] cItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int cItem = Integer.parseInt(cItems[i]);
            c[i] = cItem;
        }

        int result = jumpingOnClouds(c, k);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
