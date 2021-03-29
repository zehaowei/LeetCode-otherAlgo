package com.isswhu;

import java.util.*;

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

    void C () {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String s = in.nextLine();
            char[] dc = s.toCharArray();
            int re = 0, sum = 0, cnums = 0;
            for (int i = 0; i < dc.length; i++) {
                if (dc[i] == 'C')
                    cnums++;
                else
                    sum += cnums;
            }
            re = sum;
            cnums = 0;
            sum = 0;
            for (int i = dc.length-1; i >= 0; i--) {
                if (dc[i] == 'C')
                    cnums++;
                else
                    sum += cnums;
            }
            re = Math.min(re, sum);
            System.out.println(re);
        }
    }

    void D() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] ss = in.nextLine().split(" ");
            int[] nums = new int[ss.length];
            for (int i = 0; i < ss.length; i++)
                nums[i] = Integer.parseInt(ss[i]);
            int re = Integer.MIN_VALUE;
            int pre = 0, cur = 0;
            while (cur < nums.length) {
                if (nums[cur] < nums[pre])
                    pre = cur;
                else {
                    re = Math.max(re, nums[cur]-nums[pre]);
                }
                cur++;
            }
            System.out.println(re);
        }
    }

    void E() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] ss = in.nextLine().split(" ");
            int[] nums = new int[ss.length];
            for (int i = 0; i < ss.length; i++)
                nums[i] = Integer.parseInt(ss[i]);
            int h = Integer.parseInt(in.nextLine());
            int[][] dp = new int[nums.length][h+1];
            int re = get(nums, h, nums.length-1, dp);
            System.out.println(re);
        }
    }

    static int get(int[] nums, int h, int i, int[][] dp) {
        if (dp[i][h] != 0)
            return dp[i][h];
        if (i == 0) {
            int k = nums[i]/h;
            if (nums[i]%h != 0)
                k += 1;
            dp[i][h] = k;
            return k;
        }
        int re = Integer.MAX_VALUE;
        for (int t = h-1; t >= i; t--) {
            int times = h - t;
            int k = nums[i]/times;
            if (nums[i]%times != 0)
                k += 1;
            re = Math.min(re, Math.max(get(nums, t, i-1, dp), k));
        }
        dp[i][h] = re;
        return re;
    }

    void F() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] ss = in.nextLine().split(" ");
            int n = Integer.parseInt(ss[0]);
            int sum = 0;
            for (int i = 0; i < ss.length-1; i++) {
                int num = Integer.parseInt(ss[i+1]);
                sum += num;
            }
            int re = ((n+1)*n)/2 - sum;
            System.out.println(re);
        }
    }

    void G() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int x = in.nextInt();
            if (x == 0) {
                System.out.println(0);
                continue;
            } else if (x == 1 || x == 2) {
                System.out.println(x-1);
                continue;
            }
            int[] dp = new int[2*x+1];
        }
    }

    void G2() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();
            int re = 0;
            for (int i = 2; i <= n; i++) {
                boolean has = false;
                int num = i;
                while (num != 0) {
                    int r = num % 10;
                    num /= 10;
                    if (r == 2 || r == 5 || r == 6 || r == 9) {
                        has = true;
                    } else if (r != 0 && r != 1 && r != 8) {
                        has = false;
                        break;
                    }
                }
                if (has)
                    re++;
            }
            System.out.println(re);
        }
    }

    void G3() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();
            if (n == 0) {
                System.out.println(0);
                continue;
            }
            int[] dp = new int[n+1];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i-1]+dp[i-2];
            }
            System.out.println(dp[n]);
        }
    }

    void G4() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] ss = in.nextLine().split(" ");
            int[] nums1 = new int[ss.length];
            for (int i = 0; i < ss.length; i++)
                nums1[i] = Integer.parseInt(ss[i]);
            String[] ss2 = in.nextLine().split(" ");
            int[] nums2 = new int[ss2.length];
            for (int i = 0; i < ss2.length; i++)
                nums2[i] = Integer.parseInt(ss2[i]);
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            int p1 = 0, p2 = 0, re = 0;
            while (p1 < nums1.length && p2 < nums2.length) {
                if (nums1[p1] <= nums2[p2]) {
                    re++;
                    p1++;
                }
                p2++;
            }
            System.out.println(re);
        }
    }

    public int minCake (int n, int a, int b) {
        int anum = a/2, bnum = b/2;
        if (anum+bnum < n || a == 1 || b == 1)
            return 0;
        int re = -1;
        for (int i = 1; i <= n-1; i++) {
            int aa = a / i;
            int bb = b / (n-i);
            if (a % i == 0 && b % (n-i) == 0 && aa >= 2 && bb >= 2) {
                re = Math.min(aa, bb);
            }
        }
        if (re == -1)
            return 0;
        return re;
    }

    public int maxRealValue (int m, int[] sellPrice, int[] realValue) {
        int n = sellPrice.length;
        int[][] dp = new int[m+1][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }

        return help(dp, m, n-1, sellPrice, realValue);
    }

    int help(int[][] dp, int m, int n, int[] sellPrice, int[] realValue) {
        if (n == -1)
            return 0;
        if (dp[m][n] != -1)
            return dp[m][n];
        int re = help(dp, m, n-1, sellPrice, realValue);
        if (m-sellPrice[n] >= 0) {
            re = Math.max(re, realValue[n]+help(dp, m-sellPrice[n], n-1, sellPrice, realValue));
        }
        dp[m][n] = re;
         return dp[m][n];
    }
}
