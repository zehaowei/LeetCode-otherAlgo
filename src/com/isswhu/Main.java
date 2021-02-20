package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        int[] arr = new int[]{15252,16764,27963,7817,26155,20757,3478,22602,20404,6739,16790,10588,16521,6644,20880,15632,27078,25463,20124,15728,30042,16604,17223,4388,23646,32683,23688,12439,30630,3895,7926,22101,32406,21540,31799,3768,26679,21799,23740};
        Test t = new Test();
        char[][] cc = new char[][]{{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
        ListNode n1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        int [] a = new int[]{1,1,1,2,2,3};
        t.topKFrequent(a, 2);
    }


}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Pair {
    int num;
    int times;
    Pair(int a, int b) {
        num = a;
        times = b;
    }
}

class Test {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int num : nums) {
            if (hmap.containsKey(num))
                hmap.put(num, 1+hmap.get(num));
            else
                hmap.put(num, 1);
        }
        Pair[] nums2 = new Pair[nums.length];
        int ind = 0;
        for (Map.Entry<Integer, Integer> e : hmap.entrySet()) {
            nums2[ind++] = new Pair(e.getKey(), e.getValue());
        }

        int l = 0, h = nums2.length-1, re = -1;
        Random rand = new Random();
        while(true) {
            int t = l+rand.nextInt(h-l+1);
            re = partition(nums2, l, h, t);
            if (re < k) {
                l = re+1;
            } else if (re > k) {
                h = re-1;
            } else {
                break;
            }
        }
        int[] arr = new int[k];
        for (int i = 0; i < k; i++)
            arr[i] = nums2[k+i].num;
        return arr;
    }

    int partition(Pair[] nums, int l, int h, int target) {
        Pair temp = nums[l];
        nums[l] = nums[target];
        nums[target] = temp;

        int p = l, q = l+1;
        while (q <= h) {
            if (nums[q].times < nums[l].times) {
                p++;
                if (p != q) {
                    Pair tmp = nums[p];
                    nums[p] = nums[q];
                    nums[q] = tmp;
                }
            }
            q++;
        }

        temp = nums[l];
        nums[l] = nums[p];
        nums[p] = temp;
        return p;
    }
}
