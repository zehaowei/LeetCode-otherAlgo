package com.isswhu;

import java.lang.reflect.Array;
import java.util.*;

public class Sort {

    // 57. 插入区间
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (newInterval.length == 0)
            return intervals;
        else if (intervals.length == 0)
            return new int[][]{newInterval};
        int start = newInterval[0], end = newInterval[1];
        int l = 0, h = intervals.length-1;
        while (l <= h) {
            int mid = l + (h-l)/2;
            if (intervals[mid][0] == start)
                break;
            else if (intervals[mid][0] > start)
                h = mid-1;
            else
                l = mid+1;
        }
        int pos1;
        if (l <= h)
            pos1 = l + (h-l)/2;
        else
            pos1 = h;

        l = 0; h = intervals.length-1;
        while (l <= h) {
            int mid = l + (h-l)/2;
            if (intervals[mid][1] == end)
                break;
            else if (intervals[mid][1] > end)
                h = mid-1;
            else
                l = mid+1;
        }
        int pos2;
        if (l <= h)
            pos2 = l + (h-l)/2;
        else
            pos2 = l;

        int newS = start, newE = end, ind1 = pos1+1, ind2 = pos2-1;
        if (pos1 >= 0 && start <= intervals[pos1][1]) {
            ind1 = pos1;
            newS = intervals[pos1][0];
        }
        if (pos2 < intervals.length && end >= intervals[pos2][0]) {
            ind2 = pos2;
            newE = intervals[pos2][1];
        }

        int[] newItv = new int[]{newS, newE};
        LinkedList<int[]> list = new LinkedList<>();
        boolean add = false;
        for(int i = 0; i <= intervals.length; i++) {
            if (i >= ind1) {
                if (!add) {
                    list.add(newItv);
                    add = true;
                }
                if (i <= ind2)
                    continue;
            }
            if (i != intervals.length)
                list.add(intervals[i]);
        }
        return list.toArray(new int[list.size()][]);
    }

    // 147. 对链表进行插入排序
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int x) { val = x; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode insertionSortList(ListNode head) {
        if (head == null)
            return null;

        ListNode p = head.next, tail = head, dummy = new ListNode(0);
        dummy.next = head;
        while (p != null) {
            ListNode next = p.next;
            if (tail.val <= p.val)
                tail = p;
            else {
                ListNode pre = dummy;
                while (pre.next.val < p.val) {
                    pre = pre.next;
                }

                tail.next = next;
                p.next = pre.next;
                pre.next = p;
            }

            p = next;
        }

        return dummy.next;
    }

    // 148. 排序链表
    public ListNode sortList2(ListNode head) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode(0, head);
        quicksort(dummy, null);
        return dummy.next;
    }

    void quicksort(ListNode preHead, ListNode postTail) {
        if (preHead.next == postTail || (preHead.next.next == postTail))
            return;
        ListNode head = preHead.next;
        ListNode pre = head, p = head.next, pre_p = head;
        while (p != postTail) {
            if (p.val <= head.val) {
                if (pre.next == p) {
                    pre = pre.next;
                    p = p.next;
                } else {
                    pre_p.next = p.next;
                    p.next = pre.next;
                    pre.next = p;

                    pre = p;
                    p = pre_p.next;
                }
            } else {
                pre_p = p;
                p = p.next;
            }
        }

        if (pre != head) {
            preHead.next = head.next;
            head.next = pre.next;
            pre.next = head;
        }
        quicksort(preHead, head);
        quicksort(head, postTail);
    }

    public ListNode sortList(ListNode head) {
        if (head == null)
            return null;

        int length = 0;
        ListNode p = head;
        while (p != null) {
            length++;
            p = p.next;
        }

        ListNode dummy = new ListNode(0, head);
        for (int sublen = 1; sublen < length; sublen <<= 1) { // 每次归并排序时，被归并的子list的长度
            ListNode pre = dummy, cur = dummy.next;
            while (cur != null) {
                ListNode head1 = cur; // 找list1
                for (int i = 1; i < sublen && cur.next != null; i++) {
                    cur = cur.next;
                }

                ListNode head2 = cur.next; // 找list2
                cur.next = null; // 断开list1 and 2
                cur = head2;
                for (int i = 1; i < sublen && cur != null && cur.next != null; i++) {
                    cur = cur.next;
                }

                if (head2 != null) {
                    ListNode tmp = cur;
                    cur = cur.next;
                    tmp.next = null;        // 断开list2与后面
                    pre.next = merge(head1, head2);
                    for (int i = 0; i < 2*sublen && pre.next != null; i++) // 发生了排序，此时已不知道tail是哪个，只能一个一个数
                        pre = pre.next;
                } else {
                    pre.next = head1; // 不需要归并时也要记得把已排好序的部分与本轮list链接起来
                }
            }
        }

        return dummy.next;
    }

    ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0), cur = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                cur.next = head1;
                head1 = head1.next;
            } else {
                cur.next = head2;
                head2 = head2.next;
            }
            cur = cur.next;
        }
        if (head1 != null) {
            cur.next = head1;
        } else {
            cur.next = head2;
        }
        return dummy.next;
    }

    // 164. 最大间距
    public int maximumGap(int[] nums) {
        if (nums.length < 2)
            return 0;
        int maxnum = 0;
        for (int num : nums)
            maxnum = Math.max(num, maxnum);
        int times = 0;
        while (maxnum != 0) {
            times++;
            maxnum /= 10;
        }

        int base = 1;
        for (int i = 0; i < times; i++) {
            int[] cnt = new int[10];
            for (int num : nums) {
                int ind = (num / base) % 10;
                cnt[ind] += 1;
            }

            for (int j = 1; j < 10; j++)
                cnt[j] += cnt[j-1];

            int[] tmp = new int[nums.length];
            for (int j = nums.length-1; j >= 0; j--) {
                int ind = (nums[j] / base) % 10;
                tmp[cnt[ind]-1] = nums[j];
                cnt[ind] -= 1;
            }

            System.arraycopy(tmp, 0, nums, 0, nums.length);
            base *= 10;
        }

        int maxGap = 0;
        for (int i = 1; i < nums.length; i++) {
            maxGap = Math.max(maxGap, nums[i]-nums[i-1]);
        }
        return maxGap;
    }

    public int maximumGap2(int[] nums) {
        if (nums.length < 2)
            return 0;

        int minnum = Integer.MAX_VALUE, maxnum = 0;
        for (int num : nums) {
            minnum = Math.min(minnum, num);
            maxnum = Math.max(maxnum, num);
        }

        int d = (int)Math.ceil((double)(maxnum - minnum) / (nums.length-1));
        if (d == 0) return 0;
        int size = (maxnum-minnum)/d + 1;
        int[] maxs = new int[size], mins = new int[size];
        for (int i = 0; i < mins.length; i++) {
            maxs[i] = -1;
            mins[i] = -1;
        }

        for (int num : nums) {
            int ind = (num-minnum) / d;
            if (maxs[ind] == -1) {
                maxs[ind] = num;
                mins[ind] = num;
            } else {
                maxs[ind] = Math.max(maxs[ind], num);
                mins[ind] = Math.min(maxs[ind], num);
            }
        }

        int re = 0, pre = 0;
        for (int i = 1; i < maxs.length; i++) {
            if (maxs[i] == -1)
                continue;
            if (maxs[pre] != -1) {
                re = Math.max(mins[i] - maxs[pre], re);
            }
            pre = i;
        }
        return re;
    }

    // 179. 最大数
    public String largestNumber(int[] nums) {
        if (nums.length == 0)
            return "";
        ArrayList<String> list = new ArrayList<>();
        for (int num : nums)
            list.add(Integer.toString(num));
        list.sort((a, b) -> {
            String s1 = a+b, s2 = b+a;
            return -s1.compareTo(s2);
        });
        if (list.get(0).equals("0"))
            return "0";
        StringBuilder sb = new StringBuilder();
        for (String str : list)
            sb.append(str);
        return sb.toString();
    }

    // 215. 数组中的第K个最大元素
    public int findKthLargest(int[] nums, int k) {
        k = nums.length-k;
        int l = 0, r = nums.length-1;
        Random rand = new Random();
        while (true) {
            int s = rand.nextInt(r-l+1) + l;
            int re = partition(nums, l, r, s);
            if (re == k)
                return nums[k];
            else if (re < k) {
                l = re+1;
            } else {
                r = re-1;
            }
        }
    }

    int partition(int[] nums, int l, int r, int s) {
        int tmp = nums[l];
        nums[l] = nums[s];
        nums[s] = tmp;
        int i = l, j = l+1;
        while (j <= r) {
            if (nums[j] < nums[l]) {
                i++;
                if (i != j) {
                    int t = nums[i];
                    nums[i] = nums[j];
                    nums[j] = t;
                }
            }
            j++;
        }
        tmp = nums[i];
        nums[i] = nums[l];
        nums[l] = tmp;
        return i;
    }

    // 220. 存在重复元素 III
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {
        if (k < 1)
            return false;
        else if (k > nums.length-1)
            k = nums.length-1;

        TreeSet<Long> treeSet = new TreeSet<>();
        treeSet.add((long)nums[0]);
        int i = 0, j = 1;
        for (; j < nums.length; j++) {
            Long floor = treeSet.floor((long)nums[j]);
            if (floor != null && (long)nums[j]-floor <= t)
                return true;
            Long ceil = treeSet.ceiling((long)nums[j]);
            if (ceil != null && ceil-(long)nums[j] <= t)
                return true;
            treeSet.add((long)nums[j]);
            if (j-i == k) {
                treeSet.remove((long)nums[i]);
                i++;
            }
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k < 1 || t < 0)
            return false;
        HashMap<Long, Long> hmap = new HashMap<>();
        int i = 0, j = 1;
        hmap.put(getId(nums[0], t+1), (long)nums[0]);
        for (; j < nums.length; j++) {
            long id = getId(nums[j], t+1);
            if (hmap.containsKey(id))
                return true;
            if (hmap.containsKey(id-1) && nums[j]-hmap.get(id-1) <= t)
                return true;
            if (hmap.containsKey(id+1) && hmap.get(id+1)-nums[j] <= t)
                return true;
            hmap.put(id, (long)nums[j]);
            if (j-i == k) {
                hmap.remove(getId(nums[i], t+1));
                i++;
            }
        }
        return false;
    }

    long getId (long num, long step) {
        if (num >= 0)
            return num / step;
        else
            return (num+1) / step - 1;
    }

    // 242. 有效的字母异位词
    public boolean isAnagram(String s, String t) {
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        int[] map1 = new int[256];
        int[] map2 = new int[256];
        for (char c : chars1)
            map1[c] += 1;
        for (char c : chars2)
            map2[c] += 1;
        for (int i = 'a'; i <= 'z'; i++) {
            if (map1[i] != map2[i])
                return false;
        }
        return true;
    }

    // 274. H 指数
    public int hIndex2(int[] citations) {
        if (citations.length == 0)
            return 0;
        Arrays.sort(citations);
        int l = 0, h = citations.length-1;
        while (l < h) {
            int mid = l + (h-l)/2;
            int nums = citations.length - mid;
            if (citations[mid] == nums)
                return nums;
            else if (citations[mid] < nums) {
                l = mid + 1;
            } else {
                h = mid;
            }
        }
        return Math.min(citations[h], citations.length-h);
    }

    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] map = new int[n+1];
        for (int cit : citations) {
            if (cit > n)
                map[n] += 1;
            else
                map[cit] += 1;
        }

        int nums = n, re = 0;
        for (int i = 0; i <= n; i++) {
            if (nums >= i)
                re = i;
            nums -= map[i];
        }
        return re;
    }
}
