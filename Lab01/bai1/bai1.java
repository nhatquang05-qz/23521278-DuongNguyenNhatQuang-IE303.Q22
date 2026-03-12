import java.util.Scanner;

public class bai1 {

    public static double tinhDienTichHinhTron(double banKinh) {
        double buoc = banKinh / 10000.0; 
        double tongDienTich = 0.0;

        for (double x = -banKinh; x <= banKinh; x += buoc) {
            for (double y = -banKinh; y <= banKinh; y += buoc) {
                if (x * x + y * y <= banKinh * banKinh) {
                    tongDienTich += buoc * buoc;
                }
            }
        }
        return tongDienTich;
    }

    public static double tinhPiGanDung() {
        return tinhDienTichHinhTron(1.0);
    }

    public static void main(String[] args) {
        Scanner nhap = new Scanner(System.in);

        System.out.print("Nhap ban kinh hinh tron: ");
        double banKinh = nhap.nextDouble();

        double dienTich = tinhDienTichHinhTron(banKinh);
        double pi = tinhPiGanDung();

        System.out.println("Dien tich hinh tron xap xi: " + dienTich);
        System.out.println("Gia tri PI xap xi: " + pi);

        nhap.close();
    }
}