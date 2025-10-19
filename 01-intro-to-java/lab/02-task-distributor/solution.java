package bg.sofia.uni.fmi.mjt.show;

import java.util.Scanner;

public class TaskDistributor {
    public static int minDifference(int[] tasks) {
        int sum = 0, target = 0, n = tasks.length;
        if(n == 0) return 0;
        else if(n == 1) return tasks[0];

        for(int x : tasks) sum += x;

        target = sum / 2;

        // saving only half as other half is redundant
        boolean [] reachableSums = new boolean[target+1];
        reachableSums[0] = true; // empty set

        for(int i : tasks) {
            for(int j = target; j >= i; j--) {
                // sum j can be reached if sum j is already reached
                // or there is sum j - i reachable meaning if we add task i than sum j is reachable
                reachableSums[j] = reachableSums[j] || reachableSums[j - i];
            }
        }

        for(int i = target; i >= 0; i--) {
            // we start from target because the closer we are to target the smaller is the difference
            // diff = abs(sum_1 - sum_2)
            // sum_2 = total - sum_1
            // diff = abs(total - 2 * sum_1) = total 2 * sum_1
            if(reachableSums[i]) return sum - 2 * i;
        }

        return sum;
    }
}

void main() {
    Scanner inputReader = new Scanner(System.in);
    int[] input = Arrays.stream(inputReader.nextLine().split("\\s+"))
            .mapToInt(Integer::parseInt)
            .toArray();

    System.out.println(TaskDistributor.minDifference(input));
}