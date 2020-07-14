package com.isswhu;

public class Main {
    static int[] dp;

    public static void main(String[] args) {
        solution();
        for (int i = 0; i < 12; i++)
            System.out.println(dp[i]);
    }

    public static void solution() {
        dp = new int[1690];
        dp[0] = 1;
        int p2 = 0, p3 = 0, p5 = 0;
        for (int i = 1; i < 1690; i++) {
            int re2 = dp[p2]*2, re3 = dp[p3]*3, re5 = dp[p5]*5;
            int minnum = Math.min(re2, Math.min(re3, re5));
            if (re2 == minnum)
                p2++;
            else if (re3 == minnum)
                p3++;
            else
                p5++;
            if (minnum > dp[i-1])
                dp[i] = minnum;
            else
                i--;
        }
    }
    public static int nthUglyNumber(int n) {
        return dp[n-1];
    }
}
