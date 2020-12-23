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
}
