package Lab03.bai2;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bai2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap vao mot chuoi: ");
        String input = sc.nextLine();
        
        int letters = 0;
        int spaces = 0;
        int numbers = 0;
        int others = 0;
        
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                letters++;
            } else if (Character.isSpaceChar(c)) {
                spaces++;
            } else if (Character.isDigit(c)) {
                numbers++;
            } else {
                others++;
            }
        }
        System.out.println("Chu cai: " + letters + ", Khoang trang: " + spaces);
        System.out.println("So: " + numbers + ", Cac ky tu khac: " + others);
        
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        
        int maxFreq = 0;
        for (int freq : freqMap.values()) {
            if (freq > maxFreq) {
                maxFreq = freq;
            }
        }
        
        int secondMaxFreq = 0;
        for (int freq : freqMap.values()) {
            if (freq > secondMaxFreq && freq < maxFreq) {
                secondMaxFreq = freq;
            }
        }
        
        if (secondMaxFreq > 0) {
            for (char c : input.toCharArray()) {
                if (freqMap.get(c) == secondMaxFreq) {
                    System.out.println("Ky tu xuat hien nhieu thu hai la: " + c);
                    break;
                }
            }
        } else {
            System.out.println("Khong co ky tu xuat hien nhieu thu hai.");
        }
        
        int sumNumbers = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        while (m.find()) {
            sumNumbers += Integer.parseInt(m.group());
        }
        System.out.println("Tong cac so trong chuoi: " + sumNumbers);
        
        sc.close();
    }
}
