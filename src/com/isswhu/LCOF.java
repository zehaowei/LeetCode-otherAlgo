package com.isswhu;

import java.util.HashSet;

public class LCOF {
    // 03. 数组中重复的数字
    public int findRepeatNumber(int[] nums) {
        HashSet<Integer> hset = new HashSet<>();
        int re = 0;
        for (int i = 0; i < nums.length; i++) {
            if (hset.contains(nums[i])) {
                re = nums[i];
                break;
            }
            else
                hset.add(nums[i]);
        }
        return re;
    }

    public int findRepeatNumber2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                int ind = nums[i];
                int temp = nums[ind];
                if (ind == temp)
                    return ind;
                else {
                    nums[ind] = ind;
                    nums[i] = temp;
                }
            }
        }
        return 0;
    }

    // 05. 替换空格
    public String replaceSpace(String s) {

    }
}
