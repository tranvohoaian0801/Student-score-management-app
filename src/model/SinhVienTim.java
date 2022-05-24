/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author dell
 */
public class SinhVienTim extends STUDENTS implements Serializable{
    private int vitri;

    public int getVitri() {
        return vitri;
    }

    public void setVitri(int vitri) {
        this.vitri = vitri;
    }

    public SinhVienTim() {
    }

    public SinhVienTim(int vitri) {
        this.vitri = vitri;
    }

    public SinhVienTim(int vitri, String maSV, String tenSV, String email, String soDT, boolean gioiTinh, String diaChi, String hinhAnh) {
        super(maSV, tenSV, email, soDT, gioiTinh, diaChi, hinhAnh);
        this.vitri = vitri;
    }

  

    
}

