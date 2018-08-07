/*
TCSS - 343 | Summer 2018
Homework 4
Joshua Atherton | Armoni Atherton
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Homework for solves the coin change problem using 3 different algorithm approaches
 */
public class tcss343 {

    //3.1 Brute force
    /**
     * Design a brute force solution to solve this problem.
     * You need to print the cheapest solution, as well as the sequence.
     *
     * @param matrix the 2d array representing the graph
     * @return array where index 0 is cost, the rest in shortest path sequence
     */
    public ArrayList<Integer> bruteForce(int[][] matrix) {
        ArrayList<Integer> result = new ArrayList<>(); // hold value to return
        ArrayList<Integer> startingValues = getFirstRow(matrix);
        ArrayList<ArrayList<Integer>> powerSet = printSubsets(startingValues);
        ArrayList<Integer> shortestPath = getShortestPath(powerSet, matrix);

        System.out.println("Brute force: ");
        System.out.println("Total Cost: " + shortestPath.get(0));

        result.add(shortestPath.get(0));
        //Prints the shortest Path
        for (int i = shortestPath.get(1); i <= shortestPath.get(1); i++) {
            System.out.print("1 -> ");
            result.add(1);
            for (int j = 0; j < powerSet.get(i).size(); j++) {
                System.out.print((powerSet.get(i).get(j) + 1) + " -> ");
                result.add(powerSet.get(i).get(j) + 1);
            }
            System.out.print((matrix[0].length));
            System.out.println();
            result.add(matrix[0].length);
        }
        return result;
    }

    /**
     * This will get the first row of the matrix allowing us to see the total amount of subsets we can make.
     *
     * @param theMatrix
     * @return
     */
    public ArrayList<Integer> getFirstRow(int [][] theMatrix) {
        ArrayList<Integer> set = new ArrayList<>();
        for (int i = 0; i < 1; i++)
            for (int j = 1; j < theMatrix[0].length - 1; j++)
                set.add(j);
        return set;
    }

    /**
     * This will allow us to get the shortest path between all possible nodes.
     * \
     * @param powerSet
     * @param matrix
     * @return
     */
    private static ArrayList<Integer> getShortestPath(ArrayList<ArrayList<Integer>> powerSet, int [][] matrix) {

        ArrayList<Integer> costAndPath = new ArrayList<>();
        //This will hold the smallest sublist/path of nodes to the end.
        int smallestIndex = 0;

        //Total minimum between all subsets/paths.
        int minimumCost = 99999999;

        for (int i = 0; i < powerSet.size(); i++) {
            //This will allow us to update our current index of the smallest path.
            int currentSubsetIndex = i;
            //Current cost of the current subset.
            int currentCost = 0;

            //This will allow us to map for example(0,1)-> (1, 2) which would be (0,1)-> ((1=LastValue), 2) -> ((2=LastValue), 3)
            int LastValue = 0;
            //This will add in the 0, 0 cost.
            for (int j = 0; j < powerSet.get(i).size(); j++) {
                currentCost += matrix[LastValue][powerSet.get(i).get(j)];
                LastValue = powerSet.get(i).get(j);
            }
            //This allows for us to exit out of the last node. In example given would be three.
            currentCost += matrix[LastValue][matrix[0].length - 1];

            //This will check if global minimum between sets needs to be updated.
            if (minimumCost > currentCost) {
                minimumCost = currentCost;
                //Set smallest index.
                smallestIndex = currentSubsetIndex;
            }
        }
        //Minimum Cost.
        costAndPath.add(minimumCost);
        //Shortest Path
        costAndPath.add(smallestIndex);
        return costAndPath;
    }

    /**
     * This will get the power set.
     *
     * @param set
     * @return
     */
    public ArrayList<ArrayList<Integer>> printSubsets(ArrayList<Integer> set) {
        ArrayList<ArrayList<Integer>> MainPowerSet = new ArrayList<>();
        int n = set.size();

        // Run a loop for printing all 2^n, subsets one by obe
        for (int i = 0; i < (1<<n); i++) {
            ArrayList<Integer> subPower = new ArrayList<>();

            // Print current subset
            for (int j = 0; j < n; j++)
                // (1<<j) is a number with jth bit 1
                // so when we 'and' them with the
                // subset number we get which numbers
                // are present in the subset and which
                // are not
                if ((i & (1 << j)) > 0) {
                    subPower.add(set.get(j));
                }
            MainPowerSet.add(subPower);
        }
        return MainPowerSet;
    }


    //3.2 Divide and conquer
    /**
     *Express the problem with a purely divide-and-conquer approach.
     * Implement a recursive algorithm for the problem.
     * Be sure to consider all sub-instances needed to compute a solution
     * to the full input instance in the self-reduction, especially if it contains
     * overlaps. As before, you need to print the solution, as well as the sequence.
     */
    public void divide() {

    }


    //3.3 Dynamic programming
    /**
     *Design a dynamic programming table for this problem. How is the table initial- ized?
     * In what order will the table be filled? How would you use the table to
     * find the cheapest sequence of canoe rentals from post 1 to post n? Implement
     * the corresponding dynamic programming solution.
     */
    public void dynamic() {

    }


    public static void main(String[] args) {
        //open file and process from command line arg 0


        tcss343 tcss = new tcss343();
        // hard coded for now
        int [][] matrix = {{0, 2, 3, 7},
                           {-1, 0, 2, 4},
                           {-1, -1, 0, 2},
                           {-1, -1, -1, 0}};

        ArrayList<Integer> result = tcss.bruteForce(matrix);
        //cost = array list 0, path is the rest of array list
        System.out.println(result.toString());




    }

}
