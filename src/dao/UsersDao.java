/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.USERS;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author thanc
 */
public class UsersDao extends Database{
    public USERS dangnhap(String username, String password)
    {
       USERS us = null;
       try{
           String sql="select * from users where username=? and password=?";
           PreparedStatement pre = con.prepareStatement(sql);
           pre.setString(1, username);
           pre.setString(2, password);
           ResultSet rs = pre.executeQuery();
           if(rs.next()){
               us = new USERS();
               us.setUsername(rs.getString(1));
               us.setPassword(rs.getString(2));
               us.setRole(rs.getString(3));
           }
       }catch(Exception ex){
           ex.printStackTrace();
       }
       return us;
    }
    
}
