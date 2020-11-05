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
}
