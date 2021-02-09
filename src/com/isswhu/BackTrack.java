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

    // 89. 格雷编码
    ArrayList<Integer> re89;
    int[] state;
    public List<Integer> grayCode(int n) {
        re89 = new ArrayList<>();
        if (n == 0) {
            re89.add(0);
            return re89;
        }

        state = new int[n];
        int tmp = 0;
        backtrace89(0, n, state, tmp);
        return re89;
    }

    void backtrace89(int t, int n, int[] state, int tmp) {
        if (t == n){
            re89.add(tmp);
            return;
        }
        int move = n-t-1;
        int base = 1 << move; // to 1 |
        if (state[t] == 0) {
            tmp = tmp & (~base);
            backtrace89(t+1, n, state, tmp);

            tmp = tmp | base;
            backtrace89(t+1, n, state, tmp);

            state[t] = 1;
        } else {
            tmp = tmp | base;
            backtrace89(t+1, n, state, tmp);

            tmp = tmp & (~base);
            backtrace89(t+1, n, state, tmp);

            state[t] = 0;
        }
    }

    // 90. 子集 II
    ArrayList<List<Integer>> re90;
    LinkedList<Integer> tmp90;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        re90 = new ArrayList<>();
        tmp90 = new LinkedList<>();
        re90.add(new LinkedList<>());
        Arrays.sort(nums);
        backtrace90(0, 0, nums);
        return re90;
    }

    void backtrace90(int t, int s, int[] nums) {
        if (t == nums.length) {
            return;
        }

        for (int i = s; i < nums.length; i++) {
            if (i > s && nums[i] == nums[i-1])
                continue;
            tmp90.add(nums[i]);
            re90.add(new LinkedList<>(tmp90));
            backtrace90(t+1,i+1, nums);
            tmp90.removeLast();
        }
    }

    // 93. 复原IP地址
    public List<String> restoreIpAddresses(String s) {
        ArrayList<String> re = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        backtrace93(0, 0, s, re, tmp);
        return re;
    }

    void backtrace93(int t, int s, String nums, ArrayList<String> re, StringBuilder tmp) {
        if (t == 4 ) {
            if (s == nums.length())
                re.add(tmp.toString());
            return;
        }

        int remain = nums.length() - s;
        if (s >= nums.length() || remain > (4-t)*3 || remain < (4-t))
            return;

        if (nums.charAt(s) == '0') {
            if (t != 0)
                tmp.append('.');
            tmp.append('0');
            backtrace93(t+1, s+1, nums, re, tmp);
            tmp.deleteCharAt(tmp.length()-1);
            if (t != 0)
                tmp.deleteCharAt(tmp.length()-1);
        } else {
            for (int len = 1; len <= 3 && s+len <= nums.length(); len++) {
                int num = Integer.parseInt(nums.substring(s, s+len));
                if (num >= 1 && num <= 255) {
                    if (t != 0)
                        tmp.append('.');
                    tmp.append(num);
                    backtrace93(t+1, s+len, nums, re, tmp);
                    tmp.delete(tmp.length()-len, tmp.length());
                    if (t != 0)
                        tmp.deleteCharAt(tmp.length()-1);
                }
            }
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

    // 216. 组合总和 III
    List<List<Integer>> re216 = new ArrayList<>();
    ArrayList<Integer> tmp216 = new ArrayList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrace216(0, k, 1, n);
        return re216;
    }

    void backtrace216(int t, int k, int s, int n) {
        if (n == 0) {
            if (t == k)
                re216.add(new ArrayList<>(tmp216));
            return;
        }

        for (int i = s; i <= 9; i++) {
            if (n-i < 0)
                return;
            tmp216.add(i);
            backtrace216(t+1, k, i+1, n-i);
            tmp216.remove(tmp216.size()-1);
        }
    }

    // 282. 给表达式添加运算符
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        back282("", 0, 0, 0,num, target, result);
        return result;
    }

    void back282(String path, int s, long val, long prev, String num, int target, List<String> result) {
        if (s >= num.length()) {
            if (target == val)
                result.add(path);
            return;
        }
        for (int i = 1; i <= num.length()-s; i++) {
            long number = Long.parseLong(num.substring(s, s+i));
            if (i != 1 && num.charAt(s) == '0') break;
            if (s == 0) {
                back282(path+number, s+i, number, number, num, target, result);
            } else {
                back282(path+"+"+number, s+i, val+number, number, num, target, result);
                back282(path+"-"+number, s+i, val-number, -number, num, target, result);
                back282(path+"*"+number, s+i, val-prev+prev*number, prev*number, num, target, result);
            }
        }
    }
}
