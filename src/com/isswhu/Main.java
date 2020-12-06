package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        int[] arr = new int[]{15252,16764,27963,7817,26155,20757,3478,22602,20404,6739,16790,10588,16521,6644,20880,15632,27078,25463,20124,15728,30042,16604,17223,4388,23646,32683,23688,12439,30630,3895,7926,22101,32406,21540,31799,3768,26679,21799,23740};
        Test t = new Test();
        t.maximumGap2(arr);
    }


}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Test {
    public int maximumGap2(int[] nums) {
        if (nums.length < 2)
            return 0;

        int minnum = Integer.MAX_VALUE, maxnum = 0;
        for (int num : nums) {
            minnum = Math.min(minnum, num);
            maxnum = Math.max(maxnum, num);
        }

        int d = (int)Math.ceil((double)(maxnum - minnum) / (nums.length-1));
        if (d == 0) return 0;
        int size = (maxnum-minnum)/d + 1;
        int[] maxs = new int[size], mins = new int[size];
        for (int i = 0; i < mins.length; i++) {
            maxs[i] = -1;
            mins[i] = -1;
        }

        for (int num : nums) {
            int ind = (num-minnum) / d;
            if (maxs[ind] == -1) {
                maxs[ind] = num;
                mins[ind] = num;
            } else {
                maxs[ind] = Math.max(maxs[ind], num);
                mins[ind] = Math.min(mins[ind], num);
            }
        }

        int re = 0, pre = 0;
        for (int i = 1; i < maxs.length; i++) {
            if (maxs[i] == -1)
                continue;
            if (maxs[pre] != -1) {
                re = Math.max(mins[i] - maxs[pre], re);
            }
            pre = i;
        }
        return re;
    }
}
