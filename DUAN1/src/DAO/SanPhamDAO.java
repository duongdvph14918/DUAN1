/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MODEL.SanPham;
import Utils.hepper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SanPhamDAO extends EduSysDAO<SanPham, String> {

    String select_all_sql = "select * from SANPHAM";
    String delete_sql = "delete from SANPHAM where MASP =?";
    String update_sql = "update SANPHAM set TENSP =? , MALH =? , HINH =? , MANSX =? ,MANHAPHANPHOI =? , SOLUONG =? , DONGIA =? where MASP =?";
    String insert_sql = "insert into SANPHAM (MASP , TENSP  , MALH  , HINH  , MANSX  ,MANHAPHANPHOI  , SOLUONG  , DONGIA  ) values (?,?,?,?,?,?,?,?)";
    String selecct_byid = "select * from SANPHAM where MASP =?";

    @Override
    public void insert(SanPham entity) {
	try {
	    hepper.update(insert_sql,
		    entity.getMASP(),
		    entity.getTENSP(),
		    entity.getMALH(),
		    entity.getHINH(),
		    entity.getMANSX(),
		    entity.getMANHAPHANPHOI(),
		    entity.getSOLUONG(),
		    entity.getDONGIA()
	    );
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void update(SanPham entity) {
	try {
	    hepper.update(update_sql, entity.getTENSP(),
		    entity.getMALH(),
		    entity.getHINH(),
		    entity.getMANSX(),
		    entity.getMANHAPHANPHOI(),
		    entity.getSOLUONG(),
		    entity.getDONGIA(),
		    entity.getMASP());
	} catch (Exception e) {
	}
    }

    @Override
    public void delete(String key) {
	hepper.update(delete_sql, key);
    }

    @Override
    public List<SanPham> selectAll() {
	return this.selectBySQL(select_all_sql);
    }

    @Override
    public SanPham selectByID(String key) {
	List<SanPham> list = selectBySQL(selecct_byid, key);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    List<SanPham> selectBySQL(String sql, Object... args) {
	List<SanPham> list = new ArrayList<>();
	try {
	    ResultSet rs = hepper.query(sql, args);
	    while (rs.next()) {
		SanPham sp = new SanPham();
		sp.setMASP(rs.getString("MASP"));
		sp.setTENSP(rs.getString("TENSP"));
		sp.setMALH(rs.getString("MALH"));
		sp.setHINH(rs.getString("HINH"));
		sp.setMANSX(rs.getString("MANSX"));
		sp.setMANHAPHANPHOI(rs.getString("MANHAPHANPHOI"));
		sp.setSOLUONG(rs.getInt("SOLUONG"));
		sp.setDONGIA(rs.getDouble("DONGIA"));
		list.add(sp);
	    }
	    rs.getStatement().getConnection().close();
	    return list;
	} catch (Exception e) {
	    throw new RuntimeException();
	}
    }

    public List<SanPham> bykey(String key) {
	String sql = "select * from SANPHAM where MASP like ?";
	return this.selectBySQL(sql, "%" + key + "%");
    }
}
