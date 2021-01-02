package com.isswhu;

import java.lang.reflect.Array;
import java.util.*;

public class BasicDataStructure {
    // 15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        LinkedList<List<Integer>> re = new LinkedList<>();
        if (nums.length < 3)
            return re;
        Arrays.sort(nums);
        for (int p1 = 0; p1 <= nums.length-3; p1++) {
            if (nums[p1] > 0)
                break;
            if (p1-1 >= 0 && nums[p1-1] == nums[p1])
                continue;
            int target = -nums[p1];
            int l = p1+1, r = nums.length-1;
            while (l < r) {
                if (nums[l] + nums[r] == target) {
                    LinkedList<Integer> lst = new LinkedList<>();
                    lst.add(nums[p1]);
                    lst.add(nums[l]);
                    lst.add(nums[r]);
                    re.add(lst);
                    l++;
                    r--;
                } else if (nums[l] + nums[r] > target) {
                    r--;
                } else {
                    l++;
                }

                while (l > p1 + 1 && l < r && nums[l] == nums[l - 1])
                    l++;
                while (r < nums.length - 1 && l < r && nums[r] == nums[r + 1])
                    r--;
            }
        }
        return re;
    }

    // 18. 四数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {
        LinkedList<List<Integer>> re = new LinkedList<>();
        if (nums.length < 4)
            return re;
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length-4; i++) {
            if (i > 0 && nums[i] == nums[i-1])
                continue;
            if (target - nums[i] < 3*nums[i])
                break;
            int target2 = target-nums[i];
            for (int j = i+1; j <= nums.length-3; j++) {
                if (j > i+1 && nums[j] == nums[j-1])
                    continue;
                if (target2-nums[j] < 2*nums[j])
                    break;
                int target3 = target2-nums[j], l = j+1, r = nums.length-1;
                while (l < r) {
                    if (nums[l] + nums[r] == target3) {
                        re.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l+1] == nums[l]) l++;
                        while (l < r && nums[r-1] == nums[r]) r--;
                        l++; r--;
                    } else if (nums[l] + nums[r] > target3) {
                        r--;
                    } else {
                        l++;
                    }
                }
            }
        }
        return re;
    }

    // 48 Rotate Image
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for (int j = 0; j < n/2; j++) {
            for (int i = 0; i < n; i++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n-j-1];
                matrix[i][n-j-1] = temp;
            }
        }
    }

    // 84 Largest Rectangle in Histogram
    public static int largestRectangleArea(int[] heights) {
        Stack<Integer> stk = new Stack<>();
        stk.push(-1);
        int[] lefts = new int[heights.length];
        int[] rights = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            while (stk.peek() != -1 && heights[stk.peek()] >= heights[i])
                stk.pop();
            lefts[i] = stk.peek();
            stk.push(i);
        }
        stk.clear();
        stk.push(heights.length);
        for (int j = heights.length-1; j >= 0; j--) {
            while (stk.peek() != heights.length && heights[stk.peek()] >= heights[j])
                stk.pop();
            rights[j] = stk.peek();
            stk.push(j);
        }

        int maxnum = 0;
        for (int i = 0; i < heights.length; i++) {
            int area = heights[i] * (rights[i]-lefts[i]-1);
            if (area > maxnum)
                maxnum = area;
        }
        return maxnum;
    }

    // 150. 逆波兰表达式求值
    public int evalRPN(String[] tokens) {
        Integer[] stk = new Integer[tokens.length/2+1];
        int top = 0;
        for (String s : tokens) {
            if (Character.isDigit(s.charAt(0)) || s.charAt(0) == '-' && s.length() > 1) {
                stk[top++] = Integer.valueOf(s);
            } else {
                Integer a = stk[--top], b = stk[--top], re = 0;
                switch (s) {
                    case "+":
                        re = b+a;
                        break;
                    case "-":
                        re = b-a;
                        break;
                    case "*":
                        re = b*a;
                        break;
                    default:
                        re = b/a;
                }
                stk[top++] = re;
            }
        }
        return stk[0];
    }

    // 208 实现 Trie (前缀树)
    class Trie {

        TreeNode root;

        class TreeNode {
            boolean end;
            TreeNode[] map;

            TreeNode() {
                end = false;
                map = new TreeNode[26];
            }
        }

        /** Initialize your data structure here. */
        public Trie() {
            root = new TreeNode();
        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            char[] chs = word.toCharArray();
            TreeNode cur = root;
            for (int i = 0; i < chs.length; i++) {
                int ind = chs[i] - 'a';
                if (cur.map[ind] == null) {
                    cur.map[ind] = new TreeNode();
                }
                cur = cur.map[ind];
            }
            cur.end = true;
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            char[] chs = word.toCharArray();
            TreeNode cur = root;
            for (int i = 0; i < chs.length; i++) {
                int ind = chs[i] - 'a';
                if (cur.map[ind] == null) {
                    return false;
                }
                cur = cur.map[ind];
            }
            return cur.end;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            char[] chs = prefix.toCharArray();
            TreeNode cur = root;
            for (int i = 0; i < chs.length; i++) {
                int ind = chs[i] - 'a';
                if (cur.map[ind] == null) {
                    return false;
                }
                cur = cur.map[ind];
            }
            return true;
        }
    }

    // 211. 添加与搜索单词 - 数据结构设计
    class WordDictionary {

        TreeNode root;

        class TreeNode {
            boolean end;
            TreeNode[] map;

            TreeNode() {
                end = false;
                map = new TreeNode[26];
            }
        }

        /** Initialize your data structure here. */
        public WordDictionary() {
            root = new TreeNode();
        }

        /** Adds a word into the data structure. */
        public void addWord(String word) {
            char[] chs = word.toCharArray();
            TreeNode cur = root;
            for (int i = 0; i < chs.length; i++) {
                int ind = chs[i] - 'a';
                if (cur.map[ind] == null) {
                    cur.map[ind] = new TreeNode();
                }
                cur = cur.map[ind];
            }
            cur.end = true;
        }

        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            return backtrace(root, word.toCharArray(), 0);
        }

        boolean backtrace(TreeNode start, char[] chs, int s) {
            TreeNode cur = start;
            for (int i = s; i < chs.length; i++) {
                if (chs[i] == '.') {
                    for (int j = 0; j < 26; j++) {
                        if (cur.map[j] != null && backtrace(cur.map[j], chs, i+1))
                            return true;
                    }
                    return false;
                } else {
                    int ind = chs[i] - 'a';
                    if (cur.map[ind] == null) {
                        return false;
                    }
                    cur = cur.map[ind];
                }
            }
            return cur.end;
        }
    }

    // 212. 单词搜索 II
    class TreeNode {
        String wd;
        TreeNode[] map;
        int path;

        TreeNode() {
            wd = null;
            map = new TreeNode[26];
            path = 0;
        }
    }

    List<String> re212;

    public List<String> findWords(char[][] board, String[] words) {
        TreeNode root = new TreeNode();
        for (String wd : words) {
            TreeNode cur = root;
            char[] chs = wd.toCharArray();
            for (char c : chs) {
                int ind = c - 'a';
                if (cur.map[ind] == null) {
                    cur.map[ind] = new TreeNode();
                    cur.path++;
                }
                cur = cur.map[ind];
            }
            cur.wd = wd;
        }

        re212 = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                backtrack(root, board, i, j);
            }
        }
        return re212;
    }

    void backtrack(TreeNode root, char[][] board, int i, int j) {
        if (!(i >= 0 && i < board.length && j >= 0 && j < board[0].length && board[i][j] != '#'))
            return;
        int ind = board[i][j] - 'a';
        if (root.map[ind] == null) {
            return;
        }
        TreeNode next = root.map[ind];
        if (next.wd != null) {
            re212.add(next.wd);
            next.wd = null;
        }

        board[i][j] = '#';
        backtrack(next, board, i-1, j);
        backtrack(next, board, i+1, j);
        backtrack(next, board, i, j-1);
        backtrack(next, board, i, j+1);
        board[i][j] = (char) (ind + 'a');

        if (next.wd == null && next.path == 0) {
            root.map[ind] = null;
            root.path--;
        }
    }

    // 225. 用队列实现栈
    class MyStack {
        Queue<Integer> queue = new LinkedList<>();

        /** Initialize your data structure here. */
        public MyStack() {

        }

        /** Push element x onto stack. */
        public void push(int x) {
            queue.add(x);
            for (int i = 0; i < queue.size()-1; i++) {
                queue.add(queue.poll());
            }
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            return queue.poll();
        }

        /** Get the top element. */
        public int top() {
            return queue.peek();
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queue.isEmpty();
        }
    }

    // 232. 用栈实现队列
    class MyQueue {

        Stack<Integer> stk1;
        Stack<Integer> stk2;

        /** Initialize your data structure here. */
        public MyQueue() {
            stk1 = new Stack<>();
            stk2 = new Stack<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            stk1.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            if (stk2.isEmpty()) {
                while (!stk1.isEmpty())
                    stk2.push(stk1.pop());
            }
            return stk2.pop();
        }

        /** Get the front element. */
        public int peek() {
            if (stk2.isEmpty()) {
                while (!stk1.isEmpty())
                    stk2.push(stk1.pop());
            }
            return stk2.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return stk1.isEmpty() && stk2.isEmpty();
        }
    }

    // 237. 删除链表中的节点
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
