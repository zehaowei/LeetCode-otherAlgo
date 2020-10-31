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

    // 81. Search in Rotated Sorted Array II
    public boolean search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0)
            return false;
        int cut = 0;
        for (; cut < n-1; cut++) {
            if (nums[cut] > nums[cut+1])
                break;
        }

        if (cut == n-1)
            return bsearch(nums, 0, cut, target);
        else
            return bsearch(nums, 0, cut, target) || bsearch(nums, cut+1, n-1, target);
    }

    boolean bsearch(int[] nums, int l, int h, int target) {
        while (l <= h) {
            int mid = l + (h-l)/2;
            if (nums[mid] == target)
                return true;
            else if (nums[mid] < target)
                l = mid + 1;
            else
                h = mid - 1;
        }
        return false;
    }

    public boolean search2(int[] nums, int target) {
        int n = nums.length;
        if (n == 0)
            return false;
        int start = 0, end = n-1;
        while (start <= end) {
            int mid = start + (end-start)/2;
            if (nums[mid] == target || nums[start] == target || nums[end] == target)
                return true;
            else if (nums[start] == nums[mid])
                start++;
            else if (nums[start] < nums[mid]) {
                if (nums[start] < target && target < nums[mid])
                    return bsearch(nums, start+1, mid-1, target);
                else
                    start = mid+1;
            } else {
                if (nums[mid] < target && target < nums[end])
                    return bsearch(nums, mid+1, end-1, target);
                else
                    end = mid-1;
            }
        }
        return false;
    }

    // 153. Find Minimum in Rotated Sorted Array
    public int findMin(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];
        if (nums[0] < nums[n-1])
            return nums[0];
        int s = 0, e = n-1;
        while (s < e) {
            int mid = s + (e-s)/2;
            if (nums[mid] > nums[mid+1])
                return nums[mid+1];
            if (nums[s] < nums[mid])
                s = mid+1;
            else
                e = mid;
        }
        return 0;
    }

    // 154. Find Minimum in Rotated Sorted Array II
    public int findMin2(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0];
        if (nums[0] < nums[n-1])
            return nums[0];
        int s = 0, e = n-1;
        while (s < e) {
            int mid = s + (e-s)/2;
            if (nums[mid] > nums[mid+1])
                return nums[mid+1];
            if (nums[s] < nums[mid])
                s = mid+1;
            else if (nums[s] > nums[mid])
                e = mid;
            else {
                if (nums[s] > nums[s+1])
                    return nums[s+1];
                s++;
            }
        }
        return nums[0];
    }

    // 162. Find Peak Element
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return 0;
        int l = 0, h = n-1;
        while (l <= h) {
            int mid = l + (h-l)/2;
            if ((mid-1 == -1 || nums[mid-1] < nums[mid]) && (mid+1 == n || nums[mid] > nums[mid+1]))
                return mid;
            else if (mid-1 != -1 && nums[mid-1] > nums[mid])
                h = mid-1;
            else
                l = mid+1;
        }
        return -1;
    }


}
