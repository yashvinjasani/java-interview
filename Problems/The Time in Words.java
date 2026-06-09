//https://www.hackerrank.com/challenges/the-time-in-words/problem?isFullScreen=true
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
     * Complete the 'timeInWords' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER h
     *  2. INTEGER m
     */

    public static String timeInWords(int h, int m) {
    // Write your code here
        // Dictionary for numbers 0 to 29
    String[] words = {
        "zero", "one", "two", "three", "four",
        "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen",
        "fourteen", "fifteen", "sixteen", "seventeen",
        "eighteen", "nineteen", "twenty", "twenty one",
        "twenty two", "twenty three", "twenty four",
        "twenty five", "twenty six", "twenty seven",
        "twenty eight", "twenty nine"
    };

    // Helper for the next hour to handle 12 -> 1 wrap-around
    int nextHour = (h % 12) + 1;

    if (m == 0) {
        return words[h] + " o' clock";
    } else if (m == 15) {
        return "quarter past " + words[h];
    } else if (m == 30) {
        return "half past " + words[h];
    } else if (m == 45) {
        return "quarter to " + words[nextHour];
    } else if (m < 30) {
        String minuteWord = (m == 1) ? " minute" : " minutes";
        return words[m] + minuteWord + " past " + words[h];
    } else {
        int minutesLeft = 60 - m;
        String minuteWord = (minutesLeft == 1) ? " minute" : " minutes";
        return words[minutesLeft] + minuteWord + " to " + words[nextHour];
    }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int h = Integer.parseInt(bufferedReader.readLine().trim());

        int m = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.timeInWords(h, m);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
    
