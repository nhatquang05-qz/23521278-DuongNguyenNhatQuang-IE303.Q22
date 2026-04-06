package Lab03.bai5;

import java.util.Scanner;

public class bai5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so dong m cua ma tran M: ");
        int m = sc.nextInt();
        System.out.print("Nhap so cot n cua ma tran M: ");
        int n = sc.nextInt();
        double[][] M = new double[m][n];
        
        System.out.println("Nhap cac phan tu cua ma tran M:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("M[" + i + "][" + j + "]: ");
                M[i][j] = sc.nextDouble();
            }
        }
        
        double minEvenPos = -1;
        boolean foundEven = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] > 0 && M[i][j] % 2 == 0) {
                    if (!foundEven) {
                        minEvenPos = M[i][j];
                        foundEven = true;
                    } else if (M[i][j] < minEvenPos) {
                        minEvenPos = M[i][j];
                    }
                }
            }
        }
        if (foundEven) {
            System.out.println("Phan tu chan duong nho nhat la: " + minEvenPos);
        } else {
            System.out.println("Khong tim thay phan tu chan duong.");
        }
        
        double sumMins = 0;
        for (int j = 0; j < n; j++) {
            double colMin = M[0][j];
            for (int i = 1; i < m; i++) {
                if (M[i][j] < colMin) {
                    colMin = M[i][j];
                }
            }
            sumMins += colMin;
        }
        System.out.println("Gia tri trung binh cua cac phan tu nho nhat tren moi cot: " + (sumMins / n));
        
        double maxSum = -Double.MAX_VALUE;
        int rowMax = 0;
        for (int i = 0; i < m; i++) {
            double rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += M[i][j];
            }
            if (rowSum > maxSum) {
                maxSum = rowSum;
                rowMax = i;
            }
        }
        
        double[][] newM = new double[m - 1][n];
        int r = 0;
        for (int i = 0; i < m; i++) {
            if (i == rowMax) continue;
            for (int j = 0; j < n; j++) {
                newM[r][j] = M[i][j];
            }
            r++;
        }
        System.out.println("Da xoa dong co tong lon nhat (dong " + rowMax + ").");
        
        if (m == n) {
            System.out.println("M la ma tran vuong: True");
            boolean isUpper = true;
            boolean isLower = true;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i > j && M[i][j] != 0) isUpper = false;
                    if (i < j && M[i][j] != 0) isLower = false;
                }
            }
            System.out.println("M la ma tran tam giac tren: " + isUpper);
            System.out.println("M la ma tran tam giac duoi: " + isLower);
        } else {
            System.out.println("M la ma tran vuong: False");
        }
        
        System.out.println("Nhap ma tran N kich thuoc " + n + "x" + m + ":");
        double[][] N = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("N[" + i + "][" + j + "]: ");
                N[i][j] = sc.nextDouble();
            }
        }
        
        double[][] P = new double[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    P[i][j] += M[i][k] * N[k][j];
                }
            }
        }
        System.out.println("Ma tran tich P = M * N:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(P[i][j] + " ");
            }
            System.out.println();
        }
        
        double[][] P_T = new double[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                P_T[i][j] = P[j][i];
            }
        }
        System.out.println("Ma tran chuyen vi cua P:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(P_T[i][j] + " ");
            }
            System.out.println();
        }
        
        sc.close();
    }
}