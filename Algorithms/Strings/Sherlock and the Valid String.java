//https://www.hackerrank.com/challenges/sherlock-and-valid-string/problem?isFullScreen=true
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
     * Complete the 'isValid' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String isValid(String s) {
    Map<Character, Integer> charFreq = new HashMap<>();
    for (char c : s.toCharArray()) {
        charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
    }
    
    Map<Integer, Integer> freqCount = new HashMap<>();
    for (int freq : charFreq.values()) {
        freqCount.put(freq, freqCount.getOrDefault(freq, 0) + 1);
    }
    
    if (freqCount.size() == 1) return "YES";
    if (freqCount.size() > 2) return "NO";
    
    int[] f = new int[2];
    int[] c = new int[2];
    int i = 0;
    
    for (Map.Entry<Integer, Integer> entry : freqCount.entrySet()) {
        f[i] = entry.getKey();
        c[i] = entry.getValue();
        i++;
    }
    
    if ((f[0] == 1 && c[0] == 1) || (f[1] == 1 && c[1] == 1)) {
        return "YES";
    }
    
    if ((f[0] - f[1] == 1 && c[0] == 1) || (f[1] - f[0] == 1 && c[1] == 1)) {
        return "YES";
    }
    
    return "NO";
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
