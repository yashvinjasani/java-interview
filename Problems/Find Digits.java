//https://www.hackerrank.com/challenges/find-digits/problem?isFullScreen=true
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
     * Complete the 'findDigits' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER n as parameter.
     */

    public static int findDigits(int n) {
    // Write your code here
        int count=0;
        String string=String.valueOf(n);   //convert 124 to String
        
        for(int i=0;i<string.length();i++){    //iterate over "124" as string
            
            /* character at "124" is 1
                              ^
              character at "124" is 2
                             ^
              character at "124" is 4
                              ^      */
            char digitAsChar=string.charAt(i);
          
            // convert character each '1' to string to parse it
            String digitAsString=String.valueOf(digitAsChar);
            int digit=Integer.parseInt(digitAsString);
            
            if(digit!=0 && n%digit==0){
                count=count+1;
            }
        }
        return count;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                int result = Result.findDigits(n);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
