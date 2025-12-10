import java.sql.*;
import java.util.Scanner;

public class AplikasiCRUD
{
    // --- KONFIGURASI DATABASE ---
    // Sesuaikan user & password jika XAMPP kamu ada passwordnya
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/bluej_crud";
    static final String USER = "root";
    static final String PASS = "Maleka_0809"; 

    // Objek global untuk koneksi dan input
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // 1. Register Driver (Agar BlueJ kenal MySQL)
            Class.forName(JDBC_DRIVER);

            // 2. Buat Koneksi
            System.out.println("Sedang menyambungkan ke database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            System.out.println("Koneksi BERHASIL!");

            // 3. Loop Menu
            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GAGAL KONEKSI: Pastikan XAMPP nyala & Library sudah dipasang!");
        }
    }

    static void showMenu() {
        System.out.println("\n=== DATA MAHASISWA (BlueJ) ===");
        System.out.println("1. Tambah Data (Insert)");
        System.out.println("2. Lihat Data (Show)");
        System.out.println("3. Edit Data (Update)");
        System.out.println("4. Hapus Data (Delete)");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu> ");

        String pilihan = scanner.nextLine();

        switch (pilihan) {
            case "1": insertData(); break;
            case "2": showData(); break;
            case "3": updateData(); break;
            case "4": deleteData(); break;
            case "0": 
                System.out.println("Terima kasih!");
                System.exit(0);
                break;
            default:  System.out.println("Pilihan salah!");
        }
    }

    // --- FITUR CREATE ---
    static void insertData() {
        try {
            System.out.print("Masukkan NIM: ");
            String nim = scanner.nextLine();
            System.out.print("Masukkan Nama: ");
            String nama = scanner.nextLine();
            System.out.print("Masukkan Jurusan: ");
            String jurusan = scanner.nextLine();

            String sql = "INSERT INTO mahasiswa (nim, nama, jurusan) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nim);
            ps.setString(2, nama);
            ps.setString(3, jurusan);
            
            ps.execute();
            System.out.println(">> Data berhasil disimpan!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- FITUR READ ---
    static void showData() {
        String sql = "SELECT * FROM mahasiswa";
        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+----+------------+----------------------+------------+");
            System.out.println("| ID |    NIM     |         NAMA         |   JURUSAN  |");
            System.out.println("+----+------------+----------------------+------------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                String jurusan = rs.getString("jurusan");
                
                // Formatting output agar rapi seperti tabel
                System.out.printf("| %-2d | %-10s | %-20s | %-10s |%n", id, nim, nama, jurusan);
            }
            System.out.println("+----+------------+----------------------+------------+");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- FITUR UPDATE ---
    static void updateData() {
        try {
            showData(); // Tampilkan dulu biar user tau ID nya
            System.out.print("Ketik ID mahasiswa yang mau diedit: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("NIM Baru: ");
            String nim = scanner.nextLine();
            System.out.print("Nama Baru: ");
            String nama = scanner.nextLine();
            System.out.print("Jurusan Baru: ");
            String jurusan = scanner.nextLine();

            String sql = "UPDATE mahasiswa SET nim=?, nama=?, jurusan=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nim);
            ps.setString(2, nama);
            ps.setString(3, jurusan);
            ps.setInt(4, id);

            ps.execute();
            System.out.println(">> Data berhasil diupdate!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- FITUR DELETE ---
    static void deleteData() {
        try {
            showData();
            System.out.print("Ketik ID mahasiswa yang mau dihapus: ");
            int id = Integer.parseInt(scanner.nextLine());

            String sql = "DELETE FROM mahasiswa WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.execute();
            System.out.println(">> Data berhasil dihapus!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}