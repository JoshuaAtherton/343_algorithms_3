public class Main {
    public static void main(String[] args) {
        System.out.println("hello");
        int [][] matrix = {{0, 2, 3, 7},
                           {-1, 0, 2, 4},
                           {-1, -1, 0, 2},
                           {-1, -1, -1, 0},};

        for (int i = 0; i < matrix.length; i++) {
            System.out.println();
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + ",");
            }
        }



    }
}
