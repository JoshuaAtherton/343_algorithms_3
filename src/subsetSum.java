import java.util.Arrays;

public class subsetSum {

    /**
     *
     * @param s list to search for subset sum
     * @param t target to find
     * @return the dynamic array
     */
    public int[][] has_subSet(int s[], int t) {
        int n = s.length;
        int A[][] = new int[n][t + 1];
        for (int i = 0; i < n; i++) {
            A[i][0] = 1;
        }
        for (int j = 1; j <= t; j++) {
            if (s[0] == j) {
                A[0][j] = 1;
            } else {
                A[1][j] = 0;
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= t; j++) {
                if (j >= s[i]) {
                    if (A[i-1][j] == 1 || A[i-1][j-s[i]] == 1) {
                        A[i][j] = 1;
                    } else {
                        A[i][j] = 0;
                    }
                } else {
                    A[i][j] = A[i-1][j];
                }
            }
        }

        String found = (A[n-1][t] == 1) ? "true" : "false";
        System.out.println(found);

        return A;
    }

    /**
     *
     * @param A dynamic solution array to search
     * @param t target sum
     * @return if found or not
     */
    public String subsetSumRecover(int A[][], int list[], int t) {
        String found = "found = ", sequence = " ", indexes = " ";
        int n = A.length - 1;
        found += (A[n][t] == 1) ? "true" : "false";

        //recover from the matrix
        if (A[n][t] == 1) {
            while (n > 0 && t > 0) {
                if (A[n - 1][t] == 1) {
                    n--;
                } else {
                    sequence += list[n];
                    indexes += n;
                    sequence += ' ';
                    t = t - list[n];
                    n--;
                }
                if (n == 0 || t == 0) {
                    indexes += n;
                    sequence += list[n];
                }
            }
        }
        return found + ", sequence:" + sequence + " indexes: " + indexes;
    }

    public static void main(String[] args) {
        subsetSum sub = new subsetSum();
//        int list[] = {2, 3, 6, 1, 4};
//        int target = 8;

        int list[] = {2, 3, 5, 7, 9}; // 7, 3, 2 is the solution
        int target = 12;
        int dynamic[][];
        dynamic = sub.has_subSet(list, target);

        System.out.println(sub.subsetSumRecover(dynamic, list, target));


        for (int i = 0; i<dynamic.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j<dynamic[i].length; j++) {
                System.out.print(dynamic[i][j]);
                System.out.print(' ');
            }
            System.out.println(']');
        }





    }
}


