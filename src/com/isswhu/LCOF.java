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

    // 12. 矩阵中的路径
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    visited[i][j] = true;
                    boolean re = backtrack(board, i, j, word, 1, visited);
                    if (re) {
                        return true;
                    } else {
                        visited[i][j] = false;
                    }
                }
            }
        }
        return false;
    }

    boolean backtrack(char[][] board, int a, int b, String word, int wdIndex, boolean[][] visited) {
        if (wdIndex >= word.length())
            return true;
        int[] arr1 = {a-1, a+1, a, a}, arr2 = {b, b, b-1, b+1};
        for (int i = 0; i < 4; i++) {
            int x = arr1[i], y = arr2[i];
            if (x >= 0 && x < board.length && y >= 0 && y < board[0].length
            && board[x][y] == word.charAt(wdIndex) && !visited[x][y]) {
                visited[x][y] = true;
                if (backtrack(board, x, y, word, wdIndex+1, visited))
                    return true;
                else
                    visited[x][y] = false;
            }
        }
        return false;
    }

    // 13. 机器人的运动范围
    public int movingCount(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        return dfs(0, 0, m, n, k, visited);
    }

    int dfs(int a, int b, int m, int n, int k, boolean[][] visited) {
        if (a < 0 || a >= m || b < 0 || b >= n || !isValid(a,b,k) || !visited[a][b])
            return 0;
        visited[a][b] = true;
        int sum = 1;
        sum += dfs(a-1, b, m, n, k, visited);
        sum += dfs(a+1, b, m, n, k, visited);
        sum += dfs(a, b-1, m, n, k, visited);
        sum += dfs(a, b+1, m, n, k, visited);
        return sum;
    }

    boolean isValid(int a, int b, int k) {
        int sum = 0;
        while (a > 0) {
            sum += a%10;
            a = a/10;
        }
        while (b > 0) {
            sum += b%10;
            b = b/10;
        }
        return sum <= k;
    }

    // 14- I. 剪绳子
    public int cuttingRope(int n) {
        if (n == 2)
            return 1;
        int[] dp = new int[n];
        dp[1] = 1;
        dp[2] = 2;
        int max = 0;
        for (int i = 1; i <= n/2; i++) {
            int re = func(i, dp) * func(n-i, dp);
            if (re > max)
                max = re;
        }
        return max;
    }

    int func(int n, int[] dp) {
        if (dp[n] != 0)
            return dp[n];
        int max = n;
        for (int i = 1; i <= n/2; i++) {
            int re = func(i, dp) * func(n-i, dp);
            if (re > max)
                max = re;
        }
        dp[n] = max;
        return max;
    }

    // 15. 二进制中1的个数
    public int hammingWeight(int n) {
        int num = 0, mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0)
                num++;
            mask = mask << 1;
        }
        return num;
    }

    //  16. 数值的整数次方
    public double myPow(double x, int n) {
        if (n == 0)
            return 1;
        long times = n;
        if (n < 0) {
            times = -times;
            x = 1/x;
        }
        x = pow_log(x, times);
        return x;
    }

    double pow_log(double x, long n) {
        double origin = x, old = x;
        long times = 1;
        while (times < n) {
            old = x;
            x *= x;
            times <<= 1;
        }
        if (times == n)
            return x;
        else
            return old*pow_log(origin, n-times/2);
    }

    public double myPow2(double x, int n) {
        if (n == 0)
            return 1;
        long times = n;
        if (n < 0) {
            times = -times;
            x = 1/x;
        }
        double res = 1;
        while (times > 0) {
            if ((times & 1) == 1)
                res *= x;
            x *= x;
            times >>= 1;
        }
        return res;
    }

    // 17. 打印从1到最大的n位数
    public int[] printNumbers(int n) {
        int base = 1;
        for (int i = 1; i <= n; i++)
            base *= 10;
        int[] re = new int[base-1];
        for (int i = 1; i < base; i++) {
            re[i-1] = i;
        }
        return re;
    }

    // 18. 删除链表的节点
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    public ListNode deleteNode(ListNode head, int val) {
        ListNode p = head, q = null;
        while (p != null) {
            if (p.val == val && p == head) {
                head = head.next;
                p.next = null;
                break;
            } else if (p.val == val) {
                q.next = p.next;
                p.next = null;
                break;
            } else {
                q = p;
                p = p.next;
            }
        }
        return head;
    }

    // 19. 正则表达式匹配
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for (int j = 2; j <= p.length(); j++) {
            if (p.charAt(j-1) == '*' && dp[0][j-2])
                dp[0][j] = true;
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                // when using charAt, index should minus 1
                if (p.charAt(j-1) == '*') {
                    if (dp[i][j-2] || (dp[i-1][j] && (p.charAt(j-2) == '.' || s.charAt(i-1) == p.charAt(j-2))))
                        dp[i][j] = true;
                } else if (p.charAt(j-1) == '.' || s.charAt(i-1) == p.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    // 21. 调整数组顺序使奇数位于偶数前面
    public int[] exchange(int[] nums) {
        int p1 = -1, p2 = 0;
        while (p2 < nums.length) {
            if ((nums[p2] & 1) == 1) {
                p1++;
                if (p1 < p2) {
                    int tmp = nums[p1];
                    nums[p1] = nums[p2];
                    nums[p2] = tmp;
                }
            }
            p2++;
        }
        return nums;
    }

    // 22. 链表中倒数第k个节点
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode p1 = head, p2 = head;
        for (int i = 1; i < k; i++) {
            p2 = p2.next;
        }
        while (p2.next != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
        return p1;
    }

    // 24. 反转链表
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        ListNode pre = head, cur = head.next, next = null;
        if (cur == null)
            return pre;
        next = cur.next;
        pre.next = null;
        while (true) {
            cur.next = pre;
            pre = cur;
            cur = next;
            if (cur != null)
                next = cur.next;
            else
                break;
        }
        return pre;
    }

    // 25. 合并两个排序的链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p1 = l1, p2 = l2, head = new ListNode(-1), cur = head;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                cur.next = p1;
                p1 = p1.next;
            } else {
                cur.next = p2;
                p2 = p2.next;
            }
            cur = cur.next;
        }
        if (p1 == null)
            cur.next = p2;
        else
            cur.next = p1;
        return head.next;
    }

    // 26. 树的子结构
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null)
            return false;
        if (A.val == B.val && isSame(A.left, B.left) && isSame(A.right, B.right)) {
            return true;
        } else
            return isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    boolean isSame(TreeNode A, TreeNode B) {
        if (B == null)
            return true;
        else if (A == null)
            return false;
        else if (A.val == B.val)
            return (isSame(A.left, B.left) && isSame(A.right, B.right));
        else
            return false;
    }

    // 27. 二叉树的镜像
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode old = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(old);
        return root;
    }

    // 28. 对称的二叉树
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return recur(root.left, root.right);
    }

    boolean recur(TreeNode A, TreeNode B) {
        if (A == null && B == null)
            return true;
        else if (A == null || B == null || A.val != B.val)
            return false;
        return recur(A.left, B.right) && recur(A.right, B.left);
    }

    // 29. 顺时针打印矩阵
    public int[] spiralOrder(int[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return new int[]{};
        int n = matrix[0].length;
        if (n == 0)
            return new int[]{};
        int[] re = new int[m*n];
        int i = 0, j = 0, round = 0, ind = 0;
        while (true) {
            if (j > n-1-round) break;
            for (; j <= n-1-round; j++)
                re[ind++] = matrix[i][j];
            j--;

            i++;
            if (i > m-1-round) break;
            for (; i <= m-1-round; i++)
                re[ind++] = matrix[i][j];
            i--;

            j--;
            if (j < round) break;
            for (; j >= round; j--)
                re[ind++] = matrix[i][j];
            j++;

            i--;
            if (i < 1+round) break;
            for (; i >= 1+round; i--)
                re[ind++] = matrix[i][j];
            i++;
            j++;
            round++;
        }
        return re;
    }

    public List<Integer> spiralOrder2(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) return new ArrayList<>();
        int n = matrix[0].length;
        if (n == 0) return new ArrayList<>();
        List<Integer> re = new ArrayList<>(m*n);
        int t = 0, r = n-1, b = m-1, l = 0, i;
        while (true) {
            for (i = l; i <= r; i++)
                re.add(matrix[t][i]);
            if (++t > b) break;

            for (i = t; i <= b; i++)
                re.add(matrix[i][r]);
            if (--r < l) break;

            for (i = r; i >= l; i--)
                re.add(matrix[b][i]);
            if (--b < t) break;

            for (i = b; i >= t; i--)
                re.add(matrix[i][l]);
            if (++l > r) break;
        }
        return re;
    }

    //  30. 包含min函数的栈
    class MinStack {

        Stack<Integer> stk;
        Stack<Integer> helper;

        /** initialize your data structure here. */
        public MinStack() {
            stk = new Stack<>();
            helper = new Stack<>();
        }

        public void push(int x) {
            stk.push(x);
            if (helper.isEmpty() || x <= helper.peek())
                helper.push(x);
        }

        public void pop() {
            if (stk.peek().equals(helper.peek()))
                helper.pop();
            stk.pop();
        }

        public int top() {
            return stk.peek();
        }

        public int min() {
            return helper.peek();
        }
    }

    // 31. 栈的压入、弹出序列
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length == 0 || pushed.length == 1)
            return true;
        boolean[] visited = new boolean[pushed.length];
        int cur = -1;
        for (int i = 0; i < popped.length; i++) {
            int left = cur-1;
            for (; left >= 0; left--) {
                if (!visited[left]) break;
            }
            if (left >= 0 && pushed[left] == popped[i]) {
                cur = left;
                visited[cur] = true;
            } else {
                int right = cur+1;
                for (; right < pushed.length; right++) {
                    if (pushed[right] == popped[i]) {
                        cur = right;
                        visited[cur] = true;
                        break;
                    }
                }
                if (right == pushed.length)
                    return false;
            }
        }
        return true;
    }

    public boolean validateStackSequences2(int[] pushed, int[] popped) {
        Stack<Integer> stk = new Stack<>();
        if (pushed.length == 0 || pushed.length == 1)
            return true;
        int ind = 0;
        for (int num : pushed) {
            stk.push(num);
            while (!stk.empty() && stk.peek().equals(popped[ind])) {
                stk.pop();
                ind++;
            }
        }
        return stk.empty();
    }

    // 32 - I. 从上到下打印二叉树
    public int[] levelOrder(TreeNode root) {
        if (root == null)
            return new int[]{};
        Queue<TreeNode> q = new LinkedList<>();
        ArrayList<Integer> list = new ArrayList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            list.add(node.val);
            if (node.left != null)
                q.add(node.left);
            if (node.right != null)
                q.add(node.right);
        }
        int[] re = new int[list.size()];
        for (int i = 0; i < list.size(); i++)
            re[i] = list.get(i);
        return re;
    }

    // 32 - II. 从上到下打印二叉树 II
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        ArrayList<List<Integer>> list = new ArrayList<>();
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                l.add(node.val);
                if (node.left != null)
                    q.add(node.left);
                if (node.right != null)
                    q.add(node.right);
            }
            list.add(l);
        }
        return list;
    }

    // 32 - III. 从上到下打印二叉树 III
    public List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        ArrayList<List<Integer>> list = new ArrayList<>();
        Deque<TreeNode> old = new LinkedList<>();
        Deque<TreeNode> even = new LinkedList<>();
        old.add(root);
        while (!old.isEmpty() || !even.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            if (!old.isEmpty()) {
                for (int i = old.size(); i > 0; i--) {
                    TreeNode node = old.pollLast();
                    l.add(node.val);
                    if (node.left != null)
                        even.add(node.left);
                    if (node.right != null)
                        even.add(node.right);
                }
            } else {
                for (int i = even.size(); i > 0; i--) {
                    TreeNode node = even.pollLast();
                    l.add(node.val);
                    if (node.right != null)
                        old.add(node.right);
                    if (node.left != null)
                        old.add(node.left);
                }
            }
            list.add(l);
        }
        return list;
    }

    //  33. 二叉搜索树的后序遍历序列
    public boolean verifyPostorder(int[] postorder) {
        if (postorder == null || postorder.length == 0)
            return true;
        else
            return verify(postorder, 0, postorder.length-1);
    }

    boolean verify(int[] postorder, int l, int r) {
        if (l >= r)
            return true;
        int root = postorder[r];
        int l1 = l-1;
        while (l1 < r-1) {
            if (postorder[l1+1] < root)
                l1++;
            else
                break;
        }
        int ind = l1+1;
        while (ind < r) {
            if (postorder[ind] < root)
                return false;
            ind++;
        }
        return verify(postorder, l, l1) && verify(postorder, l1+1, r-1);
    }

    // 34. 二叉树中和为某一值的路径
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> re = new ArrayList<>();
        if (root == null)
            return re;
        ArrayList<Integer> list = new ArrayList<>();
        backTrack(re, list, root, 0, sum);
        return re;
    }

    void backTrack(List<List<Integer>> re, ArrayList<Integer> list, TreeNode root, int current, int sum) {
        list.add(root.val);
        if (root.left != null) {
            backTrack(re, list, root.left, current+root.val, sum);
        }
        if (root.right != null) {
            backTrack(re, list, root.right, current+root.val, sum);
        }
        if (root.left == null && root.right == null && current+root.val == sum) {
            ArrayList<Integer> l = new ArrayList<>(list);
            re.add(l);
        }
        list.remove(list.size()-1);
    }

    // 35. 复杂链表的复制
//    class Node {
//        int val;
//        Node next;
//        Node random;
//
//        public Node(int val) {
//            this.val = val;
//            this.next = null;
//            this.random = null;
//        }
//    }
//
//    public Node copyRandomList(Node head) {
//        if (head == null) return null;
//        Node newHead = new Node(head.val);
//        Node p1 = head.next, p2 = newHead;
//        HashMap<Node, Node> hmap = new HashMap<>();
//        hmap.put(head, newHead);
//        while (p1 != null) {
//            p2.next = new Node(p1.val);
//            hmap.put(p1, p2.next);
//            p1 = p1.next;
//            p2 = p2.next;
//        }
//        p1 = head;
//        p2 = newHead;
//        while (p1 != null) {
//            if (p1.random == null)
//                p2.random = null;
//            else if (p1.random == p1)
//                p2.random = p2;
//            else {
//                p2.random = hmap.get(p1.random);
//            }
//            p1 = p1.next;
//            p2 = p2.next;
//        }
//        return newHead;
//    }

    // 36. 二叉搜索树与双向链表
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    Node min = null;
    Node max = null;

    public Node treeToDoublyList(Node root) {
        if (root == null)
            return null;
        recur(root);
        min.left = max;
        max.right = min;
        return min;
    }

    void recur(Node root) {
        if (root == null)
            return;
        Node head = null, tail = null;
        if (root.left == null)
            head = root;
        else {
            recur(root.left);
            head = min;
            max.right = root;
            root.left = max;
        }

        if (root.right == null)
            tail = root;
        else {
            recur(root.right);
            tail = max;
            root.right = min;
            min.left = root;
        }
        max = tail;
        min = head;
    }

    // 37. 序列化二叉树
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null)
            return "";
        StringBuilder str = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        str.append(root.val).append(",");
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left != null) {
                queue.add(node.left);
                str.append(node.left.val).append(",");
            } else {
                str.append("null,");
            }

            if (node.right != null) {
                queue.add(node.right);
                str.append(node.right.val).append(",");
            } else {
                str.append("null,");
            }
        }
        return str.substring(0, str.length()-1);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals(""))
            return null;
        String[] nodes = data.split(",");
        TreeNode[] maps = new TreeNode[nodes.length];
        int slow = 0, fast = 1;
        TreeNode head = new TreeNode(Integer.parseInt(nodes[0]));
        maps[0] = head;
        while (fast < nodes.length) {
            if (nodes[slow].equals("null")) {
                slow++;
                continue;
            }
            if (!nodes[fast].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(nodes[fast]));
                maps[fast] = left;
                maps[slow].left = left;
            }
            fast++;
            if (!nodes[fast].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(nodes[fast]));
                maps[fast] = right;
                maps[slow].right = right;
            }
            fast++;
            slow++;
        }
        return head;
    }

    // 38. 字符串的排列
    public String[] permutation(String s) {
        LinkedList<String> list = new LinkedList<>();
        backtrace(list, 0, s.toCharArray());
        return list.toArray(new String[list.size()]);
    }

    void backtrace(LinkedList<String> list, int x, char[] s) {
        if (x == s.length) {
            list.add(new String(s));
            return;
        }
        HashSet<Character> hset = new HashSet<>();
        for (int i = x; i < s.length; i++) {
            if (hset.contains(s[i]))
                continue;
            hset.add(s[i]);
            swap(x, i, s);
            backtrace(list,x+1, s);
            swap(x, i, s);
        }
    }

    void swap(int a, int b, char[] s) {
        char tmp = s[a];
        s[a] = s[b];
        s[b] = tmp;
    }

    // 39. 数组中出现次数超过一半的数字
    public int majorityElement(int[] nums) {
        if (nums.length == 1 || nums.length == 2)
            return nums[0];
        int p1 = 0, p2 = 1, score = 1;
        while (p2 < nums.length) {
            if (nums[p2] != nums[p1])
                score--;
            else
                score++;
            if (score == 0) {
                p1 = p2+1;
                p2 = p1+1;
                score = 1;
            } else
                p2++;
        }
        return nums[p1];
    }

    // 40. 最小的k个数
    public int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0 || k >= arr.length)
            return new int[]{};
        int l = 0, r = arr.length-1;
        int mid = partition(arr, l, r);
        while (mid != k-1) {
            if (mid > k-1)
                r = mid-1;
            else
                l = mid+1;
            mid = partition(arr, l, r);
        }
        int[] re = new int[k];
        System.arraycopy(arr, 0, re, 0, k);
        return re;
    }

    int partition(int[] arr, int l, int r) {
        if (l > r)
            return -1;
        else if (l == r)
            return l;
        int tar = new Random().nextInt(r - l + 1) + l;
        swp(arr, l, tar);
        int p1 = l, p2 = l+1;
        while (p2 <= r) {
            if (arr[l] > arr[p2]) {
                p1++;
                if (p1 != p2) {
                    swp(arr, p1, p2);
                }
            }
            p2++;
        }
        swp(arr, l, p1);
        return p1;
    }

    void swp(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // 41. 数据流中的中位数
    class MedianFinder {

        PriorityQueue<Integer> minHeap;
        PriorityQueue<Integer> maxHeap; // keep this size >= minHeap

        public MedianFinder() {
            minHeap = new PriorityQueue<>();
            maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        }

        public void addNum(int num) {
            if (minHeap.size() == maxHeap.size()) {
                minHeap.add(num);
                maxHeap.add(minHeap.poll());
            } else {
                maxHeap.add(num);
                minHeap.add(maxHeap.poll());
            }
        }

        public double findMedian() {
            if (minHeap.size() == maxHeap.size()) {
                return (double)(minHeap.peek() + maxHeap.peek()) / 2.0;
            } else
                return maxHeap.peek();
        }
    }

    // 42. 连续子数组的最大和
    public int maxSubArray(int[] nums) {
        int a = nums[0], maxsum = a;
        for (int i = 1; i < nums.length; i++) {
            a = a > 0 ? a+nums[i] : nums[i];
            maxsum = Math.max(maxsum, a);
        }
        return maxsum;
    }

    // 43. 1～n 整数中 1 出现的次数
    public int countDigitOne(int n) {
        int low = 0, cur = n%10, digit = 1, high = n/10, sum = 0;
        while (n/digit > 0) {
            switch (cur) {
                case 0:
                    sum += high * digit;
                    break;
                case 1:
                    sum += high * digit + low + 1;
                    break;
                default:
                    sum += (high+1) * digit;
            }
            cur = high % 10;
            high /= 10;
            digit *= 10;
            low = n % digit;
        }
        return sum;
    }
}
