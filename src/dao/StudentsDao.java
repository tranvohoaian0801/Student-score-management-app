/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import model.STUDENTS;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
/**
 *
 * @author thanc
 */
public class StudentsDao extends Database{
    public Vector<STUDENTS> docStudents(){
        Vector<STUDENTS> vec = new Vector<STUDENTS>();
        try{
            String sql = "select * from Students";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                STUDENTS stu = new STUDENTS();
                stu.setMaSV(result.getString(1));
                stu.setHoten(result.getString(2));
                stu.setEmail(result.getString(3));
                stu.setSoDT(result.getString(4));
                stu.setGioitinh(result.getBoolean(5));
                stu.setDiachi(result.getString(6));
                stu.setHinh(result.getString(7));
                vec.add(stu);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return vec;
    }
    public int luuStudents(STUDENTS stu){
        try{
            String sql = "insert into students values(?,?,?,?,?,?,?)";
            PreparedStatement preStatement = con.prepareStatement(sql);
            preStatement.setString(1, stu.getMaSV());
            preStatement.setString(2, stu.getHoten());
            preStatement.setString(3, stu.getEmail());
            preStatement.setString(4, stu.getSoDT());
            preStatement.setBoolean(5, stu.isGioitinh());
            preStatement.setString(6, stu.getDiachi());
            preStatement.setString(7, stu.getHinh());
            return preStatement.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    public boolean xoaStudents(String maSV){
        try{
            String sql="delete from students where MaSV= ?";
            PreparedStatement  pstm = con.prepareStatement(sql);
            pstm.setString(1, maSV);
            return pstm.executeUpdate()>0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public int capnhatStudents(STUDENTS stu){
        try{
            String sql="update Students set Hoten=?, Email=?, SoDt=?, Gioitinh=?, Diachi=?, Hinh=? where MaSV=?";
            PreparedStatement preStatement = con.prepareStatement(sql);
            preStatement.setString(1, stu.getHoten());
            preStatement.setString(2, stu.getEmail());
            preStatement.setString(3, stu.getSoDT());
            preStatement.setBoolean(4, stu.isGioitinh());
            preStatement.setString(5, stu.getDiachi());
            preStatement.setString(6, stu.getHinh());
            preStatement.setString(7, stu.getMaSV());
            return preStatement.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
}
