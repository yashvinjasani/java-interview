//Problem Description:
//https://www.hackerrank.com/challenges/strange-advertising/problem?isFullScreen=true

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
     * Complete the 'viralAdvertising' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER n as parameter.
     */

    public static int viralAdvertising(int n) {
    // Write your code here
        int shared=5;    //Second, initial number of people to which advertisement is advertised 
        int count=0;     // Fourth: i) created int type count variable to store the total number of liked advertisements by people
        for(int i=0;i<n;i++){
            int liked=(int)Math.floor(shared/2);  //First, develop logic for shared/2 and floor it using Math.floor. Then Math.floor(a) returns double so cast it to int.
            shared=liked*3; //Third, update the shared according to question.
            count=count+liked; //Fourth: ii) update inside loop 
        }
        return count;  //Fourth: iii) Final step is, actually return the count created.
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int result = Result.viralAdvertising(n);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
