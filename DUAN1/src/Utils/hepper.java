/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class hepper {
 static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dburl="jdbc:sqlserver://localhost:1433;databaseName=DUAN1";
    static String user = "sa";
    static String pass = "1";
    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getStmt(String sql, Object...args)throws SQLException{
        Connection conn = DriverManager.getConnection(dburl, user, pass);
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt = conn.prepareCall(sql); //PROC
        }else{
            stmt = conn.prepareStatement(sql);//SQL
        }
        for(int i =0;i<args.length;i++){
            stmt.setObject(i+1, args[i]);
        }
        return stmt;
    }
    
//    public static int update(String sql,Object...args)throws SQLException{
//        try {
//            PreparedStatement stmt = jdbcHelper.getStmt(sql, args);
//            try {
//                return stmt.executeUpdate();
//            }finally{
//                stmt.getConnection().close();
//            }}
//            catch (Exception e) {
//                throw new RuntimeException(e);               
//            }
//    }
    
        public static void update(String sql,Object...args){
        try {
            PreparedStatement pstmt= getStmt(sql, args);
            try{
                pstmt.executeUpdate();
            }finally{
                pstmt.getConnection().close();            //đóng Connection từ statement
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static ResultSet query(String sql,Object...args)throws SQLException{
        PreparedStatement stmt = hepper.getStmt(sql, args);
        return stmt.executeQuery();
    }
    
    public static Object value(String sql,Object...args){
        try {
            ResultSet rs = hepper.query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
                rs.getStatement().getConnection().close();
                return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
