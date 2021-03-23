package com.isswhu;

import java.util.ArrayList;
import java.util.Scanner;

public class Niuke {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> re = new ArrayList<>();
        back(re, sb, n, n);
        for (int i = 0; i < re.size()-1; i++)
            System.out.print(re.get(i)+",");
        System.out.print(re.get(re.size()-1)+"\n");
    }

    static void back(ArrayList<String> re, StringBuilder sb, int left, int right) {
        if (left == right && left == 0) {
            re.add(sb.toString());
            return;
        }
        if (left != 0) {
            sb.append('(');
            back(re, sb, left-1, right);
            sb.delete(sb.length()-1, sb.length());
        }
        if (left < right) {
            sb.append(')');
            back(re, sb, left, right-1);
            sb.delete(sb.length()-1, sb.length());
        }
    }

    void A() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String s = in.nextLine();
            int len = s.length();
            boolean re = false;
            int ind = 0;
            for (int i = 1; i <= len/2; i++) {
                if (len % i != 0)
                    continue;
                String s1 = s.substring(0, i);
                int j = i;
                for (; j <= len-i; j += i) {
                    if (!s1.equals(s.substring(j, j+i))) {
                        break;
                    }
                }
                if (j == len) {
                    re = true;
                    ind = i;
                    break;
                }
            }
            if (re)
                System.out.println(s.substring(0, ind));
            else
                System.out.println("false");
        }
    }

    void B() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 1;
        System.out.println(help(dp, n));
    }

    static int help(int[] dp, int n) {
        if (dp[n] != 0)
            return dp[n];
        int re = 0;
        for (int i = 1; i <= n/2; i++) {
            int tmp = Math.max(help(dp, i), i) * Math.max(help(dp, n-i), n-i);
            re = Math.max(re, tmp);
        }
        dp[n] = re;
        return re;
    }
}
