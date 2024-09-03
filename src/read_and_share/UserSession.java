package read_and_share;
public class UserSession {
    // Variabel untuk menyimpan informasi pengguna yang sedang login
    private static int loggedInUserID;       // ID pengguna yang sedang login
    private static String loggedInName;      // Nama pengguna yang sedang login
    private static String loggedInTelephone; // Nomor telepon pengguna yang sedang login

    /**
     * Method untuk mengatur informasi pengguna yang sedang login.
     * @param user_id ID pengguna yang login
     * @param name Nama pengguna yang login
     * @param telephone Nomor telepon pengguna yang login
     */
    public static void setUser(int user_id, String name, String telephone) {
        loggedInUserID = user_id;
        loggedInName = name;
        loggedInTelephone = telephone;
    }

    /**
     * Method untuk mendapatkan ID pengguna yang sedang login.
     * @return ID pengguna yang sedang login
     */
    public static int getLoggedInUserID() {
        return loggedInUserID;
    }
    
    /**
     * Method untuk mendapatkan nama pengguna yang sedang login.
     * @return Nama pengguna yang sedang login
     */
    public static String getLoggedInName() {
        return loggedInName;
    }

    /**
     * Method untuk mendapatkan nomor telepon pengguna yang sedang login.
     * @return Nomor telepon pengguna yang sedang login
     */
    public static String getLoggedInTelephone() {
        return loggedInTelephone;
    }
}
