/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import model.GRADE;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author thanc
 */
public class GradeDao extends Database{
    public ArrayList<GRADE> docdiem(){
        ArrayList<GRADE> dsdiem = new ArrayList<GRADE>();
        try{
            String sql="select * from grade";
            Statement statement=con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                GRADE gr = new GRADE();
                gr.setID(rs.getInt(1));
                gr.setMaSV(rs.getString(2));
                gr.setTienganh(rs.getInt(3));
                gr.setTinhoc(rs.getInt(4));
                gr.setGDTC(rs.getInt(5));
                dsdiem.add(gr);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return dsdiem;
    }
    public ArrayList<GRADE> timtheomasv(String masv){
        ArrayList<GRADE> dsdiem = new ArrayList<GRADE>();
        try{
            String sql="select * from grade where masv like N'"+masv+"'";
            Statement statement=con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                GRADE gr = new GRADE();
                gr.setID(rs.getInt(1));
                gr.setMaSV(rs.getString(2));
                gr.setTienganh(rs.getInt(3));
                gr.setTinhoc(rs.getInt(4));
                gr.setGDTC(rs.getInt(5));
                dsdiem.add(gr);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return dsdiem;
    }
    public int capnhatGrade(GRADE gr){
        try{
            String sql="update Grade set Tienganh=?, Tinhoc=?, GDTC=? where MaSV=?";
            PreparedStatement preStatement = con.prepareStatement(sql);
            preStatement.setString(1, gr.getTienganh()+"");
            preStatement.setString(2, gr.getTinhoc()+"");
            preStatement.setString(3, gr.getGDTC()+"");
            preStatement.setString(4, gr.getMaSV()+"");
            return preStatement.executeUpdate();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    public boolean xoaGrade(String maSV){
        try{
            String sql="delete from grade where MaSV= ?";
            PreparedStatement  pstm = con.prepareStatement(sql);
            pstm.setString(1, maSV);
            return pstm.executeUpdate()>0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public int luuGrade(GRADE gr){
        try{
            String sql = "insert into grade values(?,?,?,?)";
            PreparedStatement preStatement = con.prepareStatement(sql);
            preStatement.setString(1, gr.getMaSV());
            preStatement.setInt(2, gr.getTienganh());
            preStatement.setInt(3, gr.getTinhoc());
            preStatement.setInt(4, gr.getGDTC());
            return preStatement.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    public ArrayList<GRADE> doctop3(){
        ArrayList<GRADE> dsdiem = new ArrayList<GRADE>();
        try{
            String sql="select top 3 *,(Tienganh + Tinhoc + GDTC)/3 as DTB from grade order by DTB desc";
            Statement statement=con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                GRADE gr = new GRADE();
                gr.setID(rs.getInt(1));
                gr.setMaSV(rs.getString(2));
                gr.setTienganh(rs.getInt(3));
                gr.setTinhoc(rs.getInt(4));
                gr.setGDTC(rs.getInt(5));
                dsdiem.add(gr);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return dsdiem;
    }
}
