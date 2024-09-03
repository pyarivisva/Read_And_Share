package read_and_share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Kelas SendBook mengelola proses pengiriman buku dari donor ke lokasi tertentu.
 */
public class SendBook extends javax.swing.JFrame {
    
      // Variabel untuk menyimpan koordinat lokasi jendela
    int xx, xy;
    // Objek koneksi ke database
    private Connection conn;
    // Variabel untuk menentukan apakah dalam mode edit atau tidak
    private boolean isEditMode;

    /**
     * Konstruktor kelas SendBook.
     * Inisialisasi komponen GUI dan menghubungkan ke database.
     */
    public SendBook() {
        initComponents();
        
        conn = Koneksi.getConnection(); // Mendapatkan koneksi ke database
        getData((DefaultTableModel) tbl_data.getModel()); // Mendapatkan data buku yang belum dikirim
    }

    /**
     * Memuat data buku yang belum dikirim ke dalam tabel.
     * @param par model tabel yang akan diisi dengan data buku
     */
    private void getData(DefaultTableModel par) {
        par.setRowCount(0); // Bersihkan tabel yang sudah ada sebelum menambahkan data baru
        
        try{
            String sql = "SELECT * FROM donate_donor WHERE is_send=0";
            System.out.println(sql);
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                String id_book  = rs.getString("book_id");
                int location_id = rs.getInt("location_id");
                int id_donor = rs.getInt("user_id");
                String book_title = rs.getString("book_title");
                String bookCategoryStr = rs.getString("book_category");
                String bookConditionStr = rs.getString("book_condition");
                Date delivery_date     = rs.getDate("delivery_date");
                
                par.addRow(new Object[]{id_book, location_id, id_donor, book_title, bookCategoryStr, bookConditionStr, delivery_date});
               
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            Logger.getLogger(SendBook.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    /**
     * Memperbarui status pengiriman buku berdasarkan input yang dimasukkan.
     */
    private void updateData() {
    String book_id = t_book_id.getText();
    int id_location = 0;
    
    try {
        id_location = Integer.parseInt(t_location_id.getText());
    } catch (NumberFormatException e) {
        // Handle the parsing error, e.g., show an error message
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil data dari database.");
        return;  // exit the method early
    }
    
    java.util.Date deliveryDate = delivery_date_chooser.getDate();

    // Check book condition
    String checkConditionSql = "SELECT book_condition FROM donate_donor WHERE book_id=?";
    try {
        PreparedStatement checkConditionStmt = conn.prepareStatement(checkConditionSql);
        checkConditionStmt.setString(1, book_id);
        
        ResultSet rs = checkConditionStmt.executeQuery();
        if (rs.next()) {
            String bookCondition = rs.getString("book_condition");
            if ("reject_book".equals(bookCondition)) {
                JOptionPane.showMessageDialog(null, "Tidak boleh mengirim buku yang reject.");
                return;  // exit the method early
            }
        }
        rs.close();
        checkConditionStmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
        return;  // exit the method early if there is an error
    }

    // Set up your SQL query to insert data into your database table
    String sql = "UPDATE donate_donor SET location_id=?, delivery_date=?, is_send=?, status=? WHERE book_id=?"; 
    try {
        // Assuming you have a connection object named 'connection'
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set the parameters for the prepared statement
        pstmt.setInt(1, id_location);
        pstmt.setDate(2, new java.sql.Date(deliveryDate.getTime()));
        pstmt.setInt(3, 1);
        pstmt.setString(4, "on_delivery");
        pstmt.setString(5, book_id);

        // Execute the update statement
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows > 0) {
            System.out.println("Updated id_location: " + id_location);
        }

        // Close the result set, statement, and connection
        pstmt.close();
        resetForm();
        getData((DefaultTableModel) tbl_data.getModel());
        JOptionPane.showMessageDialog(null, "Book berhasil dikirim");
        // Optionally, you can show a success message or perform other actions here
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}


    /**
     * Mengatur ulang nilai-nilai pada formulir pengiriman buku.
     */
   private void resetForm() {
       t_book_id.setText("");
       t_location_id.setText("");
       delivery_date_chooser.setDate(null);
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_kanan = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        bt_logout = new javax.swing.JButton();
        role_admin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        logo = new javax.swing.JLabel();
        bt_status_book = new javax.swing.JLabel();
        bt_data_book = new javax.swing.JLabel();
        bt_input_book = new javax.swing.JLabel();
        bt_send_book = new javax.swing.JLabel();
        bt_sending_book = new javax.swing.JLabel();
        transaction = new javax.swing.JLabel();
        bt_rejected_books = new javax.swing.JLabel();
        bt_history_book = new javax.swing.JLabel();
        report = new javax.swing.JLabel();
        information_system2 = new javax.swing.JLabel();
        bt_location = new javax.swing.JLabel();
        bt_data_donatur = new javax.swing.JLabel();
        ic_close = new javax.swing.JLabel();
        ic_minimize = new javax.swing.JLabel();
        delivery_date = new javax.swing.JLabel();
        t_book_id = new javax.swing.JTextField();
        book_id = new javax.swing.JLabel();
        bt_send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        bt_edit = new javax.swing.JButton();
        t_location_id = new javax.swing.JTextField();
        location_id = new javax.swing.JLabel();
        delivery_date_chooser = new com.toedter.calendar.JDateChooser();

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

        jPanel2.setBackground(new java.awt.Color(230, 229, 229));

        bt_logout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_logout.setForeground(new java.awt.Color(51, 51, 255));
        bt_logout.setText("LOGOUT");
        bt_logout.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 0, 0)));
        bt_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_logoutMouseClicked(evt);
            }
        });

        role_admin.setBackground(new java.awt.Color(255, 255, 255));
        role_admin.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        role_admin.setForeground(new java.awt.Color(204, 0, 102));
        role_admin.setText("                 Admin");
        role_admin.setToolTipText("");
        role_admin.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo 5.png"))); // NOI18N

        bt_status_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_status_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_status_book.setText("Status Book");
        bt_status_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_status_bookMouseClicked(evt);
            }
        });

        bt_data_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_data_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_data_book.setText("Data Books");
        bt_data_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_data_bookMouseClicked(evt);
            }
        });

        bt_input_book.setBackground(new java.awt.Color(153, 153, 153));
        bt_input_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_input_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_input_book.setText("Input Book");
        bt_input_book.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        bt_input_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_input_bookMouseClicked(evt);
            }
        });

        bt_send_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_send_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_send_book.setText("Send Book");
        bt_send_book.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.blue, java.awt.Color.cyan, java.awt.Color.blue, java.awt.Color.cyan));
        bt_send_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_send_bookMouseClicked(evt);
            }
        });

        bt_sending_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_sending_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_sending_book.setText("Sending Book");
        bt_sending_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_sending_bookMouseClicked(evt);
            }
        });

        transaction.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction"));

        bt_rejected_books.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_rejected_books.setForeground(new java.awt.Color(2, 59, 135));
        bt_rejected_books.setText("Rejected Books");
        bt_rejected_books.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_rejected_booksMouseClicked(evt);
            }
        });

        bt_history_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_history_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_history_book.setText("History Books");
        bt_history_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_history_bookMouseClicked(evt);
            }
        });

        report.setBorder(javax.swing.BorderFactory.createTitledBorder("Report"));

        information_system2.setBorder(javax.swing.BorderFactory.createTitledBorder("Information System"));

        bt_location.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_location.setForeground(new java.awt.Color(2, 59, 135));
        bt_location.setText("Location");
        bt_location.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_locationMouseClicked(evt);
            }
        });

        bt_data_donatur.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_data_donatur.setForeground(new java.awt.Color(2, 59, 135));
        bt_data_donatur.setText("Data Donatur");
        bt_data_donatur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_data_donaturMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(transaction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(report, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(role_admin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(information_system2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bt_status_book, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_input_book, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_sending_book, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_data_book, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_send_book, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_rejected_books, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_history_book, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_location, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_data_donatur, javax.swing.GroupLayout.Alignment.LEADING)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(role_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(bt_status_book)
                        .addGap(4, 4, 4)
                        .addComponent(bt_input_book, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(bt_sending_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(bt_data_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(bt_send_book))
                    .addComponent(transaction, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(bt_rejected_books))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(bt_history_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(report, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(bt_location))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(bt_data_donatur))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(information_system2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(332, 332, 332)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_kanan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 630));

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

        delivery_date.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        delivery_date.setText("Delivery Date");
        panel_kanan.add(delivery_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, -1, -1));
        panel_kanan.add(t_book_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 80, 40));

        book_id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        book_id.setText("Book ID");
        panel_kanan.add(book_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

        bt_send.setText("SEND");
        bt_send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_sendMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_send, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 590, 150, -1));

        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Book ID", "Location ID", "Donor ID", "Book Title", "Book Category", "Book Condition", "Delivery Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
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

        panel_kanan.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 760, 400));

        bt_edit.setText("EDIT");
        bt_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_editMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 150, -1));
        panel_kanan.add(t_location_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 70, 40));

        location_id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        location_id.setText("Location ID");
        panel_kanan.add(location_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));
        panel_kanan.add(delivery_date_chooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 110, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_kanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_kanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void bt_status_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_status_bookMouseClicked
        StatusBook status = new StatusBook();
        status.setVisible(true);
        status.pack();
        status.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_status_bookMouseClicked

    private void bt_input_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_input_bookMouseClicked
        InputBook input = new InputBook();
        input.setVisible(true);
        input.pack();
        input.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_input_bookMouseClicked

    private void bt_data_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_data_bookMouseClicked
        DataBook dataBook = new DataBook();
        dataBook.setVisible(true);
        dataBook.pack();
        dataBook.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_data_bookMouseClicked

    private void bt_send_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_send_bookMouseClicked
        SendBook send = new SendBook();
        send.setVisible(true);
        send.pack();
        send.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_send_bookMouseClicked

    private void bt_rejected_booksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_rejected_booksMouseClicked
        RejectedBooks reject = new RejectedBooks();
        reject.setVisible(true);
        reject.pack();
        reject.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_rejected_booksMouseClicked

    private void bt_locationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_locationMouseClicked
        Location location = new Location();
        location.setVisible(true);
        location.pack();
        location.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_locationMouseClicked

    private void bt_data_donaturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_data_donaturMouseClicked
        DataDonatur donatur = new DataDonatur();
        donatur.setVisible(true);
        donatur.pack();
        donatur.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_data_donaturMouseClicked

    private void bt_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_logoutMouseClicked
        Login login = new Login();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_logoutMouseClicked

    private void bt_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_editMouseClicked
        isEditMode = true;
        System.out.println("Mode edit diaktifkan. Sekarang Anda bisa memilih baris pada tabel.");
    }//GEN-LAST:event_bt_editMouseClicked

    private void bt_sendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_sendMouseClicked
        updateData();
    }//GEN-LAST:event_bt_sendMouseClicked

    private void tbl_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dataMouseClicked
        if(isEditMode) {
            int selectedRow = tbl_data.getSelectedRow();
        if (selectedRow != -1) {
            int i = tbl_data.getSelectedRow();
            String id = tbl_data.getValueAt(i, 0).toString(); 
            String id_location = tbl_data.getValueAt(i, 1).toString(); 
            //String delivery_date = tbl_data.getValueAt(i, 2).toString();

            t_book_id.setText(id);
            t_location_id.setText(id_location);
            //delivery_date_chooser.setDate(delivery_date);
        } else {
            System.out.print("Error: Tidak ada baris yang dipilih.");
         }
        } else {
            tbl_data.clearSelection();
        }
    }//GEN-LAST:event_tbl_dataMouseClicked

    private void bt_history_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_history_bookMouseClicked
        HistoryBook history = new HistoryBook();
        history.setVisible(true);
        history.pack();
        history.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_history_bookMouseClicked

    private void bt_sending_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_sending_bookMouseClicked
        SendingBook send = new SendingBook();
        send.setVisible(true);
        send.pack();
        send.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_sending_bookMouseClicked

    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SendBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel book_id;
    private javax.swing.JLabel bt_data_book;
    private javax.swing.JLabel bt_data_donatur;
    private javax.swing.JButton bt_edit;
    private javax.swing.JLabel bt_history_book;
    private javax.swing.JLabel bt_input_book;
    private javax.swing.JLabel bt_location;
    private javax.swing.JButton bt_logout;
    private javax.swing.JLabel bt_rejected_books;
    private javax.swing.JButton bt_send;
    private javax.swing.JLabel bt_send_book;
    private javax.swing.JLabel bt_sending_book;
    private javax.swing.JLabel bt_status_book;
    private javax.swing.JLabel delivery_date;
    private com.toedter.calendar.JDateChooser delivery_date_chooser;
    private javax.swing.JLabel ic_close;
    private javax.swing.JLabel ic_minimize;
    private javax.swing.JLabel information_system2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel location_id;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JLabel report;
    private javax.swing.JLabel role_admin;
    private javax.swing.JTextField t_book_id;
    private javax.swing.JTextField t_location_id;
    private javax.swing.JTable tbl_data;
    private javax.swing.JLabel transaction;
    // End of variables declaration//GEN-END:variables
}
