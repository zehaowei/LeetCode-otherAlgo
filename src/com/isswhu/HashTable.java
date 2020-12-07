package com.isswhu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HashTable {
    // 30. 串联所有单词的子串
    public List<Integer> findSubstring(String s, String[] words) {
        int wdlen = words[0].length();
        int len = wdlen * words.length;
        HashMap<String, Integer> hmap = new HashMap<>();
        for (String str : words) {
            if (hmap.containsKey(str))
                hmap.put(str, hmap.get(str)+1);
            else
                hmap.put(str, 1);
        }

        LinkedList<Integer> re = new LinkedList<>();
        for (int i = 0; i <= s.length()-len; i++) {
            String str = s.substring(i, i+len);
            HashMap<String, Integer> tmp = new HashMap<>(hmap);
            boolean add = true;
            for(int j = 0; j < words.length; j++) {
                String wd = str.substring(j*wdlen, (j+1)*wdlen);
                if (tmp.containsKey(wd)) {
                    int num = tmp.get(wd);
                    if (num == 1)
                        tmp.remove(wd);
                    else
                        tmp.put(wd, num-1);
                } else {
                    add = false;
                    break;
                }
            }
            if (add)
                re.add(i);
        }
        return re;
    }

    public List<Integer> findSubstring2(String s, String[] words) {
        LinkedList<Integer> re = new LinkedList<>();
        if (words.length == 0 || s.length() == 0)
            return re;

        int wdlen = words[0].length();
        HashMap<String, Integer> hmap = new HashMap<>();
        for (String str : words) {
            if (hmap.containsKey(str))
                hmap.put(str, hmap.get(str)+1);
            else
                hmap.put(str, 1);
        }

        for (int i = 0; i < wdlen; i++) {
            int left = i, right = i, count = 0;
            HashMap<String, Integer> submap = new HashMap<>();
            while (right + wdlen <= s.length()) {
                String wd = s.substring(right, right+wdlen);
                right = right+wdlen;
                if (!hmap.containsKey(wd)) {
                    left = right;
                    submap.clear();
                    count = 0;
                } else {
                    if (submap.containsKey(wd))
                        submap.put(wd, submap.get(wd)+1);
                    else
                        submap.put(wd, 1);
                    count++;
                    while (submap.get(wd) > hmap.get(wd)) {
                        String wd2 = s.substring(left, left+wdlen);
                        if (submap.get(wd2) == 1)
                            submap.remove(wd2);
                        else
                            submap.put(wd2, submap.get(wd2)-1);
                        count--;
                        left = left+wdlen;
                    }

                    if (count == words.length)
                        re.add(left);
                }
            }
        }
        return re;
    }
}
