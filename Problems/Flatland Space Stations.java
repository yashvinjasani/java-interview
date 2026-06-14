//https://www.hackerrank.com/challenges/flatland-space-stations/problem?isFullScreen=true
import java.util.Arrays;

static int flatlandSpaceStations(int n, int[] c) {
    // 1. Sort the space stations to easily find gaps
    Arrays.sort(c);
    
    // 2. Calculate the distance at the edges
    int maxDistance = Math.max(c[0], (n - 1) - c[c.length - 1]);
    
    // 3. Calculate the distance between consecutive stations
    for (int i = 1; i < c.length; i++) {
        int distance = (c[i] - c[i - 1]) / 2;
        maxDistance = Math.max(maxDistance, distance);
    }
    
    return maxDistance;
}
