package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        Test t = new Test();
        int[] re = t.singleNumbers(new int[]{1,2,5,2});

    }


}

class Test {
    public int[] singleNumbers(int[] nums) {
        int mark = nums[0];
        for (int i = 1; i < nums.length; i++)
            mark = mark ^ nums[i];
        int mask = 1;
        while (mask != 0) {
            if((mark & mask) != 0)
                break;
            mask = mask << 1;
        }
        int group0 = 0, group1 = 0;
        boolean init0 = false, init1 = false;
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & mask) == 1) {
                if (init1) {
                    group1 = group1 ^ nums[i];
                } else {
                    group1 = nums[i];
                    init1 = true;
                }
            } else {
                if (init0) {
                    group0 = group0 ^ nums[i];
                } else {
                    group0 = nums[i];
                    init0 = true;
                }
            }
        }
        return new int[]{group0, group1};
    }
}
