package com.isswhu;

import java.util.*;

public class BFS {

    // 126. 单词接龙 II
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        ArrayList<LinkedList<String>> graph = new ArrayList<>();
        HashMap<String, Integer> imap = new HashMap<>();

        int ind = 0;
        imap.put(beginWord, ind++);
        graph.add(new LinkedList<>());
        boolean end = false;
        for (String w : wordList) {
            if (w.equals(beginWord))
                continue;
            imap.put(w, ind++);
            graph.add(new LinkedList<>());
            if (w.equals(endWord))
                end = true;
        }
        if (!end)
            return new LinkedList<>();

        HashSet<String> words = new HashSet<>(wordList);
        words.add(beginWord);
        for (String wd : words) {
            char[] chs = wd.toCharArray();
            for (int i = 0; i < wd.length(); i++ ){
                char tmp = chs[i];
                for (int j = 'a'; j <= 'z'; j++) {
                    if (tmp == j)
                        continue;
                    chs[i] = (char)j;
                    String s = new String(chs);
                    if (words.contains(s)) {
                        graph.get(imap.get(wd)).add(s);
                    }
                }
                chs[i] = tmp;
            }
        }

        Queue<ArrayList<String>> queueUp = new LinkedList<>(), queueDown = new LinkedList<>();
        ArrayList<String> l1 = new ArrayList<>(), l2 = new ArrayList<>();
        l1.add(beginWord);
        l2.add(endWord);
        queueUp.add(l1);
        queueDown.add(l2);
        int lenUp = 1, lenDown = 1;
        int[] setUp = new int[words.size()], setDown = new int[words.size()];
        for (int i = 0; i < words.size(); i++) {
            setUp[i] = -1;
            setDown[i] = -1;
        }
        setUp[imap.get(beginWord)] = 1;
        setDown[imap.get(endWord)] = 1;
        ArrayList<List<String>> re = new ArrayList<>();
        boolean flag = false;
        while(!queueUp.isEmpty() && !queueDown.isEmpty()) {
            int size = queueUp.size();
            lenUp++;
            for (int i = 0; i < size; i++) {
                ArrayList<String> curList = queueUp.poll();
                String cur = curList.get(curList.size()-1);
                if (setDown[imap.get(cur)] != -1) {
                    for (ArrayList<String> l : queueDown) {
                        if (l.get(l.size()-1).equals(cur)) {
                            ArrayList<String> tmp = new ArrayList<>(curList);
                            for (int k = l.size()-2; k >= 0; k--)
                                tmp.add(l.get(k));
                            re.add(tmp);
                        }
                    }
                    flag = true;
                } else if(!flag) {
                    for (String s : graph.get(imap.get(cur))) {
                        if (setUp[imap.get(s)] == -1 || setUp[imap.get(s)] == lenUp) {
                            setUp[imap.get(s)] = lenUp;
                            ArrayList<String> l = new ArrayList<>(curList);
                            l.add(s);
                            queueUp.add(l);
                        }
                    }
                }
            }
            if (flag)
                return re;

            size = queueDown.size();
            lenDown++;
            for (int i = 0; i < size; i++) {
                ArrayList<String> curList = queueDown.poll();
                String cur = curList.get(curList.size()-1);
                if (setUp[imap.get(cur)] != -1) {
                    for (ArrayList<String> l : queueUp) {
                        if (l.get(l.size()-1).equals(cur)) {
                            ArrayList<String> tmp = new ArrayList<>(l);
                            for (int k = curList.size()-2; k >= 0; k--)
                                tmp.add(curList.get(k));
                            re.add(tmp);
                        }
                    }
                    flag = true;
                } else if (!flag) {
                    for (String s : graph.get(imap.get(cur))) {
                        if (setDown[imap.get(s)] == -1 || setDown[imap.get(s)] == lenDown) {
                            setDown[imap.get(s)] = lenDown;
                            ArrayList<String> l = new ArrayList<>(curList);
                            l.add(s);
                            queueDown.add(l);
                        }
                    }
                }
            }
        }
        return re;
    }

    // 127. 单词接龙
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<LinkedList<String>> graph = new ArrayList<>();

        addEdge(map, graph, beginWord);
        boolean end = false;
        for (String s : wordList) {
            addEdge(map, graph, s);
            if (endWord.equals(s))
                end = true;
        }
        if (!end)
            return 0;

        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        HashSet<String> visit = new HashSet<>();
        int len = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            len++;
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                visit.add(cur);
                if (cur.equals(endWord))
                    return len/2+1;
                for (String s : graph.get(map.get(cur))) {
                    if (!visit.contains(s))
                        queue.add(s);
                }
            }
        }
        return 0;
    }

    void addEdge(HashMap<String, Integer> map, ArrayList<LinkedList<String>> graph, String s) {
        if (!map.containsKey(s)) {
            int ind = map.size();
            map.put(s, ind);
            graph.add(new LinkedList<>());
        }
        char[] chs = s.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            char tmp = chs[i];
            chs[i] = '*';
            String cur = new String(chs);
            chs[i] = tmp;

            if (!map.containsKey(cur)) {
                int ind = map.size();
                map.put(cur, ind);
                graph.add(new LinkedList<>());
            }
            graph.get(map.get(s)).add(cur);
            graph.get(map.get(cur)).add(s);
        }
    }
}
