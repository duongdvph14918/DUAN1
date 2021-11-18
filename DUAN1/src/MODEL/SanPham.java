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
public class SanPham {
   private String MASP;
      private String TENSP;
      private String MALH ;
      private String HINH;
      private String MANSX;
      private String MANHAPHANPHOI ;
      private int  SOLUONG ;
       private double DONGIA;

    public SanPham() {
    }

    public SanPham(String MASP, String TENSP, String MALH, String HINH, String MANSX, String MANHAPHANPHOI, int SOLUONG, double DONGIA) {
	this.MASP = MASP;
	this.TENSP = TENSP;
	this.MALH = MALH;
	this.HINH = HINH;
	this.MANSX = MANSX;
	this.MANHAPHANPHOI = MANHAPHANPHOI;
	this.SOLUONG = SOLUONG;
	this.DONGIA = DONGIA;
    }

    public String getMASP() {
	return MASP;
    }

    public void setMASP(String MASP) {
	this.MASP = MASP;
    }

    public String getTENSP() {
	return TENSP;
    }

    public void setTENSP(String TENSP) {
	this.TENSP = TENSP;
    }

    public String getMALH() {
	return MALH;
    }

    public void setMALH(String MALH) {
	this.MALH = MALH;
    }

    public String getHINH() {
	return HINH;
    }

    public void setHINH(String HINH) {
	this.HINH = HINH;
    }

    public String getMANSX() {
	return MANSX;
    }

    public void setMANSX(String MANSX) {
	this.MANSX = MANSX;
    }

    public String getMANHAPHANPHOI() {
	return MANHAPHANPHOI;
    }

    public void setMANHAPHANPHOI(String MANHAPHANPHOI) {
	this.MANHAPHANPHOI = MANHAPHANPHOI;
    }

    public int getSOLUONG() {
	return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
	this.SOLUONG = SOLUONG;
    }

    public double getDONGIA() {
	return DONGIA;
    }

    public void setDONGIA(double DONGIA) {
	this.DONGIA = DONGIA;
    }

    public SanPham(String MANSX) {
	this.MANSX = MANSX;
    }
       
}
