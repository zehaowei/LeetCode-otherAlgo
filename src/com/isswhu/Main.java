package com.isswhu;

public class Main {

    public static void main(String[] args) {
	    int[] nums = {2, -5, -2, -4, 3};
	    test(nums);
        System.out.println(nums[0]);
    }

    public static void test(int[] num) {
        num[0] = -1;
    }
}
