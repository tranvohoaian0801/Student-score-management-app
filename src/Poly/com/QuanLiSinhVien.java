/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poly.com;

import dao.StudentsDao;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.CheckMaill;
import model.SinhVienTim;
import model.STUDENTS;
import model.GRADE;
import model.USERS;

/**
 *
 * @author dell
 */
public class QuanLiSinhVien extends javax.swing.JFrame {

    /**
     * Creates new form QuanLiSinhVien
     */
   
    String strHinhAnh = null;
    String filename="";
    int row;
    ArrayList<STUDENTS> dssv = new ArrayList<>();
    public QuanLiSinhVien() {
        initComponents();
        setLocationRelativeTo(null);
        JTableHeader thead = tbldanhsach.getTableHeader();
        thead.setOpaque(false);
        filltotable();
        Disable();
    }
    public void showDetail(int i){
        StudentsDao studao = new StudentsDao();
        Vector<STUDENTS> vec = studao.docStudents();
        txtmasv.setText(vec.get(i).getMaSV());
        txthovaten.setText(vec.get(i).getHoten());
        txtemail.setText(vec.get(i).getEmail());
        txtsodt.setText(vec.get(i).getSoDT());
        boolean gt = vec.get(i).isGioitinh();
        if(gt){
            rdonam.setSelected(true);
        }else{
            rdonu.setSelected(true);
        }
        txtdchi.setText(vec.get(i).getDiachi());
        UpHinh(vec.get(i).getHinh());
        lblimage.setText("");
    }
     public void filltotable(){
        String header[] ={"MãSV","Họ tên","Email","Số ĐT","Giới tính","Địa chỉ","Hình"};
        StudentsDao studao = new StudentsDao();
        Vector<STUDENTS> vec = studao.docStudents();
        DefaultTableModel model = new DefaultTableModel(null, header);
        model.setRowCount(0);
        for(int i=0;i<vec.size();i++){
            String Masv = vec.get(i).getMaSV();
            String Hoten = vec.get(i).getHoten();
            String Email = vec.get(i).getEmail();
            String Sodt = vec.get(i).getSoDT();
            boolean gioitinh = vec.get(i).isGioitinh();
            String Diachi = vec.get(i).getDiachi();
            String Hinh = vec.get(i).getHinh();    
            Object[] row = new Object[]{Masv,Hoten,Email,Sodt,gioitinh,Diachi,Hinh};
            model.addRow(row);
        }
        tbldanhsach.setModel(model);
    }
      public void UpHinh(String hinh){
        ImageIcon image = new ImageIcon("src\\ASSIGNMENT\\images\\"+hinh);
        Image im=image.getImage();
        ImageIcon icon = new ImageIcon(im.getScaledInstance(lblimage.getWidth(), lblimage.getHeight(), im.SCALE_SMOOTH));
        lblimage.setIcon(icon);
         }
       public void resetForm(){
        txtmasv.setText("");
        txthovaten.setText("");
        txtemail.setText("");
        txtsodt.setText("");
        btngender.clearSelection();
        txtdchi.setText("");
        lblimage.setIcon(null);
    }
   
        public void LoadDataDBToTable()
        {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement("select * from STUDENTS");
            ResultSet rs = ps.executeQuery();
            dssv.clear();
               while (rs.next()) {                    
                    String masv = rs.getString(1);
                String hoten = rs.getString(2);
              String email = rs.getString(3);
                String sodt = rs.getString(4);
                 boolean gt = rs.getBoolean(5);
                String diachi = rs.getString(6);
                String hinh = rs.getString(7);
               STUDENTS sv = new STUDENTS(masv,hoten,email,sodt,gt,diachi,hinh);
                dssv.add(sv);
                }
                  DefaultTableModel model = (DefaultTableModel) tbldanhsach.getModel();
            model.setRowCount(0);
                for (STUDENTS sv : dssv) {
                    Object[] row = new Object[]{sv.getMaSV(), sv.getHoten(),
                    sv.getEmail(),sv.getSoDT(),sv.isGioitinh(),sv.getDiachi(),sv.getHinh()};
                model.addRow(row);
                }
                
            
           } catch (Exception e)
            {
                e.printStackTrace();
            }
       }
        public int save(){
             try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement("insert into STUDENTS values (?,?,?,?,?,?,?)");
            ps.setString(1,txtmasv.getText());
            ps.setString(2,txthovaten.getText());
            ps.setString(3,txtemail.getText());
            ps.setString(4,txtsodt.getText());
            ps.setBoolean(5,(rdonam.isSelected()== true) ? true : false);
            ps.setString(6,txtdchi.getText());
            ps.setString(7,lblimage.getText());
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(this,"Thêm thành công!!");
                return 1;
            }
            ps.close();
            conn.close();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
        }
        public int update()
        {
             try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement("update STUDENTS set Hoten=?, Email=?, SoDT=?, Gioitinh=?, DiaChi=?, Hinh=? where MaSV=?");
            ps.setString(7,txtmasv.getText());
            ps.setString(1,txthovaten.getText());
            ps.setString(2,txtemail.getText());
            ps.setString(3,txtsodt.getText());
            ps.setBoolean(4,(rdonam.isSelected()== true) ? true : false);
            ps.setString(5,txtdchi.getText());
            ps.setString(6,lblimage.getText());
          
             if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(this,"UPdate thành công!!");
                return 1;
            }
             reset();
             Disable();
            ps.close();
            conn.close();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
  
        }
        public int delete(){
             try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName = PS15465_ASM_JAVA3";
            Connection conn = DriverManager.getConnection(url, "sa", "123456");
            PreparedStatement ps = conn.prepareStatement("delete STUDENTS where MaSV = ? ");
            ps.setString(1,txtmasv.getText());
           
            if(ps.executeUpdate()>0)
            {
                JOptionPane.showMessageDialog(this,"Xóa thành công!!");
                return 1;
            }
            ps.close();
            conn.close();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
        }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngender = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtmasv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txthovaten = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        txtsodt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rdonam = new javax.swing.JRadioButton();
        rdonu = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtdchi = new javax.swing.JTextArea();
        lblimage = new javax.swing.JLabel();
        btnnew = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbldanhsach = new javax.swing.JTable();
        btnlui = new javax.swing.JButton();
        btntoi = new javax.swing.JButton();
        btnfirst = new javax.swing.JButton();
        btnlast = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("QUẢN LÍ SINH VIÊN");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("MASV :");

        txtmasv.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("HỌ VÀ TÊN :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("EMAIL : ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("SỐ ĐT : ");

        txthovaten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtemail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtsodt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("GIỚI TÍNH :");

        btngender.add(rdonam);
        rdonam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdonam.setSelected(true);
        rdonam.setText("Male");
        rdonam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdonamActionPerformed(evt);
            }
        });

        btngender.add(rdonu);
        rdonu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdonu.setText("FeMale");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("ĐỊA CHỈ : ");

        txtdchi.setColumns(20);
        txtdchi.setRows(5);
        jScrollPane1.setViewportView(txtdchi);

        lblimage.setText("Image");
        lblimage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblimage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblimageMouseClicked(evt);
            }
        });

        btnnew.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/New-file-icon.png"))); // NOI18N
        btnnew.setText("NEW");
        btnnew.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btnsave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save-icon.png"))); // NOI18N
        btnsave.setText("SAVE");
        btnsave.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btndelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Actions-window-close-icon.png"))); // NOI18N
        btndelete.setText("DELETE");
        btndelete.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/edit-validated-icon.png"))); // NOI18N
        btnupdate.setText("UPDATE");
        btnupdate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        tbldanhsach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "MaSV", "Họ và Tên", "Email", "Số ĐT", "Giới Tính", "Địa Chỉ", "Hình Ảnh"
            }
        ));
        tbldanhsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldanhsachMouseClicked(evt);
            }
        });
        tbldanhsach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbldanhsachKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbldanhsach);

        btnlui.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnlui.setText("<");
        btnlui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluiActionPerformed(evt);
            }
        });

        btntoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btntoi.setText(">");
        btntoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntoiActionPerformed(evt);
            }
        });

        btnfirst.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnfirst.setText("<<");
        btnfirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfirstActionPerformed(evt);
            }
        });

        btnlast.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnlast.setText(">>");
        btnlast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(189, 189, 189))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txthovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtmasv, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel7))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(rdonam, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(16, 16, 16)
                                                    .addComponent(rdonu, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(txtsodt, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btndelete, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                        .addComponent(btnnew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btnfirst)
                        .addGap(26, 26, 26)
                        .addComponent(btnlui)
                        .addGap(26, 26, 26)
                        .addComponent(btntoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnlast)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtmasv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txthovaten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtsodt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rdonam)
                    .addComponent(rdonu)
                    .addComponent(btnnew, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntoi)
                    .addComponent(btnlui)
                    .addComponent(btnfirst)
                    .addComponent(btnlast))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        STUDENTS stu = new STUDENTS();
        StudentsDao stuDao = new StudentsDao();
        Vector<STUDENTS> vec = stuDao.docStudents();
        stu.setMaSV(txtmasv.getText());
        stu.setHoten(txthovaten.getText());
        stu.setEmail(txtemail.getText());
        stu.setSoDT(txtsodt.getText());
        if(rdonam.isSelected()){
            stu.setGioitinh(true);
        }else{
            stu.setGioitinh(false);
        }
        stu.setDiachi(txtdchi.getText());
        if(filename.length()==0){
            for(int i=0;i<vec.size();i++){
                if(vec.get(i).getMaSV().trim().equalsIgnoreCase(txtmasv.getText().trim())){
                    filename = vec.get(i).getHinh();
                }
            }
        }
        stu.setHinh(filename);
        lblimage.setText("");
        if(stuDao.capnhatStudents(stu)>0){
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            UpHinh(filename);
            filltotable();
        }else{
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại, không được sửa MaSV");
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void lblimageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblimageMouseClicked
        //try {
          //  JFileChooser jfc = new JFileChooser("D:\\JAVA3\\ASM_Java3\\src\\asm_java3\\image_java");
          // jfc.showOpenDialog(null);
            //File file = jfc.getSelectedFile();
            //Image img = ImageIO.read(file);
            //strHinhAnh = file.getName();
            //lblimage.setText("");
            //int width = lblimage.getWidth();
           // int height = lblimage.getHeight();
            //lblimage.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
        //} catch (IOException ex) {
          // System.out.println("Error :" + ex.toString());
       //}
       
       
       //JFileChooser jfc = new JFileChooser("D:\\JAVA3\\mavenproject1\\src\\main\\resources");
       //int result = jfc.showOpenDialog(null);
       //if(result == JFileChooser.APPROVE_OPTION)
       //{
         //  try {
           //    lblimage.setText(""+jfc.getSelectedFile().getName());
          // } catch (Exception e) {
            //   e.printStackTrace();
        //   }
      // }
      JFileChooser fchooser = new JFileChooser();
        int result = fchooser.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File file = fchooser.getSelectedFile();
            String fullPath = file.getAbsolutePath();
            filename=fchooser.getSelectedFile().getName();
            UpHinh(fullPath);
            try{
            Path src = Paths.get(fullPath);
            Path dest = Paths.get("src\\ASSIGNMENT\\images\\"+filename);
            Files.copy(src, dest,StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException ex){
                System.out.println(ex.toString());
            }
        }
       
     
    }//GEN-LAST:event_lblimageMouseClicked
    public void reset() {
        txtmasv.setText("");
        txthovaten.setText("");
        txtemail.setText("");
        rdonam.isSelected();
        rdonu.isSelected();
        txtsodt.setText("");
        txtdchi.setText("");
        lblimage.setIcon(null);
        strHinhAnh = null;
    }
    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed

        reset();
        Enable();
    }//GEN-LAST:event_btnnewActionPerformed
   
    
    public void EnableUpdate() {
        txtmasv.disable();
        txthovaten.setEnabled(true);
        txtdchi.setEnabled(true);
        txtsodt.setEnabled(true);
        txtemail.setEnabled(true);
        rdonam.setEnabled(true);
        rdonu.setEnabled(true);
        lblimage.setEnabled(true);
        btnsave.setEnabled(true);
    }

    public void Enable() {
        txtmasv.setEnabled(true);
        txthovaten.setEnabled(true);
        txtdchi.setEnabled(true);
        txtsodt.setEnabled(true);
        txtemail.setEnabled(true);
        rdonam.setEnabled(true);
        rdonu.setEnabled(true);
        lblimage.setEnabled(true);
        btnsave.setEnabled(true);
    }

    public void Disable() {
        txtmasv.disable();
        txthovaten.disable();
        txtdchi.disable();
        txtsodt.disable();
        txtemail.disable();
        rdonam.setEnabled(false);
        rdonu.setEnabled(false);
        lblimage.setEnabled(false);
        btnsave.setEnabled(false);
    }

   //public void LoadArrayListToTable() {

      //  DefaultTableModel model = (DefaultTableModel) tbldanhsach.getModel();
      //  model.setRowCount(0);
       // for (SinhVien sv : dssv) {

        //    model.addRow(new Object[]{sv.getMaSV(), sv.getTenSV(), sv.getEmail(),
         //       sv.getSoDT(), sv.isGioiTinh(), sv.getDiaChi(), sv.getHinhAnh()});

       // }
  //  }

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        STUDENTS stu = new STUDENTS();
        stu.setMaSV(txtmasv.getText());
        stu.setHoten(txthovaten.getText());
        stu.setEmail(txtemail.getText());
        stu.setSoDT(txtsodt.getText());
        if(rdonam.isSelected()){
            stu.setGioitinh(true);
        }else{
            stu.setGioitinh(false);
        }
        stu.setDiachi(txtdchi.getText());
        stu.setHinh(filename.trim());
        lblimage.setText("");
        StudentsDao studao = new StudentsDao();
        if(studao.luuStudents(stu)>0){
            JOptionPane.showMessageDialog(null, "Lưu thành công");
            UpHinh(filename);
            filltotable();
        }else{
            JOptionPane.showMessageDialog(null, "Lưu thất bại");
        }


    }//GEN-LAST:event_btnsaveActionPerformed
    int c = 0;
    int vitri = -1;
    ArrayList<SinhVienTim> NVTim = new ArrayList<>();

   public void LoadDataToControl(int vitri) {
        txtmasv.setText(dssv.get(vitri).getMaSV());
        txthovaten.setText(dssv.get(vitri).getHoten());
       txtemail.setText(dssv.get(vitri).getEmail());
      txtsodt.setText(String.valueOf(dssv.get(vitri).getSoDT()));
        txtdchi.setText(dssv.get(vitri).getDiachi());
       lblimage.setText(dssv.get(vitri).getHinh());
   }
    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed

       int index = tbldanhsach.getSelectedRow();
        if(index == -1){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sinh viên trong bảng để xóa",
                    "Thông báo",1);
            return;
        }
        StudentsDao studao = new StudentsDao();
        int tk = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?");
        if(tk==JOptionPane.YES_OPTION){
            if(studao.xoaStudents(tbldanhsach.getValueAt(index, 0).toString())){
                JOptionPane.showMessageDialog(this, "Xóa Sinh Viên thành công", "Thông báo",1);
                filltotable();
                resetForm();
            }else{
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống", "Thông báo",0);
            }
        }
        else{
            return;
        }

    }//GEN-LAST:event_btndeleteActionPerformed

    private void tbldanhsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldanhsachMouseClicked
        // TODO add your handling code here:
        row = tbldanhsach.getSelectedRow();
        showDetail(row);
    }//GEN-LAST:event_tbldanhsachMouseClicked

    private void tbldanhsachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbldanhsachKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tbldanhsachKeyReleased

    private void btntoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntoiActionPerformed
        if (vitri < dssv.size() - 1) {
            vitri = vitri + 1;
            LoadDataToControl(vitri);
            tbldanhsach.setRowSelectionInterval(vitri, vitri);
        }
    }//GEN-LAST:event_btntoiActionPerformed

    private void btnluiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluiActionPerformed
        if (vitri > 0) {
            vitri = vitri - 1;
            LoadDataToControl(vitri);
            tbldanhsach.setRowSelectionInterval(vitri, vitri);
        }
    }//GEN-LAST:event_btnluiActionPerformed

    private void btnfirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfirstActionPerformed
        // TODO add your handling code here:
        vitri = 0;
        LoadDataToControl(vitri);
        tbldanhsach.setRowSelectionInterval(vitri, vitri);
    }//GEN-LAST:event_btnfirstActionPerformed

    private void btnlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlastActionPerformed
        // TODO add your handling code here:
        vitri = dssv.size() - 1;
        LoadDataToControl(vitri);
        tbldanhsach.setRowSelectionInterval(vitri, vitri);
    }//GEN-LAST:event_btnlastActionPerformed

    private void rdonamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdonamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdonamActionPerformed
   // public void LoadData() {
     //   SinhVien sv = new SinhVien("SV1", "Trần Võ Hoài Ân", "hoaian@gmail.com", "0867116790", true, "Ninh Hà ", "D:\\JAVA3\\ASM_Java3\\src\\asm_java3\\image_java\\trai1.jpg");
      //  SinhVien sv1 = new SinhVien("SV2", "Nguyễn Hồng Duyên", "hieu@gmail.com", "0897445610", false, "Ninh Hưng", "D:\\JAVA3\\ASM_Java3\\src\\asm_java3\\image_java\\gai1.jpg");
     //   SinhVien sv2 = new SinhVien("SV3", "Trần Ngọc Tiến", "tien@gmail.com", "0567456120", true, "Hậu Phước", "D:\\JAVA3\\ASM_Java3\\src\\asm_java3\\image_java\\trai2.jpg");
      //  SinhVien sv3 = new SinhVien("SV4", "Nguyễn Văn Tèo", "teo@gmail.com", "0358158382", true, "Thuận Lợi", "D:\\JAVA3\\ASM_Java3\\src\\asm_java3\\image_java\\trai3.jpg");
      //  dssv.add(sv);
      //  dssv.add(sv1);
       // dssv.add(sv2);
       // dssv.add(sv3);
      // dssv.add(sv4);
  //  }
       
        
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
            java.util.logging.Logger.getLogger(QuanLiSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLiSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLiSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLiSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.ButtonGroup btngender;
    private javax.swing.JButton btnlast;
    private javax.swing.JButton btnlui;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btntoi;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblimage;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JTable tbldanhsach;
    private javax.swing.JTextArea txtdchi;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txthovaten;
    private javax.swing.JTextField txtmasv;
    private javax.swing.JTextField txtsodt;
    // End of variables declaration//GEN-END:variables
}
