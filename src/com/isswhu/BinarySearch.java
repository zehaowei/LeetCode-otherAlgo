package com.isswhu;

import java.util.HashMap;

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

    // 167. 两数之和 II - 输入有序数组
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (hmap.get(target-numbers[i]) == null) {
                hmap.put(numbers[i], i+1);
            } else {
                return new int[]{hmap.get(target-numbers[i]), i+1};
            }
        }
        return null;
    }

    public int[] twoSum2(int[] numbers, int target) {
        int l = 0, h = numbers.length-1;
        while (l < h) {
            if (numbers[l] + numbers[h] == target) {
                return new int[]{l+1, h+1};
            } else if (numbers[l] + numbers[h] < target) {
                l++;
            } else {
                h--;
            }
        }
        return null;
    }

    // 209. 长度最小的子数组
    public int minSubArrayLen(int s, int[] nums) {
        if (nums.length == 0)
            return 0;
        int re = Integer.MAX_VALUE, sum = nums[0];
        int l = 0, r = 0;
        while (l <= r) {
            if (sum >= s) {
                if (r-l+1 < re)
                    re = r-l+1;
                sum -= nums[l++];
            } else {
                r++;
                if (r < nums.length)
                    sum += nums[r];
                else
                    break;
            }
        }
        if (re == Integer.MAX_VALUE)
            return 0;
        else
            return re;
    }

    // 222. 完全二叉树的节点个数
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int x) { val = x; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        int depth = getDepth(root);
        return getNodes(root, depth);
    }

    int getNodes(TreeNode root, int depth) {
        if (root == null)
            return 0;
        else if (depth == 1)
            return 1;
        else if (root.right == null) // root.right == null -> depth = 2
            return root.left == null ? 1 : 2;
        else if (depth == 2)
            return 3;
        TreeNode right = root.right, left = right.left; // when depth == 3, left could be null, depth > 3, left must be non-null
        boolean exist = true;
        for (int i = 0; i < depth-3; i++) {
            left = left.left;
        }
        if (left == null)
            exist = false;

        if (exist) {
            return (int)Math.pow(2, depth-1) + getNodes(right, depth-1);
        } else {
            return getNodes(root.left, depth-1) + (int)Math.pow(2, depth-2);
        }
    }

    int getDepth(TreeNode root) {
        if (root == null)
            return 0;
        return 1+getDepth(root.left);
    }

    // 230. 二叉搜索树中第K小的元素
    int count;
    int ans;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return ans;
    }

    void inorder(TreeNode root, int k) {
        if (root == null || count == k)
            return;
        inorder(root.left, k);
        if (count == k - 1)
            ans = root.val;
        count++;
        inorder(root.right, k);
    }

    // 240. 搜索二维矩阵 II
    public boolean searchMatrix2(int[][] matrix, int target) {
        int n = matrix.length;
        if (n == 0)
            return false;
        int m = matrix[0].length;
        if (m == 0)
            return false;

        int i = n-1, j = 0;
        while (i >= 0 && j <= m-1) {
            if (target < matrix[i][j]) {
                int l = 0, h = i-1;
                while (l <= h) {
                    int mid = l + (h-l)/2;
                    if (matrix[mid][j] < target)
                        l = mid+1;
                    else if (matrix[mid][j] > target)
                        h = mid-1;
                    else
                        return true;
                }
                i = h;
            }
            else if (target > matrix[i][j]) {
                int l = j, h = m-1;
                while (l <= h) {
                    int mid = l + (h-l)/2;
                    if (matrix[i][mid] < target)
                        l = mid+1;
                    else if (matrix[i][mid] > target)
                        h = mid-1;
                    else
                        return true;
                }
                j = h+1;
            }
            else
                return true;
        }
        return false;
    }

    // 275. H 指数 II
    public int hIndex(int[] citations) {
        if (citations.length == 0)
            return 0;
        int l = 0, h = citations.length-1;
        while (l <= h) {
            int mid = l + (h-l)/2;
            int nums = citations.length - mid;
            if (citations[mid] == nums)
                return citations[mid];
            else if (citations[mid] > nums)
                 h = mid - 1;
            else
                l = mid + 1;
        }
        return citations.length - l;
    }

    // 278. 第一个错误的版本
    boolean isBadVersion(int version) {return true;}

    public int firstBadVersion(int n) {
        int l = 1, h = n;
        while (l <= h) {
            int mid = l + (h-l)/2;
            if (isBadVersion(mid))
                h = mid - 1;
            else
                l = mid + 1;
        }
        return l;
    }

    // 287. 寻找重复数
    public int findDuplicate(int[] nums) {
        int n = nums.length-1;
        int l = 1, h = n, times = 0;
        while (l < h) {
            int mid = l + (h-l)/2;
            times = 0;
            for (int num : nums) {
                if (num <= mid)
                    times++;
            }
            if (times <= mid)
                l = mid+1;
            else
                h = mid;
        }
        return h;
    }
}
