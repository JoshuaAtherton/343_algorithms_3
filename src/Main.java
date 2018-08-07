import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        System.out.println("hello");
//        int [][] matrix = {{0, 2, 3, 7},
//                           {-1, 0, 2, 4},
//                           {-1, -1, 0, 2},
//                           {-1, -1, -1, 0}};
        int [][] matrix = { {0, 2, 2, 1, 15},
                            {-1, 0, 2, 3, 30},
                            {-1, -1, 0, 2, 9},
                            {-1, -1, -1, 0, 20}};
        //This will get the value (1, 2) which is 1, 1 and 1,2 we want find power set now of these.
        //We dont care about 0,0 and 3,3 because they will always be the beginning and the end.
        ArrayList<Integer> startingValues = getFirstRow(matrix);

//        ArrayList<Integer> startingValues = new ArrayList<>();
//        startingValues.add(1);
//        startingValues.add(2);
//        startingValues.add(3);
//        startingValues.add(4);

        //This will get the power set.
        ArrayList<ArrayList<Integer>> powerSet = printSubsets(startingValues);

        for (int i = 0; i < powerSet.size(); i++) {
            System.out.println("AFTER:" + powerSet.get(i));
        }

        ArrayList<Integer> shortestIndex = getShortestPath(powerSet, matrix);
        System.out.println("AFTER:" + powerSet.get(shortestIndex.get(1)));

        System.out.println("Total Cost: " + shortestIndex.get(0));

        //Prints shortest Path
        for (int i = shortestIndex.get(1); i <= shortestIndex.get(1); i++) {
            System.out.print("0 -> ");
            for (int j = 0; j < powerSet.get(i).size(); j++) {
                System.out.print(powerSet.get(i).get(j)+ " ->");

            }
            System.out.print(" " + (matrix[0].length - 1));
            System.out.println();
        }
//        System.out.println(powerSet.get(shortestIndex));




//        for (int i = 0; i < matrix.length; i++) {
//            System.out.println();
//            for (int j = 0; j < matrix.length; j++) {
//                System.out.print(matrix[i][j] + ",");
//            }
//        }

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
                System.out.println("The Cost: " + currentCost + " Index 1: " + LastValue + " Index 2: " + powerSet.get(i).get(j));
                LastValue = powerSet.get(i).get(j);
            }
            //This allows for us to exit out of the last node. In example given would be three.
            currentCost += matrix[LastValue][matrix[0].length - 1];
            System.out.println("The Cost: " + currentCost + " Index 1: " + LastValue + " Index 2: " + (matrix[0].length - 1));
            System.out.println();

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
     * This will get the first row of the matrix allowing us to see the total amount of subsets we can make.
     *
     * @param theMatrix
     * @return
     */
    public static ArrayList<Integer> getFirstRow(int [][] theMatrix) {
        ArrayList<Integer> set = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            for (int j = 1; j < theMatrix[0].length - 1; j++) {
                    set.add(j);
//                    System.out.println(j);
            }

        }
        return set;
    }

    /**
     * This will get the power set.
     *
     * @param set
     * @return
     */
    static ArrayList<ArrayList<Integer>> printSubsets(ArrayList<Integer> set)
    {
        ArrayList<ArrayList<Integer>> MainPowerSet = new ArrayList<>();
        int n = set.size();

        // Run a loop for printing all 2^n
        // subsets one by obe
        for (int i = 0; i < (1<<n); i++)
        {
            System.out.print("{ ");
            ArrayList<Integer> subPower = new ArrayList<>();


            // Print current subset
            for (int j = 0; j < n; j++)

                // (1<<j) is a number with jth bit 1
                // so when we 'and' them with the
                // subset number we get which numbers
                // are present in the subset and which
                // are not
                if ((i & (1 << j)) > 0) {
                    System.out.print(set.get(j) + " ");
                    subPower.add(set.get(j));

                }
            MainPowerSet.add(subPower);
            System.out.println("}");
        }
        return MainPowerSet;
    }
}
