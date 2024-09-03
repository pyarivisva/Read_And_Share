package read_and_share;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * Class HistoryBook adalah JFrame yang digunakan untuk mengelola sejarah donasi buku.
 */
public class HistoryBook extends javax.swing.JFrame {
    
    // Variabel untuk menyimpan koordinat awal saat frame digerakkan
    int xx, xy;
    // Variabel untuk menyimpan koneksi ke database
    private Connection conn;  

     /**
     * Konstruktor untuk kelas HistoryBook.
     * Menginisialisasi komponen dan mendapatkan koneksi ke database.
     */
    public HistoryBook() {
        initComponents();
        
        conn = Koneksi.getConnection();
        getData();
    }

    /**
     * Method untuk mencari data donasi buku berdasarkan ID buku.
     * @param book_id ID buku yang akan dicari.
     */
    private void searchData(String book_id) {
    DefaultTableModel modelData = (DefaultTableModel) tbl_data.getModel();
    DefaultTableModel modelSearch = (DefaultTableModel) tbl_search.getModel();
    modelData.setRowCount(0); // Bersihkan tabel yang sudah ada sebelum menambahkan data baru
    modelSearch.setRowCount(0);

    String sql = "SELECT * FROM donate_donor WHERE book_id = ?";
    
    try {
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, book_id);
        
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int donateID = rs.getInt("donate_id");
            int donorID = rs.getInt("user_id");
            String bookId = rs.getString("book_id");
            int quantity = rs.getInt("quantity");
            String bookTitle = rs.getString("book_title");
            String author = rs.getString("author");
            String bookCategoryStr = rs.getString("book_category");
            String bookConditionStr = rs.getString("book_condition");
            Date deliveryDate = rs.getDate("delivery_date");
            Date dateReceived = rs.getDate("date_received");
            String status = rs.getString("status");

            modelData.addRow(new Object[]{donateID, donorID, bookId, quantity, bookTitle, author, bookCategoryStr, bookConditionStr, deliveryDate, dateReceived, status});
            modelSearch.addRow(new Object[]{bookId});
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    /**
     * Method untuk mengambil semua data donasi buku dari database dan menampilkannya ke dalam JTable.
     */
    private void getData() {
        DefaultTableModel modelData = (DefaultTableModel) tbl_data.getModel();
        modelData.setRowCount(0);
        
        try{
            String sql = "SELECT * FROM donate_donor";
            System.out.println(sql);
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                int locationID      = rs.getInt("location_id");
                int userID       = rs.getInt("user_id");
                String book_title = rs.getString("book_title");
                String author    = rs.getString("author");
                String bookCategoryStr = rs.getString("book_category");
                String bookConditionStr = rs.getString("book_condition");
                Date date_arrive = rs.getDate("date_arrive");
                Date delivery_date = rs.getDate("delivery_date");
                Date dateReceived = rs.getDate("date_received");
                String status = rs.getString("status");
                
                modelData.addRow(new Object[]{locationID, userID, book_title, author, bookCategoryStr, bookConditionStr, date_arrive, delivery_date, dateReceived, status});
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            Logger.getLogger(HistoryBook.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_kanan = new javax.swing.JPanel();
        ic_close = new javax.swing.JLabel();
        ic_minimize = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        role_admin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        logo = new javax.swing.JLabel();
        bt_logout = new javax.swing.JButton();
        bt_input_book = new javax.swing.JLabel();
        bt_status_book = new javax.swing.JLabel();
        bt_data_book = new javax.swing.JLabel();
        bt_send_book = new javax.swing.JLabel();
        bt_sending_book = new javax.swing.JLabel();
        transaction = new javax.swing.JLabel();
        bt_location = new javax.swing.JLabel();
        bt_data_donatur = new javax.swing.JLabel();
        information_system1 = new javax.swing.JLabel();
        bt_rejected_books = new javax.swing.JLabel();
        bt_history_book = new javax.swing.JLabel();
        report = new javax.swing.JLabel();
        t_book_id = new javax.swing.JTextField();
        book_id = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_search = new javax.swing.JTable();
        bt_search = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();

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

        role_admin.setBackground(new java.awt.Color(255, 255, 255));
        role_admin.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        role_admin.setForeground(new java.awt.Color(204, 0, 102));
        role_admin.setText("                 Admin");
        role_admin.setToolTipText("");
        role_admin.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo 5.png"))); // NOI18N

        bt_logout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_logout.setForeground(new java.awt.Color(51, 51, 255));
        bt_logout.setText("LOGOUT");
        bt_logout.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 0, 0)));
        bt_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_logoutMouseClicked(evt);
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

        bt_send_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_send_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_send_book.setText("Send Book");
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

        information_system1.setBorder(javax.swing.BorderFactory.createTitledBorder("Information System"));

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
        bt_history_book.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.blue, java.awt.Color.cyan, java.awt.Color.blue, java.awt.Color.cyan));
        bt_history_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_history_bookMouseClicked(evt);
            }
        });

        report.setBorder(javax.swing.BorderFactory.createTitledBorder("Report"));

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
                        .addGap(45, 45, 45)
                        .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(transaction, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(information_system1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(report, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(bt_input_book, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_send_book, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_sending_book, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_data_book, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_status_book, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_location)
                                        .addComponent(bt_data_donatur)
                                        .addComponent(bt_history_book, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_rejected_books, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(role_admin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
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
                        .addGap(40, 40, 40)
                        .addComponent(bt_input_book, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(bt_send_book)
                        .addGap(4, 4, 4)
                        .addComponent(bt_sending_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(bt_data_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(bt_status_book))
                    .addComponent(transaction, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(report, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(bt_history_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(bt_rejected_books)))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(bt_location))
                    .addComponent(information_system1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(bt_data_donatur)))
                .addGap(21, 21, 21)
                .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(261, 261, 261)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_kanan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 630));
        panel_kanan.add(t_book_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 90, 40));

        book_id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        book_id.setText("Book ID");
        panel_kanan.add(book_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

        tbl_search.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Book ID"
            }
        ));
        jScrollPane1.setViewportView(tbl_search);

        panel_kanan.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 760, 50));

        bt_search.setText("SEARCH");
        bt_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_searchMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 150, 30));

        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Location ID", "Donor ID", "Book Title", "Author", "Book Category", "Book Condition", "Input Date", "Delivery Date", "Date received", "Status"
            }
        ));
        jScrollPane2.setViewportView(tbl_data);

        panel_kanan.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 760, 390));

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

    private void bt_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_logoutMouseClicked
        Login login = new Login();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_logoutMouseClicked

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

    private void bt_status_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_status_bookMouseClicked
        StatusBook status = new StatusBook();
        status.setVisible(true);
        status.pack();
        status.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_status_bookMouseClicked

    private void bt_data_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_data_bookMouseClicked
        DataBook dataBook = new DataBook();
        dataBook.setVisible(true);
        dataBook.pack();
        dataBook.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_data_bookMouseClicked

    private void bt_input_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_input_bookMouseClicked
        InputBook input = new InputBook();
        input.setVisible(true);
        input.pack();
        input.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_input_bookMouseClicked

    private void bt_rejected_booksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_rejected_booksMouseClicked
        RejectedBooks reject = new RejectedBooks();
        reject.setVisible(true);
        reject.pack();
        reject.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_rejected_booksMouseClicked

    private void bt_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_searchMouseClicked
        String book_id = t_book_id.getText();
        searchData(book_id);
    }//GEN-LAST:event_bt_searchMouseClicked

    private void bt_history_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_history_bookMouseClicked
        HistoryBook history = new HistoryBook();
        history.setVisible(true);
        history.pack();
        history.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_history_bookMouseClicked

    private void bt_send_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_send_bookMouseClicked
        SendBook send = new SendBook();
        send.setVisible(true);
        send.pack();
        send.setLocationRelativeTo(null); 
        
        this.dispose();
    }//GEN-LAST:event_bt_send_bookMouseClicked

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
                new HistoryBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel book_id;
    private javax.swing.JLabel bt_data_book;
    private javax.swing.JLabel bt_data_donatur;
    private javax.swing.JLabel bt_history_book;
    private javax.swing.JLabel bt_input_book;
    private javax.swing.JLabel bt_location;
    private javax.swing.JButton bt_logout;
    private javax.swing.JLabel bt_rejected_books;
    private javax.swing.JButton bt_search;
    private javax.swing.JLabel bt_send_book;
    private javax.swing.JLabel bt_sending_book;
    private javax.swing.JLabel bt_status_book;
    private javax.swing.JLabel ic_close;
    private javax.swing.JLabel ic_minimize;
    private javax.swing.JLabel information_system1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JLabel report;
    private javax.swing.JLabel role_admin;
    private javax.swing.JTextField t_book_id;
    private javax.swing.JTable tbl_data;
    private javax.swing.JTable tbl_search;
    private javax.swing.JLabel transaction;
    // End of variables declaration//GEN-END:variables
}
