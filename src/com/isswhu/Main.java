package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        Test t = new Test();
        int[] re = t.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3);

    }


}

class Test {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0)
            return new int[]{};
        else if (nums.length == 1 || k == 1)
            return nums;

        Deque<Integer> deque = new LinkedList<>();
        deque.add(0);
        int i = 0, j = 1;
        while (j < k) {
            while (!deque.isEmpty() && nums[deque.getLast()] < nums[j])
                deque.removeLast();
            deque.add(j);
            j++;
        }

        int[] re = new int[nums.length-k+1];
        int ind = 0;
        re[ind++] = nums[deque.getFirst()];
        while (j < nums.length-1) {
            if (deque.getFirst() < ++i)
                deque.removeFirst();
            j++;
            while (!deque.isEmpty() && nums[deque.getLast()] < nums[j])
                deque.removeLast();
            deque.add(j);
            re[ind++] = nums[deque.getFirst()];
        }
        return re;
    }
}
