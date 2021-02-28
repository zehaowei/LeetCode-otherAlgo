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
    class Pair {
        int num;
        int times;
        Pair(int a, int b) {
            num = a;
            times = b;
        }
    }

    public int[] topKFrequent2(int[] nums, int k) {
        int[] re = new int[k];
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int num : nums) {
            if (hmap.containsKey(num))
                hmap.put(num, 1+hmap.get(num));
            else
                hmap.put(num, 1);
        }

        Comparator<Pair> cmp = new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.times, o2.times);
            }
        };
        PriorityQueue<Pair> heap = new PriorityQueue<>(k, cmp);
        for (Map.Entry<Integer, Integer> e : hmap.entrySet()) {
            Pair p = new Pair(e.getKey(), e.getValue());
            if (heap.size() < k)
                heap.add(p);
            else if (p.times > heap.peek().times){
                heap.poll();
                heap.add(p);
            }
        }
        for (int i = 0; i < k; i++)
            re[i] = heap.poll().num;
        return re;
    }

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int num : nums) {
            if (hmap.containsKey(num))
                hmap.put(num, 1+hmap.get(num));
            else
                hmap.put(num, 1);
        }
        Pair[] nums2 = new Pair[hmap.size()];
        int ind = 0;
        for (Map.Entry<Integer, Integer> e : hmap.entrySet()) {
            nums2[ind++] = new Pair(e.getKey(), e.getValue());
        }

        int l = 0, h = nums2.length-1, re = -1;
        Random rand = new Random();
        while(true) {
            int t = l+rand.nextInt(h-l+1);
            re = partition(nums2, l, h, t);
            if (re < nums2.length-k) {
                l = re+1;
            } else if (re > nums2.length-k) {
                h = re-1;
            } else {
                break;
            }
        }
        int[] arr = new int[k];
        for (int i = 0; i < k; i++)
            arr[i] = nums2[nums2.length-k+i].num;
        return arr;
    }

    int partition(Pair[] nums, int l, int h, int target) {
        Pair temp = nums[l];
        nums[l] = nums[target];
        nums[target] = temp;

        int p = l, q = l+1;
        while (q <= h) {
            if (nums[q].times < nums[l].times) {
                p++;
                if (p != q) {
                    Pair tmp = nums[p];
                    nums[p] = nums[q];
                    nums[q] = tmp;
                }
            }
            q++;
        }

        temp = nums[l];
        nums[l] = nums[p];
        nums[p] = temp;
        return p;
    }

    // 378. 有序矩阵中第 K 小的元素
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int l = matrix[0][0], h = matrix[n-1][n-1];
        while (l < h) {
            int mid = l + (h-l)/2;
            if (check(matrix, k, mid)) {
                h = mid;
            } else {
                l = mid+1;
            }
        }
        return l;
    }

    boolean check(int[][] mat, int k, int mid) {
        int n = mat.length, num = 0;
        int i = n-1, j = 0;
        while (i >= 0 && j < n) {
            if (mat[i][j] <= mid) {
                num += i+1;
                j++;
            } else {
                i--;
            }
        }
        return num >= k;
    }

    // 380. 常数时间插入、删除和获取随机元素
    class RandomizedSet {
        HashMap<Integer, Integer> hmap;
        ArrayList<Integer> index;
        Random rand;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            hmap = new HashMap<>();
            index = new ArrayList<>();
            rand = new Random();
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if (hmap.containsKey(val))
                return false;
            index.add(val);
            hmap.put(val, index.size()-1);
            return true;
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if (!hmap.containsKey(val))
                return false;
            int ind = hmap.get(val);
            hmap.remove(val);
            index.set(ind, index.get(index.size()-1));
            index.remove(index.size()-1);
            if (ind < index.size())
                hmap.put(index.get(ind), ind);
            return true;
        }

        /** Get a random element from the set. */
        public int getRandom() {
            int ind = rand.nextInt(index.size());
            return index.get(ind);
        }
    }

    // 384. 打乱数组
    int[] origin;
    int[] cur;
    Random rand;

    public void Solution(int[] nums) {
        origin = nums.clone();
        cur = nums;
        rand = new Random();
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        cur = origin.clone();
        return cur;
    }

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        for (int i = 0; i < cur.length; i++) {
            int ind = rand.nextInt(cur.length-i) + i;
            swap(cur, i, ind);
        }
        return cur;
    }

    void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    // 387. 字符串中的第一个唯一字符
    public int firstUniqChar(String s) {
        int[] map = new int[26];
        char[] chs = s.toCharArray();
        for (char c : chs) {
            map[c-'a'] += 1;
        }
        int i = 0;
        while (i < s.length()) {
            if (map[chs[i]-'a'] == 1)
                break;
            i++;
        }
        return i == s.length() ? -1 : i;
    }

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

    // 412. Fizz Buzz
    public List<String> fizzBuzz(int n) {
        List<String> re = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0)
                re.add("FizzBuzz");
            else if (i % 3 == 0)
                re.add("Fizz");
            else if (i % 5 == 0)
                re.add("Buzz");
            else
                re.add(String.valueOf(i));
        }
        return re;
    }

    // 419. 甲板上的战舰
    public int countBattleships(char[][] board) {
        int m = board.length;
        if (m == 0)
            return 0;
        int n = board[0].length;
        int num = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'X' && (i-1 < 0 || board[i-1][j] != 'X')
                        && (j-1 < 0 || board[i][j-1] != 'X'))
                    num++;
            }
        }
        return num;
    }

    // 442. 数组中重复的数据
    public List<Integer> findDuplicates(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int x = nums[i] % n;
            if (x == 0)
                x = n;
            nums[x-1] += n;
        }
        List<Integer> re = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] > 2*n && nums[i] <= 3*n)
                re.add(i+1);
        }
        return re;
    }

    // 443. 压缩字符串
    public int compress(char[] chars) {
        int a = 1, b = 1, nums;
        while (b < chars.length) {
            nums = 1;
            while (b < chars.length && chars[b] == chars[b-1]) {
                nums++;
                b++;
            }
            if (nums != 1) {
                Stack<Integer> stk = new Stack<>();
                while (nums != 0) {
                    stk.push(nums%10);
                    nums /= 10;
                }
                while (!stk.isEmpty()) {
                    chars[a++] = (char)('0' + stk.pop());
                }
            }
            if (b < chars.length)
                chars[a++] = chars[b++];
        }
        return a;
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

    // 448. 找到所有数组中消失的数字
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != -1 && nums[i] != i+1) {
                int num = nums[i];
                int tmp = nums[num-1];
                if (tmp == num) {
                    nums[i] = -1;
                    break;
                }
                nums[num-1] = num;
                nums[i] = tmp;
            }
        }
        List<Integer> l = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == -1)
                l.add(i+1);
        }
        return l;
    }

    // 454. 四数相加 II
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int a : A) {
            for (int b : B) {
                int sum = a + b;
                if (hmap.containsKey(sum)) {
                    hmap.put(sum, hmap.get(sum) + 1);
                } else {
                    hmap.put(sum, 1);
                }
            }
        }
        int re = 0;
        for (int c : C) {
            for (int d : D) {
                int sum = c + d;
                if (hmap.containsKey(-sum))
                    re += hmap.get(-sum);
            }
        }
        return re;
    }

    // 535. TinyURL 的加密与解密
    public class Codec2 {

        HashMap<String, String> hmap = new HashMap<>();
        int num = 0;
        String prefix = "https://test/";

        // Encodes a URL to a shortened URL.
        public String encode(String longUrl) {
            String re = prefix+num++;
            hmap.put(re, longUrl);
            return re;
        }

        // Decodes a shortened URL to its original URL.
        public String decode(String shortUrl) {
            return hmap.get(shortUrl);
        }
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

    // 560. 和为K的子数组
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        hmap.put(0, 1);
        int re = 0, sums = 0;
        for (int i = 1; i <= nums.length; i++) {
            sums += nums[i-1];
            if (hmap.containsKey(sums-k)) {
                re += hmap.get(sums-k);
            }
            if (hmap.containsKey(sums)) {
                hmap.put(sums, hmap.get(sums)+1);
            } else {
                hmap.put(sums, 1);
            }
        }
        return re;
    }

    // 636. 函数的独占时间
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] record = new int[n];
        Stack<Integer> stk = new Stack<>();
        int t = -1;
        for (String log : logs) {
            String[] wds = log.split(":");
            int s = Integer.parseInt(wds[2]), id = Integer.parseInt(wds[0]);
            if (stk.isEmpty()) {
                stk.push(id);
                t = s;
            } else if (wds[1].equals("start")){
                record[stk.peek()] += s - t;
                t = s;
                stk.push(id);
            } else {
                record[stk.peek()] += s - t + 1;
                t = s+1;
                stk.pop();
            }
        }
        return record;
    }

    // 706. 设计哈希映射
    class MyHashMap {

        class Node {
            int k;
            int v;
            Node next;
            public Node(int a, int b) {
                k = a;
                v = b;
            }
        }
        int capacity;
        Node[] array;

        /** Initialize your data structure here. */
        public MyHashMap() {
            capacity = 1024;
            array = new Node[capacity];
        }

        /** value will always be non-negative. */
        public void put(int key, int value) {
            int ind = (capacity-1) & hash(key);
            if (array[ind] != null) {
                Node p = array[ind], pre = null;
                while (p != null && p.k != key) {
                    pre = p;
                    p = p.next;
                }
                if (p != null)
                    p.v = value;
                else {
                    pre.next = new Node(key, value);
                }
            } else {
                array[ind] = new Node(key, value);
            }
        }

        int hash(int key) {
            int h = Integer.valueOf(key).hashCode();
            return h ^ (h >>> 16);
        }

        /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
        public int get(int key) {
            int ind = (capacity-1) & hash(key);
            if (array[ind] != null) {
                Node p = array[ind];
                while (p != null && p.k != key)
                    p = p.next;
                if (p != null)
                    return p.v;
                else {
                    return -1;
                }
            } else
                return -1;
        }

        /** Removes the mapping of the specified value key if this map contains a mapping for the key */
        public void remove(int key) {
            int ind = (capacity-1) & hash(key);
            if (array[ind] != null) {
                Node p = array[ind], pre = null;
                while (p != null && p.k != key) {
                    pre = p;
                    p = p.next;
                }
                if (p != null) {
                    if (pre == null) {
                        array[ind] = p.next;
                    } else {
                        pre.next = p.next;
                    }
                    p.next = null;
                }
            }
        }
    }

    // 722. 删除注释
    public List<String> removeComments(String[] source) {
        List<String> re = new LinkedList<>();
        boolean inComment = false;
        StringBuilder l = new StringBuilder();
        for (String line : source) {
            char[] chs = line.toCharArray();
            if (!inComment)
                l = new StringBuilder();
            for (int i = 0; i < chs.length; i++) {
                if (inComment) {
                    if (i < chs.length-1 && chs[i] == '*' && chs[i+1] == '/') {
                        i++;
                        inComment = false;
                    }
                    continue;
                }
                if (chs[i] != '/' || i == chs.length-1) {
                    l.append(chs[i]);
                    continue;
                }

                if (chs[i+1] == '/') {
                    break;
                } else if (chs[i+1] == '*'){
                    i++;
                    inComment = true;
                } else
                    l.append(chs[i]);
            }
            if (l.length() != 0 && !inComment)
                re.add(l.toString());
        }
        return re;
    }

    // 836. 矩形重叠
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        int x1 = rec1[0], y1 = rec1[1], x2 = rec1[2], y2 = rec1[3];
        int _x1 = rec2[0], _y1 = rec2[1], _x2 = rec2[2], _y2 = rec2[3];
        if (x1 == x2 || y1 == y2 || _x1 == _x2 || _y1 == _y2)
            return false;
        if (_x1 >= x2 || _x2 <= x1 || _y1 >= y2 || _y2 <= y1)
            return false;
        return true;
    }
}
