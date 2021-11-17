/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.hepper;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import MODEL.NhanVien;

/**
 *
 * @author Admin
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String> {
    
    String select_update = "update NhanVien set HoTen =? , DIENTHOAI =? , VAITRO =? where MANV =?";
    String select_insert = "insert into NHANVIEN (MANV , HOTEN , DIENTHOAI, VAITRO) values (?,?,?,?)";
    String delete_sql = "delete from NHANVIEN where MANV =?";
    String SELECT_ALL_SQL = "select * from NHANVIEN";
    String SELECT_BY_ID_SQL = "select * from NHANVIEN where MANV = ?";
    
    public void updatemk(NhanVien entity) {
	String sql = "UPDATE NHANVIEN set MATKHAU =? where MANV =?";
	hepper.update(sql,
		entity.getMATKHAU(),
		entity.getMANV());
    }
    
    @Override
    public void insert(NhanVien entity) {
	try {
	    hepper.update(select_insert,
		    entity.getMANV(),
		    entity.getHOTEN(),
		    entity.getDIENTHOAI(),
		    entity.isVAITRO());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    @Override
    public void update(NhanVien entity) {
	hepper.update(select_update,
		entity.getHOTEN(),
		entity.getDIENTHOAI(),
		entity.isVAITRO(),
		entity.getMANV());
	
    }
    
    @Override
    public void delete(String id) {
	hepper.update(delete_sql, id);
    }
    
    @Override
    public List<NhanVien> selectAll() {
	return this.selectBySQL(SELECT_ALL_SQL);
    }
    
    @Override
    List<NhanVien> selectBySQL(String sql, Object... args) {
	List<NhanVien> list = new ArrayList<>();
	try {
	    ResultSet rs = hepper.query(sql, args);
	    while (rs.next()) {
		NhanVien nv = new NhanVien();
		nv.setMANV(rs.getString("MANV"));
		nv.setHOTEN(rs.getString("HOTEN"));
		nv.setGIOITINH(rs.getBoolean("GIOITINH"));
		nv.setDIENTHOAI(rs.getString("DIENTHOAI"));
		nv.setEMAIL(rs.getString("EMAIL"));
		nv.setMATKHAU(rs.getString("MATKHAU"));
		nv.setVAITRO(rs.getBoolean("VAITRO"));
		list.add(nv);
	    }
	    rs.getStatement().getConnection().close();
	    return list;
	} catch (Exception e) {
	    throw new RuntimeException();
	}
    }
    
    @Override
    public NhanVien selectByID(String id) {
	List<NhanVien> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }
        public List<NhanVien> selectByKeyword(String keyword) {
       String sql ="select * from NHANVIEN where MANV like N ? ";
        return this.selectBySQL(sql, "%" + keyword + "%");
    }
}
