package com.isswhu;

import java.util.ArrayList;

public class Other {
    // 6. Z 字形变换
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;
        ArrayList<StringBuilder> strs = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            strs.add(new StringBuilder());
        }
        int ind = 0;
        while (ind < s.length()) {
            for (int i = 0; ind < s.length() && i < numRows; i++) {
                strs.get(i).append(s.charAt(ind++));
            }
            for (int i = numRows-2; ind < s.length() && i >= 1; i--) {
                strs.get(i).append(s.charAt(ind++));
            }
        }
        StringBuilder re = new StringBuilder();
        for (StringBuilder sb : strs)
            re.append(sb);
        return re.toString();
    }

    // 12. 整数转罗马数字
    public String intToRoman2(int num) {
        int[] digits = new int[4];
        for (int i = 0; i < 4; i++) {
            digits[i] = num % 10;
            num /= 10;
        }

        StringBuilder re = new StringBuilder();
        for (int i = 0; i < digits[3]; i++)
            re.append("M");

        if (digits[2] == 4)
            re.append("CD");
        else if (digits[2] == 9)
            re.append("CM");
        else if (digits[2] > 0 && digits[2] < 4) {
            for (int i = 0; i < digits[2]; i++)
                re.append("C");
        } else if (digits[2] != 0){
            re.append("D");
            for (int i = 0; i < digits[2]-5; i++)
                re.append("C");
        }

        if (digits[1] == 4)
            re.append("XL");
        else if (digits[1] == 9)
            re.append("XC");
        else if (digits[1] > 0 && digits[1] < 4) {
            for (int i = 0; i < digits[1]; i++)
                re.append("X");
        } else if (digits[1] != 0){
            re.append("L");
            for (int i = 0; i < digits[1]-5; i++)
                re.append("X");
        }

        if (digits[0] == 4)
            re.append("IV");
        else if (digits[0] == 9)
            re.append("IX");
        else if (digits[0] > 0 && digits[0] < 4) {
            for (int i = 0; i < digits[0]; i++)
                re.append("I");
        } else if (digits[0] != 0){
            re.append("V");
            for (int i = 0; i < digits[0]-5; i++)
                re.append("I");
        }

        return re.toString();
    }

    // 23. 合并K个升序链表
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        return mergeSort(lists, 0, lists.length-1);
    }

    ListNode mergeSort(ListNode[] lists, int s, int e) {
        if (s == e)
            return lists[s];

        int mid = s + (e-s)/2;
        ListNode l1 = mergeSort(lists, s, mid), l2 = mergeSort(lists, mid+1, e), dummy = new ListNode(-1);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        if (l1 == null)
            cur.next = l2;
        else
            cur.next = l1;
        return dummy.next;
    }

    // 24. 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy, p1 = head, p2 = head.next;
        while (p1 != null && p2 != null) {
            p1.next = p2.next;
            p2.next = p1;
            pre.next = p2;

            pre = p1;
            p1 = p1.next;
            if (p1 != null)
                p2 = p1.next;
        }
        return dummy.next;
    }

    // 25. K 个一组翻转链表
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1)
            return head;
        ListNode dummy = new ListNode(-1, head);
        ListNode p = head, pre = dummy;
        int num = 0;
        while (p != null) {
            num++;
            if (num == k) {
                ListNode start = pre.next, nextStart = p.next;
                pre.next = reverse(start, p);
                pre = start;
                pre.next = nextStart;
                p = nextStart;
                num = 0;
            } else
                p = p.next;
        }
        return dummy.next;
    }

    ListNode reverse(ListNode head, ListNode tail) {
        ListNode headnew = null, p = head, tailNext = tail.next;;
        while (p != tailNext) {
            ListNode tmp = p.next;
            p.next = headnew;
            headnew = p;
            p = tmp;
        }
        return headnew;
    }

    // 31. 下一个排列
    public void nextPermutation(int[] nums) {
        int len = nums.length, candi = -1;
        for (int i = len-2; i >= 0; i--) {
            if (nums[i] < nums[i+1]) {
                candi = i;
                break;
            }
        }
        if (candi != -1) {
            int cur = candi+1;
            while (cur < len) {
                if (nums[cur] > nums[candi]) {
                    cur++;
                } else {
                    break;
                }
            }
            cur--;
            int tmp = nums[cur];
            nums[cur] = nums[candi];
            nums[candi] = tmp;
        }
        int l = candi+1, h = len-1;
        while (l < h) {
            int tmp = nums[h];
            nums[h] = nums[l];
            nums[l] = tmp;
            l++;
            h--;
        }
    }

    // 38. 外观数列
    public String countAndSay(int n) {
        if (n == 1)
            return "1";
        String s = countAndSay(n-1);
        char[] chs = s.toCharArray();
        StringBuilder re = new StringBuilder();
        int cur = 0, times = 1;
        while (cur < chs.length) {
            if (cur < chs.length-1 && chs[cur] == chs[cur+1]) {
                times++;
            } else {
                re.append(times).append(chs[cur]);
                times = 1;
            }
            cur++;
        }
        return re.toString();
    }

    // 41. 缺失的第一个正数
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[i] != i+1) {
                int num = nums[i];
                int tmp =  nums[num-1];
                if (num == tmp)
                    break;
                nums[num-1] = num;
                nums[i] = tmp;
            }
        }
        for (int i = 1; i <= n; i++) {
            if (nums[i-1] != i)
                return i;
        }
        return n+1;
    }

    // 42. 接雨水
    public int trap(int[] height) {
        int sum = 0, l = 0, h = 1;
        int maxNum, maxInd;
        while (h < height.length) {

        }
    }
}
