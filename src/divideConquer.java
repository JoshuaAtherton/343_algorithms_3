import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class divideConquer {
    Stack<Integer> s = new Stack<>(); //TODO: delete after working

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

        System.out.println(dc.divide(matrix, 0, matrix.length - 1));

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
            s.pop();
            return 0;
        }
        s.push(start);
        ArrayList<Integer> costs = new ArrayList<>();
        for (int i = start + 1; i <= end; i++) {

            costs.add(A[start][i] + divide(A, i, end));
        }

        int minCost = Collections.min(costs);

        System.out.printf("start: %d, End: %d, Costs: ", start, end);
        System.out.println(costs);
//        System.out.println(" before return " + s);
//        s.pop();
        System.out.println("stack: " + s);
        return minCost;
    }

}
