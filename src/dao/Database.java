/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author thanc
 */
public class Database 
    { 
    protected Connection con=null;
    public Database(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionurl = "jdbc:sqlserver://localhost:1433;DatabaseName=PS15465_ASM_JAVA3;";
            con = DriverManager.getConnection(connectionurl,"sa","123456");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

