package com.isswhu;

import java.lang.reflect.Array;
import java.util.*;

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

    // 36. 有效的数独
    public boolean isValidSudoku(char[][] board) {
        int[][] rows = new int[9][9];
        int[][] cols = new int[9][9];
        int[][] cubes = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.')
                    continue;
                int num = board[i][j] - '1';
                if (rows[i][num] == 1 || cols[j][num] == 1
                        || cubes[i/3 + 3*(j/3)][num] == 1)
                    return false;
                else {
                    rows[i][num] = 1;
                    cols[j][num] = 1;
                    cubes[i/3 + 3*(j/3)][num] = 1;
                }
            }
        }
        return true;
    }

    // 37. 解数独
    int[] rows = new int[9];
    int[] cols = new int[9];
    int[] cubes = new int[9];

    public void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.')
                    continue;
                int num = board[i][j] - '1';
                int base = 1;
                for (int k = 0; k < num; k++)
                    base <<= 1;
                rows[i] = rows[i] | base;
                cols[j] = cols[j] | base;
                cubes[i/3 + 3*(j/3)] = cubes[i/3 + 3*(j/3)] | base;
            }
        }
        backtrack(board, 0, 0);
    }

    boolean backtrack(char[][] board, int i, int j) {
        if (i == 9 && j == 0)
            return true;
        if (board[i][j] == '.') {
            int bitmap = ~ (rows[i] | cols[j] | cubes[i/3 + 3*(j/3)]);
            if (bitmap == 0)
                return false;
            int base = 1;
            for (int k = 0; k < 9; k++) {
                int re = bitmap & base;
                if (re != 0) {
                    board[i][j] = (char)('1' + k);
                    int tmp1 = rows[i], tmp2 = cols[j], tmp3 = cubes[i/3 + 3*(j/3)];
                    rows[i] = rows[i] | base;
                    cols[j] = cols[j] | base;
                    cubes[i/3 + 3*(j/3)] = cubes[i/3 + 3*(j/3)] | base;
                    boolean pass;
                    if (j < 8)
                        pass = backtrack(board, i, j+1);
                    else
                        pass = backtrack(board, i+1, 0);
                    if (pass)
                        return true;
                    board[i][j] = '.';
                    rows[i] = tmp1;
                    cols[j] = tmp2;
                    cubes[i/3 + 3*(j/3)] = tmp3;
                }
                base <<= 1;
            }
            return false;
        }
        if (j < 8)
            return backtrack(board, i, j+1);
        else
            return backtrack(board, i+1, 0);
    }

    // 149. 直线上最多的点数
    public int maxPoints(int[][] points) {
        if (points.length < 3)
            return points.length;

        int re = 0;
        for (int i = 0; i < points.length; i++) {
            int dups = 1, maxnum = 0;
            HashMap<String, Integer> hmap = new HashMap<>();
            for (int j = i+1; j < points.length; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                if (dx == dy && dy == 0) {
                    dups++;
                    continue;
                }
                int gcd = gcd(dx, dy);
                dx /= gcd;
                dy /= gcd;
                String flag = "";
                if (dx*dy < 0) {
                    flag = "-";
                }
                dx = Math.abs(dx);
                dy = Math.abs(dy);
                String key = flag+dx+"/"+dy;

                if (hmap.containsKey(key))
                    hmap.put(key, hmap.get(key)+1);
                else
                    hmap.put(key, 1);
                maxnum = Math.max(maxnum, hmap.get(key));
            }
            re = Math.max(re, maxnum+dups);
        }
        return re;
    }

    int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    // 166. 分数到小数
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0)
            return "0";

        String flag = "";
        long numer = numerator, denomi = denominator;
        if (numer*denomi < 0) {
            flag = "-";
            numer = Math.abs(numer);
            denomi = Math.abs(denomi);
        }
        long first = numer / denomi;
        StringBuilder re = new StringBuilder();
        re.append(flag).append(first);

        long remain = numer % denomi;
        if (remain == 0)
            return re.toString();

        re.append('.');
        StringBuilder sb = new StringBuilder();
        HashMap<Long, Integer> remainsMap = new HashMap<>();
        int ind = 0;
        while (remain != 0) {
            if (remainsMap.containsKey(remain)) {
                sb.append(')');
                sb.insert((int)remainsMap.get(remain), '(');
                break;
            } else {
                remainsMap.put(remain, ind);
                long num = remain*10 / denomi;
                sb.append(num);
                remain = (remain*10) % denomi;
            }
            ind++;
        }
        re.append(sb);
        return re.toString();
    }

    // 187. 重复的DNA序列
    public List<String> findRepeatedDnaSequences(String s) {

    }
}
