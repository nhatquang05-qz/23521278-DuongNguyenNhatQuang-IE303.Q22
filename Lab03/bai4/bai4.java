package Lab03.bai4;

import java.util.Scanner;
import java.util.Arrays;

public class bai4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so luong phan tu cua mang: ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        
        System.out.println("Nhap cac phan tu cua mang (so nguyen duong):");
        for (int i = 0; i < n; i++) {
            System.out.print("Phan tu thu " + (i + 1) + ": ");
            arr[i] = sc.nextInt();
        }
        
        Arrays.sort(arr);
        int count = 0;
        
        for (int i = arr.length - 1; i >= 2; i--) {
            int l = 0;
            int r = i - 1;
            while (l < r) {
                if (arr[l] + arr[r] > arr[i]) {
                    count += r - l;
                    r--;
                } else {
                    l++;
                }
            }
        }
        
        System.out.println("So tam giac co the co la: " + count);
        
        sc.close();
    }
}