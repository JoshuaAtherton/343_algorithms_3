import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class tcss343_two {
    private static Stack<Integer> s;
    private static ArrayList<pathCost>pathCostsList;
    private static ArrayList<pathCost> pathCostsBrute;
    private int[][] inputMatrix;

    public tcss343_two(Scanner input) {
        s = new Stack<>();
        pathCostsList = new ArrayList<>();
        pathCostsBrute = new ArrayList<>();
        //read file input and assign its results to inputMatrix
        inputMatrix = getInput(input);

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
                matrix[row][col] = theFile.nextInt();
                col++;
                if (numberOfRows == col) {
                    System.out.println();
                    col = 0;
                    row++;
                }
            } else {
                if (numberOfRows == col) {
                    col= 0;
                    row++;
                }
                theFile.next();
                matrix[row][col] = -1;
                col++;
            }
        }
        return matrix;
    }

    /* **************** 3.5 Testing generate random matrices ********** */
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
            String display = "Cost of path: [ ";
            for (int i : this.path) {
                display += i + 1;
                display += " ";
            }
            display += "] = " + cost;

            return display;
        }
    }

    /* ****************** 3.1 Brute Force ********************** */
    private void bruteForce(int[][] matrix) {
        int[] innerNumbers = new int[matrix[0].length - 2];
        ArrayList<ArrayList<Integer>> powerSets; //= new ArrayList<>();
        for (int i = 1; i < matrix[0].length - 1; i++) {
            innerNumbers[i - 1] = i;
        }
//        System.out.println(Arrays.toString(innerNumbers));
        //get powersets
        powerSets = getPowersets(innerNumbers);
//        System.out.println(powerSets);
        for (int i = 0; i < powerSets.size(); i++) {
            int[] tempPath = new int[powerSets.get(i).size() + 2];
            int cost = getCostBrute(matrix, powerSets.get(i), tempPath);
            System.out.println(Arrays.toString(tempPath) + " " + cost);
            pathCostsBrute.add(new pathCost(tempPath, cost));
        }
//        Collections.sort(pathCostsBrute);
//        System.out.println("Path and cost of Brute force: ");
//        System.out.println(pathCostsBrute.get(0));

    }
    /**
     * This will get the power set.
     *
     * @param set
     * @return
     */
    private ArrayList<ArrayList<Integer>> getPowersets(int[] set) {
        ArrayList<ArrayList<Integer>> MainPowerSet = new ArrayList<>();
        int n = set.length;

        // Run a loop for printing all 2^n, subsets one by obe
        for (int i = 0; i < (1<<n); i++) {
            ArrayList<Integer> subPower = new ArrayList<>();

            // Print current subset
            for (int j = 0; j < n; j++)
                if ((i & (1 << j)) > 0) {
                    subPower.add(set[j]);
                }
            MainPowerSet.add(subPower);
        }
        return MainPowerSet;
    }

    public int getCostBrute(int[][] A, ArrayList<Integer> powerSets, int[] path) {
        path[0] = 0;
        path[path.length - 1] = A[0].length - 1;
        int cost = 0;
        System.out.println("Powersets: " + powerSets);
        for(int i = 0; i < powerSets.size(); i++) {
            cost += A[path[i]][powerSets.get(i)];
            path[i+1] = powerSets.get(i);

        }
        if (path.length < 3 ) {
            cost += A[0][path[1]];
        } else {
            cost += A[path.length - 2][path.length - 1];
        }

        return cost;
    }



    /* ****************** 3.2 Divide and conquer ********************** */
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


    /* ****************** 3.3 Dynamic Programming ********************** */
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
    private void dynamic(int [][] matrix) {
        int[] solution = generateSolutionMatrix(matrix);

        System.out.println("\nDynamic implementation path and cost: ");
        System.out.printf("Cost of path: [ %d", solution[1]);
        for(int i = 2; i < solution.length; i++) {
            System.out.print(" ");
            System.out.print(solution[i]);
        }
        System.out.printf(" ] = %d\n", solution[0]);
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


    public static void main (String[] Args) throws IOException {
        //get input file from consoleScanner input = null;
        Boolean fileFound = false;
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            fileFound = true;
        } catch (Exception e) {
            System.out.print("File not found " + e);
        }
        if (fileFound) {
           tcss343_two tcss = new tcss343_two(input);
            //generate random matrices
           tcss.randomMatrixGenerator(true);

           long startTime;
            //call brute force on input matrix
            startTime = System.nanoTime();
            tcss.bruteForce(tcss.inputMatrix);
            System.out.printf("Function took: %,d nanoseconds\n",
                    (System.nanoTime() - startTime));

            //call divide and conquer on on input matrix
            startTime = System.nanoTime();
            tcss.divideAndConquer(tcss.inputMatrix);
            System.out.printf("Function took: %,d nanoseconds\n",
                    (System.nanoTime() - startTime));

            //call dynamic on input matrix
            startTime = System.nanoTime();
            tcss.dynamic(tcss.inputMatrix);
            System.out.printf("Function took: %,d nanoseconds\n",
                    (System.nanoTime() - startTime));

            input.close();
        }

    }
}
