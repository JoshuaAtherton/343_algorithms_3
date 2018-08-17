import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class divideVersion2 {
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
        divideVersion2 ps = new divideVersion2();
        int [][] matrix = {
                {0, 2, 3, 7},
                {-1, 0, 2, 4},
                {-1, -1, 0, 2},
                {-1, -1, -1, 0}};
        int[][] matrix1 = {
                {0, 2, 3, 7, 4, 12, 46, 35, 90, 34},
                {0, 0, 2, 4, 5, 14, 24, 54, 22, 43},
                {0, 0, 0, 2, 1, 24, 54, 22, 43, 89},
                {0, 0, 0, 0, 3, 24, 54, 22, 43, 12},
                {0, 0, 0, 0, 0, 35, 90, 24, 44, 45},
                {0, 0, 0, 0, 0, 0, 44, 45, 99, 89},
                {0, 0, 0, 0, 0, 0, 0, 22, 43, 89},
                {0, 0, 0, 0, 0, 0, 0, 0, 13, 32},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 67},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        int [][] matrix3 = {
                {0, 2, 20, 80, 90},
                {-1, 0, 300, 8, 100},
                {-1, -1, 0, 23, 1222},
                {-1, -1, -1, 0, 343},
                {-1, -1, -1, -1, 0}};

        int[] test = {0,1,2,3};
        ps.getsets(matrix3, 0, matrix3[0].length - 1);

        Collections.sort(pathCostsList);
//        System.out.println(pathCostsList.get(0));
        System.out.println("\nDivide and conquer optimal cost and path:\n" + pathCostsList.get(0));
        for (int i = 1; i < pathCostsList.size(); i++) {
            if (pathCostsList.get(i).getCost() == pathCostsList.get(0).getCost()) {
                System.out.println(pathCostsList.get(i));
            } else {
                break;
            }
        }
    }

    public void getsets(int[][] arr, int start, int end) {
        if (start == end) {
            s.push(end);
//            System.out.println("stack: " + s);
            //call get cost
            int[] path = new int[s.size()];
            int cost = getCost(arr, s, path);
            //add the path and cost to the class
//            System.out.println("Path: " + Arrays.toString(path) + " " + cost + "\n");
            pathCostsList.add(new pathCost(path, cost));
            s.pop(); s.pop();
            return;
        }
        s.push(start);
        for (int i = start + 1; i <= end; i++) {
            getsets(arr, i, end);
        }
    }

    public int getCost(int[][] A, Stack<Integer> s, int[] path) {
        Stack<Integer> temp = (Stack<Integer>)s.clone();
        int cost = 0;
        for(int i = s.size() - 1; i >= 0; i--) {
            path[i] = temp.pop();
//            System.out.println(Arrays.toString(path) + " " + path[i]);
            if (i > 0) {
//                System.out.printf("A[%d][%d] = %d \n", temp.peek(), path[i], A[temp.peek()][path[i]]);
                cost += A[temp.peek()][path[i]];
            }
        }
        return cost;
    }

}
