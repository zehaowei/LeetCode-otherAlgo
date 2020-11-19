package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        Test t = new Test();
        LCOF.TreeNode root = new LCOF.TreeNode(6);
        LCOF.TreeNode root2 = new LCOF.TreeNode(2);
        LCOF.TreeNode root0 = new LCOF.TreeNode(0);
        LCOF.TreeNode root4 = new LCOF.TreeNode(4);
        root.left = root2;
        root2.left = root0;
        root2.right = root4;
        t.lowestCommonAncestor(root, root2, root4);

    }


}

class Test {
    public LCOF.TreeNode lowestCommonAncestor(LCOF.TreeNode root, LCOF.TreeNode p, LCOF.TreeNode q) {
        LinkedList<LCOF.TreeNode> list1 = new LinkedList<>(), list2 = new LinkedList<>();
        getPath(root, p, list1);
        getPath(root, q, list2);
        Iterator<LCOF.TreeNode> it1 = list1.iterator(), it2 = list2.iterator();
        LCOF.TreeNode last = root;
        while (it1.hasNext() && it2.hasNext()) {
            LCOF.TreeNode n1 = it1.next(), n2 = it2.next();
            if (n1.val != n2.val)
                return last;
            last = n1;
        }
        return last;
    }

    boolean getPath(LCOF.TreeNode root, LCOF.TreeNode p, LinkedList<LCOF.TreeNode> list) {
        if (root == null)
            return false;

        list.add(p);
        if (root == p || getPath(root.left, p, list) || getPath(root.right, p, list)) {
            return true;
        }

        list.removeLast();
        return false;
    }
}
