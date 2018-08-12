import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;


public class divideConquer {
    static Stack<Integer> s = new Stack<>(); //TODO: delete after working
    static ArrayList<pathCost> pathCostsList = new ArrayList<>();

    public class pathCost {
        private int[] path;
        private int cost;
        public pathCost(int[] path, int cost) {
            this.path = path;
            this.cost = cost;
        }
        public void incrementCost(int cost) {
            this.cost += cost;
        }
        public String toString() {
            return "Cost of path: " + Arrays.toString(path) + " = " + cost;
        }
    }


    public static void main(String[] args) {
        divideConquer dc = new divideConquer();
        int [][] matrix = {
                {0, 2, 3, 7},
                {-1, 0, 2, 4},
                {-1, -1, 0, 2},
                {-1, -1, -1, 0} };

        int[][] matrix2 = {
                {0, 2, 3, 7, 4},
                {-1, 0, 2, 4, 5},
                {-1, -1, 0, 2, 1},
                {-1, -1, -1, 0, 3},
                {-1, -1, -1, -1, 0}};

        System.out.println(dc.divide(matrix, 0, matrix.length - 1) + "\n");

        for (pathCost pc : pathCostsList) {
            System.out.println(pc);
        }
//        s.push(5); s.push(8);
//        System.out.println(Arrays.toString(dc.stackToArray(s)));

    }

    public int[] stackToArray(Stack<Integer> s) {
        int[] result = new int[s.size()];
        Stack<Integer> temp = (Stack<Integer>)s.clone();
        for(int i = s.size() - 1; i >= 0; i--) {
            result[i] = temp.pop();
        }
        return result;
    }

    /**
     * Gets the minimum cost but still need to get the optimal path
     * @param A
     * @param start
     * @param end
     * @return
     */
    public int divide(int[][] A, int start, int end) {
        if (start == end) {
            s.push(end);
            System.out.println("return: " + s);

            int[] result = stackToArray(s);
            pathCostsList.add(new pathCost(result, 5)); // how to get the costs?
//            for (int i = 1; i <= end - 1; i++)
//                s.pop();
            s.pop(); s.pop();
            return 0;
        }

        s.push(start);
        ArrayList<Integer> costs = new ArrayList<>();
        for (int i = start + 1; i <= end; i++) {
            costs.add(A[start][i] + divide(A, i, end));
        }

        int minCost = Collections.min(costs);

//        System.out.println("stack: " + s);
        System.out.println("\tcosts: " + costs);
//        System.out.printf("\treturnCost- s%d e%d = %d \n", start, end, minCost);
        return minCost;
    }

}
