package com.isswhu;

import java.util.*;

public class JiJing {
    public ListNode ReverseList(ListNode head) {
        ListNode dummy = new ListNode(-1), p = null;
        while (head != null) {
            p = head.next;
            head.next = dummy.next;
            dummy.next = head;
            head = p;
        }
        return dummy.next;
    }

    public boolean hasCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null)
                return false;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
                return true;
        }
    }

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode() {

        }
        TreeNode(int v) {
            val = v;
        }
    }

    public int[][] threeOrders (TreeNode root) {
        Stack<TreeNode> stk = new Stack<>();
        TreeNode p = root;
        ArrayList<Integer> nums = new ArrayList<>(), nums2 = new ArrayList<>();
        while (!stk.empty() || p != null) {
            while (p != null) {
                nums.add(p.val);
                stk.push(p);
                p = p.left;
            }
            TreeNode n = stk.pop();
            nums2.add(n.val);
            p = n.right;
        }
        int len = nums.size();
        int[][] re = new int[3][len];
        for (int i = 0; i < len; i++) {
            re[0][i] = nums.get(i);
            re[1][i] = nums2.get(i);
        }

        p = root;
        LinkedList<Integer> l = new LinkedList<>();
        while (!stk.empty() || p != null) {
            while (p != null) {
                l.addFirst(p.val);
                stk.push(p);
                p = p.right;
            }
            p = stk.pop().left;
        }
        int i = 0;
        for (int num : l) {
            re[2][i++] = num;
        }
        return re;
    }

    public ListNode mergeTwoLists (ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1), head = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                head.next = l1;
                head = l1;
                l1 = l1.next;
            } else {
                head.next = l2;
                head = l2;
                l2 = l2.next;
            }
        }
        if (l1 == null)
            head.next = l2;
        else
            head.next = l1;
        return dummy.next;
    }

    public int JumpFloor(int target) {
        if (target == 1)
            return 1;
        int[] dp = new int[target+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= target; i++)
            dp[i] = dp[i-1]+dp[i-2];
        return dp[target];
    }

    public int maxsumofSubarray (int[] arr) {
        int a = arr[0];
        int re = a;
        for (int i = 1; i < arr.length; i++) {
            if (a > 0)
                a = a+arr[i];
            else
                a = arr[i];
            re = Math.max(re, a);
        }
        return re;
    }

    public int maxLength (int[] arr) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        int s = 0, e = 0, re = 0;
        for (; e < arr.length; e++) {
            if (hmap.containsKey(arr[e])) {
                s = Math.max(s, hmap.get(arr[e])+1);
            }
            hmap.put(arr[e], e);
            re = Math.max(re, e-s+1);
        }
        return re;
    }

    public void merge(int A[], int m, int B[], int n) {
        int e = m+n-1, p1 = m-1, p2 = n-1;
        while (p1 >= 0 && p2 >= 0) {
            if (A[p1] > B[p2]) {
                A[e--] = A[p1--];
            } else {
                A[e--] = B[p2--];
            }
        }
        if (p1 < 0) {
            while (p2 >= 0)
                A[e--] = B[p2--];
        }
    }

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        for (int i = m-1; i >= 0; i--) {
            nums1[i+n] = nums1[i];
        }
        int p1 = n, p2 = 0, s = 0;
        while (p1 < m+n && p2 < n) {
            if (nums1[p1] < nums2[p2]) {
                nums1[s++] = nums1[p1++];
            } else {
                nums1[s++] = nums2[p2++];
            }
        }
        if (p1 == m+n) {
            while (p2 < n)
                nums1[s++] = nums2[p2++];
        }
    }

    public String solve (String s, String t) {
        StringBuilder sb = new StringBuilder();
        int n = Math.max(s.length(), t.length()), p1 = s.length()-1, p2 = t.length()-1, add = 0;
        for (int i = 0; i < n; i++) {
            int a = p1 >= 0 ? s.charAt(p1--)-'0' : 0;
            int b = p2 >= 0 ? t.charAt(p2--)-'0' : 0;
            int num = a+b+add;
            add = num / 10;
            num = num % 10;
            sb.append(num);
        }
        if (add != 0)
            sb.append(add);
        return sb.reverse().toString();
    }

    public ArrayList<ArrayList<Integer>> zigzagLevelOrder (TreeNode root) {
        int level = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        ArrayList<ArrayList<Integer>> re = new ArrayList<>();
        if (root == null)
            return re;
        queue.add(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            ArrayList<Integer> l = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
                l.add(node.val);
            }
            if (level % 2 == 0) {
                ArrayList<Integer> l2 = new ArrayList<>();
                for (int i = l.size()-1; i >= 0; i--)
                    l2.add(l.get(i));
                re.add(l2);
            } else
                re.add(l);
            level++;
        }
        return re;
    }

    public String LCS (String str1, String str2) {
        int[][] dp = new int[str1.length()][str2.length()];
        char[] s1 = str1.toCharArray(), s2 = str2.toCharArray();
        int re = 0, ind = -1;
        for (int i = 0; i < s1.length; i++) {
            if (s1[i] == s2[0])
                dp[i][0] = 1;
            if (dp[i][0] > re) {
                re = 1;
                ind = i;
            }
        }
        for (int i = 0; i < s2.length; i++) {
            if (s2[i] == s1[0])
                dp[0][i] = 1;
            if (dp[0][i] > re) {
                re = 1;
                ind = 0;
            }
        }
        for (int i = 1; i < s1.length; i++) {
            for (int j = 1; j < s2.length; j++) {
                if (s1[i] == s2[j])
                    dp[i][j] = 1+dp[i-1][j-1];
                if (dp[i][j] > re) {
                    re = dp[i][j];
                    ind = i;
                }
            }
        }
        if (re == 0)
            return "";
        else
            return str1.substring(ind-re+1, ind+1);
    }

    public ListNode addInList (ListNode head1, ListNode head2) {
        Deque<ListNode> stk1 = new LinkedList<>(), stk2 = new LinkedList<>();
        ListNode p = head1;
        while (p != null) {
            stk1.push(p);
            p = p.next;
        }
        p = head2;
        while (p != null) {
            stk2.push(p);
            p = p.next;
        }
        int carry = 0;
        ListNode head = new ListNode(-1);
        while (!stk1.isEmpty() || !stk2.isEmpty()) {
            ListNode n1 = stk1.isEmpty() ? null : stk1.pop(), n2 = stk2.isEmpty() ? null : stk2.pop();
            int a = n1 == null ? 0 : n1.val, b = n2 == null ? 0 : n2.val;
            int sum = a+b+carry;
            int val = sum % 10;
            carry = sum / 10;
            ListNode cur;
            if (n1 != null) {
                cur = n1;
                cur.val = val;
            }
            else
                cur = new ListNode(val);
            cur.next = head.next;
            head.next = cur;
        }
        if (carry != 0) {
            ListNode cur = new ListNode(1);
            cur.next = head.next;
            head.next = cur;
        }
        return head.next;
    }

    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return new ArrayList<>();
        int n = matrix[0].length;
        int t = 0, b = m-1, l = 0, r = n-1;
        ArrayList<Integer> re = new ArrayList<>();
        while (true) {
            if (t <= b) {
                for (int i = l; i <= r; i++)
                    re.add(matrix[t][i]);
                t++;
            } else
                break;

            if (l <= r) {
                for (int i = t; i <= b; i++)
                    re.add(matrix[i][r]);
                r--;
            } else
                break;

            if (t <= b) {
                for (int i = r; i >= l; i--)
                    re.add(matrix[b][i]);
                b--;
            } else
                break;

            if (l <= r) {
                for (int i = b; i >= t; i--)
                    re.add(matrix[i][l]);
                l++;
            } else
                break;
        }
        return re;
    }

    public int lowestCommonAncestor (TreeNode root, int o1, int o2) {
        return find(root, o1, o2).val;
    }

    TreeNode find(TreeNode root, int o1, int o2) {
        if (root == null)
            return null;
        if (root.val == o1 || root.val == o2)
            return root;
        TreeNode left = find(root.left, o1, o2), right = find(root.right, o1, o2);
        if (left != null && right != null)
            return root;
        else if (left != null)
            return left;
        else if (right != null)
            return right;
        return null;
    }

    public int sqrt (int x) {
        int l = 0, h = x, ans = -1;
        while (l <= h) {
            int mid = l + (h-l)/2;
            long re = (long)mid * mid;
            if (re == x)
                return mid;
            else if (re > x) {
                h = mid-1;
            } else {
                ans = mid;
                l = mid+1;
            }
        }
        return ans;
    }

    public int findKth(int[] a, int n, int K) {
        int ind = n - K, l = 0, h = n-1, re;
        Random rand = new Random();
        while (true) {
            int base = rand.nextInt(h-l+1) + l;
            int tmp = a[base];
            a[base] = a[l];
            a[l] = tmp;
            re = partition(a, l, h);
            if (re == ind)
                return a[ind];
            else if (re < ind) {
                l = re+1;
            } else {
                h = re-1;
            }
        }
    }

    int partition(int[] a, int l, int h) {
        int pre = l, cur = l+1;
        while (cur <= h) {
            if (a[cur] < a[l]) {
                pre++;
                if (pre != cur) {
                    int tmp = a[pre];
                    a[pre] = a[cur];
                    a[cur] = tmp;
                }
            }
            cur++;
        }
        int tmp = a[pre];
        a[pre] = a[l];
        a[l] = tmp;
        return pre;
    }

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        return cons(pre, 0, pre.length-1, in, 0, in.length-1);
    }

    TreeNode cons(int[] pre, int l1, int h1, int[] in, int l2, int h2) {
        if (l1 > h1)
            return null;
        TreeNode root = new TreeNode(pre[l1]);
        int i = l2;
        for (; i <= h2; i++) {
            if (in[i] == pre[l1])
                break;
        }
        root.left = cons(pre, l1+1, l1+i-l2, in, l2, i-1);
        root.right = cons(pre, l1+i-l2+1, h1, in, i+1, h2);
        return root;
    }

    public int solution(int N) {
        if (N == 0)
            return 0;
        int l = 0;
        long fl = 0;
        while (fl <= N) {
            l++;
            fl += l;
        }
        return l;
    }

    public int solution(int[] A, int[] B, int N) {
        int[] cnt = new int[N+1];
        for (int i = 1; i <= N; i++) {
            cnt[i] = 0;
        }
        int M = A.length;
        for (int i = 0; i < M; i++) {
            cnt[A[i]] += 1;
            cnt[B[i]] += 1;
        }
        int re = 0;
        for (int i = 0; i < M; i++) {
            re = Math.max(cnt[A[i]]+cnt[B[i]]-1, re);
        }
        return re;
    }

    public int solution(int[] A, int M) {
        if (A.length == 0)
            return 0;
        else if (M == 1)
            return A.length;

        int n = A.length;
        Arrays.sort(A);
        if (A[n-1]-A[0] < M)
            return 1;

        // if a subset is divisible by M, all points in the subset must have the same remainder when (A[i]-A[0]) % M
        int[] arr = new int[M];
        for (int i = 1; i < n; i++) {
            int ind = (A[i]-A[0]) % M;
            arr[ind] += 1;
        }
        // every entry represents a kind of remainder, and represents the number of points who have the same remainder, that is, a subset. The largest one is the largest subset.
        int re = 0;
        for (int i = 0; i < M; i++)
            re = Math.max(re, arr[i]);
        return re;

        // time complexity: O(NlogN)
        // space:
    }
}