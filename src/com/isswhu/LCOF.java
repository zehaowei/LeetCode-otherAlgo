package com.isswhu;

import java.util.*;

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
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ')
                count++;
        }
        char[] str = new char[s.length()+count*2];
        for (int i = s.length()-1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                str[i + count*2] = s.charAt(i);
            } else {
                count--;
                str[i+count*2] = '%';
                str[i+1+count*2] = '2';
                str[i+2+count*2] = '0';
            }
        }
        return new String(str);
    }

    // 06. 从尾到头打印链表
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public int[] reversePrint(ListNode head) {
        Stack<Integer> stk = new Stack<>();
        while (head != null) {
            stk.push(head.val);
            head = head.next;
        }
        int[] arr = new int[stk.size()];
        int i = 0;
        while (!stk.empty()) {
            arr[i++] = stk.pop();
        }
        return arr;
    }

    // 07. 重建二叉树
     static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
     }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0)
            return null;
        else if (preorder.length == 1)
            return new TreeNode(preorder[0]);
        int root = preorder[0], cut = -1;
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            hmap.put(inorder[i], i);
            if (root == inorder[i]) {
                cut = i;
            }
        }
        TreeNode rootNode = new TreeNode(root);
        rootNode.left = build(preorder, 1, cut, inorder, 0, cut-1, hmap);
        rootNode.right = build(preorder, cut+1, preorder.length-1, inorder, cut+1, preorder.length-1, hmap);
        return rootNode;
    }

    TreeNode build(int[] preorder, int l1, int h1, int[] inorder, int l2, int h2, HashMap<Integer, Integer> hmap) {
        if (l1 > h1 || l2 > h2)
            return null;
        else if (l1 == h1 && l2 == h2)
            return new TreeNode(preorder[l1]);

        TreeNode root = new TreeNode(preorder[l1]);
        int cut = hmap.get(preorder[l1]);
        root.left = build(preorder, l1+1, l1+cut-l2, inorder, l2, cut-1, hmap);
        root.right = build(preorder, l1+cut-l2+1, h1, inorder, cut+1, h2, hmap);
        return root;
    }

    // 09. 用两个栈实现队列
    class CQueue {

        private Stack<Integer> stk1;
        private Stack<Integer> stk2;

        public CQueue() {
            stk1 = new Stack<>();
            stk2 = new Stack<>();
        }

        public void appendTail(int value) {
            stk1.push(value);
        }

        public int deleteHead() {
            if (stk2.empty()) {
                while (!stk1.empty()) {
                    stk2.push(stk1.pop());
                }
            }
            if (stk2.empty())
                return -1;
            else
                return stk2.pop();
        }
    }

    // 10- I. 斐波那契数列
    public int fib(int n) {
        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;
        int a = 0, b = 1, sum = 0;
        for (int i = 2; i <= n; i++) {
            sum = a + b;
            if (sum > 1000000007)
                sum %= 1000000007;
            a = b;
            b = sum;
        }
        return sum;
    }

    // 10- II. 青蛙跳台阶问题
    public int numWays(int n) {
        if (n == 0)
            return 1;
        else if (n == 1)
            return 1;
        int a = 1, b = 1, sum = 0;
        for (int i = 2; i <= n; i++) {
            sum = a + b;
            if (sum > 1000000007)
                sum %= 1000000007;
            a = b;
            b = sum;
        }
        return sum;
    }

    // 11. 旋转数组的最小数字
    public int minArray(int[] numbers) {
        if (numbers.length == 1)
            return numbers[0];
        int l = 0, h = numbers.length-1;
        while (l < h) {
            int mid = l + (h-l)/2;
            if (numbers[mid] < numbers[h]) {
                h = mid;
            } else if (numbers[mid] > numbers[h]) {
                l = mid+1;
            } else {
                h--;
            }
        }
        return numbers[l];
    }
}
