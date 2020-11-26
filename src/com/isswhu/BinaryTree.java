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

    // 116. 填充每个节点的下一个右侧节点指针
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    public Node connect(Node root) {
        if (root == null)
            return null;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node pre = null;
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (pre != null)
                    pre.next = node;
                pre = node;
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return root;
    }

    public Node connect2(Node root) {
        if (root == null)
            return null;
        Node pre = root;
        while (pre.left != null) {
            Node head = pre;
            while (pre != null) {
                pre.left.next = pre.right;
                if (pre.next != null)
                    pre.right.next = pre.next.left;
                pre = pre.next;
            }
            pre = head.left;
        }
        return root;
    }

    // 117. 填充每个节点的下一个右侧节点指针 II
    public Node connect3(Node root) {
        if (root == null)
            return null;
        Node pre = root;
        while (pre != null) {
            while (pre != null && pre.left == null && pre.right == null)
                pre = pre.next;
            if (pre == null) break;

            Node tmp = pre;
            while (tmp != null) {
                Node cur;
                if (tmp.left != null && tmp.right != null) {
                    tmp.left.next = tmp.right;
                    cur = tmp.right;
                } else if (tmp.left != null)
                    cur = tmp.left;
                else
                    cur = tmp.right;

                tmp = tmp.next;
                while (tmp != null && tmp.left == null && tmp.right == null)
                    tmp = tmp.next;
                if (tmp != null) {
                    if (tmp.left != null)
                        cur.next = tmp.left;
                    else
                        cur.next = tmp.right;
                }
            }
            if (pre.left != null)
                pre = pre.left;
            else
                pre = pre.right;
        }
        return root;
    }

    // 124. 二叉树中的最大路径和
    int re = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        getMax(root);
        return re;
    }

    int getMax(TreeNode root) {
        if (root == null)
            return 0;
        int left = getMax(root.left);
        int right = getMax(root.right);
        int num = Math.max(root.val, root.val+Math.max(left, right));
        int mid = Math.max(num, left+root.val+right);
        re = Math.max(mid, re);

        return num;
    }

    // 129. 求根到叶子节点数字之和
    int result = 0;
    public int sumNumbers(TreeNode root) {
        dfs129(root, 0);
        return result;
    }

    void dfs129(TreeNode root, int sum) {
        if (root == null)
            return;
        else if (root.left == null & root.right == null) {
            result += sum*10 + root.val;
            return;
        }

        dfs129(root.left, sum*10 + root.val);
        dfs129(root.right, sum*10 + root.val);
    }

    // 144. 二叉树的前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> l = new LinkedList<>();
        if (root == null)
            return l;

        Stack<TreeNode> stk = new Stack<>();
        while (!stk.empty() || root != null) {
            while (root != null) {
                l.add(root.val);
                stk.add(root);
                root = root.left;
            }
            root = stk.pop().right;
        }
        return l;
    }

    // 145. 二叉树的后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> l = new LinkedList<>();
        if (root == null)
            return l;

        Stack<TreeNode> stk = new Stack<>();
        while (!stk.empty() || root != null) {
            while (root != null) {
                l.addFirst(root.val);
                stk.add(root);
                root = root.right;
            }
            root = stk.pop();
            root = root.left;
        }
        return l;
    }

    // 173. 二叉搜索树迭代器
    class BSTIterator {
        Stack<TreeNode> stk;
        TreeNode node;
        public BSTIterator(TreeNode root) {
            stk = new Stack<>();
            node = root;
        }

        /** @return the next smallest number */
        public int next() {
            while (node != null) {
                stk.add(node);
                node = node.left;
            }
            node = stk.pop();
            int re = node.val;
            node = node.right;
            return re;
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return !stk.isEmpty() || node != null;
        }
    }

    // 199. 二叉树的右视图
    public List<Integer> rightSideView(TreeNode root) {
        LinkedList<Integer> l = new LinkedList<>();
        if (root == null)
            return l;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode node = null;
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            l.add(node.val);
        }
        return l;
    }

    // 257. 二叉树的所有路径
    public List<String> binaryTreePaths(TreeNode root) {
        StringBuilder str = new StringBuilder();
        List<String> re = new LinkedList<>();
        dfs257(root, str, re);
        return re;
    }

    void dfs257(TreeNode root, StringBuilder str, List<String> re) {
        if (root == null)
            return;
        else if (root.left == null && root.right == null) {
            int start = str.length();
            String s = Integer.toString(root.val);
            str.append(s);
            re.add(str.toString());
            str.delete(start, start+s.length());
        }

        int start = str.length();
        String s = root.val + "->";
        str.append(s);
        dfs257(root.left, str, re);
        dfs257(root.right, str, re);
        str.delete(start, start+s.length());
    }
}
