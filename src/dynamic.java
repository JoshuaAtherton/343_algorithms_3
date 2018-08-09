import java.util.Arrays;

public class dynamic {
    public static void main(String[] args) {
        dynamic d = new dynamic();
        int [][] matrix = {
                {0, 2, 3, 7},
                {-1, 0, 2, 4},
                {-1, -1, 0, 2},
                {-1, -1, -1, 0} };

        d.generateSolutionMatrix(matrix);

    }

    public int[] generateSolutionMatrix(int[][] A) {
        int[] result = null;

        int rows = A.length;
        int cols = A[0].length;
        int[][] M = new int[rows][cols];

        // initialize row1 and col1 with values
        for (int c = 0; c < cols; c++) {
           M[0][c] = A[0][c];
           if (c == cols - 1) {
//               code the min for last col here
//               int min =
//               M[0][0]
           }
        }
        for (int r = 1; r < rows; r++) {
            M[r][0] = -1;
        }

        // dynamically add costs to each path
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                if (r >= c) {
                    M[r][c] = -1;
                } else {
                    M[r][c] = M[r-1][c-1] + A[c-1][c];
                }
            }
        }

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
        int rows = M.length;
        int cols = M[0].length;

        //get minimum index
        int minIndex = M[0][cols - 1];
        for (int i = 0; i < rows; i++) {
            if (M[i][cols - 1] < minIndex && M[i][cols - 1] > 0) {
                minIndex = i;
            }
            System.out.println(M[minIndex][cols - 1]);
        }
        System.out.println(minIndex);
        System.out.println(M[minIndex][cols - 1]);


    }

}
