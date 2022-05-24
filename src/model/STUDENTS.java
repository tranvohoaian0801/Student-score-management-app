/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author thanc
 */
public class STUDENTS {
    private String MaSV;
    private String Hoten;
    private String Email;
    private String SoDT;
    private boolean Gioitinh;
    private String Diachi;
    private String Hinh;

    public STUDENTS(String MaSV, String Hoten, String Email, String SoDT, boolean Gioitinh, String Diachi, String Hinh) {
        this.MaSV = MaSV;
        this.Hoten = Hoten;
        this.Email = Email;
        this.SoDT = SoDT;
        this.Gioitinh = Gioitinh;
        this.Diachi = Diachi;
        this.Hinh = Hinh;
    }

    public STUDENTS() {
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public void setHoten(String Hoten) {
        this.Hoten = Hoten;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }

    public void setGioitinh(boolean Gioitinh) {
        this.Gioitinh = Gioitinh;
    }

    public void setDiachi(String Diachi) {
        this.Diachi = Diachi;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getMaSV() {
        return MaSV;
    }

    public String getHoten() {
        return Hoten;
    }

    public String getEmail() {
        return Email;
    }

    public String getSoDT() {
        return SoDT;
    }

    public boolean isGioitinh() {
        return Gioitinh;
    }

    public String getDiachi() {
        return Diachi;
    }

    public String getHinh() {
        return Hinh;
    }
    
}
