package Lab01.bai4;

import java.util.Scanner;

public class bai4 {

    public static void timDayConLonNhat(int[] mang) {
        if (mang == null || mang.length == 0) return;

        int tongMax = mang[0];
        int tongHienTai = mang[0];

        int batDau = 0;
        int ketThuc = 0;
        int tam = 0;

        for (int i = 1; i < mang.length; i++) {

            if (mang[i] > tongHienTai + mang[i]) {
                tongHienTai = mang[i];
                tam = i;
            } else {
                tongHienTai += mang[i];
            }

            if (tongHienTai > tongMax) {
                tongMax = tongHienTai;
                batDau = tam;
                ketThuc = i;
            }
        }

        System.out.println("\nDay con co tong lon nhat la:");
        for (int i = batDau; i <= ketThuc; i++) {
            System.out.print(mang[i] + (i == ketThuc ? "" : ", "));
        }

        System.out.println("\nTong lon nhat = " + tongMax);
    }

    public static void main(String[] args) {

        Scanner nhap = new Scanner(System.in);

        System.out.print("Nhap so phan tu cua mang: ");
        int n = nhap.nextInt();

        int[] mang = new int[n];

        System.out.println("Nhap cac phan tu:");
        for (int i = 0; i < n; i++) {
            System.out.print("Phan tu " + (i + 1) + ": ");
            mang[i] = nhap.nextInt();
        }

        timDayConLonNhat(mang);

        nhap.close();
    }
}