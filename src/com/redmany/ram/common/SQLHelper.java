package com.redmany.ram.common;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/8.
 */
public class SQLHelper {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private CallableStatement callableStatement=null;

    public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String URL = "jdbc:sqlserver://120.78.15.108;DatabaseName=redmany_[CompanyId];useunicode=true;characterEncoding=UTF-8";
    public static final String USERNAME = "redmany2908";
    public static final String PASSWORD = "g_z38248269_";

    static{
        try{
            Class.forName(DRIVER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 执行带参数的查询语句
     * @param CompanyId 企业ID
     * @param sql 查询语句
     * @param parameters 参数
     * @return ResultSet 结果集
     */
    public ResultSet executeQuery(String CompanyId, String sql, String[] parameters){
        String url = URL.replace("[CompanyId]",CompanyId);
        rs= null;
        try {
            conn = DriverManager.getConnection(url,USERNAME,PASSWORD);
            ps =conn.prepareStatement(sql);
            if(parameters != null) {
                for (int i =0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs =ps.executeQuery();
            if(rs == null){
                return null;
            }
            return rs;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally{
            close(rs,ps,conn);
        }
    }

    /**
     * 返回数据集第一行第一列
     * @param CompanyId 企业ID
     * @param sql 查询语句
     * @param parameters 参数
     * @return Object 结果
     */
    public Object ExecScalar(String CompanyId, String sql, String[] parameters){
        String url = URL.replace("[CompanyId]",CompanyId);
        rs= null;
        try{
            conn = DriverManager.getConnection(url,USERNAME,PASSWORD);
            ps =conn.prepareStatement(sql);
            if(parameters != null) {
                for (int i =0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs =ps.executeQuery();
            if(rs == null){
                return null;
            }
            Object obj = null;
            if(rs.next()){
                obj = rs.getObject(1);
            }
            return obj;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            close(rs,ps,conn);
        }
    }

    public int ExecuteNonQuery(String CompanyId, String sql, String[] parameters){
        String url = URL.replace("[CompanyId]",CompanyId);
        int result = -1;
        try{
            conn = DriverManager.getConnection(url,USERNAME,PASSWORD);
            ps = conn.prepareStatement(sql);
            if(parameters!=null){
                for (int i =0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            result = ps.executeUpdate();
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            close(rs,ps,conn);
        }
    }

    /**
     * 执行插入返回当前插入行Id
     * @param CompanyId 企业ID
     * @param sql   插入语句
     * @param parameters 参数
     * @return 返回结果Id
     */
    public Long ExecuteInsertGetId(String CompanyId,String sql,String[] parameters){
        String url = URL.replace("[CompanyId]",CompanyId);
        rs = null;
        Long id = 0L;
        try{
            conn = DriverManager.getConnection(url,USERNAME,PASSWORD);
            ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            if(parameters != null) {
                for (int i =0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs == null){
                id = 0L;
            }else if(rs.next()){
                id = rs.getLong(1);
            }
            return id;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally{
            close(rs,ps,conn);
        }
    }

    /**
     * 执行带参数的查询语句
     * @param CompanyId 企业ID
     * @param sql 查询语句
     * @param parameters 参数
     * @return List<Map<String,Objecy>> 结果集合
     */
    public List<Map<String,Object>> executeQueryList(String CompanyId, String sql, String[] parameters){
        String url = URL.replace("[CompanyId]",CompanyId);
        rs= null;
        try {
            conn = DriverManager.getConnection(url,USERNAME,PASSWORD);
            ps =conn.prepareStatement(sql);
            if(parameters != null) {
                for (int i =0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs =ps.executeQuery();
            if(rs == null){
                return null;
            }
            ResultSetMetaData rsmd = ps.getMetaData();
            // 取得结果集列数
            int columnCount = rsmd.getColumnCount();
            // 构造泛型结果集
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            Map<String, Object> data = null;
            // 循环结果集
            while (rs.next()) {
                data = new HashMap<String, Object>();
                // 每循环一条将列名和列值存入Map
                for (int i = 1; i <= columnCount; i++) {
                    data.put(rsmd.getColumnLabel(i), rs.getObject(rsmd.getColumnLabel(i)));
                }
                // 将整条数据的Map存入到List中
                datas.add(data);
            }
            return datas;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally{
            close(rs,ps,conn);
        }
    }

    /**
     * 执行带参数的存储过程
     * @param CompanyId 企业ID
     * @param procedure 存储过程 Sql
     * @param parameters 参数集
     * @return
     */
    public List<Map<String,Object>> executeQueryProcedure(String CompanyId, String procedure, String[] parameters){
        String url = URL.replace("[CompanyId]",CompanyId);
        rs= null;
        try {
            conn = DriverManager.getConnection(url,USERNAME,PASSWORD);
            callableStatement = conn.prepareCall(procedure);
            if(parameters != null) {
                for (int i =0; i < parameters.length; i++) {
                    callableStatement.setString(i + 1, parameters[i]);
                }
            }
            rs = callableStatement.executeQuery();
            if(rs == null){
                return null;
            }
            ResultSetMetaData rsmd = callableStatement.getMetaData();
            // 取得结果集列数
            int columnCount = rsmd.getColumnCount();
            // 构造泛型结果集
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            Map<String, Object> data = null;
            // 循环结果集
            while (rs.next()) {
                data = new HashMap<String, Object>();
                // 每循环一条将列名和列值存入Map
                for (int i = 1; i <= columnCount; i++) {
                    data.put(rsmd.getColumnLabel(i), rs.getObject(rsmd
                            .getColumnLabel(i)));
                }
                // 将整条数据的Map存入到List中
                datas.add(data);
            }
            return datas;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            close(rs,callableStatement,conn);
        }
    }

    /**
     * 关闭释放
     * @param rs
     * @param ps
     * @param conn
     */
    public static void close(ResultSet rs, Statement ps, Connection conn){
        if (rs !=null)
            try {
                rs.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        rs =null;
        if (ps !=null)
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        ps =null;
        if (conn !=null)
            try {
                conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        conn =null;
    }

}
