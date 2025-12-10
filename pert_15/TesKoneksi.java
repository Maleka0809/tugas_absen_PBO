import java.sql.DriverManager;

public class TesKoneksi {
    public static void main(String[] args) {
        System.out.println("=== MULAI CEK KONEKSI ===");
        
        // CEK 1: Driver / Library
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[V] Library Driver DITEMUKAN.");
        } catch (ClassNotFoundException e) {
            System.out.println("[X] Library Driver TIDAK DITEMUKAN!");
            System.out.println("    Solusi: Masukkan file .jar ke Tools > Preferences > Libraries, lalu RESTART BlueJ.");
            return; // Stop di sini
        }

        // CEK 2: Database & Login
        try {
            // Pastikan nama database 'bluej_crud' sesuai
            DriverManager.getConnection("jdbc:mysql://localhost:3306/bluej_crud", "root", "");
            System.out.println("[V] Koneksi ke Database SUKSES!");
            System.out.println("    Selamat! Masalah sudah selesai.");
        } catch (Exception e) {
            System.out.println("[X] Gagal Konek ke Database.");
            System.out.println("    Pesan Error: " + e.getMessage());
            
            if (e.getMessage().contains("Unknown database")) {
                System.out.println("    Solusi: Kamu belum buat database 'bluej_crud' di HeidiSQL/Laragon.");
            } else if (e.getMessage().contains("Access denied")) {
                System.out.println("    Solusi: Password salah. Coba isi password di kodingan.");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.out.println("    Solusi: Laragon/MySQL belum dijalankan (Start All).");
            }
        }
    }
}