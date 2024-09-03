package read_and_share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 * Class Location bertanggung jawab untuk mengelola data lokasi dalam aplikasi.
 */
public class Location extends javax.swing.JFrame {
    // Variabel untuk menyimpan koordinat lokasi jendela
    int xx, xy;
    // Objek koneksi ke database
    private Connection conn;  

    /**
     * Konstruktor kelas Location.
     * Menginisialisasi komponen GUI dan menghubungkan ke database.
     */
    public Location() {
        initComponents();
        
        conn = Koneksi.getConnection();
        getData((DefaultTableModel) tbl_data.getModel());
    }

    /**
     * Method untuk menambahkan data lokasi ke dalam database.
     */
    private void insertData() {
    String location_name = t_location_name.getText();
    String location_address = t_location_address.getText();

    // Set up your SQL query to insert data into your database table
    String sql = "INSERT INTO location (id_location, location_name, address) VALUES (DEFAULT, ?, ?)"; 

    try {
        // Assuming you have a connection object named 'connection'
        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        // Set the parameters for the prepared statement
        pstmt.setString(1, location_name);
        pstmt.setString(2, location_address);

        // Execute the insert statement
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows > 0) {
            // Retrieve the auto-generated donate_id
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int location_id = generatedKeys.getInt(1);
                System.out.println("Inserted location_id: " + location_id);
                // Optionally, you can use the donateId here or perform other actions
            }
        }

        // Close the result set, statement, and connection
        pstmt.close();
        resetForm();
        getData((DefaultTableModel) tbl_data.getModel());
        showMessageDialog(null, "Location berhasil ditambahkan");
        // Optionally, you can show a success message or perform other actions here
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}  
 
    /**
     * Method untuk memperbarui data lokasi dalam database.
     */
    private void updateData() {
    String location_id = t_id.getText();
    String location_name = t_location_name.getText();
    String location_address = t_location_address.getText();

    // Set up your SQL query to insert data into your database table
    String sql = "UPDATE location SET location_name=?, address=? WHERE id_location=?"; 

    try {
        // Assuming you have a connection object named 'connection'
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set the parameters for the prepared statement
        pstmt.setString(1, location_name);
        pstmt.setString(2, location_address);
        pstmt.setString(3, location_id);

        // Execute the insert statement
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows > 0) {
                System.out.println("Updated location_id: " + id);
            }


        // Close the result set, statement, and connection
        pstmt.close();
        resetForm();
        getData((DefaultTableModel) tbl_data.getModel());
        showMessageDialog(null, "Location berhasil diperbarui");
        // Optionally, you can show a success message or perform other actions here
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}  
 
    /**
     * Method untuk menghapus data lokasi dari database.
     */
    private void deleteData() {
    String location_id = t_id.getText();

    // Set up your SQL query to insert data into your database table
    String sql = "DELETE FROM location WHERE id_location=?"; 

    try {
        // Assuming you have a connection object named 'connection'
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set the parameters for the prepared statement
        pstmt.setString(1, location_id);

        // Execute the insert statement
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows > 0) {
                System.out.println("Deleted id_location: " + id);
            }
        // Close the result set, statement, and connection
        pstmt.close();
        resetForm();
        getData((DefaultTableModel) tbl_data.getModel());
        showMessageDialog(null, "Location berhasil dihapus");
        // Optionally, you can show a success message or perform other actions here
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}  
   
    /**
     * Method untuk mendapatkan data lokasi dari database dan menampilkannya di JTable.
     * @param par Model tabel yang akan diisi dengan data.
     */
    private void getData(DefaultTableModel par) {
        DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
        model.setRowCount(0);
        
        try{
            String sql = "SELECT * FROM location";
            System.out.println(sql);
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                int locationID       = rs.getInt("id_location");
                String location_name         = rs.getString("location_name");
                String address    = rs.getString("address");
                
                model.addRow(new Object[]{locationID, location_name, address});
               
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
     /**
     * Method untuk mereset form input lokasi.
     */
    private void resetForm() {
       t_id.setText("");
       t_location_name.setText("");
       t_location_address.setText("");
   }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_kanan = new javax.swing.JPanel();
        ic_close = new javax.swing.JLabel();
        ic_minimize = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        bt_logout = new javax.swing.JButton();
        role_admin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        logo = new javax.swing.JLabel();
        information_system = new javax.swing.JLabel();
        bt_location = new javax.swing.JLabel();
        bt_data_donatur = new javax.swing.JLabel();
        bt_sending_book = new javax.swing.JLabel();
        bt_status_book = new javax.swing.JLabel();
        bt_data_book = new javax.swing.JLabel();
        bt_input_book = new javax.swing.JLabel();
        bt_send_book = new javax.swing.JLabel();
        transaction = new javax.swing.JLabel();
        Report = new javax.swing.JLabel();
        bt_history_book = new javax.swing.JLabel();
        bt_rejected_books = new javax.swing.JLabel();
        t_location_name = new javax.swing.JTextField();
        location_name = new javax.swing.JLabel();
        t_location_address = new javax.swing.JTextField();
        location_address = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        bt_delete = new javax.swing.JButton();
        bt_update = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        t_id = new javax.swing.JTextField();
        id = new javax.swing.JLabel();

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

        information_system.setBorder(javax.swing.BorderFactory.createTitledBorder("Information System"));

        bt_location.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_location.setForeground(new java.awt.Color(2, 59, 135));
        bt_location.setText("Location");
        bt_location.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.blue, java.awt.Color.cyan, java.awt.Color.blue, java.awt.Color.cyan));
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

        bt_sending_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_sending_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_sending_book.setText("Sending Book");
        bt_sending_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_sending_bookMouseClicked(evt);
            }
        });

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
        bt_send_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_send_bookMouseClicked(evt);
            }
        });

        transaction.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction"));

        Report.setBorder(javax.swing.BorderFactory.createTitledBorder("Report"));

        bt_history_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_history_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_history_book.setText("History Books");
        bt_history_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_history_bookMouseClicked(evt);
            }
        });

        bt_rejected_books.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_rejected_books.setForeground(new java.awt.Color(2, 59, 135));
        bt_rejected_books.setText("Rejected Books");
        bt_rejected_books.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_rejected_booksMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(transaction, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(bt_data_book, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_send_book)
                                        .addComponent(bt_sending_book)
                                        .addComponent(bt_status_book, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_input_book, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_rejected_books)
                                        .addComponent(bt_history_book)
                                        .addComponent(bt_location, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_data_donatur)))
                                .addComponent(Report, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(information_system, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(role_admin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(role_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(bt_data_book)
                        .addGap(4, 4, 4)
                        .addComponent(bt_send_book)
                        .addGap(4, 4, 4)
                        .addComponent(bt_sending_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(bt_status_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(bt_input_book, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transaction, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(bt_rejected_books))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(bt_history_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Report, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(bt_location))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(bt_data_donatur))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(information_system, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        panel_kanan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 630));
        panel_kanan.add(t_location_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 100, 40));

        location_name.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        location_name.setText("Location Name");
        panel_kanan.add(location_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, -1, -1));
        panel_kanan.add(t_location_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 100, 40));

        location_address.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        location_address.setText("Location Address");
        panel_kanan.add(location_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, -1, -1));

        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Location ID", "Location Name", "Location Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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

        bt_delete.setText("DELETE");
        bt_delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_deleteMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 150, -1));

        bt_update.setText("UPDATE");
        bt_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_updateMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 130, 150, -1));

        bt_add.setText("ADD");
        bt_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_addMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 150, -1));
        panel_kanan.add(t_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 40, 40));

        id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        id.setText("ID");
        panel_kanan.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_kanan, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
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

    private void bt_history_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_history_bookMouseClicked
        HistoryBook history = new HistoryBook();
        history.setVisible(true);
        history.pack();
        history.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_history_bookMouseClicked

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

    private void bt_deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_deleteMouseClicked
        deleteData();
    }//GEN-LAST:event_bt_deleteMouseClicked

    private void bt_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_updateMouseClicked
        updateData();
    }//GEN-LAST:event_bt_updateMouseClicked

    private void bt_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_addMouseClicked
       insertData();
    }//GEN-LAST:event_bt_addMouseClicked

    private void tbl_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dataMouseClicked
         if (tbl_data.getSelectedRow() != -1) {
            int i = tbl_data.getSelectedRow();
            String id = tbl_data.getValueAt(i, 0).toString(); 
            String name = tbl_data.getValueAt(i, 1).toString();
            String address = tbl_data.getValueAt(i, 2).toString();

            t_id.setText(id);
            t_location_name.setText(name);
            t_location_address.setText(address);
        } else {
            System.out.print("Error: Tidak ada baris yang dipilih.");
         }
    }//GEN-LAST:event_tbl_dataMouseClicked

    private void bt_rejected_booksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_rejected_booksMouseClicked
        RejectedBooks reject = new RejectedBooks();
        reject.setVisible(true);
        reject.pack();
        reject.setLocationRelativeTo(null); 
        
        this.dispose();        
    }//GEN-LAST:event_bt_rejected_booksMouseClicked

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
                new Location().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Report;
    private javax.swing.JButton bt_add;
    private javax.swing.JLabel bt_data_book;
    private javax.swing.JLabel bt_data_donatur;
    private javax.swing.JButton bt_delete;
    private javax.swing.JLabel bt_history_book;
    private javax.swing.JLabel bt_input_book;
    private javax.swing.JLabel bt_location;
    private javax.swing.JButton bt_logout;
    private javax.swing.JLabel bt_rejected_books;
    private javax.swing.JLabel bt_send_book;
    private javax.swing.JLabel bt_sending_book;
    private javax.swing.JLabel bt_status_book;
    private javax.swing.JButton bt_update;
    private javax.swing.JLabel ic_close;
    private javax.swing.JLabel ic_minimize;
    private javax.swing.JLabel id;
    private javax.swing.JLabel information_system;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel location_address;
    private javax.swing.JLabel location_name;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JLabel role_admin;
    private javax.swing.JTextField t_id;
    private javax.swing.JTextField t_location_address;
    private javax.swing.JTextField t_location_name;
    private javax.swing.JTable tbl_data;
    private javax.swing.JLabel transaction;
    // End of variables declaration//GEN-END:variables

}
