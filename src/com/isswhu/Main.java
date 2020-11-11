package com.isswhu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Test t = new Test();
        LCOF.TreeNode node1 = new LCOF.TreeNode(1);
        node1.left = new LCOF.TreeNode(2);
        node1.right = new LCOF.TreeNode(3);
        node1.right.left = new LCOF.TreeNode(4);
        node1.right.right = new LCOF.TreeNode(5);
        LCOF.TreeNode head = t.deserialize(t.serialize(node1));
    }
}

class Test {
    // Encodes a tree to a single string.
    public String serialize(LCOF.TreeNode root) {
        if (root == null)
            return "";
        StringBuilder str = new StringBuilder();
        Queue<LCOF.TreeNode> queue = new LinkedList<>();
        queue.add(root);
        str.append(root.val).append(",");
        while (!queue.isEmpty()) {
            LCOF.TreeNode node = queue.poll();
            if (node.left != null) {
                queue.add(node.left);
                str.append(node.left.val).append(",");
            } else {
                str.append("null,");
            }

            if (node.right != null) {
                queue.add(node.right);
                str.append(node.right.val).append(",");
            } else {
                str.append("null,");
            }
        }
        return str.substring(0, str.length()-1);
    }

    // Decodes your encoded data to tree.
    public LCOF.TreeNode deserialize(String data) {
        if (data.equals(""))
            return null;
        String[] nodes = data.split(",");
        LCOF.TreeNode[] maps = new LCOF.TreeNode[nodes.length];
        int slow = 0, fast = 1;
        LCOF.TreeNode head = new LCOF.TreeNode(Integer.parseInt(nodes[0]));
        maps[0] = head;
        while (fast < nodes.length) {
            if (nodes[slow].equals("null")) {
                slow++;
                continue;
            }
            if (!nodes[fast].equals("null")) {
                LCOF.TreeNode left = new LCOF.TreeNode(Integer.parseInt(nodes[fast]));
                maps[fast] = left;
                maps[slow].left = left;
            }
            fast++;
            if (!nodes[fast].equals("null")) {
                LCOF.TreeNode right = new LCOF.TreeNode(Integer.parseInt(nodes[fast]));
                maps[fast] = right;
                maps[slow].right = right;
            }
            fast++;
            slow++;
        }
        return head;
    }
}
