package com.isswhu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DP {

    // 91 decode ways
    public int numDecodings(String s) {
        char[] chars = s.toCharArray();
        int[] dp = new int[chars.length];
        if (chars[0] > '0')
            dp[0] = 1;
        else
            return 0;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] > '0')
                dp[i] = dp[i-1];
            boolean yes = false;
            if (chars[i-1] == '1' || (chars[i-1] == '2'&& chars[i] <= '6'))
                yes = true;
            if (i-2 >= 0 && chars[i-1] > '0' && yes) {
                dp[i] += dp[i-2];
            } else if (i-2 == -1 && yes) {
                dp[i] += 1;
            }
        }
        return dp[chars.length-1];
    }

    // 95 unique binary search trees 2
    static class Solution {
        static class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode() {}
            TreeNode(int val) { this.val = val; }
            TreeNode(int val, TreeNode left, TreeNode right) {
                this.val = val;
                this.left = left;
                this.right = right;
            }
        }
        public List<TreeNode> generateTrees(int n) {
            if (n == 0)
                return new LinkedList<>();
            else {
                List<TreeNode>[][] dp = new List[n+1][n+1];
                return produce(1, n, dp);
            }
        }
        public List<TreeNode> produce(int i, int j, List<TreeNode>[][] dp) {
            if (i > j) {
                return null;
            }
            if (dp[i][j] != null)
                return dp[i][j];
            if (i == j) {
                TreeNode node = new TreeNode(i);
                List<TreeNode> l = new LinkedList<>();
                l.add(node);
                dp[i][j] = l;
                return l;
            }
            List<TreeNode> list = new LinkedList<>();
            for (int k = i; k <= j; k++) {
                List<TreeNode> left = produce(i, k-1, dp);
                List<TreeNode> right = produce(k+1, j, dp);
                if (left == null) {
                    for (TreeNode nd : right) {
                        TreeNode n = new TreeNode(k, null, nd);
                        list.add(n);
                    }
                } else if (right == null) {
                    for (TreeNode nd : left) {
                        TreeNode n = new TreeNode(k, nd, null);
                        list.add(n);
                    }
                } else {
                    for (TreeNode nd_left : left) {
                        for (TreeNode nd_right : right) {
                            TreeNode n = new TreeNode(k, nd_left, nd_right);
                            list.add(n);
                        }
                    }
                }
            }
            dp[i][j] = list;
            return list;
        }
    }

    // 120 triangle
    public int minimumTotal(List<List<Integer>> triangle) {
        int m = triangle.size();
        if (m == 0)
            return 0;
        int n = triangle.get(m-1).size();
        int[][] dp = new int[m+1][n+1];
        for (int i = 1; i < m+1; i++) {
            for (int j = 1; j < n+1; j++) {
                dp[i][j] = -1;
            }
        }
        return dp(1, 1, triangle, dp, m);
    }

    public int dp(int i, int j, List<List<Integer>> triangle, int[][] dp, int last) {
        if (dp[i][j] != -1)
            return dp[i][j];
        if (i == last)
            return triangle.get(i-1).get(j-1);
        dp[i][j] = Math.min(dp(i+1, j, triangle, dp, last), dp(i+1, j+1, triangle, dp, last));
        dp[i][j] += triangle.get(i-1).get(j-1);
        return dp[i][j];
    }

    // 139 word break
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()];
        HashSet<String> hs = new HashSet<>(wordDict);
        int maxlen = 0;
        for (String str : wordDict) {
            if (str.length() > maxlen)
                maxlen = str.length();
        }
        String s1 = s.substring(0,1);
        dp[0] = hs.contains(s1);
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < maxlen; j++) {
                if (i - j < 0)
                    break;
                if (hs.contains(s.substring(i-j, i+1))) {
                    if (i - j == 0) {
                        dp[i] = true;
                        break;
                    } else if (dp[i-j-1]){
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        return dp[s.length()-1];
    }

    // 152 maximum product subarray
    public int maxProduct(int[] nums) {
        int[] dpmax = new int[nums.length];
        int[] dpmin = new int[nums.length];
        dpmax[0] = nums[0];
        dpmin[0] = nums[0];
        int maxnum = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                dpmax[i] = 0;
                dpmin[i] = 0;
                continue;
            } else if (nums[i] > 0) {
                if (dpmax[i-1] > 0) {
                    dpmax[i] = dpmax[i-1] * nums[i];
                } else {
                    dpmax[i] = nums[i];
                }
                dpmin[i] = dpmin[i-1] * nums[i];
            } else {
                if (dpmin[i-1] <= 0) {
                    dpmax[i] = dpmin[i-1] * nums[i];
                } else {
                    dpmax[i] = nums[i];
                }
                dpmin[i] = dpmax[i-1] * nums[i];
            }
            if (dpmax[i] > maxnum)
                maxnum = dpmax[i];
        }
        return maxnum;
    }

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
