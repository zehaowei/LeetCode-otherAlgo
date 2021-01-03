package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        int[] arr = new int[]{15252,16764,27963,7817,26155,20757,3478,22602,20404,6739,16790,10588,16521,6644,20880,15632,27078,25463,20124,15728,30042,16604,17223,4388,23646,32683,23688,12439,30630,3895,7926,22101,32406,21540,31799,3768,26679,21799,23740};
        Test t = new Test();
        char[][] cc = new char[][]{{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
        ListNode n1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        t.diffWaysToCompute("2-1-1");
    }


}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Test {
    public List<Integer> diffWaysToCompute(String input) {
        ArrayList<String> ops = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i)))
                num = num*10 + (input.charAt(i) - '0');
            else {
                ops.add(String.valueOf(num));
                num = 0;
                ops.add(String.valueOf(input.charAt(i)));
            }
        }
        ops.add(String.valueOf(num));
        return dac(ops, 0, ops.size()-1);
    }

    List<Integer> dac(ArrayList<String> ops, int l, int r) {
        List<Integer> re = new ArrayList<>();
        if (r-l+1 <= 3) {
            re.add(calcu(Integer.parseInt(ops.get(l)), ops.get(l+1), Integer.parseInt(ops.get(r))));
            return re;
        } else if (l == r) {
            re.add(Integer.parseInt(ops.get(l)));
            return re;
        }
        for (int i = l; i < r; i+=2) {
            List<Integer> re1 = dac(ops, l, i), re2 = dac(ops, i+2, r);
            for (int num1 : re1) {
                for (int num2 : re2)
                    re.add(calcu(num1, ops.get(i+1), num2));
            }
        }
        return re;
    }

    int calcu(int a, String op, int b) {
        switch (op) {
            case "+":
                return a+b;
            case "-":
                return a-b;
            default:
                return a*b;
        }
    }
}
