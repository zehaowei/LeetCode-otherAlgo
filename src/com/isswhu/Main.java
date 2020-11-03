package com.isswhu;

public class Main {

    public static void main(String[] args) {
        System.out.println(movingCount(1,2,1));
    }

    public static int movingCount(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        return dfs(0, 0, m, n, k, visited);
    }

    static int dfs(int a, int b, int m, int n, int k, boolean[][] visited) {
        if (a < 0 || a >= m || b < 0 || b >= n || !isValid(a,b,k) || visited[a][b])
            return 0;
        visited[a][b] = true;
        int sum = 1;
        sum += dfs(a-1, b, m, n, k, visited);
        sum += dfs(a+1, b, m, n, k, visited);
        sum += dfs(a, b-1, m, n, k, visited);
        sum += dfs(a, b+1, m, n, k, visited);
        return sum;
    }

    static boolean isValid(int a, int b, int k) {
        int sum = 0;
        while (a > 0) {
            sum += a%10;
            a = a/10;
        }
        while (b > 0) {
            sum += b%10;
            b = b/10;
        }
        return sum <= k;
    }
}
