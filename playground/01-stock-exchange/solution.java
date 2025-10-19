package bg.sofia.uni.fmi.mjt.show;

import java.util.Scanner;

//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/description/
public class StockExchange {
    public static int maxProfit(int[] prices) {
        int n = prices.length;
        if(n == 0) return 0;

        int[][] dp=new int[3][n];
        for (int k=1;k<=2;k++){
            int min=prices[0];
            for (int i=1;i<n;i++){
                min=Math.min(min, prices[i]-dp[k-1][i-1]);
                dp[k][i] = Math.max(dp[k][i-1], prices[i] - min);
            }
        }
        return dp[2][n-1];
    }
}

void main() {
    Scanner inputReader = new Scanner(System.in);
    int[] input = Arrays.stream(inputReader.nextLine().split("\\s+"))
            .mapToInt(Integer::parseInt)
            .toArray();

    System.out.println(StockExchange.maxProfit(input));
}