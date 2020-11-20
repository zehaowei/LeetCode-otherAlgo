package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        Test t = new Test();
        BinarySearch.TreeNode root = new BinarySearch.TreeNode(3);
        BinarySearch.TreeNode root1 = new BinarySearch.TreeNode(1);
        BinarySearch.TreeNode root2 = new BinarySearch.TreeNode(4);
        BinarySearch.TreeNode root3 = new BinarySearch.TreeNode(2);
        root.left = root1;
        root.right = root2;
        root1.right = root3;
        t.kthSmallest(root, 3);
    }


}

class Test {
    int ans;

    public int kthSmallest(BinarySearch.TreeNode root, int k) {
        inorder(root, 0, k);
        return ans;
    }

    int inorder(BinarySearch.TreeNode root, int no, int k) {
        if (root == null)
            return 0;
        int num = inorder(root.left, no, k);
        if (num == -1)
            return -1;
        else if (no+num+1 == k) {
            ans = root.val;
            return -1;
        }
        no += num + 1;
        num = inorder(root.right, no, k);
        if (num == -1)
            return -1;
        return no+num;
    }
}
