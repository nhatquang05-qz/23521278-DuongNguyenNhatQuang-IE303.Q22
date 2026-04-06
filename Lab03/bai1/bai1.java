package Lab03.bai1;

import java.util.Scanner;

public class bai1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap vao so nguyen duong n: ");
        int n = sc.nextInt();
        
        if (n <= 0) {
            System.out.println("Vui long nhap so nguyen duong!");
            return;
        }
        
        int temp1 = n;
        while (temp1 >= 10) {
            temp1 /= 10;
        }
        System.out.println("Chu so dau tien cua n la: " + temp1);
        
        boolean allOdd = true;
        int temp2 = n;
        while (temp2 > 0) {
            if ((temp2 % 10) % 2 == 0) {
                allOdd = false;
                break;
            }
            temp2 /= 10;
        }
        if (allOdd) {
            System.out.println("n co toan chu so le: True");
        } else {
            System.out.println("n co toan chu so le: False");
        }
        
        long product = 1;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                product *= i;
            }
        }
        System.out.println("Tich tat ca cac uoc so cua n la: " + product);
        
        int sumDivisors = 0;
        for (int i = 1; i < n; i++) {
            if (n % i == 0) {
                sumDivisors += i;
            }
        }
        if (sumDivisors == n) {
            System.out.println("n co phai la so hoan thien khong: True");
        } else {
            System.out.println("n co phai la so hoan thien khong: False");
        }
        
        sc.close();
    }
}