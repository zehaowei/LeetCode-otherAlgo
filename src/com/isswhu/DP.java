package com.isswhu;

public class DP {
    // 221 Maximal Square
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return 0;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxnum = 0;
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == '1') {
                dp[0][j] = 1;
                maxnum = 1;
            } else
                dp[0][j] = 0;
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == '1') {
                dp[i][0] = 1;
                maxnum = 1;
            } else
                dp[i][0] = 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(matrix[i][j] == '1')
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                else
                    dp[i][j] = 0;
                if (dp[i][j] > maxnum)
                    maxnum = dp[i][j];
            }
        }
        return maxnum*maxnum;
    }

    // 303 Range Sum Query - Immutable
    static class NumArray {
        int[] sums;
        public NumArray(int[] nums) {
            sums = new int[nums.length+1];
            sums[0] = 0;
            int sum = 0;
            for(int i = 0; i < nums.length; i++) {
                sum += nums[i];
                sums[i+1] = sum;
            }
        }

        public int sumRange(int i, int j) {
            return sums[j+1] - sums[i];
        }
    }

    // 392 Is Subsequence
    public boolean isSubsequence(String s, String t) {
        int ind1 = 0, ind2 = 0;
        while(ind1 < s.length() && ind2 < t.length()) {
            if(t.charAt(ind2) == s.charAt(ind1)) {
                ind1++;
            }
            ind2++;
        }
        return ind1 >= s.length();
    }
}
