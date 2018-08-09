import java.util.Arrays;
import java.util.Stack;

public class dynamic {
    public static void main(String[] args) {
        dynamic d = new dynamic();
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

        d.generateSolutionMatrix(matrix);

    }

    /**
     * Returns an array where first element is the cost and remaining elements are the path.
     * @param A
     * @return
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

        //print the matrix
//        for (int r = 0; r < rows; r++) {
//            for (int c = 0; c < cols; c++) {
//                System.out.printf("%2d ", M[r][c]);
//            }
//            System.out.println();
//        }

        path = recoverSolution(M);
        result = new int[path.length + 1];
        result[0] = M[rows - 1][cols - 1];
        for (int i = 0; i < path.length; i++) {
            result[i + 1] = path[i];
        }
        System.out.println(Arrays.toString(result));
        return result;
    }

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

//        System.out.println(Arrays.toString(path));
        return path;
    }

}
