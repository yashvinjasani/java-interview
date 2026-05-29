//https://www.hackerrank.com/challenges/acm-icpc-team/problem?isFullScreen=true
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
     * Complete the 'acmTeam' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts STRING_ARRAY topic as parameter.
     */

    public static List<Integer> acmTeam(List<String> topic) {
    // Write your code here
        // Write your code here
        int max = 0;
        int count = 0;
        int n = topic.size();
        
        for (int i = 0; i < n; i++) {
            int m = topic.get(i).length();
            
            // Compare attendee 'i' with every subsequent attendee 'j'
            for (int j = i + 1; j < n; j++) {
                int currentTeamTopics = 0;
                
                // Count how many topics this team knows collectively
                for (int k = 0; k < m; k++) {
                    if (topic.get(i).charAt(k) == '1' || topic.get(j).charAt(k) == '1') {
                        currentTeamTopics++;
                    }
                }
                
                // If we find a new maximum, update max and reset team count to 1
                if (currentTeamTopics > max) {
                    max = currentTeamTopics;
                    count = 1;
                } 
                // If it matches the current maximum, increment the team count
                else if (currentTeamTopics == max) {
                    count++;
                }
            }
        }
        
        // Return the result as a list containing [max_topics, number_of_teams]
        List<Integer> result = new ArrayList<>();
        result.add(max);
        result.add(count);
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<String> topic = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        List<Integer> result = Result.acmTeam(topic);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
