package read_and_share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 * Class InputBook adalah JFrame yang digunakan untuk mengelola donasi buku yang tiba di admin.
 */
public class InputBook extends javax.swing.JFrame {
    
    // Variabel untuk menyimpan koordinat awal saat frame digerakka
    int xx, xy;
    // Variabel untuk menyimpan koneksi ke database
    private Connection conn;
    private boolean isEditMode;

    /**
     * Konstruktor untuk kelas InputBook.
     * Menginisialisasi komponen, mendapatkan koneksi ke database,
     * dan memanggil metode untuk mengisi data dan combo box.
     */
    public InputBook() {
        initComponents();
        
        conn = Koneksi.getConnection();
        getData((DefaultTableModel) tbl_data.getModel());
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

    //Enum untuk mendefinisikan kategori buku.
    public enum BookCategory {
    fiction,
    non_fiction,
    children_literature,
    textbook,
    parenting_book,
    history
}
    //Enum untuk mendefinisikan kondisi buku.
    public enum BookCondition {
    new_book,
    old_book,
    reject_book
    }
    
    //Method untuk memperbarui data donasi buku.
    private void updateData() {
    String donate_id = t_donate_id.getText();
    String book_id = setIdBook();
    String book_title = t_book_title.getText();
    String author = t_author.getText();
    String bookCategoryStr = cb_book_category.getSelectedItem().toString();
    String bookConditionStr = cb_book_condition.getSelectedItem().toString();
    

    // SQL query untuk memperbarui data dalam tabel database
    String sql = "UPDATE donate_donor SET book_title=?, author=?, book_category=?, book_condition=?, date_arrive=NOW(), status=?, book_id=?, is_send=?, is_input=? WHERE donate_id=?"; 
    try {
        // Assuming you have a connection object named 'connection'
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set parameter untuk prepared statement
        pstmt.setString(1, book_title);
        pstmt.setString(2, author);
        pstmt.setString(3, bookCategoryStr);
        pstmt.setString(4, bookConditionStr);
        pstmt.setString(5, "arrive");
        pstmt.setString(6, book_id);
        pstmt.setInt(7,0);
        pstmt.setInt(8, 1);
        pstmt.setString(9, donate_id);

        // Eksekusi statement update
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows > 0) {
                System.out.println("Updated book_title: " + book_title);
            }

        pstmt.close();
        resetForm();
        getData((DefaultTableModel) tbl_data.getModel());
        showMessageDialog(null, "Book berhasil ditambahkan");
    } catch (SQLException e) {
        e.printStackTrace(); 
    }
}
  
    /**
     * Method untuk mengambil data donasi buku dari database dan menampilkannya ke dalam JTable.
     * @param par DefaultTableModel untuk JTable.
     */
    private void getData(DefaultTableModel par) {
        DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
        model.setRowCount(0);
        
        try{
            String sql = "SELECT * FROM donate_donor WHERE is_input=0";
            System.out.println(sql);
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                int donateID      = rs.getInt("donate_id");
                int donorID       = rs.getInt("user_id");
                String bookID        = rs.getString("book_id");
                int quantity      = rs.getInt("quantity");
                String book_title = rs.getString("book_title");
                String author    = rs.getString("author");
                String bookCategoryStr = rs.getString("book_category");
                String bookConditionStr = rs.getString("book_condition");
                
                model.addRow(new Object[]{donateID, donorID, bookID, quantity, book_title, author, bookCategoryStr, bookConditionStr});
               
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            Logger.getLogger(InputBook.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    /**
     * Method untuk mengatur ID buku berdasarkan urutan yang ada.
     * @return ID buku yang telah diatur.
     */
    private String setIdBook() {
    String urutan = null;
    Date now = new Date();
    SimpleDateFormat noFormat = new SimpleDateFormat("yyMM");
    String no = noFormat.format(now);

    
    String sql = "SELECT RIGHT(book_id, 3) AS Nomor " + 
                 "FROM donate_donor " + 
                 "WHERE book_id LIKE 'BK" + no + "%' " + 
                 "ORDER BY book_id DESC " + 
                 "LIMIT 1";

    try {
        System.out.println(sql);
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            int nomor = Integer.parseInt(rs.getString("Nomor")) + 1;
            urutan = "BK" + no + String.format("%03d", nomor);
        } else {
            urutan = "BK" + no + "001";
        }

        rs.close();
        st.close();
    } catch (SQLException e) {
        java.util.logging.Logger.getLogger(InputBook.class.getName()).log(Level.SEVERE, null, e);
    }
    return urutan;
}
    // Method untuk mereset form input data.
   private void resetForm() {
       t_donate_id.setText("");
       t_book_id.setText("");
       t_book_title.setText("");
       t_author.setText("");
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
        bt_sending_book = new javax.swing.JLabel();
        bt_status_book = new javax.swing.JLabel();
        bt_data_book = new javax.swing.JLabel();
        bt_input_book = new javax.swing.JLabel();
        bt_send_book = new javax.swing.JLabel();
        transaction = new javax.swing.JLabel();
        bt_rejected_book = new javax.swing.JLabel();
        bt_history_book1 = new javax.swing.JLabel();
        report = new javax.swing.JLabel();
        information_system1 = new javax.swing.JLabel();
        bt_location = new javax.swing.JLabel();
        bt_data_donatur = new javax.swing.JLabel();
        t_book_id = new javax.swing.JTextField();
        quantity = new javax.swing.JLabel();
        t_donate_id = new javax.swing.JTextField();
        donor_id = new javax.swing.JLabel();
        t_book_title = new javax.swing.JTextField();
        book_title = new javax.swing.JLabel();
        author = new javax.swing.JLabel();
        t_author = new javax.swing.JTextField();
        book_category = new javax.swing.JLabel();
        cb_book_category = new javax.swing.JComboBox<>();
        book_condition = new javax.swing.JLabel();
        cb_book_condition = new javax.swing.JComboBox<>();
        bt_save = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        bt_edit = new javax.swing.JButton();

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
        bt_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_logoutActionPerformed(evt);
            }
        });

        role_admin.setBackground(new java.awt.Color(255, 255, 255));
        role_admin.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        role_admin.setForeground(new java.awt.Color(204, 0, 102));
        role_admin.setText("                 Admin");
        role_admin.setToolTipText("");
        role_admin.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo 5.png"))); // NOI18N

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
        bt_input_book.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.blue, java.awt.Color.cyan, java.awt.Color.cyan, java.awt.Color.blue));
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

        bt_rejected_book.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_rejected_book.setForeground(new java.awt.Color(2, 59, 135));
        bt_rejected_book.setText("Rejected Books");
        bt_rejected_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_rejected_bookMouseClicked(evt);
            }
        });

        bt_history_book1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        bt_history_book1.setForeground(new java.awt.Color(2, 59, 135));
        bt_history_book1.setText("History Books");
        bt_history_book1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_history_book1MouseClicked(evt);
            }
        });

        report.setBorder(javax.swing.BorderFactory.createTitledBorder("Report"));

        information_system1.setBorder(javax.swing.BorderFactory.createTitledBorder("Information System"));

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
                        .addGap(40, 40, 40)
                        .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(transaction, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(role_admin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(report, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(information_system1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_input_book, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_send_book)
                                    .addComponent(bt_data_book, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_status_book, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_sending_book)
                                    .addComponent(bt_history_book1)
                                    .addComponent(bt_rejected_book)
                                    .addComponent(bt_data_donatur)
                                    .addComponent(bt_location))))))
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
                        .addComponent(bt_send_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(bt_data_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(bt_status_book))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(bt_sending_book))
                    .addComponent(transaction, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(bt_history_book1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(bt_rejected_book))
                    .addComponent(report, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(bt_data_donatur))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(bt_location))
                    .addComponent(information_system1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(bt_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_kanan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 630));
        panel_kanan.add(t_book_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 60, 40));

        quantity.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        quantity.setText("Book ID");
        panel_kanan.add(quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, -1, -1));
        panel_kanan.add(t_donate_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 60, 40));

        donor_id.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        donor_id.setText("Donate ID");
        panel_kanan.add(donor_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));
        panel_kanan.add(t_book_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 100, 40));

        book_title.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        book_title.setText("Book Title");
        panel_kanan.add(book_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, -1));

        author.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        author.setText("Author");
        panel_kanan.add(author, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, -1, -1));
        panel_kanan.add(t_author, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 100, 40));

        book_category.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        book_category.setText("Book Category");
        panel_kanan.add(book_category, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, -1, -1));

        panel_kanan.add(cb_book_category, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 100, 40));

        book_condition.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        book_condition.setText("Book Condition");
        panel_kanan.add(book_condition, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, -1, -1));

        panel_kanan.add(cb_book_condition, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 100, 40));

        bt_save.setText("SAVE");
        bt_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_saveMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 590, 150, -1));

        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Donate ID", "Donor ID", "Book ID", "Quantity of Books", "Book Title", "Author", "Book Category", "Book Condition"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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

        bt_edit.setText("ADD");
        bt_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_editMouseClicked(evt);
            }
        });
        panel_kanan.add(bt_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_kanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1047, Short.MAX_VALUE)
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

    private void bt_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_editMouseClicked
        isEditMode = true;
        System.out.println("Mode edit diaktifkan. Sekarang Anda bisa memilih baris pada tabel.");
    }//GEN-LAST:event_bt_editMouseClicked

    private void bt_saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_saveMouseClicked
        updateData();
    }//GEN-LAST:event_bt_saveMouseClicked

    private void bt_data_donaturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_data_donaturMouseClicked
        DataDonatur donatur = new DataDonatur();
        donatur.setVisible(true);
        donatur.pack();
        donatur.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_data_donaturMouseClicked

    private void bt_locationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_locationMouseClicked
        Location location = new Location();
        location.setVisible(true);
        location.pack();
        location.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_locationMouseClicked

    private void bt_sending_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_sending_bookMouseClicked
        SendingBook send = new SendingBook();
        send.setVisible(true);
        send.pack();
        send.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_sending_bookMouseClicked

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

    private void bt_status_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_status_bookMouseClicked
        StatusBook status = new StatusBook();
        status.setVisible(true);
        status.pack();
        status.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_status_bookMouseClicked

    private void bt_rejected_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_rejected_bookMouseClicked
        RejectedBooks history = new RejectedBooks();
        history.setVisible(true);
        history.pack();
        history.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_rejected_bookMouseClicked

    private void bt_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_logoutActionPerformed

    }//GEN-LAST:event_bt_logoutActionPerformed

    private void bt_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_logoutMouseClicked
        Login login = new Login();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_logoutMouseClicked

    private void ic_minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ic_minimizeMouseClicked
        this.setState(1);
    }//GEN-LAST:event_ic_minimizeMouseClicked

    private void ic_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ic_closeMouseClicked
        dispose();
    }//GEN-LAST:event_ic_closeMouseClicked

    private void tbl_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dataMouseClicked
        if(isEditMode) {
            int selectedRow = tbl_data.getSelectedRow();
        if (selectedRow != -1) {
            String id = tbl_data.getValueAt(selectedRow, 0).toString(); 
            String book_id = setIdBook();

            t_donate_id.setText(id);
            t_book_id.setText(book_id);
        } else {
            System.out.print("Error: Tidak ada baris yang dipilih.");
         }
        } else {
            tbl_data.clearSelection();
        }
    }//GEN-LAST:event_tbl_dataMouseClicked

    private void bt_send_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_send_bookMouseClicked
        SendBook send = new SendBook();
        send.setVisible(true);
        send.pack();
        send.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_send_bookMouseClicked

    private void bt_history_book1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_history_book1MouseClicked
        HistoryBook history = new HistoryBook();
        history.setVisible(true);
        history.pack();
        history.setLocationRelativeTo(null);

        this.dispose();
    }//GEN-LAST:event_bt_history_book1MouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InputBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel author;
    private javax.swing.JLabel book_category;
    private javax.swing.JLabel book_condition;
    private javax.swing.JLabel book_title;
    private javax.swing.JLabel bt_data_book;
    private javax.swing.JLabel bt_data_donatur;
    private javax.swing.JButton bt_edit;
    private javax.swing.JLabel bt_history_book1;
    private javax.swing.JLabel bt_input_book;
    private javax.swing.JLabel bt_location;
    private javax.swing.JButton bt_logout;
    private javax.swing.JLabel bt_rejected_book;
    private javax.swing.JButton bt_save;
    private javax.swing.JLabel bt_send_book;
    private javax.swing.JLabel bt_sending_book;
    private javax.swing.JLabel bt_status_book;
    private javax.swing.JComboBox<String> cb_book_category;
    private javax.swing.JComboBox<String> cb_book_condition;
    private javax.swing.JLabel donor_id;
    private javax.swing.JLabel ic_close;
    private javax.swing.JLabel ic_minimize;
    private javax.swing.JLabel information_system1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JLabel quantity;
    private javax.swing.JLabel report;
    private javax.swing.JLabel role_admin;
    private javax.swing.JTextField t_author;
    private javax.swing.JTextField t_book_id;
    private javax.swing.JTextField t_book_title;
    private javax.swing.JTextField t_donate_id;
    private javax.swing.JTable tbl_data;
    private javax.swing.JLabel transaction;
    // End of variables declaration//GEN-END:variables
}
