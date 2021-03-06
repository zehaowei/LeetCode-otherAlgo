package com.isswhu;

import java.util.*;

public class DP {

    // 10 regular expression matching
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for (int i = 1; i < s.length()+1; i++)
            dp[i][0] = false;
        for (int i = 0; i < s.length()+1; i++) {
            for (int j = 1; j < p.length()+1; j++) {
                 if (p.charAt(j-1) != '*'){
                    if (i == 0) {
                        dp[i][j] = false;
                    } else if (p.charAt(j-1) == '.'){
                        dp[i][j] = dp[i-1][j-1];
                    } else
                        dp[i][j] = dp[i-1][j-1] && s.charAt(i-1) == p.charAt(j-1);
                } else {
                    if (i == 0) {
                        dp[i][j] = dp[i][j-2];
                    } else {
                        dp[i][j] = dp[i][j-2] ||
                                (dp[i-1][j] &&
                                        (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.'));
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    // 32 longest valid parenthesis
    public int longestValidParentheses(String s) {
        if (s.length() == 0)
            return 0;
        int[] dp = new int[s.length()];
        dp[0] = 0;
        int maxnum = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                dp[i] = 0;
            else {
                int ind = i-1-dp[i-1];
                if (ind >= 0 && s.charAt(ind) == '(') {
                    dp[i] = dp[i-1] + 2;
                    if (ind-1 > 0)
                        dp[i] += dp[ind-1];
                } else {
                    dp[i] = 0;
                }
            }
            if (dp[i] > maxnum)
                maxnum = dp[i];
        }
        return maxnum;
    }

    // 44 wildcard matching
    public boolean isMatch44(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for (int i = 1; i < s.length()+1; i++)
            dp[i][0] = false;
        for (int i = 0; i < s.length()+1; i++) {
            for (int j = 1; j < p.length()+1; j++) {
                if (p.charAt(j-1) != '*') {
                    if (i == 0)
                        dp[i][j] = false;
                    else {
                        if (p.charAt(j-1) == '?') {
                            dp[i][j] = dp[i-1][j-1];
                        } else {
                            dp[i][j] = dp[i-1][j-1] && s.charAt(i-1) == p.charAt(j-1);
                        }
                    }
                } else {
                    if (i == 0)
                        dp[i][j] = dp[i][j-1];
                    else {
                        dp[i][j] = dp[i][j-1] || dp[i-1][j];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    // 72. Edit Distance
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        for (int i = 0; i < word1.length()+1; i++)
            dp[i][0] = i;
        for (int j = 1; j < word2.length()+1; j++)
            dp[0][j] = j;

        for (int i = 1; i < word1.length()+1; i++) {
            for (int j = 1; j < word2.length()+1; j++) {
                int step1 = dp[i][j-1]+1, step2 = dp[i-1][j]+1, step3 = dp[i-1][j-1];
                if (word1.charAt(i-1) != word2.charAt(j-1))
                    step3++;
                dp[i][j] = Math.min(step1, Math.min(step2, step3));
            }
        }
        return dp[word1.length()][word2.length()];
    }

    // 85. Maximal Rectangle
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int colnum = matrix[0].length;
        int[] height = new int[colnum];
        int[] left = new int[colnum];
        int[] right = new int[colnum];
        int maxarea = 0;
        for (int i = 0; i < matrix.length; i++) {
            int cur_zero = matrix[i][0] == '1' ? 0 : 1;
            for (int j = 0; j < colnum; j++) {
                if (matrix[i][j] == '1') {
                    if (height[j] == 0) {
                        left[j] = cur_zero;
                    } else {
                        left[j] = Math.max(left[j], cur_zero);
                    }
                } else {
                    cur_zero = j+1;
                }
            }
            int cur_right = matrix[i][colnum-1] == '1' ? colnum-1 : colnum-2;
            for (int j = colnum - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    if (height[j] == 0)
                        right[j] = cur_right;
                    else
                        right[j] = Math.min(right[j], cur_right);
                    height[j] += 1;
                } else {
                    cur_right = j-1;
                    height[j] = 0;
                }
            }
            for (int j = 0; j < colnum; j++) {
                int area = (right[j]-left[j]+1)*height[j];
                if (area > maxarea)
                    maxarea = area;
            }
        }
        return maxarea;
    }

    // 87 Scramble String
    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;
        if (s1.length() == 0)
            return true;
        boolean[][][] dp = new boolean[s1.length()][s1.length()][s1.length()+1];
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s1.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j))
                    dp[i][j][1] = true;
            }
        }
        for (int l = 2; l <= s1.length(); l++) {
            for (int i = 0; i <= s1.length()-l; i++) {
                for (int j = 0; j <= s1.length()-l; j++) {
                    boolean re = false;
                    for (int w = 1; w <= l-1; w++) {
                        if(dp[i][j][w] && dp[i+w][j+w][l-w]
                                || dp[i][j+l-w][w] && dp[i+w][j][l-w]) {
                            re = true;
                            break;
                        }
                    }
                    dp[i][j][l] = re;
                }
            }
        }
        return dp[0][0][s1.length()];
    }

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

    // 97 Interleaving String
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length()+s2.length() != s3.length())
            return false;
        boolean[][] dp = new boolean[s1.length()+1][s2.length()+1];
        dp[0][0] = true;
        for (int i = 1; i <= s1.length(); i++) {
            if(s1.substring(0,i).equals(s3.substring(0,i)))
                dp[i][0] = true;
        }
        for (int j = 1; j <= s2.length(); j++) {
            if(s2.substring(0,j).equals(s3.substring(0,j)))
                dp[0][j] = true;
        }
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                dp[i][j] = dp[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j+1)
                        || dp[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j+1);
            }
        }
        return dp[s1.length()][s2.length()];
    }

    // 115 Distinct Subsequences
    public int numDistinct(String s, String t) {
        int[][] dp = new int[s.length()+1][t.length()+1];
        for (int i = 0; i <= s.length(); i++)
            dp[i][0] = 1;
        for (int j = 1; j <= t.length(); j++)
            dp[0][j] = 0;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i-1) == t.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[s.length()][t.length()];
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

    // 121 Best Time to Buy and Sell Stock
    public int maxProfit1(int[] prices) {
        int max = 0;
        int i = 0, j = 1;
        while (j < prices.length) {
            int diff = prices[j] - prices[i];
            if (diff >= 0) {
                if (diff > max)
                    max = diff;
            } else {
                i = j;
            }
            j++;
        }
        return max;
    }

    // 123 Best Time to Buy and Sell Stock III
    public int maxProfit3(int[] prices) {
        if (prices.length == 0)
            return 0;
        int[] left = new int[prices.length];
        left[0] = 0;
        int max = 0, i = 0, j = 1;
        while (j < prices.length) {
            int dif = prices[j] - prices[i];
            if (dif >= 0) {
                if (dif > max)
                    max = dif;
            } else {
                i = j;
            }
            left[j] = max;
            j++;
        }

        int[] right = new int[prices.length];
        right[prices.length-1] = 0;
        max = 0;
        j = prices.length-1;
        i = j-1;
        while (i >= 0) {
            int dif = prices[j] - prices[i];
            if (dif >= 0) {
                if (dif > max)
                    max = dif;
            } else {
                j = i;
            }
            right[i] = max;
            i--;
        }

        int answer = Math.max(right[0], left[prices.length-1]);
        for (int k = 0; k < prices.length-1; k++) {
            int re = left[k] + right[k+1];
            if (re > answer)
                answer = re;
        }
        return answer;
    }

    // 132 Palindrome Partitioning II
    public int minCut(String s) {
        if (s.length() == 0)
            return 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        int[][] dp2 = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
            dp2[i][i] = 0;
        }
        for (int i = 0; i < s.length()-1; i++) {
            if (s.charAt(i) == s.charAt(i+1)) {
                dp[i][i+1] = true;
                dp2[i][i+1] = 0;
            } else {
                dp2[i][i+1] = 1;
            }
        }
        for (int k = 2; k <= s.length()-1; k++) {
            for (int i = 0; i <= s.length()-1-k; i++) {
                dp[i][i+k] = dp[i+1][i+k-1] && s.charAt(i) == s.charAt(i+k);
                int minCut = Integer.MAX_VALUE;
                if (dp[i][i+k])
                    minCut = 0;
                else {
                    for (int m = 0; m < k; m++) {
                        if (dp[i][i+m]) {
                            int re = 1 + dp2[i+m+1][i+k];
                            if (re < minCut)
                                minCut = re;
                        }
                    }
                }
                dp2[i][i+k] = minCut;
            }
        }
        return dp2[0][s.length()-1];
    }

    public int minCut2(String s) {
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

        int[] dp2 = new int[s.length()];
        dp2[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            if (dp[0][i]) {
                dp2[i] = 0;
            } else {
                int minCut = Integer.MAX_VALUE;
                for (int k = 0; k < i; k++) {
                    if (dp[k+1][i]) {
                        int re = dp2[k] + 1;
                        if (re < minCut)
                            minCut = re;
                    }
                }
                dp2[i] = minCut;
            }
        }
        return dp2[s.length()-1];
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

    // 140 Word Break II
    public List<String> wordBreak2(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        dp[s.length()] = true;
        HashSet<String> hset = new HashSet<>(wordDict);
        int end = s.length()-1;
        if (hset.contains(s.substring(end, end+1)))
            dp[end] = true;
        for (int i = end-1; i >= 0; i--) {
            for (int k = i; k <= end; k++) {
                if (hset.contains(s.substring(i,k+1))) {
                    if (k == end) {
                        dp[i] = true;
                        break;
                    } else if (dp[k+1]) {
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        List<String> results = new LinkedList<>();
        List<String> curList = new LinkedList<>();
        back(s, 0, end, hset, results, curList, dp);
        return results;
    }

    void back(String s, int begin, int end, HashSet<String> hset,
              List<String> results, List<String> curList, boolean[] dp) {
        if (begin > end) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < curList.size()-1; k++) {
                sb.append(curList.get(k));
                sb.append(" ");
            }
            sb.append(curList.get(curList.size()-1));
            results.add(sb.toString());
            return;
        }
        for (int i = begin; i <= end; i++) {
            if (hset.contains(s.substring(begin, i+1)) && dp[i+1]) {
                curList.add(s.substring(begin, i+1));
                back(s, i+1, end, hset, results, curList, dp);
                curList.remove(curList.size()-1);
            }
        }
    }

    // real dp method
    public List<String> wordBreak3(String s, List<String> wordDict) {
        if (s.length() == 0)
            return new LinkedList<>();
        ArrayList<LinkedList<String>> dp = new ArrayList<>(s.length());
        HashSet<String> hset = new HashSet<>(wordDict);
        for (int i = 0; i < s.length(); i++) {
            LinkedList<String> list = new LinkedList<>();
            if (hset.contains(s.substring(0,i+1)))
                list.add(s.substring(0,i+1));
            for (int k = 1; k <= i; k++) {
                if (dp.get(k-1).size() != 0
                        && hset.contains(s.substring(k,i+1))) {
                    for (String str : dp.get(k-1)) {
                        list.add(str + " " + s.substring(k, i + 1));
                    }
                }
            }
            dp.add(list);
        }
        return dp.get(s.length()-1);
    }

    // 152 maximum product subarray
    public int maxProduct(int[] nums) {
        int[] dpmax = new int[nums.length];
        int[] dpmin = new int[nums.length];
        dpmax[0] = nums[0];
        dpmin[0] = nums[0];
        int maxnum = dpmax[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                dpmax[i] = 0;
                dpmin[i] = 0;
            } else if (nums[i] > 0) {
                if (dpmax[i-1] > 0) {
                    dpmax[i] = dpmax[i-1] * nums[i];
                } else {
                    dpmax[i] = nums[i];
                }
                dpmin[i] = Math.min(dpmin[i-1] * nums[i], nums[i]);
            } else {
                if (dpmin[i-1] <= 0) {
                    dpmax[i] = dpmin[i-1] * nums[i];
                } else {
                    dpmax[i] = nums[i];
                }
                dpmin[i] = Math.min(dpmax[i-1] * nums[i], nums[i]);
            }
            if (dpmax[i] > maxnum)
                maxnum = dpmax[i];
        }
        return maxnum;
    }

    // 174 Dungeon Game
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        if (m == 0)
            return 1;
        int n = dungeon[0].length;
        int[][] dp = new int[m+1][n+1];
        dp[m-1][n] = 1;
        dp[m][n-1] = 1;
        for (int i = 0; i <= m-2; i++)
            dp[i][n] = Integer.MAX_VALUE;
        for (int j = 0; j <= n-2; j++)
            dp[m][j] = Integer.MAX_VALUE;
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                int require = Math.min(dp[i][j+1], dp[i+1][j]);
                if (require - dungeon[i][j] > 0)
                    dp[i][j] = require - dungeon[i][j];
                else
                    dp[i][j] = 1;
            }
        }
        return dp[0][0];
    }

    // 188. Best Time to Buy and Sell Stock IV
    public int maxProfit(int k, int[] prices) {
        int days = prices.length;
        if (days == 0 || days == 1 || k == 0)
            return 0;
        if (k > days/2) {
            int re = 0;
            for (int i = 0; i < days-1; i++) {
                if (prices[i+1] > prices[i])
                    re += prices[i+1] - prices[i];
            }
            return re;
        }
        int[][] dp = new int[k+1][2];
        for (int i = 0; i < days; i++) {
            dp[0][0] = 0;
            dp[0][1] = Integer.MIN_VALUE;
        }
        for (int j = 1; j <= k; j++) {
            dp[j][0] = 0;
            dp[j][1] = -prices[0];
        }

        for (int i = 1; i < days; i++) {
            int last = 0;  // dp[i-1][j-1][0]
            for (int j = 1; j <= k; j++) {
                int temp = dp[j][0]; // store the dp[i-1][j-1][0] that would be used next round
                dp[j][0] = Integer.max(dp[j][0], dp[j][1] + prices[i]);
                dp[j][1] = Integer.max(dp[j][1], last - prices[i]);
                last = temp;
            }
        }

        return dp[k][0];
    }

    // 198 house rob 1
    public int rob1(int[] nums) {
        if (nums.length == 0)
            return 0;
        else if (nums.length == 1)
            return nums[0];
        int i = 0, last = nums.length-1;
        int dp2 = nums[last];
        int dp1 = Math.max(nums[last], nums[last-1]);
        for (int j = last-2; j >= i; j--) {
            int newdp1 = Math.max(nums[j]+dp2, dp1);
            dp2 = dp1;
            dp1 = newdp1;
        }
        return dp1;
    }

    // 213 house robber 2
    public int rob(int[] nums) {
        if (nums.length == 0)
            return 0;
        else if (nums.length == 1)
            return nums[0];
        else if (nums.length == 2)
            return Math.max(nums[0], nums[1]);
        else {
            return Math.max(getMaxSequence(0, nums.length-2, nums),
                    getMaxSequence(1, nums.length-1, nums));
        }
    }

    public int getMaxSequence(int i, int last, int[] nums) {
        int dp2 = nums[last];
        int dp1 = Math.max(nums[last], nums[last-1]);
        for (int j = last-2; j >= i; j--) {
            int newdp1 = Math.max(nums[j]+dp2, dp1);
            dp2 = dp1;
            dp1 = newdp1;
        }
        return dp1;
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

    // 264 ugly number
    static class Solution264 {
        int[] dp;
        Solution264() {
            dp = new int[1690];
            dp[0] = 1;
            int p2 = 0, p3 = 0, p5 = 0;
            for (int i = 1; i < 1690; i++) {
                int re2 = dp[p2]*2, re3 = dp[p3]*3, re5 = dp[p5]*5;
                int minnum = Math.min(re2, Math.min(re3, re5));
                if (re2 == minnum)
                    p2++;
                if (re3 == minnum)
                    p3++;
                if (re5 == minnum)
                    p5++;
                dp[i] = minnum;
            }
        }
        public int nthUglyNumber(int n) {
            return dp[n-1];
        }
    }

    // 279 perfect squares
    static class Solution279 {
        public int numSquares(int n) {
            int[] dp = new int[n+1];
            dp[0] = 0;
            dp[1] = 1;
            for (int i = 2; i < n+1; i++)
                dp[i] = -1;
            return dp(n, dp);
        }

        public int dp(int n, int[] dp) {
            if (dp[n] != -1)
                return dp[n];
            int minnum = Integer.MAX_VALUE;
            for (int i = 1; i*i <= n; i++) {
                int num = dp(n-i*i, dp) + 1;
                if (num < minnum)
                    minnum = num;
            }
            dp[n] = minnum;
            return minnum;
        }

        public int numSquares2(int n) {
            int[] dp = new int[n+1];
            dp[0] = 0;
            for (int i = 1; i <= n; i++) {
                dp[i] = i;
                for (int j = 1; j*j <= i; j++) {
                    dp[i] = Math.min(dp[i], dp[i-j*j]+1);
                }
            }
            return dp[n];
        }
    }

    // 300 longest increasing subsequence
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0)
            return 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int maxnum = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j <= i-1; j++) {
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j]+1);
            }
            if (dp[i] > maxnum)
                maxnum = dp[i];
        }
        return maxnum;
    }

    public int lengthOfLIS2(int[] nums) {
        if (nums.length == 0)
            return 0;
        int[] dp = new int[nums.length+1];
        dp[0] = Integer.MIN_VALUE;
        dp[1] = nums[0];
        int len = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > dp[len]) {
                len++;
                dp[len] = nums[i];
            } else {
                int index = bsearch(nums[i], dp, 1, len);
                if (dp[index] != nums[i])
                    dp[index+1] = nums[i];
            }
        }
        return len;
    }

    public int bsearch(int target, int[] nums, int l, int h) {
        while (l <= h) {
            int mid = (h-l)/2 + l;
            if (target == nums[mid])
                return mid;
            else if (target < nums[mid])
                h = mid-1;
            else
                l = mid+1;
        }
        return h;
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

    // 516. 最长回文子序列
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int l = 0; l < n; l++) {
            for (int i = 0; i+l < n; i++) {
                int j = i+l;
                if (i == j)
                    dp[i][j] = 1;
                else if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = i+1 > j-1 ? 2 : dp[i+1][j-1]+2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        return dp[0][n-1];
    }

    // 647. 回文子串
    public int countSubstrings(String s) {
        int nums = 0, n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int l = 0; l < n; l++) {
            for (int i = 0; i+l < n; i++) {
                int j = i+l;
                if (i == j) {
                    dp[i][j] = true;
                    nums++;
                } else if (s.charAt(i) == s.charAt(j) && (i+1 > j-1 || dp[i+1][j-1])) {
                    dp[i][j] = true;
                    nums++;
                }
            }
        }
        return nums;
    }

    // 1143. 最长公共子序列
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        if (n < 1 || m < 1)
            return 0;
        int[][] dp = new int[n][m];
        if (text1.charAt(0) == text2.charAt(0))
            dp[0][0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i][0] = text1.charAt(i) == text2.charAt(0) ? 1 : dp[i-1][0];
        }
        for (int j = 1; j < m; j++) {
            dp[0][j] = text1.charAt(0) == text2.charAt(j) ? 1 : dp[0][j-1];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (text1.charAt(i) == text2.charAt(j))
                    dp[i][j] = dp[i-1][j-1] + 1;
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
        return dp[n-1][m-1];
    }
}
