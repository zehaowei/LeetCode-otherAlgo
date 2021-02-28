package com.isswhu;

public class Basic {
    // 冒泡排序
    void sort(int[] nums) {
        for (int i = nums.length-2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                if (nums[j] > nums[j+1]) {
                    int tmp = nums[j+1];
                    nums[j+1] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
    }

    public void bubbleSort2(int[] arr) {
        int i = arr.length - 1;//初始时,最后位置保持不变　　
        while (i > 0) {
            int flag = 0;//每趟开始时,无记录交换
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = j;//记录交换的位置
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
            i = flag; //为下一趟排序作准备
        }
    }

    // 二分搜索
}
