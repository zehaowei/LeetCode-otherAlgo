package com.isswhu;

import java.lang.reflect.Array;
import java.util.*;

public class Sort {

    // 57. 插入区间
    public int[][] insert(int[][] intervals, int[] newInterval) {

    }

    // 147. 对链表进行插入排序
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int x) { val = x; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode insertionSortList(ListNode head) {
        if (head == null)
            return null;

        ListNode p = head.next, tail = head, dummy = new ListNode(0);
        dummy.next = head;
        while (p != null) {
            ListNode next = p.next;
            if (tail.val <= p.val)
                tail = p;
            else {
                ListNode pre = dummy;
                while (pre.next.val < p.val) {
                    pre = pre.next;
                }

                tail.next = next;
                p.next = pre.next;
                pre.next = p;
            }

            p = next;
        }

        return dummy.next;
    }

    // 148. 排序链表
    public ListNode sortList2(ListNode head) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode(0, head);
        quicksort(dummy, null);
        return dummy.next;
    }

    void quicksort(ListNode preHead, ListNode postTail) {
        if (preHead.next == postTail || (preHead.next.next == postTail))
            return;
        ListNode head = preHead.next;
        ListNode pre = head, p = head.next, pre_p = head;
        while (p != postTail) {
            if (p.val <= head.val) {
                if (pre.next == p) {
                    pre = pre.next;
                    p = p.next;
                } else {
                    pre_p.next = p.next;
                    p.next = pre.next;
                    pre.next = p;

                    pre = p;
                    p = pre_p.next;
                }
            } else {
                pre_p = p;
                p = p.next;
            }
        }

        if (pre != head) {
            preHead.next = head.next;
            head.next = pre.next;
            pre.next = head;
        }
        quicksort(preHead, head);
        quicksort(head, postTail);
    }

    public ListNode sortList(ListNode head) {
        if (head == null)
            return null;

        int length = 0;
        ListNode p = head;
        while (p != null) {
            length++;
            p = p.next;
        }

        ListNode dummy = new ListNode(0, head);
        for (int sublen = 1; sublen < length; sublen <<= 1) { // 每次归并排序时，被归并的子list的长度
            ListNode pre = dummy, cur = dummy.next;
            while (cur != null) {
                ListNode head1 = cur; // 找list1
                for (int i = 1; i < sublen && cur.next != null; i++) {
                    cur = cur.next;
                }

                ListNode head2 = cur.next; // 找list2
                cur.next = null; // 断开list1 and 2
                cur = head2;
                for (int i = 1; i < sublen && cur != null && cur.next != null; i++) {
                    cur = cur.next;
                }

                if (head2 != null) {
                    ListNode tmp = cur;
                    cur = cur.next;
                    tmp.next = null;        // 断开list2与后面
                    pre.next = merge(head1, head2);
                    for (int i = 0; i < 2*sublen && pre.next != null; i++) // 发生了排序，此时已不知道tail是哪个，只能一个一个数
                        pre = pre.next;
                } else {
                    pre.next = head1; // 不需要归并时也要记得把已排好序的部分与本轮list链接起来
                }
            }
        }

        return dummy.next;
    }

    ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0), cur = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                cur.next = head1;
                head1 = head1.next;
            } else {
                cur.next = head2;
                head2 = head2.next;
            }
            cur = cur.next;
        }
        if (head1 != null) {
            cur.next = head1;
        } else {
            cur.next = head2;
        }
        return dummy.next;
    }

    // 179. 最大数
    public String largestNumber(int[] nums) {
        if (nums.length == 0)
            return "";
        ArrayList<String> list = new ArrayList<>();
        for (int num : nums)
            list.add(Integer.toString(num));
        list.sort((a, b) -> {
            String s1 = a+b, s2 = b+a;
            return -s1.compareTo(s2);
        });
        if (list.get(0).equals("0"))
            return "0";
        StringBuilder sb = new StringBuilder();
        for (String str : list)
            sb.append(str);
        return sb.toString();
    }

    // 220. 存在重复元素 III
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {
        if (k < 1)
            return false;
        else if (k > nums.length-1)
            k = nums.length-1;

        TreeSet<Long> treeSet = new TreeSet<>();
        treeSet.add((long)nums[0]);
        int i = 0, j = 1;
        for (; j < nums.length; j++) {
            Long floor = treeSet.floor((long)nums[j]);
            if (floor != null && (long)nums[j]-floor <= t)
                return true;
            Long ceil = treeSet.ceiling((long)nums[j]);
            if (ceil != null && ceil-(long)nums[j] <= t)
                return true;
            treeSet.add((long)nums[j]);
            if (j-i == k) {
                treeSet.remove((long)nums[i]);
                i++;
            }
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k < 1 || t < 0)
            return false;
        HashMap<Long, Long> hmap = new HashMap<>();
        int i = 0, j = 1;
        hmap.put(getId(nums[0], t+1), (long)nums[0]);
        for (; j < nums.length; j++) {
            long id = getId(nums[j], t+1);
            if (hmap.containsKey(id))
                return true;
            if (hmap.containsKey(id-1) && nums[j]-hmap.get(id-1) <= t)
                return true;
            if (hmap.containsKey(id+1) && hmap.get(id+1)-nums[j] <= t)
                return true;
            hmap.put(id, (long)nums[j]);
            if (j-i == k) {
                hmap.remove(getId(nums[i], t+1));
                i++;
            }
        }
        return false;
    }

    long getId (long num, long step) {
        if (num >= 0)
            return num / step;
        else
            return (num+1) / step - 1;
    }
}
