public class divideConquer {
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

        dc.divide(matrix);

    }


    public void divide(int[][] A) {
        int[] result = null;
        int[] path = null;

        int rows = A.length;
        int cols = A[0].length;
        int[][] M = new int[rows][cols];




    }

}
