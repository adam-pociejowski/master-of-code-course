package com.valverde.leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
You are given an array prices where prices[i] is the price of a given stock on the ith day.
You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 */
public class _121_BestTimeToBuyAndSellStock {

    @Test
    void test1() {
        assertEquals(5, maxProfit(new int[]{7,1,5,3,6,4}));
    }

    @Test
    void test2() {
        assertEquals(0, maxProfit(new int[]{7,6,4,3,1}));
    }

    // O(N)
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int currentMin = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - currentMin > maxProfit) {
                maxProfit = prices[i] - currentMin;
            }
            if (prices[i] < currentMin) {
                currentMin = prices[i];
            }
        }
        return maxProfit;
    }

    // O(N^2)
    public int maxProfitSlow(int[] prices) {
        int maxProfit = 0;
        int[] maxProfits = new int[prices.length - 1];
        for (int i = 1; i < prices.length; i++) {
            int currentPrice = prices[i];
            for (int j = 0; j < i; j++) {
                if (currentPrice - prices[j] > maxProfits[j]) {
                    maxProfits[j] = currentPrice - prices[j];
                    if (maxProfits[j] > maxProfit) {
                        maxProfit = maxProfits[j];
                    }
                }
            }
        }
        return maxProfit;
    }
}
