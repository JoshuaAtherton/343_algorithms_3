import java.util.ArrayList;

public class dynamic2 {
    public static void main(String[] args) {
        dynamic2 d = new dynamic2();
        int [][] matrix = {
                {0, 2, 3, 7},
                {-1, 0, 2, 4},
                {-1, -1, 0, 2},
                {-1, -1, -1, 0}};

        int [] sol = d.dynamicSolve(matrix);

        for (int i = 0; i < sol.length; i++) {
            System.out.print(sol[i] + ", ");
        }
    }

    /**
     * Returns an array where first element is the cost and remaining elements are the path.
     * @param A
     * @return
     */
    public int [] dynamicSolve(int [][] matrix) {
        int rowSize = matrix.length;
        System.out.println(rowSize);
        int [] solutionMatrix = new int[rowSize + 5];

        for (int r = 1; r < rowSize; r++) {
            int min = matrix[1][r];
            for (int c = 1; c < r - 1; c++) {
                if (solutionMatrix[c] + matrix[c][r] < min) {
                    min = solutionMatrix[c] + matrix[c][r];
                }
            }
            solutionMatrix[r] = min;
        }
        return solutionMatrix;
    }

    //CostCanoe(R)
    //n = number of rows[R]
    //C[1] = 0
    // for i = 2 to n
    //      min = R[1, i]
    //      for k = 2 to i - 1
    //          if C[k] + R[k][i] < min
    //              min = C[k] + R[k][i]
    //      C[i] = min
    //Return C[n]

}
