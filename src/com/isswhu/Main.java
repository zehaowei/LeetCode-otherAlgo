package com.isswhu;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //int[] arr = new int[]{0,1,1,1,4,5,3,7,7,8,10,2,7,8,0,5,2,16,12,1,19,15,5,18,2,2,22,15,8,22,17,6,22,6,22,26,32,8,10,11,2,26,9,12,9,7,28,33,20,7,2,17,44,3,52,27,2,23,19,56,56,58,36,31,1,19,19,6,65,49,27,63,29,1,69,47,56,61,40,43,10,71,60,66,42,44,10,12,83,69,73,2,65,93,92,47,35,39,13,75};
        int[] arr = new int[]{0,1,1,1,4,5,3,7,};
        Test t = new Test();
        t.quicksort(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i]+" ");
    }
}

class Test {
    void quicksort(int[] arr, int l, int r) {
        if (l >= r)
            return;
        int ind = partition(arr, l, r);
        quicksort(arr, l, ind-1);
        quicksort(arr, ind+1, r);
    }

    int partition(int[] arr, int l, int r) {
        int p1 = l, p2 = l+1;
        while (p2 <= r) {
            if (arr[l] > arr[p2]) {
                p1++;
                if (p1 != p2) {
                    swp(arr, p1, p2);
                }
            }
            p2++;
        }
        swp(arr, l, p1);
        return p1;
    }

    void swp(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
