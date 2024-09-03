package read_and_share;

import java.awt.HeadlessException;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JFrame;

/**
 * Kelas Signup mengelola proses registrasi dan menampilkan halaman login jika sudah berhasil signup.
 */
public class Signup extends javax.swing.JFrame {
    
    int xx, xy;
    private Connection conn;

    /**
     * Konstruktor kelas Signup.
     * Inisialisasi komponen GUI dan menghubungkan ke database.
     */
    public Signup() {
        initComponents();
        conn = Koneksi.getConnection();
    }

/**
     * Validasi input untuk signup.
     * @return true jika input valid, false jika salah satu input kosong
     */
private boolean checkSignup(String username, String fullname, String address, String telephone, String email, String password){
    if(conn != null){       
        try{
            // Query SQL untuk memilih baris yang sesuai dengan username, fullname, address, phone number, email, password, dan konfirmasi
            String sql = "SELECT * FROM user WHERE username=? AND fullname=? AND address=? AND telephone=? AND email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,fullname);
            preparedStatement.setString(3,address);
            preparedStatement.setString(4,telephone);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, password);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
    }
        return false;
}
    /**
     * Proses Signup berdasarkan input yang divalidasi.
     */
    private void signup() {
    // Deklarasi variabel
    String username, fullname, address, telephone, email, password, query;
    String SUrl, SUser, SPass;

    // Persiapan database, ganti nama db
    SUrl = "jdbc:MySQL://localhost:3306/db_read_and_share";
    SUser = "root";
    SPass = "";

    try {
        // Menghubungkan ke database
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
        Statement st = con.createStatement();

        // Mendapatkan data dari input
        username = t_username.getText();
        fullname = t_fullname.getText();
        address = t_address.getText();
        telephone = t_telephone.getText();
        email = t_email.getText();
        password = t_password.getText();

        // Memeriksa apakah pengguna sudah ada
        if (checkSignup(username, fullname, address, telephone, email, password)) {
            JOptionPane.showMessageDialog(new JFrame(), "Pengguna dengan data yang sama sudah terdaftar!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return; // Keluar dari metode jika sudah ada
        }

        // Validasi input
        if ("".equals(username)) {
            JOptionPane.showMessageDialog(new JFrame(), "Username Diperlukan", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if ("".equals(fullname)) {
            JOptionPane.showMessageDialog(new JFrame(), "Full Name Diperlukan", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if ("".equals(address)) {
            JOptionPane.showMessageDialog(new JFrame(), "Address Diperlukan", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if ("".equals(telephone)) {
            JOptionPane.showMessageDialog(new JFrame(), "Telephone Diperlukan", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if ("".equals(email)) {
            JOptionPane.showMessageDialog(new JFrame(), "Email Diperlukan", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if ("".equals(password)) {
            JOptionPane.showMessageDialog(new JFrame(), "Password Diperlukan", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!(t_confirm.getText().equals(t_confirm.getText()))) { // Kalo password dan re password beda
            JOptionPane.showMessageDialog(new JFrame(), "Password dan password ulang gagal", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Username: " + username);
            System.out.println("Fullname: " + fullname);
            System.out.println("Address: " + address);
            System.out.println("Telephone: " + telephone);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);

            // Buat query untuk insert data ke database
            query = "INSERT INTO user (username, fullname, address, telephone, email, password)VALUES('" + username
                    + "', '" + fullname + "', '" + address + "', '" + telephone + "', '" + email + "' , '" + password
                    + "')";

            // Eksekusi ke database
            st.execute(query);

            // Membersihkan field input
            t_username.setText("");
            t_fullname.setText("");
            t_address.setText("");
            t_telephone.setText("");
            t_email.setText("");
            t_password.setText("");
            t_confirm.setText("");

            // Menampilkan pesan sukses
            JOptionPane.showMessageDialog(null, "Sign Up Berhasil");

            // Beralih ke frame Login
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    } catch (HeadlessException | ClassNotFoundException | SQLException e) {
        System.out.println("Error!" + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        panel_kanan = new javax.swing.JPanel();
        ic_close = new javax.swing.JLabel();
        ic_minimize = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        welcome = new javax.swing.JLabel();
        regist_your = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        fullname = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        telephone = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        password = new javax.swing.JLabel();
        confirm = new javax.swing.JLabel();
        t_username = new javax.swing.JTextField();
        t_fullname = new javax.swing.JTextField();
        t_email = new javax.swing.JTextField();
        t_telephone = new javax.swing.JTextField();
        t_address = new javax.swing.JTextField();
        t_password = new javax.swing.JPasswordField();
        t_confirm = new javax.swing.JPasswordField();
        lb_hide_pass = new javax.swing.JLabel();
        lb_show_pass = new javax.swing.JLabel();
        show_pass = new javax.swing.JLabel();
        lb_hide_confirm = new javax.swing.JLabel();
        lb_show_confirm = new javax.swing.JLabel();
        show_confirm = new javax.swing.JLabel();
        bt_signup = new javax.swing.JButton();
        ask_account = new javax.swing.JLabel();
        bt_login = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Password");

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
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        welcome.setFont(new java.awt.Font("Segoe UI Black", 1, 17)); // NOI18N
        welcome.setForeground(new java.awt.Color(2, 59, 135));
        welcome.setText("Welcome to Read & Share!");
        jPanel1.add(welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 36, -1, -1));

        regist_your.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        regist_your.setForeground(new java.awt.Color(153, 153, 153));
        regist_your.setText("Register your account");
        jPanel1.add(regist_your, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 66, -1, -1));

        username.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        username.setText("Username");
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 94, -1, -1));

        fullname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fullname.setText("Full Name");
        jPanel1.add(fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 152, -1, -1));

        address.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        address.setText("Address");
        jPanel1.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 210, -1, -1));

        telephone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        telephone.setText("Telephone");
        jPanel1.add(telephone, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 274, -1, -1));

        email.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        email.setText("Email");
        jPanel1.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 329, -1, -1));

        password.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        password.setText("Password");
        jPanel1.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 385, -1, -1));

        confirm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirm.setText("Confirm Password");
        jPanel1.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 463, -1, -1));

        t_username.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));
        t_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_usernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_usernameFocusLost(evt);
            }
        });
        t_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_usernameActionPerformed(evt);
            }
        });
        jPanel1.add(t_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 120, 397, 20));

        t_fullname.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_fullname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));
        t_fullname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_fullnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_fullnameFocusLost(evt);
            }
        });
        t_fullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_fullnameActionPerformed(evt);
            }
        });
        jPanel1.add(t_fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 178, 397, 20));

        t_email.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));
        t_email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_emailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_emailFocusLost(evt);
            }
        });
        t_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_emailActionPerformed(evt);
            }
        });
        jPanel1.add(t_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 355, 397, -1));

        t_telephone.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_telephone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));
        t_telephone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_telephoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_telephoneFocusLost(evt);
            }
        });
        t_telephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_telephoneActionPerformed(evt);
            }
        });
        jPanel1.add(t_telephone, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 303, 397, 20));

        t_address.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_address.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));
        t_address.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_addressFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_addressFocusLost(evt);
            }
        });
        t_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_addressActionPerformed(evt);
            }
        });
        jPanel1.add(t_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 236, 397, 20));

        t_password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));
        t_password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_passwordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_passwordFocusLost(evt);
            }
        });
        t_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_passwordActionPerformed(evt);
            }
        });
        jPanel1.add(t_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 411, 397, 20));

        t_confirm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));
        t_confirm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_confirmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_confirmFocusLost(evt);
            }
        });
        t_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_confirmActionPerformed(evt);
            }
        });
        jPanel1.add(t_confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 489, 397, 20));

        lb_hide_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png"))); // NOI18N
        lb_hide_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_hide_passMouseClicked(evt);
            }
        });
        jPanel1.add(lb_hide_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        lb_show_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/uncheck.png"))); // NOI18N
        lb_show_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_show_passMouseClicked(evt);
            }
        });
        jPanel1.add(lb_show_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        show_pass.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        show_pass.setForeground(new java.awt.Color(102, 102, 102));
        show_pass.setText("show password");
        jPanel1.add(show_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, 20));

        lb_hide_confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png"))); // NOI18N
        lb_hide_confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_hide_confirmMouseClicked(evt);
            }
        });
        jPanel1.add(lb_hide_confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, -1, -1));

        lb_show_confirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/uncheck.png"))); // NOI18N
        lb_show_confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_show_confirmMouseClicked(evt);
            }
        });
        jPanel1.add(lb_show_confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, -1, -1));

        show_confirm.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        show_confirm.setForeground(new java.awt.Color(102, 102, 102));
        show_confirm.setText("show password");
        jPanel1.add(show_confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 510, -1, 20));

        bt_signup.setBackground(new java.awt.Color(2, 59, 135));
        bt_signup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_signup.setForeground(new java.awt.Color(255, 255, 255));
        bt_signup.setText("SIGN UP");
        bt_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_signupActionPerformed(evt);
            }
        });
        jPanel1.add(bt_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 547, 397, 30));

        ask_account.setText("Already have an account?");
        jPanel1.add(ask_account, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 585, -1, -1));

        bt_login.setBackground(new java.awt.Color(252, 251, 251));
        bt_login.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_login.setForeground(new java.awt.Color(2, 59, 135));
        bt_login.setText("LOGIN");
        bt_login.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bt_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_loginActionPerformed(evt);
            }
        });
        jPanel1.add(bt_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 580, 70, 20));

        panel_kanan.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 500, 630));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bg belakang.png"))); // NOI18N
        logo.setText("  ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void t_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_usernameFocusGained
        
    }//GEN-LAST:event_t_usernameFocusGained

    private void t_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_usernameFocusLost
        
    }//GEN-LAST:event_t_usernameFocusLost

    private void t_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_usernameActionPerformed

    private void ic_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ic_closeMouseClicked
        dispose();
    }//GEN-LAST:event_ic_closeMouseClicked

    private void ic_minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ic_minimizeMouseClicked
        this.setState(1);
    }//GEN-LAST:event_ic_minimizeMouseClicked

    private void t_fullnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_fullnameFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_t_fullnameFocusGained

    private void t_fullnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_fullnameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_t_fullnameFocusLost

    private void t_fullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_fullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_fullnameActionPerformed

    private void t_passwordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_passwordFocusGained
        
    }//GEN-LAST:event_t_passwordFocusGained

    private void t_passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_passwordFocusLost
        
    }//GEN-LAST:event_t_passwordFocusLost

    private void t_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_passwordActionPerformed

    private void t_confirmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_confirmFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_t_confirmFocusGained

    private void t_confirmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_confirmFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_t_confirmFocusLost

    private void t_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_confirmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_confirmActionPerformed

    private void lb_hide_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_hide_passMouseClicked
        lb_show_pass.setVisible(true);
        lb_hide_pass.setVisible(false);
        t_password.setEchoChar('•');
    }//GEN-LAST:event_lb_hide_passMouseClicked

    private void lb_hide_confirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_hide_confirmMouseClicked
        lb_show_confirm.setVisible(true);
        lb_hide_confirm.setVisible(false);
        t_confirm.setEchoChar('•');
    }//GEN-LAST:event_lb_hide_confirmMouseClicked

    private void bt_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_signupActionPerformed
       signup();
    }//GEN-LAST:event_bt_signupActionPerformed

    private void bt_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_loginActionPerformed
        Login login = new Login();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_loginActionPerformed

    private void t_telephoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_telephoneFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_t_telephoneFocusGained

    private void t_telephoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_telephoneFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_t_telephoneFocusLost

    private void t_telephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_telephoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_telephoneActionPerformed

    private void t_addressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_addressFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_t_addressFocusGained

    private void t_addressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_addressFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_t_addressFocusLost

    private void t_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_addressActionPerformed

    private void t_emailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_emailFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_t_emailFocusGained

    private void t_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_emailFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_t_emailFocusLost

    private void t_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_emailActionPerformed

    private void lb_show_confirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_show_confirmMouseClicked
        lb_show_confirm.setVisible(false);
        lb_hide_confirm.setVisible(true);
        t_confirm.setEchoChar((char)0);
    }//GEN-LAST:event_lb_show_confirmMouseClicked

    private void lb_show_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_show_passMouseClicked
        lb_show_pass.setVisible(false);
        lb_hide_pass.setVisible(true);
        t_password.setEchoChar((char)0);
    }//GEN-LAST:event_lb_show_passMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Signup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel address;
    private javax.swing.JLabel ask_account;
    private javax.swing.JButton bt_login;
    private javax.swing.JButton bt_signup;
    private javax.swing.JLabel confirm;
    private javax.swing.JLabel email;
    private javax.swing.JLabel fullname;
    private javax.swing.JLabel ic_close;
    private javax.swing.JLabel ic_minimize;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lb_hide_confirm;
    private javax.swing.JLabel lb_hide_pass;
    private javax.swing.JLabel lb_show_confirm;
    private javax.swing.JLabel lb_show_pass;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JLabel password;
    private javax.swing.JLabel regist_your;
    private javax.swing.JLabel show_confirm;
    private javax.swing.JLabel show_pass;
    private javax.swing.JTextField t_address;
    private javax.swing.JPasswordField t_confirm;
    private javax.swing.JTextField t_email;
    private javax.swing.JTextField t_fullname;
    private javax.swing.JPasswordField t_password;
    private javax.swing.JTextField t_telephone;
    private javax.swing.JTextField t_username;
    private javax.swing.JLabel telephone;
    private javax.swing.JLabel username;
    private javax.swing.JLabel welcome;
    // End of variables declaration//GEN-END:variables
}

