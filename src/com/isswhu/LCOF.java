package com.isswhu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

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
        for (int i = 0; i < inorder.length; i++) {
            if (root == inorder[i]) {
                cut = i;
                break;
            }
        }

    }

    TreeNode build(int[] preorder, int l1, int h1, int[] inorder, int l2, int h2) {

    }

}
