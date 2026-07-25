public static int alternatingCharacters(String s) {
    int deletions = 0;
    
    // Start from the second character and compare with the previous one
    for (int i = 1; i < s.length(); i++) {
        if (s.charAt(i) == s.charAt(i - 1)) {
            deletions++;
        }
    }
    
    return deletions;
}
