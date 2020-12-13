package com.isswhu;

import java.util.Arrays;

public class TwoPointer {

    // 16. 最接近的三数之和
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int gap = Integer.MAX_VALUE, re = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i-1])
                continue;
            int left = i+1, right = nums.length-1, tar = target-nums[i];
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum > tar) {
                    if (gap > sum-tar) {
                        gap = sum-tar;
                        re = target+gap;
                    }
                    right--;
                } else if (sum < tar) {
                    if (gap > tar-sum) {
                        gap = tar-sum;
                        re = target-gap;
                    }
                    left++;
                } else {
                    return target;
                }
            }
        }
        return re;
    }

    // 61. 旋转链表
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0)
            return head;
        int len = 0;
        ListNode p = head;
        while (p != null) {
            len++;
            p = p.next;
        }

        k = k % len;
        if (k == 0)
            return head;
        ListNode p2 = head;
        for (int i = 0; i < len-k; i++) {
            p = p2;
            p2 = p2.next;
        }
        p.next = null;
        p = p2;
        for (int i = 0; i < k-1; i++) {
            p = p.next;
        }
        p.next = head;
        return p2;
    }

    // 80. 删除排序数组中的重复项 II
    public int removeDuplicates(int[] nums) {

    }
}
