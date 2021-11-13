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

    }

    @Override
    public void update(NhanVien entity) {

    }

    @Override
    public void delete(String key) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
