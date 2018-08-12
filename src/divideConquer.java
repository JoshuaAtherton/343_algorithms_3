import java.util.*;


public class divideConquer {

    static Stack<Integer> s = new Stack<>();
    static ArrayList<pathCost> pathCostsList = new ArrayList<>();

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
            return this.cost - pc.cost;
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

        System.out.println(dc.divide(matrix2, 0, matrix2.length - 1) + "\n");

        Collections.sort(pathCostsList);
        //print out the path objects
        for (pathCost pc : pathCostsList) {
            System.out.println(pc);
        }


        System.out.println("\nDivide and conquer: optimal cost and path\n" + pathCostsList.get(0));
        for (int i = 1; i < pathCostsList.size(); i++) {
            if (pathCostsList.get(i).getCost() == pathCostsList.get(0).getCost()) {
                System.out.println(pathCostsList.get(i));
            } else {
                break;
            }
        }

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
        for (int i = 0; i < nodes.length - 1; i++) {
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

}
