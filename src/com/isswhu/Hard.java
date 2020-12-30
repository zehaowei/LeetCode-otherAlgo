package com.isswhu;

import java.util.*;

public class Hard {
    // 218. 天际线问题
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> re = new ArrayList<>();
        ArrayList<ArrayList<Integer>> points = new ArrayList<>();
        for (int[] building : buildings) {
            int x = building[0], y = building[1], h = building[2];
            ArrayList<Integer> a = new ArrayList<>(), b = new ArrayList<>();
            a.add(x);
            a.add(-h);
            b.add(y);
            b.add(h);
            points.add(a);
            points.add(b);
        }
        points.sort(Comparator.comparingInt(o -> o.get(0)));
        TreeMap<Integer, Integer> treeMap = new TreeMap<>((o1, o2) -> o2-o1);
        treeMap.put(0, 1);
        int curH = 0;
        for (int i = 0; i < points.size(); i++) {
            ArrayList<Integer> point = points.get(i);
            int p = point.get(0), h = point.get(1);
            if (h < 0) {
                h = -h;
                if (treeMap.containsKey(h))
                    treeMap.put(h, treeMap.get(h)+1);
                else
                    treeMap.put(h, 1);
            } else {
                int times = treeMap.get(h);
                times--;
                if (times == 0)
                    treeMap.remove(h);
                else
                    treeMap.put(h, times);
            }
            if ((i == points.size()-1 || !point.get(0).equals(points.get(i+1).get(0)))
                    && curH != treeMap.firstKey()) {
                curH = treeMap.firstKey();
                ArrayList<Integer> pit = new ArrayList<>();
                pit.add(p); pit.add(treeMap.firstKey());
                re.add(pit);
            }
        }
        return re;
    }
}
