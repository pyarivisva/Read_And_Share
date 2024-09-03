package read_and_share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class Donate adalah JFrame yang digunakan untuk mengelola donasi buku.
 */
public class Donate extends javax.swing.JFrame {
    
    // Variabel untuk menyimpan koordinat awal saat frame digerakkan
    int xx, xy;
    // Variabel untuk menyimpan koneksi ke database
    private Connection conn;       
    
    /**
     * Konstruktor untuk kelas Donate.
     * Menginisialisasi komponen, mendapatkan koneksi ke database,
     * dan memanggil metode untuk mengisi data dan combo box.
     */
    public Donate() {
        initComponents();
        
        conn = Koneksi.getConnection();
        getData();
        fillComboBoxBookCategory();
        fillComboBoxBookCondition();
    }

    /**
     * Method untuk mengisi combo box kategori buku dengan nilai-nilai dari enum BookCategory.
     */
    private void fillComboBoxBookCategory() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (BookCategory category : BookCategory.values()) {
            model.addElement(category.name());
        }
        cb_book_category.setModel(model);
    }

    /**
     * Method untuk mengisi combo box kondisi buku dengan nilai-nilai dari enum BookCondition.
     */
    private void fillComboBoxBookCondition() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (BookCondition condition : BookCondition.values()) {
            model.addElement(condition.name());
        }
        cb_book_condition.setModel(model);
    }

    /**
     * Enum untuk mendefinisikan kategori buku.
     */
    public enum BookCategory {
    fiction,
    non_fiction,
    children_literature,
    textbook,
    parenting_book,
    history
    }
    
    /**
     * Enum untuk mendefinisikan kondisi buku.
     */
    public enum BookCondition {
    new_book,
    old_book
    }
    
    /**
     * Method untuk mengambil data dari database dan menampilkannya
     * ke dalam JTable yang ada di frame.
     */
    private void getData() {
        DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
        model.setRowCount(0);
        
        try{
            String sql = "SELECT * FROM donate_donor WHERE name='"+UserSession.getLoggedInName()+"' && is_save=0";
            System.out.println(sql);
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                int donateID        = rs.getInt("donate_id");
                String name         = rs.getString("name");
                String telephone    = rs.getString("telephone");
                Date donateDate     = rs.getDate("donate_date");
                String bookCategoryStr = rs.getString("book_category");
                int quantity        = rs.getInt("quantity");
                String bookConditionStr = rs.getString("book_condition");
                
                model.addRow(new Object[]{donateID, name, telephone, donateDate, bookCategoryStr, quantity, bookConditionStr});
               
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            Logger.getLogger(Donate.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    /**
     * Method untuk menyisipkan data donasi buku ke dalam database.
     */
    private void insertData() {
    // Mendapatkan data dari form
    java.util.Date donateDate = donate_date_chooser.getDate();
    String bookCategoryStr = cb_book_category.getSelectedItem().toString();
    int quantity = (int) js_qty.getValue();
    String bookConditionStr = cb_book_condition.getSelectedItem().toString();

    // SQL query untuk menyisipkan data donasi
    String sql = "INSERT INTO donate_donor (donate_id, name, telephone, donate_date, book_category, quantity, book_condition, user_id, status, is_save, is_input)" +
                 "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try {
        // Mempersiapkan statement SQL
        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        // Mengisi parameter dalam query
        pstmt.setString(1, UserSession.getLoggedInName()); // Mendapat nama dari UserSession
        pstmt.setString(2, UserSession.getLoggedInTelephone()); // Mendapat telephone dari UserSession
        pstmt.setDate(3, new java.sql.Date(donateDate.getTime()));
        pstmt.setString(4, bookCategoryStr);
        pstmt.setInt(5, (int) quantity);
        pstmt.setString(6, bookConditionStr);
        pstmt.setInt(7, UserSession.getLoggedInUserID());
        pstmt.setString(8,"sent");
        pstmt.setInt(9, 0);
        pstmt.setInt(10, 0);

        // Menjalankan query dan mendapatkan jumlah baris yang terpengaruh
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            // Mendapatkan ID donasi yang baru disisipkan
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int donateId = generatedKeys.getInt(1);
                System.out.println("Inserted donate_id: " + donateId);
            }
        }
        // Menutup statement
        pstmt.close();
        getData();
        showMessageDialog(null, "Donate berhasil ditambahkan");
        resetForm();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    /**
     * Method untuk mereset form donasi.
     */
   private void resetForm() {
       donate_date_chooser.setDate(null);
       cb_book_category.setSelectedItem(null);
       cb_book_condition.setSelectedItem(null);
       t_resi.setText("");
   }
   
   /**
     * Method untuk menghapus data donasi dari database berdasarkan ID donasi yang dipilih.
     */
  private void resetData() {
    int selectedRow = tbl_data.getSelectedRow();

    if (selectedRow != -1) {
        String donate_id = tbl_data.getValueAt(selectedRow, 0).toString(); // Ambil donate_id dari kolom pertama
        String sql = "DELETE FROM donate_donor WHERE donate_id = ? AND is_save = 0";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, donate_id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Deleted row with donate_id: " + donate_id);
                showMessageDialog(null, "Data berhasil dihapus");
            } else {
                System.out.println("No row deleted with donate_id: " + donate_id);
                showMessageDialog(null, "Tidak ada data yang dihapus");
            }

            pstmt.close();
            DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
            model.removeRow(selectedRow);

        } catch (SQLException e) {
            e.printStackTrace();
            showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
        }
    } else {
        showMessageDialog(null, "Pilih baris yang ingin dihapus.");
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_kanan = new javax.swing.JPanel();
        ic_close = new javax.swing.JLabel();
        ic_minimize = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        donate = new javax.swing.JLabel();
        book_category = new javax.swing.JLabel();
        book_condition = new javax.swing.JLabel();
        donate_date = new javax.swing.JLabel();
        qty = new javax.swing.JLabel();
        cb_book_category = new javax.swing.JComboBox<>();
        cb_book_condition = new javax.swing.JComboBox<>();
        js_qty = new javax.swing.JSpinner();
        bt_reset = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        no_resi = new javax.swing.JLabel();
        t_resi = new javax.swing.JTextField();
        donate_date_chooser = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        bt_donate = new javax.swing.JLabel();
        bt_donation_detail = new javax.swing.JLabel();
        role_donor = new javax.swing.JLabel();
        bt_logout = new javax.swing.JButton();

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

        donate.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        donate.setForeground(new java.awt.Color(2, 59, 135));
        donate.setText("Donate");

        book_category.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        book_category.setForeground(new java.awt.Color(2, 59, 135));
        book_category.setText("Book Category");

        book_condition.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        book_condition.setForeground(new java.awt.Color(2, 59, 135));
        book_condition.setText("Book Condition");

        donate_date.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        donate_date.setForeground(new java.awt.Color(2, 59, 135));
        donate_date.setText("Donate Date");

        qty.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        qty.setForeground(new java.awt.Color(2, 59, 135));
        qty.setText("Quantity");

        bt_reset.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bt_reset.setText("RESET");
        bt_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_resetMouseClicked(evt);
            }
        });

        bt_add.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bt_add.setText("ADD");
        bt_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_addMouseClicked(evt);
            }
        });

        bt_save.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bt_save.setText("SAVE");
        bt_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_saveMouseClicked(evt);
            }
        });

        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "DonateID", "Name", "Telephone", "Donate Date", "Book Category", "Quantity", "Book Condition"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_dataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_data);

        no_resi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        no_resi.setForeground(new java.awt.Color(2, 59, 135));
        no_resi.setText("Receipt Number");

        t_resi.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        t_resi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 59, 135)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(no_resi)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(donate_date)
                                    .addComponent(donate_date_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(54, 54, 54)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(93, 93, 93)
                                        .addComponent(donate)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(js_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(qty))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cb_book_category, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(book_category))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(book_condition)
                                            .addComponent(cb_book_condition, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(bt_add, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(t_resi, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(bt_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(72, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(donate)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(donate_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(book_category, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(book_condition))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cb_book_condition, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(js_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb_book_category, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(donate_date_chooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(bt_add)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(no_resi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(t_resi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt_save)
                            .addComponent(bt_reset))
                        .addContainerGap())))
        );

        panel_kanan.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 830, 630));

        jPanel2.setBackground(new java.awt.Color(230, 229, 229));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo 5.png"))); // NOI18N
        logo.setText("jLabel3");

        bt_donate.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_donate.setForeground(new java.awt.Color(2, 59, 135));
        bt_donate.setText("Donate");
        bt_donate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 255), new java.awt.Color(0, 255, 255), new java.awt.Color(0, 255, 255), new java.awt.Color(0, 0, 255)));
        bt_donate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_donateMouseClicked(evt);
            }
        });

        bt_donation_detail.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_donation_detail.setForeground(new java.awt.Color(2, 59, 135));
        bt_donation_detail.setText("Donation Detail");
        bt_donation_detail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_donation_detailMouseClicked(evt);
            }
        });

        role_donor.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        role_donor.setForeground(new java.awt.Color(214, 0, 102));
        role_donor.setText("             Donor");
        role_donor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bt_logout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_logout.setForeground(new java.awt.Color(51, 51, 255));
        bt_logout.setText("LOGOUT");
        bt_logout.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 0, 0)));
        bt_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_logoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(role_donor, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_donate, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bt_donation_detail)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(role_donor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bt_donate)
                .addGap(18, 18, 18)
                .addComponent(bt_donation_detail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 283, Short.MAX_VALUE)
                .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        panel_kanan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 630));

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

    private void bt_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_logoutMouseClicked
        Login login = new Login();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_logoutMouseClicked

    private void bt_donateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_donateMouseClicked
        Donate donate = new Donate();
        donate.setVisible(true);
        donate.pack();
        donate.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_donateMouseClicked

    private void bt_donation_detailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_donation_detailMouseClicked
        DonationDetail detail = new DonationDetail();
        detail.setVisible(true);
        detail.pack();
        detail.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_donation_detailMouseClicked

    private void bt_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_addMouseClicked
        insertData();
    }//GEN-LAST:event_bt_addMouseClicked

    private void bt_saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_saveMouseClicked
        String no_resi = t_resi.getText();
        String sql = "UPDATE donate_donor SET no_resi=?, is_save=1 WHERE no_resi IS NULL && name='"+UserSession.getLoggedInName()+"'";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, no_resi);

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
            showMessageDialog(null, "No Resi berhasil ditambahkan");

            pstmt.close();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_bt_saveMouseClicked

    private void tbl_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dataMouseClicked
        if (tbl_data.getSelectedRow() != -1) {
            int i = tbl_data.getSelectedRow();
            java.util.Date donateDate = (java.util.Date) tbl_data.getValueAt(i, 0);
            String bookCategoryStr = (String) tbl_data.getValueAt(i, 1);
            int quantity = (int) tbl_data.getValueAt(i, 2);
            String bookConditionStr = (String) tbl_data.getValueAt(i, 3);
            
            donate_date_chooser.setDate(donateDate);
            cb_book_category.setSelectedItem(bookCategoryStr);
            js_qty.setValue(quantity);
            cb_book_condition.setSelectedItem(bookConditionStr);
        } else {
            System.out.print("Error: Tidak ada baris yang dipilih.");
         }
    }//GEN-LAST:event_tbl_dataMouseClicked

    private void bt_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_resetMouseClicked
        resetData();
    }//GEN-LAST:event_bt_resetMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Donate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel book_category;
    private javax.swing.JLabel book_condition;
    private javax.swing.JButton bt_add;
    private javax.swing.JLabel bt_donate;
    private javax.swing.JLabel bt_donation_detail;
    private javax.swing.JButton bt_logout;
    private javax.swing.JButton bt_reset;
    private javax.swing.JButton bt_save;
    private javax.swing.JComboBox<String> cb_book_category;
    private javax.swing.JComboBox<String> cb_book_condition;
    private javax.swing.JLabel donate;
    private javax.swing.JLabel donate_date;
    private com.toedter.calendar.JDateChooser donate_date_chooser;
    private javax.swing.JLabel ic_close;
    private javax.swing.JLabel ic_minimize;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner js_qty;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel no_resi;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JLabel qty;
    private javax.swing.JLabel role_donor;
    private javax.swing.JTextField t_resi;
    private javax.swing.JTable tbl_data;
    // End of variables declaration//GEN-END:variables

}
    
