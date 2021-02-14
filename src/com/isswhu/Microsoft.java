package com.isswhu;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Microsoft {
    // 316. 去除重复字母
    public String removeDuplicateLetters(String s) {

    }

    // 322. 零钱兑换
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        for (int i = 0; i < amount+1; i++)
            dp[i] = -2;
        Arrays.sort(coins);
        return getNum(coins, dp, amount);
    }

    int getNum(int[] coins, int[] dp, int target) {
        if (target < 0)
            return -1;
        else if (target == 0)
            return 0;
        if (dp[target] != -2)
            return dp[target];

        int re = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int num = getNum(coins, dp, target-coins[i]);
            if (num != -1 && num+1 < re)
                re = num+1;
        }
        if (re == Integer.MAX_VALUE)
            re = -1;
        dp[target] = re;
        return re;
    }

    // 344. 反转字符串
    public void reverseString(char[] s) {
        if (s.length == 0 || s.length == 1)
            return;
        for (int i = 0; i <= (s.length-1)/2; i++) {
            char tmp = s[i];
            s[i] = s[s.length-i-1];
            s[s.length-i-1] = tmp;
        }
    }

    // 402. 移掉K位数字
    public String removeKdigits(String num, int k) {
        Deque<Character> stk = new LinkedList<>();
        for(char c : num.toCharArray()) {
            while (!stk.isEmpty() && stk.peek() > c && k > 0) {
                stk.pop();
                k--;
            }
            if (!(stk.isEmpty() && c == '0'))
                stk.push(c);
        }
        while (k > 0 && !stk.isEmpty()) {
            stk.pop();
            k--;
        }
        StringBuilder re = new StringBuilder();
        while (!stk.isEmpty())
            re.append(stk.pollLast());
        if (re.length() == 0)
            re.append(0);
        return re.toString();
    }
}
