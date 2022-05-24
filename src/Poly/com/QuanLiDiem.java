/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poly.com;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.GRADE;

/**
 *
 * @author dell
 */
public class QuanLiDiem extends javax.swing.JFrame {

    ArrayList<GRADE> list = new ArrayList<GRADE>();
    int current = 0;

    /**
     * Creates new form QuanLiDiem
     */
    public QuanLiDiem() {
        initComponents();
        setLocationRelativeTo(null);
        LoadDataGRADEToTable();
    }

    public void LoadDataGRADEToTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement(
                    "select *, (TinHoc + TiengAnh + GDTC)/3 as DiemTB from GRADE join students on STUDENTS.MaSV = GRADE.MaSV ");
            ResultSet rs = ps.executeQuery();
            list.clear();
            while (rs.next()) { 
                GRADE gr = new GRADE();
                gr.setMaSV(rs.getString("MaSV"));
                gr.setHoten(rs.getString("Hoten"));
                gr.setTienganh(rs.getInt("TiengAnh"));
                gr.setTinhoc(rs.getInt("TinHoc"));
                gr.setGDTC(rs.getInt("GDTC"));
                gr.setDiemTB(rs.getDouble("DiemTB"));
                list.add(gr);
            }
            DefaultTableModel model = (DefaultTableModel) tbldanhsach.getModel();
            model.setRowCount(0);
            for (GRADE grade : list) {
                Object[] row = new Object[]{grade.getMaSV(), grade.getHoten(),
                    grade.getTienganh(), grade.getTinhoc(), grade.getGDTC(), grade.getDiemTB()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void save()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement("insert into GRADE values (?,?,?,?)");
            ps.setString(1,txtmasv.getText());
            ps.setInt(2,Integer.parseInt(txttienganh.getText()));
            ps.setInt(3,Integer.parseInt(txttinhoc.getText()));
            ps.setInt(4,Integer.parseInt(txtGDTC.getText()));
            int kq = ps.executeUpdate();
            if(kq ==1)
            {
                JOptionPane.showMessageDialog(this,"Thêm Thành Công");
                
            }
            ps.close();
            conn.close();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public boolean CheckValidate()
    {
        if(txthoten.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(this,"Họ tên không được để trống");
            txthoten.setBackground(Color.yellow);
            txthoten.requestFocus();
            return false;
        }
         
         if(txtmasv.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(this," MaSV không được để trống");
            txtmasv.setBackground(Color.yellow);
            txtmasv.requestFocus();
            return false;
        }
        
          if(txttienganh.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(this,"Tiếng anh không được để trống");
            txttienganh.setBackground(Color.yellow);
            txttienganh.requestFocus();
            return false;
        }
          try {
            double DiemTiengAnh = Double.parseDouble(txttienganh.getText());
        } catch (Exception e) 
        {
             JOptionPane.showMessageDialog(this,"Tiếng anh phải nhập số");
            txttienganh.setBackground(Color.yellow);
            txttienganh.setText("");
            txttienganh.requestFocus();
            return false;
        }
           if(txttinhoc.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(this,"Tin học không được để trống");
            txttinhoc.setBackground(Color.yellow);
            txttinhoc.requestFocus();
            return false;
        }
           try {
            double DiemTinHoc = Double.parseDouble(txttinhoc.getText());
        } catch (Exception e) 
        {
             JOptionPane.showMessageDialog(this,"Tin Học phải nhập số");
            txttinhoc.setBackground(Color.yellow);
            txttinhoc.setText("");
            txttinhoc.requestFocus();
            return false;
        }
            if(txtGDTC.getText().trim().length()==0)
        {
            JOptionPane.showMessageDialog(this,"GDTC không được để trống");
            txtGDTC.setBackground(Color.yellow);
            txtGDTC.requestFocus();
            return false;
        }
            
           try {
            double DiemGDTC = Double.parseDouble(txtGDTC.getText());
        } catch (Exception e) 
        {
             JOptionPane.showMessageDialog(this,"GDTC phải nhập số");
            txtGDTC.setBackground(Color.yellow);
            txtGDTC.setText("");
            txtGDTC.requestFocus();
            return false;
        }
        return true;
    }
    public void delete(){
         try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement("delete from GRADE where MaSV = ?");
            ps.setString(1,txtmasv.getText());
           
            int kq = ps.executeUpdate();
            if(kq ==1)
            {
                JOptionPane.showMessageDialog(this,"Xóa Thành Công");
                
            }
            ps.close();
            conn.close();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void Seach()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement(
                    "select *, (TinHoc + TiengAnh + GDTC)/3 as DiemTB from GRADE join students on STUDENTS.MaSV = GRADE.MaSV where STUDENTS.MaSV = ? ");
            ps.setString(1,txtfind.getText());
            ResultSet rs = ps.executeQuery();
            list.clear();
            while (rs.next()) {
                GRADE gr = new GRADE();
                gr.setMaSV(rs.getString("MaSV"));
                gr.setHoten(rs.getString("Hoten"));
                gr.setTienganh(rs.getInt("TiengAnh"));
                gr.setTinhoc(rs.getInt("TinHoc"));
                gr.setGDTC(rs.getInt("GDTC"));
                gr.setDiemTB(rs.getDouble("DiemTB"));
                list.add(gr);
            }
            DefaultTableModel model = (DefaultTableModel) tbldanhsach.getModel();
            model.setRowCount(0);
            for (GRADE grade : list) {
                Object[] row = new Object[]{grade.getMaSV(), grade.getHoten(),
                    grade.getTienganh(), grade.getTinhoc(), grade.getGDTC(), grade.getDiemTB()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtfind = new javax.swing.JTextField();
        btnseach = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txthoten = new javax.swing.JTextField();
        txtmasv = new javax.swing.JTextField();
        txttienganh = new javax.swing.JTextField();
        txttinhoc = new javax.swing.JTextField();
        txtGDTC = new javax.swing.JTextField();
        lbldiemtb = new javax.swing.JLabel();
        btnnew = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnfirst = new javax.swing.JButton();
        btnnext = new javax.swing.JButton();
        btnprive = new javax.swing.JButton();
        btnlast = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbldanhsach = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TimKiem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("MaSV : ");

        btnseach.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnseach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ModernXP-20-Seach-icon.png"))); // NOI18N
        btnseach.setText("Seach");
        btnseach.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnseach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfind, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnseach, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtfind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnseach))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Quản Lí  Điểm Sinh Viên");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Họ Tên :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("MaSV : ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tiếng Anh :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Tin Học :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("GDTC :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Điểm TB : ");

        lbldiemtb.setText("jLabel9");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGDTC, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(txttinhoc, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttienganh)
                                .addGap(2, 2, 2)))
                        .addGap(32, 32, 32)
                        .addComponent(lbldiemtb))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(26, 26, 26)
                                .addComponent(txtmasv, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(txtmasv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txttienganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldiemtb))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txttinhoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtGDTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        btnnew.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/New-file-icon.png"))); // NOI18N
        btnnew.setText("NEW");
        btnnew.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btnsave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Save-icon.png"))); // NOI18N
        btnsave.setText("SAVE");
        btnsave.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btndelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Actions-window-close-icon.png"))); // NOI18N
        btndelete.setText("DELETE");
        btndelete.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit-validated-icon.png"))); // NOI18N
        btnupdate.setText("UPDATE");
        btnupdate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btnfirst.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnfirst.setText("<<");

        btnnext.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnnext.setText("<");

        btnprive.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnprive.setText(">");

        btnlast.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnlast.setText(">>");

        tbldanhsach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MaSV", "Hoten", "Tiếng Anh", "Tin học", "GDTC", "Điểm TB"
            }
        ));
        jScrollPane2.setViewportView(tbldanhsach);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnfirst)
                        .addGap(35, 35, 35)
                        .addComponent(btnnext)
                        .addGap(32, 32, 32)
                        .addComponent(btnprive)
                        .addGap(38, 38, 38)
                        .addComponent(btnlast)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnsave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btndelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(btnnew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 23, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnnew, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnfirst)
                    .addComponent(btnprive)
                    .addComponent(btnnext)
                    .addComponent(btnlast))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed


    }//GEN-LAST:event_btnnewActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        if(CheckValidate())
        {
             save();
             LoadDataGRADEToTable();
        }
       

    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        delete();
        LoadDataGRADEToTable();

    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed

    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnseachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseachActionPerformed
        // TODO add your handling code here:
        Seach();
    }//GEN-LAST:event_btnseachActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLiDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLiDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLiDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLiDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnfirst;
    private javax.swing.JButton btnlast;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnnext;
    private javax.swing.JButton btnprive;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnseach;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbldiemtb;
    private javax.swing.JTable tbldanhsach;
    private javax.swing.JTextField txtGDTC;
    private javax.swing.JTextField txtfind;
    private javax.swing.JTextField txthoten;
    private javax.swing.JTextField txtmasv;
    private javax.swing.JTextField txttienganh;
    private javax.swing.JTextField txttinhoc;
    // End of variables declaration//GEN-END:variables
}
