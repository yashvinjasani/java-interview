//https://www.hackerrank.com/challenges/the-love-letter-mystery/problem?isFullScreen=true
public static int theLoveLetterMystery(String s) {
    int operations = 0;
    int n = s.length();
    
    // Loop through the first half of the string
    for (int i = 0; i < n / 2; i++) {
        // Find the symmetric characters
        char leftChar = s.charAt(i);
        char rightChar = s.charAt(n - 1 - i);
        
        // Add the absolute difference of their ASCII values to the total
        operations += Math.abs(leftChar - rightChar);
    }
    
    return operations;
}
