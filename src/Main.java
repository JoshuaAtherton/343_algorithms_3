import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        System.out.println("hello");
        int [][] matrix = {{0, 2, 3, 7},
                           {-1, 0, 2, 4},
                           {-1, -1, 0, 2},
                           {-1, -1, -1, 0}};
        //This will get the value (1, 2) which is 1, 1 and 1,2 we want find power set now of these.
        //We dont care about 0,0 and 3,3 because they will always be the beginning and the end.
        ArrayList<Integer> startingValues = getFirstRow(matrix);

//        ArrayList<Integer> startingValues = new ArrayList<>();
//        startingValues.add(1);
//        startingValues.add(2);
//        startingValues.add(3);
//        startingValues.add(4);
        ArrayList<ArrayList<Integer>> powerSet = printSubsets(startingValues);

        for (int i = 0; i < powerSet.size(); i++) {
            System.out.println("AFTER:" + powerSet.get(i));
        }

        getShortestPath(powerSet);




//        for (int i = 0; i < matrix.length; i++) {
//            System.out.println();
//            for (int j = 0; j < matrix.length; j++) {
//                System.out.print(matrix[i][j] + ",");
//            }
//        }

    }

    private static int getShortestPath(ArrayList<ArrayList<Integer>> powerSet) {
        for (int i = 0; i < powerSet.size(); i++) {
            for (int j = 0; j < powerSet.get(i).size(); j++) {
                System.out.println(powerSet.get(i).get(j));

            }
        }
    return 0;
    }

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
    static ArrayList<ArrayList<Integer>> printSubsets(ArrayList<Integer> set)
    {
        ArrayList<ArrayList<Integer>> MainPowerSet = new ArrayList<>();
        int n = set.size();

        // Run a loop for printing all 2^n
        // subsets one by obe
        for (int i = 1; i < (1<<n); i++)
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
