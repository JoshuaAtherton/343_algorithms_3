/*
TCSS - 343 | Summer 2018
Homework 4
Joshua Atherton | Armoni Atherton
 */


import java.util.*;
import java.io.*;

/**
 * Project 4 solves the problem of the shortest path from one point to
 * another using 3 different approaches: brute force, divide and conquer,
 * and dynamic programming.
 */
public class tcss343 {
    static Stack<Integer> s = new Stack<>();
    static ArrayList<pathCost> pathCostsList = new ArrayList<>();

    /**
     * Inner class to store the path and cheapest costs of a graph.
     */
    public class pathCost implements Comparable<pathCost> {
        private int[] path;
        private int cost;
        public pathCost(int[] path, int cost) {
            this.path = path;
            this.cost = cost;
        }
        public void incrementCost(int cost) {
            this.cost += cost;
        }
        public int getCost() {
            return cost;
        }
        public int compareTo(pathCost pc) {
            return this.cost - pc.getCost();
        }
        public String toString() {
            String display = "Cost of path: [";
            for (int i : this.path) {
                display += " ";
                display += i + 1;
                display += " ";
            }
            display += "] = " + cost;

            return display;
        }
    }

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
        int minimumCost = 999999;

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
    private ArrayList<ArrayList<Integer>> findMinSubset(ArrayList<Integer> set) {
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
    private void divideAndConquer(int[][] matrix) {
        divideGetSets(matrix, 0, matrix.length - 1);

        Collections.sort(pathCostsList);

        System.out.println("\nDivide and conquer optimal cost and path:\n" + pathCostsList.get(0));
        for (int i = 1; i < pathCostsList.size(); i++) {
            if (pathCostsList.get(i).getCost() == pathCostsList.get(0).getCost()) {
                System.out.println(pathCostsList.get(i));
            } else {
                break;
            }
        }
    }

    /**
     * Get the powersets of the matrix that is the possible routes
     * that can be taken to get to the destination end node.
     * @param arr the 2-d matrix representing the graph
     * @param start starting node
     * @param end ending node
     */
    public void divideGetSets(int[][] arr, int start, int end) {
        if (start == end) {
            s.push(end);
            int[] path = new int[s.size()];
            int cost = getCost(arr, s, path);
            //add the path and cost to the class
            pathCostsList.add(new pathCost(path, cost));
            s.pop(); s.pop();
            return;
        }
        s.push(start);
        for (int i = start + 1; i <= end; i++) {
            divideGetSets(arr, i, end);
        }
    }

    /**
     * Get the cost of the path and convert the stack to an array.
     * @param A the matrix representing the graph
     * @param s the stack that holds the path
     * @param path an array representation of the path
     * @return integer representing the cost of the path
     */
    @SuppressWarnings("unchecked")
    public int getCost(int[][] A, Stack<Integer> s, int[] path) {
        Stack<Integer> temp = (Stack<Integer>)s.clone();
        int cost = 0;
        for(int i = s.size() - 1; i >= 0; i--) {
            path[i] = temp.pop();
            if (i > 0) {
                cost += A[temp.peek()][path[i]];
            }
        }
        return cost;
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
    private int[] dynamic(int [][] matrix) {
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
    private int[] generateSolutionMatrix(int[][] A) {
        int[] result;
        int[] path;

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
        //todo: remove this when fixed
        //for testing print out the solution matrix
//        for (int r = 1; r < rows; r++) {
//            for (int c = 0; c < cols; c++) {
//                System.out.print(M[r][c] + " ");
//            }
//            System.out.println();
//        }

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
        int[] path;
        Stack<Integer> S = new Stack<>();
        int i = M.length - 1;
        int j = M[0].length - 1;

        while (i > 0 && j > 0) {
            if (M[i][j] != M[i-1][j]) {
                S.push(j + 1);
                j = i;
                i--;
            } else {
                i--;
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
     * @param args the command line input.
     * @throws FileNotFoundException throws IOException if file not created correctly.
     */
    public static void main(String[] args) throws IOException {
        //open file and process from command line arg 0
        int inputMatrix[][] = null;
        Scanner input = null;
        Boolean test = false; //Checks if file was openend succesfully.
        try {
            input = new Scanner(System.in);
            //Sets up file for output.
            test = true; //Shows it opened files succesfully.

        } catch (Exception e) {
            System.out.print("File not found " + e);
        }
        //Checks to make sure files were open succesfully.
        if (test) {
            tcss343 tcss = new tcss343();

            //generate the random matrices
//            randomMatrixGenerator(false);

            inputMatrix = getInput(input);

            // hard coded for testing
            int[][] matrix = {
                    {0, 2, 3, 7, 4},
                    {-1, 0, 2, 4, 5},
                    {-1, -1, 0, 2, 1},
                    {-1, -1, -1, 0, 3},
                    {-1, -1, -1, -1, 0}};

            // *******  Begin Main Function Calls  *******

            // get the solution with the brute force method
            long startTime = System.nanoTime();
            ArrayList<Integer> bruteResult = tcss.bruteForce(inputMatrix);
            System.out.printf("Function took: %,d nanoseconds\n",
                    (System.nanoTime() - startTime));
//
//            //get the solution with the divide and conquer method
//            startTime = System.nanoTime();
//            tcss.divideAndConquer(inputMatrix);
//            System.out.printf("Function took: %,d nanoseconds\n",
//                    (System.nanoTime() - startTime));

            //get the solution with the dynamic method
            startTime = System.nanoTime();
            int[] dynamicResult = tcss.dynamic(inputMatrix);
            System.out.printf("Function took: %,d nanoseconds\n",
                    (System.nanoTime() - startTime));

            input.close();
        }
    }

    /**
     * This will read in the input from the file and store it into a
     * matrix.
     *
     * @param theInput the current file that is being read in.
     * @return the matrix that will hold the contents of the file.
     */
    public static int[][] getInput(Scanner theInput)  {

        StringBuilder sb = new StringBuilder();
        int numberOfRows = 0;
        //This will count how big to make matrix.
        while (theInput.hasNextLine()) {
            numberOfRows++;
            sb.append(theInput.nextLine());
            sb.append("\n");
        }

        int [][] matrix = new int[numberOfRows][numberOfRows];
        Scanner theFile = new Scanner(sb.toString());

        int row = 0, col = 0;
        int cnt = 0;
        while (theFile.hasNext()) {
            if (theFile.hasNextInt()) {
//                System.out.print(theFile.nextInt() + " ");
                matrix[row][col] = theFile.nextInt();
                col++;
                if (numberOfRows == col) {
                    System.out.println();
                    col = 0;
                    row++;
                }
            } else {
//                System.out.println("Num: " + numberOfRows + " cnt: " + cnt);
                if (numberOfRows == col) {
//                    System.out.println();
                    col= 0;
                    row++;
                }
                theFile.next();
                matrix[row][col] = -1;
//                System.out.print(theFile.next() + " ");
                col++;
            }
        }
        //This will allow for print the matrix that is returned.

//        for (int r = 0; r < matrix.length; r++) {
//            System.out.println();
//            for (int c = 0; c < matrix.length; c++) {
//                System.out.printf("%5d", matrix[r][c]);
//            }
//        }
//        System.out.println();

        return matrix;
    }

    // 3.5 Testing
    /**
     * Generate random matrices. If true generate completely random
     * else false random increasing along columns.
     * @param isRandom specify to generate completely random or
     *                 random increasing along columns
     * @throws IOException if files were not created
     */
    private static void randomMatrixGenerator(boolean isRandom)
            throws IOException {
//        int[] n = {25, 50, 100, 200, 400, 800};
        int[] n = {5, 10};
        Random rand = new Random();
        //run through each power of n
        for (int i : n) {
            //This will create the file name.
            String fileOut = "./" + i + "out.txt";
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(fileOut));
            } catch (IOException e) {
                System.out.println(e);
            }

            int[][] nthMatrix = new int[i][i];
            // generate the n-th matrix
            for (int r = 0; r < i; r++) {
                for (int c = 0; c < i; c++) {
                    if (r == c) {
                        nthMatrix[r][c] = 0;
                    } else if (r >= c) {
                        nthMatrix[r][c] = -1;
                    } else {
                        if (isRandom) {
                            //part 1 everything is random
                            nthMatrix[r][c] = rand.nextInt(1000);
                        } else
                            //part 2 random except each column is larger than the last
                            nthMatrix[r][c] = (nthMatrix[r][c-1] + rand.nextInt(1000)) + 1;
                    }
                }
            }
            //write the nthMatrix out to file here
            for (int r = 0; r < nthMatrix.length; r++) {
                for (int c = 0; c < nthMatrix.length; c++) {
                    if (nthMatrix[r][c] == -1) {
                        output.write("NA\t");
                    } else {
                        output.write(nthMatrix[r][c] + "\t");
                    }
                }
                output.write("\n");
            }
            output.flush();
            output.close();
        } // end n-th for loop
    }
}
