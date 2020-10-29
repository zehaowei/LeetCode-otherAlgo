package com.isswhu;

public class BinarySearch {

    // 29. Divide Two Integers
    public int divide(int dividend, int divisor) {
        if (divisor == 1)
            return dividend;
        else if (divisor == -1) {
            if (dividend == Integer.MIN_VALUE)
                return Integer.MAX_VALUE;
            else
                return -dividend;
        } else if (dividend == 0)
            return 0;

        int flag = 1;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) {
            flag = -1;
        }

        if (dividend > 0)
            dividend = -dividend;
        if (divisor > 0)
            divisor = -divisor;

        int re = helper(dividend, divisor);
        if (flag == -1)
            return -re;
        else
            return re;
    }

    // dividend and divisor are negative
    int helper(int dividend, int divisor) {
        if (dividend > divisor)
            return 0;
        int temp = 1;
        int old = divisor;
        while (divisor+divisor < 0 && dividend <= divisor+divisor) {
            temp += temp;
            divisor += divisor;
        }

        return temp+helper(dividend-divisor, old);
    }

    // 74. Search a 2D Matrix
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0)
            return false;
        int n = matrix[0].length;
        if (n == 0)
            return false;

        int l = 0, h = m-1;
        while (l <= h) {
            int mid = l + (h-l)/2;
            if (matrix[mid][0] == target)
                return true;
            else if (matrix[mid][0] < target) {
                l = mid + 1;
            } else {
                h = mid - 1;
            }
        }
        if (h == -1)
            return false;

        int left = 1, right = n-1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[h][mid] == target)
                return true;
            else if (matrix[h][mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false;
    }


}
