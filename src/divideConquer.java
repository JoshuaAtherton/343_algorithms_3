import java.util.*;


public class divideConquer {
    static Stack<Integer> s = new Stack<>(); //TODO: delete after working
    static Stack<Integer> s_costs = new Stack<>();
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
    public int getCost(int[][] A, int[] nodes) {
        int cost = 0;
        for (int i = 0; i < nodes.length -1; i++) {
            cost += A[nodes[i]][nodes[i+1]];
        }

        return cost;
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

            System.out.println("S return: " + s);

            int[] result = stackToArray(s);
            System.out.println("result: " + Arrays.toString(result));
            int cost = getCost(A, result);
            pathCostsList.add(new pathCost(result, cost)); // how to get the costs?
            s.pop(); s.pop();

            return 0;
        }

        s.push(start);

        s_costs.push(A[s.peek()][start + 1]);
        ArrayList<Integer> costs = new ArrayList<>();
        for (int i = start + 1; i <= end; i++) {
            costs.add(A[start][i] + divide(A, i, end));

        }

        int minCost = Collections.min(costs);

        int pos = pathCostsList.size() - 1;
        if (s.size() > 0)
            s_costs.push(A[s.peek()][start + 1]);
//        pathCostsList.get(pos).incrementCost(minCost);

        return minCost;
    }

}
