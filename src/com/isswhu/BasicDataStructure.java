package com.isswhu;

import java.util.Stack;

public class BasicDataStructure {
    // 48 Rotate Image
    public void rotate(int[][] matrix) {

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
