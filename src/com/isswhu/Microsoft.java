package com.isswhu;

import java.util.*;

public class Microsoft {
    // 316. 去除重复字母
    public String removeDuplicateLetters(String s) {
        int[] mp = new int[128];
        char[] chs = s.toCharArray();
        HashSet<Character> hset = new HashSet<>();
        for (char c : chs)
            mp[c] += 1;
        Deque<Character> deque = new LinkedList<>();
        for (char c : chs) {
            if (hset.contains(c)) {
                mp[c] -= 1;
                continue;
            }
            while (!deque.isEmpty() && deque.peek() > c && mp[deque.peek()] > 1) {
                hset.remove(deque.peek());
                mp[deque.pop()] -= 1;
            }
            deque.push(c);
            hset.add(c);
        }
        StringBuilder re = new StringBuilder();
        for (int i = 0; i < hset.size(); i++)
            re.append(deque.pollLast());
        return re.toString();
    }

    // 321. 拼接最大数
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i <= k; i++) {
            if (i > m || k-i > n)
                continue;
            ArrayList<Integer> arr1 = pickUp(nums1, i);
            ArrayList<Integer> arr2 = pickUp(nums2, k-i);
            ArrayList<Integer> arr = merge(arr1, arr2);
            if (res.size() == 0)
                res = arr;
            else {
                for (int j = 0; j <= k; j++) {
                    if (res.get(j) > arr.get(j)) {
                        break;
                    } else if (res.get(j) < arr.get(j)) {
                        res = arr;
                        break;
                    }
                }
            }
        }
        int[] re = new int[k];
        for (int i = 0; i < k; i++) {
            re[i] = res.get(i);
        }
        return re;
    }

    ArrayList<Integer> pickUp(int[] nums, int n) {
        ArrayList<Integer> re = new ArrayList<>();
        Deque<Integer> deque = new LinkedList<>();
        int k = nums.length - n;
        for (int num : nums) {
            while (!deque.isEmpty() && deque.peek() < num && k > 0) {
                deque.pop();
                k--;
            }
            deque.push(num);
        }
        for (int i = 0; i < n; i++)
            re.add(deque.pollLast());
        return re;
    }

    ArrayList<Integer> merge(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
        ArrayList<Integer> re = new ArrayList<>();
        int ind1 = 0, ind2 = 0;
        while (ind1 < arr1.size() && ind2 < arr2.size()) {
            if (arr1.get(ind1) > arr2.get(ind2)) {
                re.add(arr1.get(ind1));
                ind1++;
            } else if (arr1.get(ind1) < arr2.get(ind2)) {
                re.add(arr2.get(ind2));
                ind2++;
            } else {
                int tmp1 = ind1+1, tmp2 = ind2+1;
                while (tmp1 < arr1.size() && tmp2 < arr2.size()
                        && arr1.get(tmp1).equals(arr2.get(tmp2))) {
                    tmp1++;
                    tmp2++;
                }
                if (tmp1 >= arr1.size()) {
                    re.add(arr2.get(ind2));
                    ind2++;
                } else if (tmp2 >= arr2.size() || arr1.get(tmp1) > arr2.get(tmp2)) {
                    re.add(arr1.get(ind1));
                    ind1++;
                } else {
                    re.add(arr2.get(ind2));
                    ind2++;
                }
            }
        }
        if (ind1 >= arr1.size()) {
            while (ind2 < arr2.size()) {
                re.add(arr2.get(ind2));
                ind2++;
            }
        } else {
            while (ind1 < arr1.size()) {
                re.add(arr1.get(ind1));
                ind1++;
            }
        }
        return re;
    }

    // 322. 零钱兑换
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        for (int i = 0; i < amount+1; i++)
            dp[i] = -2;
        Arrays.sort(coins);
        return getNum(coins, dp, amount);
    }

    int getNum(int[] coins, int[] dp, int target) {
        if (target < 0)
            return -1;
        else if (target == 0)
            return 0;
        if (dp[target] != -2)
            return dp[target];

        int re = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int num = getNum(coins, dp, target-coins[i]);
            if (num != -1 && num+1 < re)
                re = num+1;
        }
        if (re == Integer.MAX_VALUE)
            re = -1;
        dp[target] = re;
        return re;
    }

    // 328. 奇偶链表
    public ListNode oddEvenList(ListNode head) {
        ListNode dummy_odd = new ListNode(-1), dummy_even = new ListNode(-1);
        ListNode odd = dummy_odd, even = dummy_even, cur = head;
        int ind = 1;
        while (cur != null) {
            if (ind % 2 == 1) {
                odd.next = cur;
                odd = cur;
            } else {
                even.next = cur;
                even = cur;
            }
            cur = cur.next;
            ind++;
        }
        even.next = null;
        odd.next = dummy_even.next;
        return dummy_odd.next;
    }

    // 344. 反转字符串
    public void reverseString(char[] s) {
        if (s.length == 0 || s.length == 1)
            return;
        for (int i = 0; i <= (s.length-1)/2; i++) {
            char tmp = s[i];
            s[i] = s[s.length-i-1];
            s[s.length-i-1] = tmp;
        }
    }

    // 347. 前 K 个高频元素


    // 402. 移掉K位数字
    public String removeKdigits(String num, int k) {
        Deque<Character> stk = new LinkedList<>();
        for(char c : num.toCharArray()) {
            while (!stk.isEmpty() && stk.peek() > c && k > 0) {
                stk.pop();
                k--;
            }
            if (!(stk.isEmpty() && c == '0'))
                stk.push(c);
        }
        while (k > 0 && !stk.isEmpty()) {
            stk.pop();
            k--;
        }
        StringBuilder re = new StringBuilder();
        while (!stk.isEmpty())
            re.append(stk.pollLast());
        if (re.length() == 0)
            re.append(0);
        return re.toString();
    }

    // 445. 两数相加 II
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);
        ListNode dummy = new ListNode(-1);
        int add = 0;
        while(l1 != null || l2 != null) {
            int val1 = l1 == null ? 0 : l1.val, val2 = l2 == null ? 0 : l2.val;
            int val = (val1+val2+add) % 10;
            add = (val1+val2+add) / 10;
            ListNode cur = new ListNode(val);
            cur.next = dummy.next;
            dummy.next = cur;
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        if (add == 1) {
            ListNode cur = new ListNode(1);
            cur.next = dummy.next;
            dummy.next = cur;
        }
        return dummy.next;
    }

    ListNode reverse(ListNode head) {
        ListNode dummy = new ListNode(0);
        while (head != null) {
            ListNode tmp = head;
            head = head.next;
            tmp.next = dummy.next;
            dummy.next = tmp;
        }
        return dummy.next;
    }

    // 450. 删除二叉搜索树中的节点
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

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            TreeNode candidate;
            if (root.left == null && root.right != null) {
                candidate = root.right;
                while (candidate.left != null)
                    candidate = candidate.left;
                root.val = candidate.val;
                root.right = deleteNode(root.right, candidate.val);
            } else if (root.left != null){
                candidate = root.left;
                while (candidate.right != null)
                    candidate = candidate.right;
                root.val = candidate.val;
                root.left = deleteNode(root.left, candidate.val);
            } else {
                return null;
            }
        }

        return root;
    }

    // 557. 反转字符串中的单词 III
    public String reverseWords(String s) {
        int a = 0, b = 0;
        StringBuilder re = new StringBuilder();
        while (b < s.length()) {
            while (b < s.length() && s.charAt(b) != ' ')
                b++;
            for (int i = b-1; i >= a; i--)
                re.append(s.charAt(i));
            if (b < s.length())
                re.append(' ');
            b++;
            a = b;
        }
        return re.toString();
    }
}
