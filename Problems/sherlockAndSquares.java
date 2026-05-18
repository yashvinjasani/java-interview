//https://www.hackerrank.com/domains/algorithms?utm_source=hrwCandidateFeedback
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
     * Complete the 'squares' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER a
     *  2. INTEGER b
     */

    public static int squares(int a, int b) {
    // Write your code here
        int start=a;
        //calculate end=b. Eg b=19 in 4...19
        //rt. 19=4.3 and Math.ceil=5, end=5
        double end=Math.ceil(Math.sqrt(b));
        List<Integer> squaresInRange=new ArrayList<>();
        //Iterate until i<= square root of b. Eg b=19 in 4...19
        //so iterate i=0 to 25,where rt19=4.3 and Math.ceil=5
        for(int i=0;i<=end;i++){
    
            if(i*i>b){
                break;    
            }
            if(i * i >=start) {
                squaresInRange.add(i*i);
            }
        }
        int squares=squaresInRange.size();
        return squares;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int a = Integer.parseInt(firstMultipleInput[0]);

                int b = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.squares(a, b);

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
