package com.isswhu;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.util.Arrays;
import java.util.Locale;

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
        if (nums.length == 0)
            return 0;
        int i = -1, j = 1, times = 1;
        while (j < nums.length) {
            if (nums[j] != nums[j-1]) {
                times = 1;
            } else
                times++;

            if (times > 2 && i == -1) {
                i = j;
            } else if (times <= 2 && i != -1) {
                nums[i++] = nums[j];
            }
            j++;
        }
        return i == -1 ? nums.length : i;
    }

    // 86. 分隔链表
    public ListNode partition(ListNode head, int x) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy, cur = head;
        while (cur != null && cur.val < x) {
            pre = cur;
            cur = cur.next;
        }
        ListNode cut = cur, cutPre = pre;
        while (cur != null) {
            if (cur.val < x) {
                cutPre.next = cur;
                cutPre = cur;
                pre.next = cur.next;
                cur.next = cut;
                cur = pre.next;
            } else {
                pre = cur;
                cur = cur.next;
            }
        }
        return dummy.next;
    }

    // 125. 验证回文串
    public boolean isPalindrome(String s) {
        char[] chs = s.toCharArray();
        int i = 0, j = s.length()-1;
        while (i < j) {
            while (!Character.isLetterOrDigit(chs[i]) && i < j)
                i++;
            while (!Character.isLetterOrDigit(chs[j]) && i < j)
                j--;
            if (i >= j)
                return true;
            if (Character.toLowerCase(chs[i]) == Character.toLowerCase(chs[j])) {
                i++; j--;
                continue;
            }
            return false;
        }
        return true;
    }

    // 142. 环形链表 II
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null)
                return null;
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast)
                break;
        }
        ListNode p1 = head, p2 = fast;
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }
}
