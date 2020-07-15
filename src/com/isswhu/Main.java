package com.isswhu;

public class Main {
    static int[] dp;

    public static void main(String[] args) {
        String s = "adceb", p = "*a*b";
        solution(s, p);
    }

    public static void solution(String s, String p) {
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
                        dp[i][j] = dp[i-1][j];
                    }
                }
            }
        }

        for (int i = 0; i < s.length()+1; i++) {
            for (int j = 0; j < p.length()+1; j++)
                System.out.print(dp[i][j]+" ");
            System.out.println();
        }
    }
}
