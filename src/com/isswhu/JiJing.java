package com.isswhu;

import java.util.*;

public class JiJing {
    public ListNode ReverseList(ListNode head) {
        ListNode dummy = new ListNode(-1), p = null;
        while (head != null) {
            p = head.next;
            head.next = dummy.next;
            dummy.next = head;
            head = p;
        }
        return dummy.next;
    }

    public boolean hasCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null)
                return false;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
                return true;
        }
    }

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }

    public int[][] threeOrders (TreeNode root) {
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        ArrayList<Integer> nums = new ArrayList<>(), nums2 = new ArrayList<>();
        while (!stk.empty() || p != null) {
            while (p != null) {
                nums.add(p.val);
                stk.push(p);
                p = p.left;
            }
            TreeNode n = stk.pop();
            nums2.add(n.val);
            p = n.right;
        }
        int len = nums.size();
        int[][] re = new int[3][len];
        for (int i = 0; i < len; i++) {
            re[0][i] = nums.get(i);
            re[1][i] = nums2.get(i);
        }

        p = root;
        LinkedList<Integer> l = new LinkedList<>();
        while (!stk.empty() || p != null) {
            while (p != null) {
                l.addFirst(p.val);
                stk.push(p);
                p = p.right;
            }
            p = stk.pop().left;
        }
        int i = 0;
        for (int num : l) {
            re[2][i++] = num;
        }
        return re;
    }

    public ListNode mergeTwoLists (ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1), head = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                head.next = l1;
                head = l1;
                l1 = l1.next;
            } else {
                head.next = l2;
                head = l2;
                l2 = l2.next;
            }
        }
        if (l1 == null)
            head.next = l2;
        else
            head.next = l1;
        return dummy.next;
    }

    public int JumpFloor(int target) {
        if (target == 1)
            return 1;
        int[] dp = new int[target+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= target; i++)
            dp[i] = dp[i-1]+dp[i-2];
        return dp[target];
    }

    public int maxsumofSubarray (int[] arr) {
        int a = arr[0];
        int re = a;
        for (int i = 1; i < arr.length; i++) {
            if (a > 0)
                a = a+arr[i];
            else
                a = arr[i];
            re = Math.max(re, a);
        }
        return re;
    }

    public int maxLength (int[] arr) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        int s = 0, e = 0, re = 0;
        for (; e < arr.length; e++) {
            if (hmap.containsKey(arr[e])) {
                s = Math.max(s, hmap.get(arr[e])+1);
            }
            hmap.put(arr[e], e);
            re = Math.max(re, e-s+1);
        }
        return re;
    }

    public void merge(int A[], int m, int B[], int n) {
        int e = m+n-1, p1 = m-1, p2 = n-1;
        while (p1 >= 0 && p2 >= 0) {
            if (A[p1] > B[p2]) {
                A[e--] = A[p1--];
            } else {
                A[e--] = B[p2--];
            }
        }
        if (p1 < 0) {
            while (p2 >= 0)
                A[e--] = B[p2--];
        }
    }

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        for (int i = m-1; i >= 0; i--) {
            nums1[i+n] = nums1[i];
        }
        int p1 = n, p2 = 0, s = 0;
        while (p1 < m+n && p2 < n) {
            if (nums1[p1] < nums2[p2]) {
                nums1[s++] = nums1[p1++];
            } else {
                nums1[s++] = nums2[p2++];
            }
        }
        if (p1 == m+n) {
            while (p2 < n)
                nums1[s++] = nums2[p2++];
        }
    }

    public String solve (String s, String t) {
        StringBuilder sb = new StringBuilder();
        int n = Math.max(s.length(), t.length()), p1 = s.length()-1, p2 = t.length()-1, add = 0;
        for (int i = 0; i < n; i++) {
            int a = p1 >= 0 ? s.charAt(p1--)-'0' : 0;
            int b = p2 >= 0 ? t.charAt(p2--)-'0' : 0;
            int num = a+b+add;
            add = num / 10;
            num = num % 10;
            sb.append(num);
        }
        if (add != 0)
            sb.append(add);
        return sb.reverse().toString();
    }

    public ArrayList<ArrayList<Integer>> zigzagLevelOrder (TreeNode root) {
        int level = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        ArrayList<ArrayList<Integer>> re = new ArrayList<>();
        if (root == null)
            return re;
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            ArrayList<Integer> l = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
                l.add(node.val);
            }
            if (level % 2 == 0) {
                ArrayList<Integer> l2 = new ArrayList<>();
                for (int i = l.size()-1; i >= 0; i--)
                    l2.add(l.get(i));
                re.add(l2);
            } else
                re.add(l);
            level++;
        }
        return re;
    }

}
