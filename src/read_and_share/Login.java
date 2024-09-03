package read_and_share;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Kelas Login mengelola proses autentikasi pengguna dan menampilkan halaman beranda sesuai dengan peran pengguna.
 */
public class Login extends javax.swing.JFrame {
     // Variabel untuk menyimpan koordinat lokasi jendela
    int xx, xy;
    // Objek koneksi ke database
    private Connection conn;

     /**
     * Konstruktor kelas Login.
     * Inisialisasi komponen GUI dan menghubungkan ke database.
     */
    public Login() {
        initComponents();
        conn = Koneksi.getConnection();
    }

    /**
     * Validasi input username dan password.
     * @return true jika input valid, false jika salah satu input kosong
     */
    private boolean validasiInput(){
    boolean valid = false;
    if(t_username.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,"Username tidak boleh kosong");
    }else if(t_password.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,"Password tidak boleh kosong");
    }else{
        valid = true;
    }
    return valid;
    }
    
    /**
     * Memeriksa login pengguna berdasarkan username dan password.
     * @param username username pengguna yang akan diverifikasi
     * @param password password pengguna yang akan diverifikasi
     * @return true jika login berhasil, false jika gagal
     */
    private boolean checkLogin(String username, String password){
    if(conn != null){       
        try{
            // Query SQL untuk memilih baris yang sesuai dengan username, password
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, password);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                // Jika login berhasil, set data pengguna ke User_Session
                String telephone = resultSet.getString("telephone"); // Ganti dengan nama kolom telepon Anda
                int user_id = resultSet.getInt("user_id");
                UserSession.setUser(user_id, username, telephone);
                return true;
            }
        } catch (SQLException e) {
        }
    }
        return false;
}

    /**
     * Proses login berdasarkan input yang divalidasi.
     */
    private void processLogin() {
    if(validasiInput()) {
        String username = t_username.getText();
        String password = t_password.getText().trim();
    
    if("admin".equals(username) && "admin".equals(password)) {
        // Login sebagai admin
        HomePageAdmin homeAdmin = new HomePageAdmin();
        homeAdmin.setVisible(true);
        JOptionPane.showMessageDialog(null, "Login berhasil!", "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }else if (checkLogin(username, password)) {
        //Login sebagai user
        HomePageDonor homeDonor = new HomePageDonor();
        homeDonor.setVisible(true);
        JOptionPane.showMessageDialog(null, "Login berhasil!", "Notifikasi", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();          
    } else {
        JOptionPane.showMessageDialog(this,"Login gagal","Message", JOptionPane.INFORMATION_MESSAGE);
    }
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_kanan = new javax.swing.JPanel();
        ic_close = new javax.swing.JLabel();
        ic_minimize = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        welcome = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        t_username = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        t_password = new javax.swing.JPasswordField();
        lb_show_pass = new javax.swing.JLabel();
        lb_hide_pass = new javax.swing.JLabel();
        show_pass = new javax.swing.JLabel();
        bt_login = new javax.swing.JButton();
        ask_account = new javax.swing.JLabel();
        bt_signup = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        panel_kanan.setBackground(new java.awt.Color(255, 255, 255));
        panel_kanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ic_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ic_close.png"))); // NOI18N
        ic_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ic_closeMouseClicked(evt);
            }
        });
        panel_kanan.add(ic_close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1015, 6, -1, -1));

        ic_minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ic_minimize.png"))); // NOI18N
        ic_minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ic_minimizeMouseClicked(evt);
            }
        });
        panel_kanan.add(ic_minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(979, 6, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        welcome.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        welcome.setForeground(new java.awt.Color(2, 59, 135));
        welcome.setText("Welcome to Read & Share!");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Login your account");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Username");

        t_username.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Password");

        t_password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));

        lb_show_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/uncheck.png"))); // NOI18N
        lb_show_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_show_passMouseClicked(evt);
            }
        });

        lb_hide_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png"))); // NOI18N
        lb_hide_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_hide_passMouseClicked(evt);
            }
        });

        show_pass.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        show_pass.setForeground(new java.awt.Color(102, 102, 102));
        show_pass.setText("show password");

        bt_login.setBackground(new java.awt.Color(2, 59, 135));
        bt_login.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_login.setForeground(new java.awt.Color(255, 255, 255));
        bt_login.setText("LOGIN");
        bt_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_loginActionPerformed(evt);
            }
        });

        ask_account.setText("I don't have an account");

        bt_signup.setBackground(new java.awt.Color(252, 251, 251));
        bt_signup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_signup.setForeground(new java.awt.Color(2, 59, 135));
        bt_signup.setText("SIGN UP");
        bt_signup.setBorder(null);
        bt_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_signupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bt_login, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ask_account)
                        .addGap(25, 25, 25)
                        .addComponent(bt_signup, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_hide_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_show_pass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addComponent(show_pass))
                    .addComponent(welcome)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(t_password, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                    .addComponent(t_username))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(welcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(t_username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t_password, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_hide_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_show_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(show_pass)))
                .addGap(48, 48, 48)
                .addComponent(bt_login, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(ask_account))
                    .addComponent(bt_signup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        panel_kanan.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 500, 630));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bg belakang.png"))); // NOI18N
        jLabel6.setText("  ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panel_kanan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 630));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_kanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_kanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void ic_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ic_closeMouseClicked
        dispose();
    }//GEN-LAST:event_ic_closeMouseClicked

    private void ic_minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ic_minimizeMouseClicked
        this.setState(1);
    }//GEN-LAST:event_ic_minimizeMouseClicked

    private void bt_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_signupActionPerformed
        Signup signup = new Signup();
        signup.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bt_signupActionPerformed

    private void bt_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_loginActionPerformed
       processLogin();
    }//GEN-LAST:event_bt_loginActionPerformed

    private void lb_hide_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_hide_passMouseClicked
        lb_show_pass.setVisible(true);
        lb_hide_pass.setVisible(false);
        t_password.setEchoChar('•');
    }//GEN-LAST:event_lb_hide_passMouseClicked

    private void lb_show_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_show_passMouseClicked
        lb_show_pass.setVisible(false);
        lb_hide_pass.setVisible(true);
        t_password.setEchoChar((char)0);

    }//GEN-LAST:event_lb_show_passMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ask_account;
    private javax.swing.JButton bt_login;
    private javax.swing.JButton bt_signup;
    private javax.swing.JLabel ic_close;
    private javax.swing.JLabel ic_minimize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lb_hide_pass;
    private javax.swing.JLabel lb_show_pass;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JLabel show_pass;
    private javax.swing.JPasswordField t_password;
    private javax.swing.JTextField t_username;
    private javax.swing.JLabel welcome;
    // End of variables declaration//GEN-END:variables
}
