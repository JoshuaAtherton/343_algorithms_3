/*
TCSS - 343 | Summer 2018
Homework 4
Joshua Atherton | Armoni Atherton
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.util.Stack;

/**
 * Project 4 solves the problem of the shortest path from one point to
 * another using 3 different approaches: brute force, divide and conquer,
 * and dynamic programming.
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
        ArrayList<ArrayList<Integer>> powerSet = findMinSubset(startingValues);
        ArrayList<Integer> shortestPath = getShortestPath(powerSet, matrix);

        System.out.println("\nBrute force: ");
        System.out.println("Total Cost: " + shortestPath.get(0));
        System.out.print("Path: ");

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
     * This will get the first row of the matrix allowing us to see the
     * total amount of subsets we can make.
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
    private static ArrayList<Integer> getShortestPath(
            ArrayList<ArrayList<Integer>> powerSet, int [][] matrix) {

        ArrayList<Integer> costAndPath = new ArrayList<>();
        //This will hold the smallest sublist/path of nodes to the end.
        int minSubsetIndex = 0;

        //Total minimum between all subsets/paths.
        int minimumCost = 99999999;

        for (int i = 0; i < powerSet.size(); i++) {
            //This will allow us to update our current index of the smallest path.
            int currentSubsetIndex = i;
            //Current cost of the current subset.
            int currentCost = 0;

            //This will allow us to map for example(0,1)-> (1, 2)
            // which would be (0,1)-> ((1=LastValue), 2) -> ((2=LastValue), 3)
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
                minSubsetIndex = currentSubsetIndex;
            }
        }
        //Minimum Cost.
        costAndPath.add(minimumCost);
        //Shortest Path
        costAndPath.add(minSubsetIndex);
        return costAndPath;
    }

    /**
     * This will get the power set.
     *
     * @param set
     * @return
     */
    public ArrayList<ArrayList<Integer>> findMinSubset(ArrayList<Integer> set) {
        ArrayList<ArrayList<Integer>> MainPowerSet = new ArrayList<>();
        int n = set.size();

        // Run a loop for printing all 2^n, subsets one by obe
        for (int i = 0; i < (1<<n); i++) {
            ArrayList<Integer> subPower = new ArrayList<>();

            // Print current subset
            for (int j = 0; j < n; j++)
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
     *
     * @param matrix represents the graph of destinations and costs
     * @return an array where index one is the cost and what
     *         follows is the shortest path
     */
    public int[] dynamic(int [][] matrix) {
        int[] solution = generateSolutionMatrix(matrix);

        System.out.println("\nDynamic implementation: ");
        System.out.println("Cost: " + solution[0]);
        System.out.printf("Path: %d", solution[1]);
        for(int i = 2; i < solution.length; i++) {
            System.out.print(" -> ");
            System.out.print(solution[i]);
        }
        System.out.println();

        return solution;
    }

    /**
     * Returns an array where first element is the cost and remaining elements are the path.
     * @param A represents the graph of destinations and costs
     * @return an array where index one is the cost and what
     *         follows is the shortest path
     */
    public int[] generateSolutionMatrix(int[][] A) {
        int[] result = null;
        int[] path = null;

        int rows = A.length;
        int cols = A[0].length;
        int[][] M = new int[rows][cols];

        // initialize row1 with initial direct values
        for (int c = 0; c < cols; c++) {
            M[0][c] = A[0][c];
        }

        // create the rest of the solution matrix
        for (int r = 1; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (c <= r) {
                    M[r][c] = M[r - 1][c];
                } else {
                    M[r][c] = Math.min(M[r-1][c], M[r-1][r] + A[r][c]);
                }
            }
        }

        // get the path
        path = recoverSolution(M);
        result = new int[path.length + 1];
        result[0] = M[rows - 1][cols - 1];
        for (int i = 0; i < path.length; i++) {
            result[i + 1] = path[i];
        }

        return result;
    }

    /**
     * Given an 2-d array that represents a solution matrix for another graph
     * this method will recover the shortest path from this matrix.
     * @param M the solution matrix to recover the shortest path from
     * @return an array representing the shortest path
     */
    public int[] recoverSolution(int M[][]) {
        int[] path = null;
        Stack<Integer> S = new Stack<>();
        int i = M.length - 1;
        int j = M[0].length - 1;

        while (i > 0 && j > 0) {
            if (M[i-1][j] == M[i][j]) {
                i--;
            } else {
                S.push(j + 1);
                j--;
            }
            if (i == 0 || j == 0) {
                S.push(j + 1);
            }
        }
        S.push(1);

        // reverse the path and store in an array
        path = new int[S.size()];
        //pop stack into an array
        int count = 0;
        while (!S.isEmpty()) {
            path[count] = S.pop();
            count++;
        }

        return path;
    }


    /**
     *  Driver to open a .txt file that hold an adjacency matrix that represents
     *  a graph. After reading the contents of the file will solve the shortest
     *  path problem using different algorithm implementations.
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        //open file and process from command line arg 0
        int m[][] = null;
        Scanner input = null;
//        String fileIn = args[0]; //Stores input file name.
        String fileIn = "src/input.txt"; // to run without using terminal
        Boolean test = false; //Checks if file was openend succesfully.
        try {
            input = new Scanner(new File(fileIn)); //Opens file with scanner.
            //Sets up file for output.
            test = true; //Shows it opened files succesfully.

        } catch (FileNotFoundException e) {
            System.out.print("File not found " + e);
        }
        //Checks to make sure files were open succesfully.
        if (test) {
            tcss343 tcss = new tcss343();
            try {
                m = getInput(input, fileIn);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // hard coded for now // not needed // for testing
            int[][] matrix = {
                    {0, 2, 3, 7, 4},
                    {-1, 0, 2, 4, 5},
                    {-1, -1, 0, 2, 1},
                    {-1, -1, -1, 0, 3},
                    {-1, -1, -1, -1, 0}};

            //begin main function calls

            // get the solution with the brute force method
            ArrayList<Integer> bruteResult = tcss.bruteForce(matrix);
//            System.out.println(bruteResult.toString());
//            System.out.println(Arrays.deepToString(m));


            //get the solution with the dynamic method
            int[] dynamicResult = tcss.dynamic(m);

        }
    }

    /**
     * THIS WORKS BUT VERY MESSY PROBABLY NEED TO REDO IT.
     * @param theInput
     * @param theFileIn
     * @throws FileNotFoundException
     */
    public static int[][] getInput(Scanner theInput, String theFileIn) throws FileNotFoundException {
        /* THIS WILL GET THE NUMBER OF ROWS */
        ArrayList<Integer> array = new ArrayList<>();
        String input = null;
        String s = "";
        int row = 0;
        while (theInput.hasNextLine()) {
            row++;
            s += theInput.nextLine() + "\n";
        }

        /* THIS WILL GET THE NUMBER OF COLUMNS */
        Scanner sc = new Scanner(new File(theFileIn));
        int col = 0;
        boolean flag = true;
        while (sc.hasNext()) {
            String temp =sc.next();
            if (!(temp.equals("NA")) && flag) {
                col++;
            } else {
                flag = false;
            }
        }

        /* THIS WILL PUT THE VALUES INTO THE MATRIX */
        Scanner newsc = new Scanner(new File(theFileIn));
        int [][] matrix = new int[row][col];
        int tempRow = 0;
        int tempCol = 0;

        while (newsc.hasNext()) {
            if (tempCol == col - 1) {
                if (newsc.hasNextInt()) {

                    int num = newsc.nextInt();
                    matrix[tempRow][tempCol] = num;
                } else {
                    matrix[tempRow][tempCol] = -1;
                    newsc.next();
                }
                tempCol = 0;
                tempRow++;
            } else {
                if (tempRow != row) {
                    if (newsc.hasNextInt()) {
                        int num = newsc.nextInt();
                        matrix[tempRow][tempCol] = num;
                        tempCol++;
                    } else {
                        matrix[tempRow][tempCol] = -1;
                        newsc.next();
                        tempCol++;
                    }
                }
            }
        }
        return matrix;
    }

    // 3.5 Testing
    /**
     * Generate random matrices.
     */
    public static void randomMatrixGenerator() {
        int[] n = {25, 50, 100, 200, 400, 800};

        //run through each power of n
        for (int i: n) {
            int[][] nthMatrix = new int[i][i];
            // generate the n-th matrix
            for (int r = 0; r < i; r++) {
                for (int c = 0; c < i; c++) {
                    if (r <= c) {
                        nthMatrix[r][c] = 0;
                    } else {
                        //todo: part 1 done but part 2?
                        nthMatrix[r][c] = (int) Math.random();
                    }
                }
            }
            //write the nthMatrix out to file here???

        } // end n-th for loop
    }
}
