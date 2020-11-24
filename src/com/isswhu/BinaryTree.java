package com.isswhu;

import java.util.*;

public class BinaryTree {

    // 98. 验证二叉搜索树
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    TreeNode tail = null;
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        if (!isValidBST(root.left))
            return false;
        if (tail != null && tail.val >= root.val)
               return false;
        tail = root;
        return isValidBST(root.right);
    }

    // 99. 恢复二叉搜索树
    public void recoverTree(TreeNode root) {
        Stack<TreeNode> stk = new Stack<>();
        TreeNode tail = null, p1 = null, p2 = null;
        while (!stk.isEmpty() || root != null) {
            while (root != null) {
                stk.add(root);
                root = root.left;
            }
            root = stk.pop();
            if (tail != null && tail.val > root.val) {
                p2 = root;
                if (p1 == null)
                    p1 = tail;
            }
            tail = root;
            root = root.right;
        }

        int tmp = p1.val;
        p1.val = p2.val;
        p2.val = tmp;
    }

    // 103. 二叉树的锯齿形层次遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Deque<TreeNode> queue = new LinkedList<>();
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null)
            return result;

        queue.add(root);
        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> list = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
                if (level % 2 == 0)
                    list.addFirst(node.val);
                else
                    list.addLast(node.val);
            }
            level++;
            result.add(list);
        }
        return result;
    }

    // 105. 从前序与中序遍历序列构造二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return func(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
    }

    TreeNode func(int[] preorder, int l1, int h1, int[] inorder, int l2, int h2) {
        if (l1 > h1)
            return null;

        int root = preorder[l1];
        TreeNode rootNode = new TreeNode(root);
        if (l1 == h1)
            return rootNode;

        int ind = l2;
        while (inorder[ind] != root)
            ind++;
        int leftLen = ind - l2;
        rootNode.left = func(preorder, l1+1, l1+leftLen, inorder, l2, ind-1);
        rootNode.right = func(preorder, l1+leftLen+1, h1, inorder, ind+1, h2);
        return rootNode;
    }

    // 106. 从中序与后序遍历序列构造二叉树
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        return func(postorder, 0, postorder.length-1, inorder, 0, inorder.length-1);
    }

    TreeNode func2(int[] postorder, int l1, int h1, int[] inorder, int l2, int h2) {
        if (l1 > h1)
            return null;

        int root = postorder[h1];
        TreeNode rootNode = new TreeNode(root);
        if (l1 == h1)
            return rootNode;

        int ind = l2;
        while (inorder[ind] != root)
            ind++;
        int rightLen = h2 - ind;
        rootNode.left = func(postorder, l1, h1-rightLen-1, inorder, l2, ind-1);
        rootNode.right = func(postorder, h1-rightLen, h1-1, inorder, ind+1, h2);
        return rootNode;
    }

    // 107. 二叉树的层次遍历 II
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null)
            return result;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> list = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            result.addFirst(list);
        }
        return result;
    }

    // 108. 将有序数组转换为二叉搜索树
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0)
            return null;
        return trans(nums, 0, nums.length-1);
    }

    TreeNode trans(int[] nums, int l, int h) {
        if (l > h)
            return null;
        int mid = l + (h-l)/2;
        TreeNode root = new TreeNode(nums[mid]);
        if (l == h)
            return root;
        root.left = trans(nums, l, mid-1);
        root.right = trans(nums, mid+1, h);
        return root;
    }

    // 111. 二叉树的最小深度
    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        else if (root.left == null && root.right == null)
            return 1;
        else if (root.left != null && root.right != null)
            return 1 + Math.min(minDepth(root.left), minDepth(root.right));
        else if (root.left == null) {
            return 1 + minDepth(root.right);
        } else
            return 1 + minDepth(root.left);
    }

    public int minDepth2(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left == null && node.right == null)
                    return level;
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            level++;
        }
        return level;
    }

    // 112. 路径总和
    public boolean hasPathSum(TreeNode root, int sum) {
        return dfs(root, 0, sum);
    }

    boolean dfs(TreeNode root, int sum, int target) {
        if (root == null)
            return false;
        else if (root.left == null && root.right == null)
            return sum+root.val == target;

        sum += root.val;
        return dfs(root.left, sum, target) || dfs(root.right, sum, target);
    }

    // 114. 二叉树展开为链表
    TreeNode end = null;
    public void flatten(TreeNode root) {
        if (root == null)
            return;
        flatten(root.right);
        flatten(root.left);
        root.right = end;
        root.left = null;
        end = root;
    }

    //
}
