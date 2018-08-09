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

        d.generateSolutionMatrix(matrix2);

    }

    public int[] generateSolutionMatrix(int[][] A) {
        int[] result = null;

        int rows = A.length;
        int cols = A[0].length;
        int[][] M = new int[rows][cols];

        // initialize row1 with initial direct values
        for (int c = 0; c < cols; c++) {
           M[0][c] = A[0][c];
        }
        // initialize col1 with initial values
        for (int r = 1; r < rows; r++) {
            M[r][0] = -1;
        }

        // dynamically add costs to each path not quite there
        /*
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                if (r >= c) {
                    M[r][c] = -1;
                } else {
                    M[r][c] = M[r-1][c-1] + A[c-1][c];
                }
            }
        }
        */

        //working version
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.printf("%2d ", M[r][c]);
            }
            System.out.println();
        }

        recoverSolution(M);
        return result;
    }

    public void recoverSolution(int M[][]) {
        Stack<Integer> S = new Stack<>();
        int i = M.length - 1;
        int j = M[0].length - 1;
        S.push(3 + 1);
        while (i > 0 && j > 0) {
            int min = M[i][j];
            if (M[i-1][j] < min) {
                S.push(M[i-1][j] + 1);
                min = M[i-1][j];
                i--;
            } else if (M[i][j-1] < min) {
                S.push(M[i][j-1] + 1);
                min = M[i][j-1];
                j--;
            }
        }
        S.push(0 + 1);

        System.out.println(S.toString());
        /* pseudo code
        recover(M[1..n][1..m])
            let S be an empty stack
            let i = n and j = m
            while i > 1 and j > 1:
                if M[i,j] = up
                    then push down onto S and i = i - 1
                else
                    push right onto S and j = j - 1
            return S
         */
    }

}
