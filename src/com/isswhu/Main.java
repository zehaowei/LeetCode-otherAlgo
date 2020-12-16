package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        int[] arr = new int[]{15252,16764,27963,7817,26155,20757,3478,22602,20404,6739,16790,10588,16521,6644,20880,15632,27078,25463,20124,15728,30042,16604,17223,4388,23646,32683,23688,12439,30630,3895,7926,22101,32406,21540,31799,3768,26679,21799,23740};
        Test t = new Test();
        char[][] cc = new char[][]{{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
        t.findOrder(3, new int[][]{{0,1},{0,2},{1,2}});
    }


}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Test {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        HashMap<Integer, LinkedList<Integer>> hmap = new HashMap<>();
        for (int[] tup : prerequisites) {
            int course = tup[0], pre = tup[1];
            if (hmap.containsKey(course))
                hmap.get(course).add(pre);
            else {
                hmap.put(course, new LinkedList<>());
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (!hmap.containsKey(i)) {
                queue.add(i);
            }
        }

        int[] re = new int[numCourses];
        int ind = 0;
        while (!queue.isEmpty()) {
            int cour = queue.poll();
            re[ind++] = cour;
            LinkedList<Integer> removes = new LinkedList<>();
            for (Map.Entry<Integer, LinkedList<Integer>> entry : hmap.entrySet()) {
                entry.getValue().remove((Integer)cour);
                if (entry.getValue().size() == 0) {
                    queue.add(entry.getKey());
                    removes.add(entry.getKey());
                }
            }

            for (Integer i : removes)
                hmap.remove(i);
        }

        if (ind != numCourses)
            return new int[]{};
        return re;
    }


}
