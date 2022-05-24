/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dell
 */
public class CheckMaill {
    public static void parseEmail(String email) throws Exception{
        String mau="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(mau);
        Matcher matcher = pattern.matcher(email);
        
        if(matcher.matches()==false){
            throw new Exception();
        }
        
    }

}
