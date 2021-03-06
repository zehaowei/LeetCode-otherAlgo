package com.isswhu;

import java.util.*;

public class Other {
    // 6. Z 字形变换
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;
        ArrayList<StringBuilder> strs = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            strs.add(new StringBuilder());
        }
        int ind = 0;
        while (ind < s.length()) {
            for (int i = 0; ind < s.length() && i < numRows; i++) {
                strs.get(i).append(s.charAt(ind++));
            }
            for (int i = numRows-2; ind < s.length() && i >= 1; i--) {
                strs.get(i).append(s.charAt(ind++));
            }
        }
        StringBuilder re = new StringBuilder();
        for (StringBuilder sb : strs)
            re.append(sb);
        return re.toString();
    }

    // 12. 整数转罗马数字
    public String intToRoman2(int num) {
        int[] digits = new int[4];
        for (int i = 0; i < 4; i++) {
            digits[i] = num % 10;
            num /= 10;
        }

        StringBuilder re = new StringBuilder();
        for (int i = 0; i < digits[3]; i++)
            re.append("M");

        if (digits[2] == 4)
            re.append("CD");
        else if (digits[2] == 9)
            re.append("CM");
        else if (digits[2] > 0 && digits[2] < 4) {
            for (int i = 0; i < digits[2]; i++)
                re.append("C");
        } else if (digits[2] != 0){
            re.append("D");
            for (int i = 0; i < digits[2]-5; i++)
                re.append("C");
        }

        if (digits[1] == 4)
            re.append("XL");
        else if (digits[1] == 9)
            re.append("XC");
        else if (digits[1] > 0 && digits[1] < 4) {
            for (int i = 0; i < digits[1]; i++)
                re.append("X");
        } else if (digits[1] != 0){
            re.append("L");
            for (int i = 0; i < digits[1]-5; i++)
                re.append("X");
        }

        if (digits[0] == 4)
            re.append("IV");
        else if (digits[0] == 9)
            re.append("IX");
        else if (digits[0] > 0 && digits[0] < 4) {
            for (int i = 0; i < digits[0]; i++)
                re.append("I");
        } else if (digits[0] != 0){
            re.append("V");
            for (int i = 0; i < digits[0]-5; i++)
                re.append("I");
        }

        return re.toString();
    }

    // 23. 合并K个升序链表
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        return mergeSort(lists, 0, lists.length-1);
    }

    ListNode mergeSort(ListNode[] lists, int s, int e) {
        if (s == e)
            return lists[s];

        int mid = s + (e-s)/2;
        ListNode l1 = mergeSort(lists, s, mid), l2 = mergeSort(lists, mid+1, e), dummy = new ListNode(-1);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        if (l1 == null)
            cur.next = l2;
        else
            cur.next = l1;
        return dummy.next;
    }

    // 24. 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy, p1 = head, p2 = head.next;
        while (p1 != null && p2 != null) {
            p1.next = p2.next;
            p2.next = p1;
            pre.next = p2;

            pre = p1;
            p1 = p1.next;
            if (p1 != null)
                p2 = p1.next;
        }
        return dummy.next;
    }

    // 25. K 个一组翻转链表
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1)
            return head;
        ListNode dummy = new ListNode(-1, head);
        ListNode p = head, pre = dummy;
        int num = 0;
        while (p != null) {
            num++;
            if (num == k) {
                ListNode start = pre.next, nextStart = p.next;
                pre.next = reverse(start, p);
                pre = start;
                pre.next = nextStart;
                p = nextStart;
                num = 0;
            } else
                p = p.next;
        }
        return dummy.next;
    }

    ListNode reverse(ListNode head, ListNode tail) {
        ListNode headnew = null, p = head, tailNext = tail.next;;
        while (p != tailNext) {
            ListNode tmp = p.next;
            p.next = headnew;
            headnew = p;
            p = tmp;
        }
        return headnew;
    }

    // 31. 下一个排列
    public void nextPermutation(int[] nums) {
        int len = nums.length, candi = -1;
        for (int i = len-2; i >= 0; i--) {
            if (nums[i] < nums[i+1]) {
                candi = i;
                break;
            }
        }
        if (candi != -1) {
            int cur = candi+1;
            while (cur < len) {
                if (nums[cur] > nums[candi]) {
                    cur++;
                } else {
                    break;
                }
            }
            cur--;
            int tmp = nums[cur];
            nums[cur] = nums[candi];
            nums[candi] = tmp;
        }
        int l = candi+1, h = len-1;
        while (l < h) {
            int tmp = nums[h];
            nums[h] = nums[l];
            nums[l] = tmp;
            l++;
            h--;
        }
    }

    // 38. 外观数列
    public String countAndSay(int n) {
        if (n == 1)
            return "1";
        String s = countAndSay(n-1);
        char[] chs = s.toCharArray();
        StringBuilder re = new StringBuilder();
        int cur = 0, times = 1;
        while (cur < chs.length) {
            if (cur < chs.length-1 && chs[cur] == chs[cur+1]) {
                times++;
            } else {
                re.append(times).append(chs[cur]);
                times = 1;
            }
            cur++;
        }
        return re.toString();
    }

    // 41. 缺失的第一个正数
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[i] != i+1) {
                int num = nums[i];
                int tmp =  nums[num-1];
                if (num == tmp)
                    break;
                nums[num-1] = num;
                nums[i] = tmp;
            }
        }
        for (int i = 1; i <= n; i++) {
            if (nums[i-1] != i)
                return i;
        }
        return n+1;
    }

    // 42. 接雨水
    public int trap2(int[] height) {
        int sum = 0, l = 0, h = 1;
        int maxNum = 0, maxInd = -1;
        while (l < height.length-1) {
            if (h >= height.length) {
                for (int s = l+1; s <= maxInd-1; s++) {
                    sum += maxNum - height[s];
                }
                l = maxInd;
                h = maxInd+1;
                maxNum = 0;
                maxInd = -1;
                continue;
            }
            if (height[h] >= maxNum) {
                maxNum = height[h];
                maxInd = h;
            }
            if (height[h] >= height[l]) {
                for (int s = l+1; s <= h-1; s++) {
                    sum += height[l] - height[s];
                }
                l = h;
                maxNum = 0;
                maxInd = -1;
            }
            h++;
        }
        return sum;
    }

    public int trap(int[] height) {
        if (height.length <= 2)
            return 0;
        int left = 1, right = height.length - 2;
        int maxLeft = height[0], maxRight = height[height.length-1];
        int sum = 0;
        while (left <= right) {
            if (maxLeft < maxRight) {
                sum += Math.max(maxLeft-height[left], 0);
                left++;
                maxLeft = Math.max(maxLeft, height[left-1]);
            } else {
                sum += Math.max(maxRight-height[right], 0);
                right--;
                maxRight = Math.max(maxRight, height[right+1]);
            }
        }
        return sum;
    }

    // 43. 字符串相乘
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0"))
            return "0";
        if (num2.length() < num1.length()) {
            String tmp = num1;
            num1 = num2;
            num2 = tmp;
        }

        String re = "";
        for (int i = num1.length()-1; i >= 0; i--) {
            StringBuilder str1 = new StringBuilder();
            int remain = 0;
            for (int j = 0; j < num1.length()-1-i; j++)
                str1.append(0);
            for (int j = num2.length()-1; j >= 0; j--) {
                int a = num1.charAt(i) - '0', b = num2.charAt(j) - '0';
                int sum = a*b+remain;
                int num = sum % 10;
                remain = sum / 10;
                str1.append(num);
            }
            if (remain != 0)
                str1.append(remain);
            re = add(re, str1.reverse().toString());
        }

        return re;
    }

    String add(String s1, String s2) {
        if (s1.equals(""))
            return s2;
        int remain = 0;
        StringBuilder re = new StringBuilder();
        for (int i = s1.length()-1, j = s2.length()-1;
             i >= 0 || j >= 0 || remain > 0;
             i--, j--) {
            int a = i < 0 ? 0 : s1.charAt(i) - '0';
            int b = j < 0 ? 0 : s2.charAt(j) - '0';
            int sum = a+b+remain;
            int num = sum % 10;
            remain = sum / 10;
            re.append(num);
        }
        return re.reverse().toString();
    }

    // 59. 螺旋矩阵 II
    public int[][] generateMatrix(int n) {
        int[][] re = new int[n][n];
        int t = 0, b = n-1, l = 0, r = n-1;
        int num = 1;
        while (true) {
            for (int i = l; i <= r; i++) {
                re[t][i] = num++;
            }
            if (++t > b)
                break;

            for (int i = t; i <= b; i++) {
                re[i][r] = num++;
            }
            if (--r < l)
                break;

            for (int i = r; i >= l; i--) {
                re[b][i] = num++;
            }
            if (--b < t)
                break;

            for (int i = b; i >= t; i--) {
                re[i][l] = num++;
            }
            if (++l > r)
                break;
        }
        return re;
    }

    // 65. 有效数字
    public boolean isNumber(String s) {
        if (s == null || s.length() == 0)
            return false;
        boolean hasE = false, hasDot = false, hasNum = false;
        char[] chs = s.trim().toCharArray();
        for (int i = 0; i < chs.length; i++) {
            if (Character.isDigit(chs[i]))
                hasNum = true;
            else if (chs[i] == '.') {
                if (hasDot || hasE)
                    return false;
                hasDot = true;
            } else if (chs[i] == 'e') {
                if (hasE || !hasNum)
                    return false;
                hasE = true;
                hasNum = false;
            } else if (chs[i] == '+' || chs[i] == '-') {
                if (i != 0 && chs[i-1] != 'e')
                    return false;
            } else
                return false;
        }
        return hasNum;
    }

    // 68. 文本左右对齐
    public List<String> fullJustify(String[] words, int maxWidth) {
        ArrayList<String> re = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (line.length() == 0) {
                line.append(words[i]);
            } else if (line.length() + words[i].length() + 1 > maxWidth) {
                if (line.length() != maxWidth) {
                    int blanks = maxWidth - line.length();
                    String[] wds = line.toString().split(" ");
                    blanks += wds.length - 1;
                    if (wds.length == 1) {
                        for (int k = 0; k < blanks; k++)
                            line.append(' ');
                    } else {
                        int base = blanks / (wds.length - 1);
                        int remain = blanks % (wds.length - 1);
                        line = new StringBuilder();
                        line.append(wds[0]);
                        for (int j = 1; j < wds.length; j++) {
                            for (int k = 0; k < base; k++)
                                line.append(' ');
                            if (remain != 0) {
                                line.append(' ');
                                remain--;
                            }
                            line.append(wds[j]);
                        }
                    }
                }
                re.add(line.toString());
                line = new StringBuilder();
                line.append(words[i]);
            } else {
                line.append(' ').append(words[i]);
            }
        }

        for (int i = line.length(); i < maxWidth; i++) {
            line.append(' ');
        }
        re.add(line.toString());
        return re;
    }

    // 71. 简化路径
    public String simplifyPath(String path) {
        char[] chs = path.toCharArray();
        Deque<String> queue = new LinkedList<>();
        for (int i = 0; i < chs.length; ) {
            if (chs[i] == '.' && (i == chs.length-1 || chs[i+1] == '/')) {
                i = i+2;
            } else if (i < chs.length-1 && chs[i] == '.' && chs[i+1] == '.'
                    && (i == chs.length-2 || chs[i+2] == '/')) {
                if (!queue.isEmpty())
                    queue.pollLast();
                i = i+3;
            } else if (chs[i] == '/'){
                i++;
            } else {
                StringBuilder wd = new StringBuilder();
                while (i < chs.length && chs[i] != '/') {
                    wd.append(chs[i++]);
                }
                if (wd.length() != 0)
                    queue.addLast(wd.toString());
            }
        }
        StringBuilder re = new StringBuilder();
        while (!queue.isEmpty())
            re.append('/').append(queue.pollFirst());
        if (re.length() == 0)
            re.append('/');
        return re.toString();
    }

    // 73. 矩阵置零
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return;
        int n = matrix[0].length;
        int row = -1, col = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    if (row == -1) {
                        row = j;
                        col = i;
                    } else {
                        matrix[i][row] = 0;
                        matrix[col][j] = 0;
                    }
                }
            }
        }

        if (row == -1)
            return;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i != col && j != row && (matrix[i][row] == 0 || matrix[col][j] == 0)) {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int j = 0; j < n; j++)
            matrix[col][j] = 0;
        for (int i = 0; i < m; i++)
            matrix[i][row] = 0;
    }

    // 82. 删除排序链表中的重复元素 II
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy, cur1 = head, cur2 = head.next;
        while (cur2 != null) {
            if (cur1.val == cur2.val) {
                while (cur2 != null && cur2.val == cur1.val)
                    cur2 = cur2.next;
                pre.next = cur2;
                cur1 = cur2;
            } else {
                pre = pre.next;
                cur1 = cur1.next;
            }
            if (cur2 != null)
                cur2 = cur2.next;
        }

        return dummy.next;
    }

    // 92. 反转链表 II
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null)
            return head;
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy, cur = head, next = head.next;
        int num = 1;
        while (num != m) {
            pre = pre.next;
            cur = cur.next;
            next = next.next;
            num++;
        }

        ListNode first = cur;
        pre.next = null;
        while (num < n) {
            cur.next = pre.next;
            pre.next = cur;
            cur = next;
            next = next.next;
            num++;
        }
        cur.next = pre.next;
        pre.next = cur;
        first.next = next;
        return dummy.next;
    }

    // 118. 杨辉三角
    public List<List<Integer>> generate(int numRows) {
        ArrayList<List<Integer>> re = new ArrayList<>();
        if (numRows == 0)
            return re;
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        re.add(first);
        for (int i = 1; i < numRows; i++) {
            ArrayList<Integer> line = new ArrayList<>();
            line.add(1);
            for (int j = 1; j < i; j++) {
                line.add(re.get(i-1).get(j) + re.get(i-1).get(j-1));
            }
            line.add(1);
            re.add(line);
        }
        return re;
    }

    // 119. 杨辉三角 II
    public List<Integer> getRow(int rowIndex) {
        Integer[] line = new Integer[rowIndex+1];
        line[0] = 1;
        for (int i = 1; i <= rowIndex; i++) {
            line[i] = 1;
            for (int j = i-1; j > 0; j--) {
                line[j] = line[j] + line[j-1];
            }
        }

        List<Integer> re = new ArrayList<>();
        Collections.addAll(re, line);
        return re;
    }

    // 128. 最长连续序列
    int unionFind(HashMap<Integer, Integer> nums, int num) {
        int root = num;
        if (!nums.containsKey(root))
            nums.put(num, num);
        else {
            while (nums.get(root) != root) {
                root = nums.get(root);
            }

            int son = num;
            while (nums.get(son) != root) {
                int tmp = nums.get(son);
                nums.put(son, root);
                son = tmp;
            }
        }
        return root;
    }

    void unionJoin(HashMap<Integer, Integer> nums, int a, int b) {
        int root1 = unionFind(nums, a), root2 = unionFind(nums, b);
        if (root1 != root2) {
            nums.put(root2, root1);
        }
    }

    public int longestConsecutive2(int[] nums) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        HashSet<Integer> hset = new HashSet<>();
        for (int num : nums)
            hset.add(num);
        for (int num : nums) {
            int root1 = unionFind(hmap, num);
            if (hset.contains(num+1)) {
                int rootR = unionFind(hmap, num+1);
                unionJoin(hmap, rootR, root1);
            }
        }

        int re = 0;
        for (Map.Entry<Integer, Integer> e : hmap.entrySet()){
            int root = unionFind(hmap, e.getValue());
            re = Math.max(re, root - e.getKey()+1);
        }
        return re;
    }

    public int longestConsecutive(int[] nums) {
        HashSet<Integer> hset = new HashSet<>();
        for (int num : nums)
            hset.add(num);

        int re = 0;
        for (int num : nums) {
            if (!hset.contains(num-1)) {
                int times = 1;
                while(hset.contains(num+times))
                    times++;
                re = Math.max(re, times);
            }
        }
        return re;
    }

    // 143. 重排链表
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return;
        Stack<ListNode> stk = new Stack<>();
        int nums = 0;
        ListNode cur = head;
        while (cur != null) {
            nums++;
            cur = cur.next;
        }

        int ind = nums/2+1;
        cur = head;
        for (int i = 0; i < ind-1; i++)
            cur = cur.next;
        ListNode tmp = cur;
        cur = cur.next;
        tmp.next = null;
        while (cur != null) {
            stk.add(cur);
            cur = cur.next;
        }

        ListNode pre = head, next = head.next;
        while (!stk.isEmpty()) {
            ListNode n = stk.pop();
            pre.next = n;
            n.next = next;
            pre = next;
            next = next.next;
        }
    }

    class LRUCache {

        class DNode {
            DNode prev;
            DNode next;
            int key;
            int val;
            DNode(int k, int v, DNode prev, DNode next) {
                this.key = k;
                this.val = v;
                this.prev = prev;
                this.next = next;
            }
        }

        HashMap<Integer, DNode> store;
        int capacity;
        DNode head;
        DNode tail;

        public LRUCache(int capacity) {
            store = new HashMap<>(capacity);
            this.capacity = capacity;
            head = new DNode(-1,-1, null, null);
            tail = new DNode(-1,-1, head, null);
            head.next = tail;
        }

        void updatePos(DNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        public int get(int key) {
            if (store.containsKey(key)) {
                DNode n = store.get(key);
                updatePos(n);
                return n.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (store.containsKey(key)) {
                DNode n = store.get(key);
                updatePos(n);
                n.val = value;
            }
            else {
                if (store.size() == capacity) {
                    DNode last = tail.prev;
                    store.remove(last.key);
                    last.prev.next = tail;
                    tail.prev = last.prev;
                    last.prev = null;
                    last.next = null;
                }
                DNode node = new DNode(key, value, head, head.next);
                head.next.prev = node;
                head.next = node;
                store.put(key, node);
            }
        }
    }

    // 151. 翻转字符串里的单词
    public String reverseWords(String s) {
        StringBuilder re = new StringBuilder();
        s = s.trim();
        String[] wds = s.split(" ");
        for (int i = wds.length-1; i >= 0; i--) {
            if (!wds[i].equals(" ")) {
                if (re.length() != 0)
                    re.append(" ");
                re.append(wds[i]);
            }
        }
        return re.toString();
    }

    // 165. 比较版本号
    public int compareVersion(String version1, String version2) {
        String[] vers1 = version1.split("\\.");
        String[] vers2 = version2.split("\\.");
        for (int i = 0; i < Math.max(vers1.length, vers2.length); i++) {
            int a = i < vers1.length ? Integer.parseInt(vers1[i]) : 0;
            int b = i < vers2.length ? Integer.parseInt(vers2[i]) : 0;
            if (a < b)
                return -1;
            else if (a > b)
                return 1;
        }
        return 0;
    }

    // 168. Excel表列名称
    public String convertToTitle(int n) {
        StringBuilder re = new StringBuilder();
        while (true) {
            int tmp = n, remain = 0, times = 0;
            while (tmp != 0) {
                remain = tmp % 26 == 0 ? 26 : tmp % 26;
                tmp = tmp % 26 == 0 ? (tmp - 26) / 26 : tmp / 26;
                times++;
            }
            re.append((char)('A'+remain-1));
            if (times == 1)
                break;
            n = n - (int)Math.pow(26, times-1);
        }
        return re.toString();
    }

    // 171. Excel表列序号
    public int titleToNumber(String s) {
        char[] chs = s.toCharArray();
        int re = 0, digits = 1;
        for (int i = s.length()-1; i >= 0; i--) {
            re += (chs[i] - 'A' + 1) * digits;
            digits *= 26;
        }
        return re;
    }

    // 172. 阶乘后的零
    public int trailingZeroes(int n) {
        int re = 0;
        while (n != 0) {
            re += n / 5;
            n /= 5;
        }
        return re;
    }

    // 189. 旋转数组
    public void rotate2(int[] nums, int k) {
        k = k % nums.length;
        int tmp;
        for (int i = 0; i < k; i++) {
            tmp = nums[nums.length-1];
            for (int j = nums.length-1; j > 0; j--) {
                nums[j] = nums[j-1];
            }
            nums[0] = tmp;
        }
    }

    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        if (k == 0)
            return;
        int count = 0;
        for (int i = 0; count < nums.length; i++) {
            int tmp = nums[i], ind = (i+k)%nums.length;
            do {
                int a = nums[ind];
                nums[ind] = tmp;
                tmp = a;
                ind = (ind+k)%nums.length;
                count++;
            } while (ind != (i+k)%nums.length);
        }
    }

    // 190. 颠倒二进制位
    public int reverseBits(int n) {
        int re = 0, base = 1, base2 = 1 << 31;
        for (int i = 0; i < 32; i++) {
            int r = n & base;
            base <<= 1;
            if (r > 0) {
                re = re | base2;
            }
            base2 >>>= 1;
        }
        return re;
    }

    // 201. 数字范围按位与
    public int rangeBitwiseAnd(int m, int n) {
        if (m == 0)
            return 0;
        else if (m == n)
            return m;

        int re = 0;
        while (true) {
            int base = 1, tmp = m;
            while (tmp > 1) {
                tmp /= 2;
                base <<= 1;
            }
            if (n / base == m / base) {
                re = re | base;
                n -= base;
                m -= base;
            } else
                break;
        }
        return re;
    }

    // 203. 移除链表元素
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy, cur = head;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
                cur.next = null;
                cur = pre.next;
            } else {
                pre = cur;
                cur = cur.next;
            }
        }
        return dummy.next;
    }

    // 214. 最短回文串
    public String shortestPalindrome(String s) {
        if (s.length() == 0 || s.length() == 1)
            return s;
        int start = (s.length()-1) / 2;
        for (int i = start; i >= 0; i--) {
            int l = i , r = i + 1;
            if (s.length() % 2 == 1) {
                l = i - 1;
                r = i + 1;
            }
            String re = valid(s, l, r);
            if (re == null) {
                l = i - 1;
                r = i + 1;
                if (s.length() % 2 == 1) {
                    l = i - 1;
                    r = i;
                }
                re = valid(s, l, r);
                if (re != null)
                    return re;
            } else
                return re;
        }
        return "";
    }

    String valid(String s, int l, int r) {
        while (l >= 0 && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        if (l == -1) {
            StringBuilder re = new StringBuilder();
            for (int k = s.length()-1; k >= r; k--) {
                re.append(s.charAt(k));
            }
            re.append(s);
            return re.toString();
        }
        return null;
    }

    // 223. 矩形面积
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        if (B >= H || F >= D || C <= E || G <= A) {
            return (C-A) * (D-B) + (G-E) * (H-F);
        }

        int left = Math.max(E, A), right = Math.min(C, G), top = Math.min(D, H), bottom = Math.max(B, F);
        return (C-A) * (D-B) - (right - left) * (top - bottom) + (G-E) * (H-F);
    }

    // 228. 汇总区间
    public List<String> summaryRanges(int[] nums) {
        List<String> re = new ArrayList<>();
        if (nums.length == 0)
            return re;
        int i = 0, j = 1;
        while (j < nums.length) {
            if (nums[j] != nums[j-1]+1) {
                if (i == j-1)
                    re.add(String.valueOf(nums[i]));
                else
                    re.add(nums[i]+"->"+nums[j-1]);
                i = j;
            }
            j++;
        }
        if (i == j-1)
            re.add(String.valueOf(nums[i]));
        else
            re.add(nums[i]+"->"+nums[j-1]);
        return re;
    }

    // 224. 基本计算器
    public int calculate2(String s) {
        char[] chs = s.toCharArray();
        Stack<Character> stk = new Stack<>();
        ArrayList<String> lst = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        for (char c : chs) {
            if (c == ' ')
                continue;

            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                if (number.length() != 0) {
                    lst.add(number.toString());
                    number = new StringBuilder();
                }
                if (c == ')') {
                    while (stk.peek() != '(')
                        lst.add(String.valueOf(stk.pop()));
                    stk.pop();
                } else if (c == '+' || c == '-') {
                    while (!stk.isEmpty() && stk.peek() != '(')
                        lst.add(String.valueOf(stk.pop()));
                    stk.push(c);
                } else if (c == '(') {
                    stk.push(c);
                }
            }
        }
        while (!stk.isEmpty())
            lst.add(String.valueOf(stk.pop()));
        Stack<Integer> stk2 = new Stack<>();
        for (String op : lst) {
            if (op.equals("+") || op.equals("-")) {
                int a = stk2.pop(), b = stk2.pop();
                if (op.equals("+"))
                    stk2.push(a+b);
                else
                    stk2.push(b-a);
            } else {
                stk2.push(Integer.parseInt(op));
            }
        }
        return stk2.pop();
    }

    // 227. 基本计算器 II
    public int calculate(String s) {
        char[] chs = s.trim().toCharArray();
        Stack<Integer> stk = new Stack<>();
        int start = 0;
        if (Character.isDigit(chs[0])) {
            StringBuilder num = new StringBuilder();
            num.append(chs[0]);
            int i = 1;
            for (; i < chs.length; i++) {
                if (!Character.isDigit(chs[i]))
                    break;
                num.append(chs[i]);
            }
            start = i;
            stk.push(Integer.parseInt(num.toString()));
        }

        while (start < chs.length) {
            while (chs[start] == ' ') {
                start++;
            }
            char op = chs[start++];
            StringBuilder num = new StringBuilder();
            while (start < chs.length && (chs[start] == ' ' || Character.isDigit(chs[start]))) {
                if (Character.isDigit(chs[start]))
                    num.append(chs[start]);
                start++;
            }
            int n = Integer.parseInt(num.toString());
            if (op == '+')
                stk.push(n);
            else if (op == '-')
                stk.push(-n);
            else if (op == '*')
                stk.push(stk.pop()*n);
            else
                stk.push(stk.pop()/n);
        }
        int re = 0;
        while (!stk.isEmpty())
            re += stk.pop();
        return re;
    }

    // 229. 求众数 II
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> re = new ArrayList<>();
        if (nums.length == 0)
            return re;
        else if (nums.length == 1) {
            re.add(nums[0]);
            return re;
        }
        int a = Integer.MAX_VALUE, acount = 0, b = Integer.MAX_VALUE, bcount = 0;
        for (int num : nums) {
            if (num == a)
                acount++;
            else if (num == b)
                bcount++;
            else {
                if (acount != 0 && bcount != 0) {
                    acount--;
                    bcount--;
                } else if (acount == 0) {
                    a = num;
                    acount = 1;
                } else {
                    b = num;
                    bcount = 1;
                }
            }
        }
        acount = 0;
        bcount = 0;
        for (int num : nums) {
            if (num == a)
                acount++;
            else if (num == b)
                bcount++;
        }
        if (acount > nums.length/3)
            re.add(a);
        if (bcount > nums.length/3)
            re.add(b);
        return re;
    }

    // 231. 2的幂
    public boolean isPowerOfTwo(int n) {
        if (n <= 0)
            return false;
        while (true) {
            if (n == 1)
                return true;
            else if (n % 2 == 1)
                return false;
            n >>= 1;
        }
    }

    // 238. 除自身以外数组的乘积
    public int[] productExceptSelf(int[] nums) {
        if (nums.length == 0)
            return new int[]{};
        else if (nums.length == 1)
            return new int[1];

        int[] re = new int[nums.length];
        re[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            re[i] = re[i-1]*nums[i-1];
        }
        int base = 1;
        for (int i = nums.length-2; i >= 0; i--) {
            base *= nums[i+1];
            re[i] *= base;
        }
        return re;
    }

    // 241. 为运算表达式设计优先级
    public List<Integer> diffWaysToCompute(String input) {
        HashMap<String, List<Integer>> hmap = new HashMap<>();
        ArrayList<String> ops = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i)))
                num = num*10 + (input.charAt(i) - '0');
            else {
                ops.add(String.valueOf(num));
                num = 0;
                ops.add(String.valueOf(input.charAt(i)));
            }
        }
        ops.add(String.valueOf(num));
        return dac(hmap, ops, 0, ops.size()-1);
    }

    List<Integer> dac(HashMap<String, List<Integer>> hmap, ArrayList<String> ops, int l, int r) {
        if (hmap.containsKey(l+""+r))
            return hmap.get(l+""+r);
        List<Integer> re = new ArrayList<>();
        if (l == r) {
            re.add(Integer.parseInt(ops.get(l)));
            hmap.put(l+""+r, re);
            return re;
        } else if (r-l+1 <= 3) {
            re.add(calcu(Integer.parseInt(ops.get(l)), ops.get(l+1), Integer.parseInt(ops.get(r))));
            hmap.put(l+""+r, re);
            return re;
        }
        for (int i = l; i < r; i+=2) {
            List<Integer> re1 = dac(hmap, ops, l, i), re2 = dac(hmap, ops, i+2, r);
            for (int num1 : re1) {
                for (int num2 : re2)
                    re.add(calcu(num1, ops.get(i+1), num2));
            }
        }
        hmap.put(l+""+r, re);
        return re;
    }

    int calcu(int a, String op, int b) {
        switch (op) {
            case "+":
                return a+b;
            case "-":
                return a-b;
            default:
                return a*b;
        }
    }

    // 258. 各位相加
    public int addDigits(int num) {
        int tmp = num;
        do {
            num = tmp;
            tmp = 0;
            while (num > 0) {
                tmp += num % 10;
                num /= 10;
            }
        } while (tmp > 9);
        return tmp;
    }

    // 260. 只出现一次的数字 III
    public int[] singleNumber(int[] nums) {
        int mask = 0;
        for (int num : nums)
            mask = mask ^ num;

        int diff = mask & (-mask);

        int x = 0;
        for (int num : nums) {
            if ((num & diff) != 0)
                x = x ^ num;
        }
        return new int[]{x, mask ^ x};
    }

    // 268. 丢失的数字
    public int missingNumber(int[] nums) {
        int sum = (1+nums.length)*nums.length/2;
        int actualSum = 0;
        for (int num : nums)
            actualSum += num;
        return sum - actualSum;
    }

    // 263. 丑数
    public boolean isUgly(int num) {
        if (num <= 0)
            return false;
        while (num != 1) {
            if (num % 2 == 0)
                num /= 2;
            else if (num % 3 == 0)
                num /= 3;
            else if (num % 5 == 0)
                num /= 5;
            else
                return false;
        }
        return true;
    }

    // 273. 整数转换英文表示
    public String numberToWords(int num) {
        if (num == 0)
            return "Zero";
        String[] units = new String[]{"", "Thousand", "Million", "Billion"};
        String[] low = {"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
        String[] mid = {"Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
        String[] high = {"","","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
        Deque<String> result = new LinkedList<>();
        int pos = 0;
        while (num != 0) {
            int number = 0;
            for (int i = 0; num != 0 && i < 3; i++) {
                int digit = num % 10;
                num /= 10;
                number = (int)(digit*Math.pow(10, i)) + number;
            }
            if (number > 0)
                result.addFirst(units[pos]);
            Deque<String> tem = new LinkedList<>();
            if (number >= 100) {
                int a = number / 100;
                tem.addLast(low[a]);
                tem.addLast("Hundred");
                number %=  100;
            }
            if (number >= 20) {
                int a = number / 10;
                tem.addLast(high[a]);
                number %= 10;
            }
            if (number >= 10) {
                tem.addLast(mid[number-10]);
                number = 0;
            }
            if (number > 0)
                tem.addLast(low[number]);
            while (!tem.isEmpty())
                result.addFirst(tem.pollLast());
            pos++;
        }
        StringBuilder re = new StringBuilder();
        re.append(result.pollFirst());
        while (!result.isEmpty()) {
            if (!result.peek().equals(""))
                re.append(' ');
            re.append(result.pollFirst());
        }
        return re.toString();
    }

    // 289. 生命游戏
    public void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                int num = getLiveNum(board, i, j);
                if (board[i][j]%2 == 1) {
                    if (num <= 1 || num > 3)
                        board[i][j] = 3;
                } else if (num == 3){
                    board[i][j] = 2;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 2)
                    board[i][j] = 1;
                else if (board[i][j] == 3)
                    board[i][j] = 0;
            }
        }
    }

    int getLiveNum(int[][] board, int i, int j) {
        int m = board.length, n = board[0].length, num = 0;
        if (j-1 >= 0) {
            if (i-1 >= 0 && board[i-1][j-1]%2 == 1)
                num++;
            if (board[i][j-1]%2 == 1)
                num++;
            if (i+1 < m && board[i+1][j-1]%2 == 1)
                num++;
        }
        if (j+1 < n) {
            if (i-1 >= 0 && board[i-1][j+1]%2 == 1)
                num++;
            if (board[i][j+1]%2 == 1)
                num++;
            if (i+1 < m && board[i+1][j+1]%2 == 1)
                num++;
        }
        if (i-1 >= 0 && board[i-1][j]%2 == 1)
            num++;
        if (i+1 < m && board[i+1][j]%2 == 1)
            num++;
        return num;
    }

    // 292. Nim 游戏
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }

    // 415. 字符串相加
    public String addStrings(String num1, String num2) {
        StringBuilder re = new StringBuilder();
        int remain = 0;
        for (int i = num1.length()-1, j = num2.length()-1;
        i >= 0 || j >= 0 || remain > 0;
        i--, j--) {
            int a = i < 0 ? 0 : num1.charAt(i) - '0';
            int b = j < 0 ? 0 : num2.charAt(j) - '0';
            int sum = a+b+remain;
            re.append(sum % 10);
            remain = sum / 10;
        }
        return re.reverse().toString();
    }

    // 1318. 或运算的最小翻转次数
    public int minFlips(int a, int b, int c) {
        int n = 0, mask = 1;
        for (int i = 0; i < 31; i++) {
            int a1 = (a & mask) > 0 ? 1 : 0, b1 = (b & mask) > 0 ? 1 : 0, c1 = (c & mask) > 0 ? 1 : 0;
            mask <<= 1;
            if ((a1 | b1) == c1)
                continue;
            if (c1 == 0) {
                n += a1+b1;
            } else {
                n++;
            }
        }
        return n;
    }
}
