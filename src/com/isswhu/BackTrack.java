package com.isswhu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BackTrack {
    // 40. 组合总和 II
    ArrayList<List<Integer>> re;
    ArrayList<Integer> tmp;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        re = new ArrayList<>();
        tmp = new ArrayList<>();
        Arrays.sort(candidates);
        btrack(candidates, 0, target);
        return re;
    }

    void btrack(int[] nums, int s, int target) {
        if (target == 0) {
            re.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = s; i < nums.length; i++) {
            if (nums[i] > target)
                break;
            if ((i > s && nums[i] == nums[i-1]))
                continue;
            tmp.add(nums[i]);
            btrack(nums, i+1, target-nums[i]);
            tmp.remove(tmp.size()-1);
        }
    }

    // 47. 全排列 II
    ArrayList<List<Integer>> res;
    LinkedList<Integer> temp;
    boolean[] visited;
    public List<List<Integer>> permuteUnique(int[] nums) {
        res = new ArrayList<>();
        if (nums.length == 0)
            return res;
        temp = new LinkedList<>();
        visited = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack47(nums, 0);
        return res;
    }

    void backtrack47(int[] nums, int t) {
        if (t == nums.length) {
            res.add(new LinkedList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] || i > 0 && nums[i] == nums[i-1] && !visited[i-1])
                continue;
            visited[i] = true;
            temp.add(nums[i]);
            backtrack47(nums, t+1);
            temp.removeLast();
            visited[i] = false;
        }
    }

    // 51. N 皇后
    ArrayList<List<String>> result;
    ArrayList<StringBuilder> list;
    boolean[] cols;
    boolean[] iplusj;
    boolean[] iminusj;
    public List<List<String>> solveNQueens(int n) {
        result = new ArrayList<>();
        list = new ArrayList<>();
        cols = new boolean[n];
        iplusj = new boolean[2*n-1];
        iminusj = new boolean[2*n-1];
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++)
                sb.append('.');
            list.add(sb);
        }
        backtrack51(0, n, -1);
        return result;
    }

    void backtrack51(int t, int n, int q) {
        if (t == n) {
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i < n; i++)
                l.add(list.get(i).toString());
            result.add(l);
            return;
        }

        int i = q+1;
        for (int j = 0; j < n; j++) {
            if (cols[j] || iplusj[i+j] || iminusj[i-j+n-1])
                continue;
            list.get(i).replace(j, j+1, "Q");
            cols[j] = true;
            iplusj[i+j] = true;
            iminusj[i-j+n-1] = true;
            backtrack51(t+1, n, i);
            list.get(i).replace(j, j+1, ".");
            cols[j] = false;
            iplusj[i+j] = false;
            iminusj[i-j+n-1] = false;
        }
    }

    // 52. N皇后 II
    int sum;
    public int totalNQueens(int n) {
        sum = 0;
        boolean[] cols = new boolean[n], iplusj = new boolean[2*n+1], iminusj = new boolean[2*n+1];
        bktract52(cols, iplusj, iminusj, 0, n, -1);
        return sum;
    }

    void bktract52(boolean[] cols, boolean[] iplusj, boolean[] iminusj,
                   int t, int n, int row) {
        if (t == n) {
            sum++;
            return;
        }
        int i = row+1;
        for (int j = 0; j < n; j++) {
            if (cols[j] || iplusj[i+j] || iminusj[i-j+n-1])
                continue;
            cols[j] = true;
            iplusj[i+j] = true;
            iminusj[i-j+n-1] = true;
            bktract52(cols, iplusj, iminusj, t+1, n, i);
            cols[j] = false;
            iplusj[i+j] = false;
            iminusj[i-j+n-1] = false;
        }
    }

    // 60. 排列序列
    public String getPermutation(int n, int k) {
        int sum = 1;
        int[] nums = new int[n];
        for (int i = 1; i <= n-1; i++) {
            sum *= i;
            nums[i] = sum;
        }
        boolean[] visited = new boolean[n+1];
        StringBuilder re = new StringBuilder();
        backtrack60(re, nums, visited, n, k-1);
        return re.toString();
    }

    void backtrack60(StringBuilder re, int[] nums, boolean[] visited, int n, int k) {
        if (n == 1) {
            for (int i = 1; i < visited.length; i++) {
                if (!visited[i]) {
                    re.append(i);
                    return;
                }
            }
        }

        int ind = k / nums[n-1], t = -1;
        for (int i = 1; i < visited.length; i++) {
            if (!visited[i]) {
                if (++t == ind) {
                    visited[i] = true;
                    re.append(i);
                    backtrack60(re, nums, visited, n-1, k % nums[n-1]);
                    break;
                }
            }
        }
    }

    // 77. 组合
    ArrayList<List<Integer>> re77;
    ArrayList<Integer> tmp77;
    public List<List<Integer>> combine(int n, int k) {
        re77 = new ArrayList<>();
        tmp77 = new ArrayList<>();
        backtrack77(1, n, 0, k);
        return re77;
    }

    void backtrack77(int start, int n, int cur, int k) {
        if (cur == k) {
            re77.add(new ArrayList<>(tmp77));
            return;
        }

        for (int i = start; i <= n-k+cur+1; i++) {
            tmp77.add(i);
            backtrack77(i+1, n, cur+1, k);
            tmp77.remove(tmp77.size()-1);
        }
    }

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
