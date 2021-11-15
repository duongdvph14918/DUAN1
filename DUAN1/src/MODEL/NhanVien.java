/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

/**
 *
 * @author Admin
 */
public class NhanVien {
    private String MANV;
     private String HOTEN;
    private boolean GIOITINH ;
    private String DIENTHOAI;
     private boolean VAITRO = false ;
    private String EMAIL;
    private String MATKHAU;
   

    public NhanVien() {
    }

    public NhanVien(String MANV, String HOTEN, boolean GIOITINH, String DIENTHOAI, String EMAIL, String MATKHAU, boolean VAITRO) {
	this.MANV = MANV;
	this.HOTEN = HOTEN;
	this.GIOITINH = GIOITINH;
	this.DIENTHOAI = DIENTHOAI;
	this.EMAIL = EMAIL;
	this.MATKHAU = MATKHAU;
	this.VAITRO = VAITRO;
    }

    public String getMANV() {
	return MANV;
    }

    public void setMANV(String MANV) {
	this.MANV = MANV;
    }

    public String getHOTEN() {
	return HOTEN;
    }

    public void setHOTEN(String HOTEN) {
	this.HOTEN = HOTEN;
    }

    public boolean isGIOITINH() {
	return GIOITINH;
    }

    public void setGIOITINH(boolean GIOITINH) {
	this.GIOITINH = GIOITINH;
    }

    public String getDIENTHOAI() {
	return DIENTHOAI;
    }

    public void setDIENTHOAI(String DIENTHOAI) {
	this.DIENTHOAI = DIENTHOAI;
    }

    public String getEMAIL() {
	return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
	this.EMAIL = EMAIL;
    }

    public String getMATKHAU() {
	return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
	this.MATKHAU = MATKHAU;
    }

    public boolean isVAITRO() {
	return VAITRO;
    }

    public void setVAITRO(boolean VAITRO) {
	this.VAITRO = VAITRO;
    }

   
}
