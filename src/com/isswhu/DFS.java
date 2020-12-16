package com.isswhu;

import java.util.*;

public class DFS {
    // 109. 有序链表转换二叉搜索树
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

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null)
            return null;
        else if (head.next == null)
            return new TreeNode(head.val);
        ListNode pre = null, cur = head;
        while (cur != null) {
            pre = cur;
            cur = cur.next;
        }
        return buildTree(head, pre);
    }

    TreeNode buildTree(ListNode head, ListNode tail) {
        if (head == tail)
            return new TreeNode(head.val);
        else if (tail == null)
            return null;
        ListNode fast = head, slow = head, pre = null;
        while (fast != tail) {
            if (fast.next == tail)
                break;
            fast = fast.next.next;
            pre = slow;
            slow = slow.next;
        }
        TreeNode node = new TreeNode(slow.val);
        node.left = buildTree(head, pre);
        node.right = buildTree(slow.next, tail);
        return node;
    }

    // 130. 被围绕的区域
    void join(int[] set, int a, int b) {
        int root1 = find(set, a), root2 = find(set, b);
        if (root1 != root2)
            set[root2] = root1;
    }

    int find(int[] set, int a) {
        int root = a;
        while (root != set[root]) {
            root = set[root];
        }
        int tmp = a;
        while (tmp != set[tmp]) {
            int b = set[tmp];
            set[tmp] = root;
            tmp = b;
        }
        return root;
    }

    public void solve(char[][] board) {
        if (board.length == 0)
            return;
        int m = board.length, n = board[0].length;
        int[] set = new int[m*n+1];
        for (int i = 0; i < m*n+1; i++) {
            set[i] = i;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    int ind = i*n+j;
                    if (i == 0 || i == m-1 || j == 0 || j == n-1)
                        join(set, ind, m*n);
                    if (j+1 < n && board[i][j+1] == 'O')
                        join(set, ind, ind+1);
                    if (i+1 < m && board[i+1][j] == 'O')
                        join(set, ind, ind+n);
                }
            }
        }

        for (int i = 1; i < m-1; i++) {
            for (int j = 1; j < n-1; j++) {
                if (board[i][j] == 'O' && find(set, i*n+j) != find(set, m*n))
                    board[i][j] = 'X';
            }
        }
    }

    // 133. 克隆图
    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        if (node == null)
            return null;
        HashMap<Integer, Node> hmap = new HashMap<>();
        return dfs(hmap, node);
    }

    Node dfs(HashMap<Integer, Node> hmap, Node node) {
        if (hmap.containsKey(node.val))
            return hmap.get(node.val);
        Node n = new Node(node.val);
        hmap.put(node.val, n);
        for (Node nd : node.neighbors) {
            n.neighbors.add(dfs(hmap, nd));
        }
        return n;
    }

    // 200. 岛屿数量
    public int numIslands(char[][] grid) {
        int m = grid.length;
        if (m == 0)
            return 0;
        int n = grid[0].length;
        int num = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    num++;
                    dfs200(grid, i, j, m, n);
                }
            }
        }
        return num;
    }

    void dfs200(char[][] grid, int i, int j, int m, int n) {
        grid[i][j] = 'A';
        if (j+1 < n && grid[i][j+1] == '1')
            dfs200(grid, i, j+1, m, n);
        if (i+1 < m && grid[i+1][j] == '1')
            dfs200(grid, i+1, j, m, n);
        if (j-1 >= 0 && grid[i][j-1] == '1')
            dfs200(grid, i, j-1, m, n);
        if (i-1 >= 0 && grid[i-1][j] == '1')
            dfs200(grid, i-1, j, m, n);
    }

    // 210. 课程表 II
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] map = new int[numCourses];
        ArrayList<LinkedList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++)
            edges.add(new LinkedList<>());
        for (int[] tup : prerequisites) {
            int course = tup[0];
            map[course] += 1;
            edges.get(tup[1]).add(course);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (map[i] == 0) {
                queue.add(i);
            }
        }

        int[] re = new int[numCourses];
        int ind = 0;
        while (!queue.isEmpty()) {
            int cour = queue.poll();
            re[ind++] = cour;

            for (Integer after : edges.get(cour)) {
                map[after] -= 1;
                if (map[after] == 0) {
                    queue.add(after);
                }
            }
        }

        if (ind != numCourses)
            return new int[]{};
        return re;
    }
}
