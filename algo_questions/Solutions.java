package algo_questions;
import java.util.Arrays;

/**
 * this class contains 4 different   algorithms
 */
public class Solutions  extends java.lang.Object {
    /**
     * constructor
     */
    public Solutions(){};

    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time slots.
     * A task can only be completed in a time slot if the length of the time slot is grater than the no.
     * of hours needed to complete the task.
     * @param tasks array of integers of length n. tasks[i] is the time in hours required to complete task i.
     * @param timeSlots array of integersof length m. timeSlots[i] is the length in hours of the slot i.
     * @return maximal amount of tasks that can be completed
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots) {
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        int maximalAmount = 0;
        int i = 0;
        int j = 0;
        while (i < tasks.length && j < timeSlots.length) {
            if (tasks[i] <= timeSlots[j]) {
                maximalAmount = maximalAmount + 1;
                i++;
                j++;
            }
            else {
                j++;
            }
        }
    return maximalAmount;
    }



    /**
     * Method computing the nim amount of leaps a frog needs to jumb across n waterlily leaves,
     * from leaf 1 to leaf n. The leaves vary in size and how stable they are,
     * so some leaves allow larger leaps than others. leapNum[i] is an integer telling you
     * how many leaves ahead you can jump from leaf i. If leapNum[3]=4, the frog can jump from leaf 3,
     * and land on any of the leaves 4, 5, 6 or 7.
     * @param leapNum array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return minimal no. of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum) {
        int numOfJumps = 0;
        int maxReach = 0;
        int curLeaf = 0;
        int i =0;
        while(i < leapNum.length)
        {

            if (curLeaf < i) {
                curLeaf = maxReach;
                numOfJumps++;
            }
            if (leapNum[i] + i > maxReach) {
                maxReach = leapNum[i] +i;
            }
            i++;
        }

        return numOfJumps;
    }


    /**
     * Method computing the solution to the following problem:
     * A boy is filling the water trough for his father's cows in their village.
     * The trough holds n liters of water. With every trip to the village well,
     * he can return using either the 2 bucket yoke, or simply with a single bucket.
     * A bucket holds 1 liter. In how many different ways can he fill the water trough?
     * n can be assumed to be greater or equal to 0, less than or equal to 48.
     * @param n trough capacity
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n){
        if(n == 0){
            return 1;
        }
        if (n==1){
            return  1;
        }
        int[] numOfSequences =  new int[n+1];
        numOfSequences[0] =1;
        numOfSequences[1] = 1;
        for (int i = 2; i <=n ; i++) {
            numOfSequences[i] = numOfSequences[i-1] + numOfSequences[i-2];

        }
        return numOfSequences[n];
    }

    /**
     * Method computing the solution to the following problem: Given an integer n,
     * return the number of structurally unique BST's (binary search trees)
     * which has exactly n nodes of unique values from 1 to n. You can assume n is at least 1 and at most 19
     * @param n
     * @return valid output of algorithm.
     */
    public static int numTrees(int n){
        int[] numOfTrees =  new int[n+1];
        if(n==0){
            return 0;
        }
        numOfTrees[0] =1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                numOfTrees[i] += numOfTrees[j]*numOfTrees[i-j-1];
            }

        }
        return  numOfTrees[n];


    }


}





