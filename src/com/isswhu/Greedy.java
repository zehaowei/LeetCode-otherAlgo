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

    // 134. 加油站
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int[] scores = new int[gas.length];
        for (int i = 0; i < gas.length; i++)
            scores[i] = gas[i] - cost[i];
        if (gas.length == 1)
            return scores[0] >= 0 ? 0 : -1;

        int start = 0;
        while (true) {
            int cur = start, sum = scores[cur];
            while (sum >= 0) {
                if (start > 0 && cur == start-1 || start == 0 && cur == gas.length-1)
                    return start;
                cur = (cur+1) % gas.length;
                sum += scores[cur];
            }
            if ((cur+1) % gas.length <= start)
                return -1;
            start = (cur+1) % gas.length;
        }
    }
}
