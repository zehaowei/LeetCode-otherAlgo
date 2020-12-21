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
