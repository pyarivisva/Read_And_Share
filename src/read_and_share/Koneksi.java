package read_and_share;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Koneksi bertanggung jawab untuk mengelola koneksi ke database MySQL.
 */
public class Koneksi {
    // Variabel koneksi statis
    private static Connection conn;
    // URL koneksi ke database
    private static final String URL = "jdbc:mysql://localhost:3306/db_read_and_share";
    // Username untuk koneksi ke database
    private static final String USER = "root";
    // Password untuk koneksi ke database
    private static final String PASS = "";
    // Logger untuk mencatat pesan log
    private static final Logger LOGGER = Logger.getLogger(Koneksi.class.getName());
    
    /**
     * Method untuk mendapatkan koneksi ke database.
     * Menggunakan pola singleton untuk memastikan hanya ada satu koneksi yang dibuat.
     * @return objek Connection ke database.
     */
    public static synchronized Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to create the database connection.", e);
            }
        }
        return conn;
    }
    
    /**
     * Method untuk menutup koneksi ke database.
     * Menetapkan koneksi ke null setelah ditutup.
     */
    public static synchronized void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to close the database connection.", e);
            }
        }
    }
}