package com.isswhu;

public class Greedy {
    // 122
    public int maxProfit(int[] prices) {
        int max = 0, sum = 0;
        int i = 0, j = 1;
        while (j < prices.length) {
            int diff = prices[j] - prices[i];
            if (diff >= max) {
                max = diff;
            } else {
                sum += max;
                max = 0;
                i = j;
            }
            j++;
        }
        sum += max;
        return sum;
    }
}
