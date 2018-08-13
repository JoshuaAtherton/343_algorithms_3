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
    public void divideAndConquer(int[][] matrix) {
        divide(matrix, 0, matrix.length - 1);

        Collections.sort(pathCostsList);

        System.out.println("\nDivide and conquer: optimal cost and path\n" + pathCostsList.get(0));
        for (int i = 1; i < pathCostsList.size(); i++) {
            if (pathCostsList.get(i).getCost() == pathCostsList.get(0).getCost()) {
                System.out.println(pathCostsList.get(i));
            } else {
                break;
            }
        }
    }

    /**
     * Take a stack as input and return that stack in the
     * same order as an array
     * @param s a stack of integers
     * @return an array that is equivalent to the stack
     */
    @SuppressWarnings("unchecked")
    public int[] stackToArray(Stack<Integer> s) {
        int[] result = new int[s.size()];
        Stack<Integer> temp = (Stack<Integer>)s.clone();
        for(int i = s.size() - 1; i >= 0; i--) {
            result[i] = temp.pop();
        }
        return result;
    }

    /**
     *  The cost it will take to travel in the given nodes
     *  sequence.
     * @param A the matrix that represents the graph with costs
     * @param nodes a sequence of nodes
     * @return the cost to travel that node sequence
     */
    public int getCost(int[][] A, int[] nodes) {
        int cost = 0;
        for (int i = 0; i < nodes.length - 1; i++) {
            cost += A[nodes[i]][nodes[i+1]];
        }
        return cost;
    }

    /**
     * Gets the minimum cost of the graph to go from one
     * start node to the end node.
     * @param A the matrix that represents the graph to search
     * @param start starting node
     * @param end node to end on (get to)
     * @return the minimum possible cost
     */
    public int divide(int[][] A, int start, int end) {
        if (start == end) {
            s.push(end);
            int[] result = stackToArray(s);
            int cost = getCost(A, result);
            pathCostsList.add(new pathCost(result, cost));
            s.pop(); s.pop();
            return 0;
        }

        s.push(start);
        ArrayList<Integer> costs = new ArrayList<>();
        for (int i = start + 1; i <= end; i++) {
            costs.add(A[start][i] + divide(A, i, end));
        }

        int minCost = Collections.min(costs);

        return minCost;
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
    public static void main(String[] args) throws IOException {
        //open file and process from command line arg 0
        int m[][] = null;
        Scanner input = null;
//        String fileIn = args[0]; //Stores input file name.
        String fileIn = "src/input.txt"; // to run without using terminal
        Boolean test = false; //Checks if file was openend succesfully.
        try {
//            input = new Scanner(new File(fileIn)); //Opens file with scanner.
            input = new Scanner(System.in);
            //Sets up file for output.
            test = true; //Shows it opened files succesfully.

        } catch (Exception e) {
            System.out.print("File not found " + e);
        }
        //Checks to make sure files were open succesfully.
        if (test) {
            tcss343 tcss = new tcss343();
            randomMatrixGenerator();
//            m = getInput(input);

            // hard coded for now // not needed // for testing
            int[][] matrix = {
                    {0, 2, 3, 7, 4},
                    {-1, 0, 2, 4, 5},
                    {-1, -1, 0, 2, 1},
                    {-1, -1, -1, 0, 3},
                    {-1, -1, -1, -1, 0}};

            /************* begin main function calls ********************/


            // get the solution with the brute force method
            ArrayList<Integer> bruteResult = tcss.bruteForce(matrix);

            //get the solution with the divide and conquer method
            tcss.divideAndConquer(matrix);

            //get the solution with the dynamic method
            int[] dynamicResult = tcss.dynamic(matrix);

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
            sb.append(theInput.nextLine() + "\n");
        }

        int [][] matrix = new int[numberOfRows][numberOfRows];
//        System.out.println(sb.toString() + numberOfRows);

        int row = 0, col = 0;
        int size = sb.toString().toCharArray().length;
        for (int r = 0; r < size; r++) {
            //This will check new line.
            if (sb.charAt(r) == '\n') {
//                System.out.println();
                row++;
                col = 0;

            } else if (sb.charAt(r) == ' ') { //This will check space
                    //Do nothing
            } else if (sb.charAt(r) == 'N') {
                //This increments past the 'A'.
//                System.out.print(-1 + " ");
                matrix[row][col] = -1;
                //Added a value so increment another spot over.
                col++;
                r++;
            } else {
//                System.out.print(sb.charAt(r) + " ");
                matrix[row][col] = sb.charAt(r) - '0';
                col++;
            }
        }
        //This will allow for print the matrix that is returned.
    /*
        for (int r = 0; r < matrix.length; r++) {
            System.out.println();
            for (int c = 0; c < matrix.length; c++) {
                System.out.print(matrix[r][c] + "  ");
            }
        }
    */
        return matrix;
    }

    // 3.5 Testing
    /**
     * Generate random matrices.
     */
    public static void randomMatrixGenerator() throws IOException {
//        int[] n = {25, 50, 100, 200, 400, 800};
        int[] n = {5};
        Random rand = new Random();
        //run through each power of n
        for (int i : n) {
            //This will create the file name.
            String fileOut = i + "out.txt";
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
                        //todo: part 1 done but part 2?
                        nthMatrix[r][c] = rand.nextInt(10);
                    }
                }
            }
            //write the nthMatrix out to file here???
            for (int r = 0; r < nthMatrix.length; r++) {
                output.write("\n");
                for (int c = 0; c < nthMatrix.length; c++) {
                    output.write(nthMatrix[r][c] + "\t");
                }
            }
            output.flush();
            output.close();
        } // end n-th for loop
    }
}
