package com.isswhu;

import java.util.*;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    public void test(List<? extends ListNode> l) {

    }
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
