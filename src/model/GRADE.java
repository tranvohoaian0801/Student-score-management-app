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
public class GRADE {
    private int ID;
    private String MaSV;
    private int Tienganh;
    private int Tinhoc;
    private int GDTC;

    public GRADE(int ID, String MaSV, int Tienganh, int Tinhoc, int GDTC) {
        this.ID = ID;
        this.MaSV = MaSV;
        this.Tienganh = Tienganh;
        this.Tinhoc = Tinhoc;
        this.GDTC = GDTC;
    }

    public GRADE() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public void setTienganh(int Tienganh) {
        this.Tienganh = Tienganh;
    }

    public void setTinhoc(int Tinhoc) {
        this.Tinhoc = Tinhoc;
    }

    public void setGDTC(int GDTC) {
        this.GDTC = GDTC;
    }

    public int getID() {
        return ID;
    }

    public String getMaSV() {
        return MaSV;
    }

    public int getTienganh() {
        return Tienganh;
    }

    public int getTinhoc() {
        return Tinhoc;
    }

    public int getGDTC() {
        return GDTC;
    }
}
