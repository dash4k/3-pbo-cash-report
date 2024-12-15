package cashreport;

import java.sql.*;

public class KoneksiMySql {
    static Connection koneksi;
    static Statement stmt;
    static ResultSet rs;

    public static Connection getKoneksi() {
        try {
            String url = "jdbc:mysql://localhost:3306/test-pbo";
            String user = "root";
            String pass = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return koneksi;
    }
}