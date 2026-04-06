package Lab03.bai3;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class bai3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so luong phan tu cua mang: ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        
        System.out.println("Nhap cac phan tu cua mang:");
        for (int i = 0; i < n; i++) {
            System.out.print("Phan tu thu " + (i + 1) + ": ");
            arr[i] = sc.nextInt();
        }
        
        Set<Integer> set = new HashSet<>();
        for (int num : arr) {
            set.add(num);
        }
        
        int longestStreak = 0;
        int bestStart = 0;
        
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                while (set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                
                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak;
                    bestStart = num;
                }
            }
        }
        
        if (longestStreak > 1) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < longestStreak; i++) {
                res.add(bestStart + i);
            }
            System.out.println("Mang con lien tiep dai nhat la: " + res);
        } else {
            System.out.println("Khong ton tai");
        }
        
        sc.close();
    }
}