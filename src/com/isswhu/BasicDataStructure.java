package com.isswhu;

import java.lang.reflect.Array;
import java.util.*;

public class BasicDataStructure {
    // 15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        LinkedList<List<Integer>> re = new LinkedList<>();
        if (nums.length < 3)
            return re;
        Arrays.sort(nums);
        for (int p1 = 0; p1 <= nums.length-3; p1++) {
            if (nums[p1] > 0)
                break;
            if (p1-1 >= 0 && nums[p1-1] == nums[p1])
                continue;
            int target = -nums[p1];
            int l = p1+1, r = nums.length-1;
            while (l < r) {
                if (nums[l] + nums[r] == target) {
                    LinkedList<Integer> lst = new LinkedList<>();
                    lst.add(nums[p1]);
                    lst.add(nums[l]);
                    lst.add(nums[r]);
                    re.add(lst);
                    l++;
                    r--;
                } else if (nums[l] + nums[r] > target) {
                    r--;
                } else {
                    l++;
                }

                while (l > p1 + 1 && l < r && nums[l] == nums[l - 1])
                    l++;
                while (r < nums.length - 1 && l < r && nums[r] == nums[r + 1])
                    r--;
            }
        }
        return re;
    }

    // 18. 四数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {
        LinkedList<List<Integer>> re = new LinkedList<>();
        if (nums.length < 4)
            return re;
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length-4; i++) {
            if (i > 0 && nums[i] == nums[i-1])
                continue;
            if (target - nums[i] < 3*nums[i])
                break;
            int target2 = target-nums[i];
            for (int j = i+1; j <= nums.length-3; j++) {
                if (j > i+1 && nums[j] == nums[j-1])
                    continue;
                if (target2-nums[j] < 2*nums[j])
                    break;
                int target3 = target2-nums[j], l = j+1, r = nums.length-1;
                while (l < r) {
                    if (nums[l] + nums[r] == target3) {
                        re.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l+1] == nums[l]) l++;
                        while (l < r && nums[r-1] == nums[r]) r--;
                        l++; r--;
                    } else if (nums[l] + nums[r] > target3) {
                        r--;
                    } else {
                        l++;
                    }
                }
            }
        }
        return re;
    }

    // 48 Rotate Image
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for (int j = 0; j < n/2; j++) {
            for (int i = 0; i < n; i++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n-j-1];
                matrix[i][n-j-1] = temp;
            }
        }
    }

    // 84 Largest Rectangle in Histogram
    public static int largestRectangleArea(int[] heights) {
        Stack<Integer> stk = new Stack<>();
        stk.push(-1);
        int[] lefts = new int[heights.length];
        int[] rights = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            while (stk.peek() != -1 && heights[stk.peek()] >= heights[i])
                stk.pop();
            lefts[i] = stk.peek();
            stk.push(i);
        }
        stk.clear();
        stk.push(heights.length);
        for (int j = heights.length-1; j >= 0; j--) {
            while (stk.peek() != heights.length && heights[stk.peek()] >= heights[j])
                stk.pop();
            rights[j] = stk.peek();
            stk.push(j);
        }

        int maxnum = 0;
        for (int i = 0; i < heights.length; i++) {
            int area = heights[i] * (rights[i]-lefts[i]-1);
            if (area > maxnum)
                maxnum = area;
        }
        return maxnum;
    }
}
