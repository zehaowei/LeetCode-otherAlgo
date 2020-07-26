package com.isswhu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BackTrack {
    // 131 Palindrome Partitioning
    public static List<List<String>> partition(String s) {
        if (s.length() == 0)
            return new ArrayList<>();
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        for (int i = 0; i < s.length()-1; i++) {
            if (s.charAt(i) == s.charAt(i+1))
                dp[i][i+1] = true;
        }
        for (int k = 2; k <= s.length()-1; k++) {
            for (int i = 0; i <= s.length()-1-k; i++) {
                dp[i][i+k] = dp[i+1][i+k-1] && s.charAt(i) == s.charAt(i+k);
            }
        }
        List<List<String>> results = new LinkedList<>();
        LinkedList<String> list = new LinkedList<>();
        backTrack(s, 0, s.length()-1, results, list, dp);
        return results;
    }

    static void backTrack(String s, int begin, int end,
                   List<List<String>> results, List<String> curList, boolean[][] dp) {
        if (begin > end) {
            results.add(new LinkedList<>(curList));
            return;
        }
        for (int i = begin; i <= end; i++) {
            if (dp[begin][i]) {
                curList.add(s.substring(begin, i+1));
                backTrack(s, i+1, end, results, curList, dp);
                curList.remove(curList.size()-1);
            }
        }
    }
}
