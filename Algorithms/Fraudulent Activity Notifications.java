//https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem?isFullScreen=true
class Result {

    // Helper function to find 2x the median to avoid float precision issues
    private static int getDoubleMedian(int[] freq, int d) {
        int count = 0;
        int first = -1;
        int second = -1;
        
        for (int i = 0; i < 201; i++) {
            count += freq[i];
            
            // If d is even, we need the sum of the two middle elements
            if (d % 2 == 0) {
                if (first == -1 && count >= d / 2) {
                    first = i;
                }
                if (second == -1 && count >= d / 2 + 1) {
                    second = i;
                    break;
                }
            } 
            // If d is odd, the median is just the exact middle element
            else {
                if (count >= d / 2 + 1) {
                    first = i;
                    second = i;
                    break;
                }
            }
        }
        // Returning the sum of the two middle values acts as 2 * median
        return first + second;
    }

    /*
     * Complete the 'activityNotifications' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY expenditure
     *  2. INTEGER d
     */
    public static int activityNotifications(List<Integer> expenditure, int d) {
        int notifications = 0;
        // Since expenditure values are exactly between 0 and 200 based on constraints
        int[] freq = new int[201]; 
        
        // Initialize frequency array with the first 'd' days
        for (int i = 0; i < d; i++) {
            freq[expenditure.get(i)]++;
        }
        
        // Start sliding the window
        for (int i = d; i < expenditure.size(); i++) {
            int currentExpense = expenditure.get(i);
            int oldestExpense = expenditure.get(i - d);
            
            // Get 2x median to compare against (since we need currentExpense >= 2 * median)
            int medianX2 = getDoubleMedian(freq, d);
            
            if (currentExpense >= medianX2) {
                notifications++;
            }
            
            // Slide window forward: add current day, remove oldest day in window
            freq[currentExpense]++;
            freq[oldestExpense]--;
        }
        
        return notifications;
    }
}
