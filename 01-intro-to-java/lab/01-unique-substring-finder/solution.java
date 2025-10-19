package bg.sofia.uni.fmi.mjt.show;

import java.util.Scanner;

public class UniqueSubstringFinder {
    public static String longestUniqueSubstring(String s) {
        // only small latin letter (from problem description)
        int [] lastSeen = new int [26]; // imitating hash set
        Arrays.fill(lastSeen, -1);
        int start = 0, maxLen = 0, maxStart = 0, n = s.length();

        for(int i = 0; i < n; i++) {
            int index = s.charAt(i) - 'a';
            if(lastSeen[index] >= start) {
                start = lastSeen[index] + 1;
            }

            lastSeen[index] = i;

            int currLen = i - start + 1;
            if(currLen > maxLen) {
                maxLen = currLen;
                maxStart = start;
            }
        }

        return s.substring(maxStart, maxStart + maxLen);
    }
}

void main() {
    Scanner inputReader = new Scanner(System.in);
    String input = inputReader.nextLine();
    System.out.println(UniqueSubstringFinder.longestUniqueSubstring(input));
}