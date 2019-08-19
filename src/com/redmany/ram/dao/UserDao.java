package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/8.
 */
public class UserDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 检测CompanyId
     * @param companyid
     * @return
     */
    public String checkCompany(String companyid){
        int users_mobile = getMUser(companyid);
        int users_computer = getCUser(companyid);
        String companyStatus = getCompanyStatus(companyid,users_mobile,users_computer);
        return companyStatus;
    }

    /**
     * 获取 企业 电脑端用户数
     * @param CompanyId
     * @return
     */
    public int getCUser(String CompanyId) {
        int users_computer = 0;
        String sql = "SELECT count(*) from [user] WHERE status=0";
        Object result = sqlHelper.ExecScalar(CompanyId,sql,null);
        if(result != null){
            users_computer = (int)result;
        }
        return users_computer;
    }

    /**
     * 获取 企业 移动端用户数
     * @param CompanyId
     * @return
     */
    public int getMUser(String CompanyId) {
        int users_mobile = 0;
        String sql = "SELECT count(*) from [user] WHERE status=1";
        Object result = sqlHelper.ExecScalar(CompanyId,sql,null);
        if(result != null){
            users_mobile = (int)result;
        }
        return users_mobile;
    }

    /**
     * 获取企业状态
     * @param companyid
     * @param users_mobile
     * @param users_computer
     * @return
     */
    public String getCompanyStatus(String companyid, int users_mobile, int users_computer) {
        String companyStatus = "no";
        String sql = "SELECT expire_time,users_mobile,users_computer FROM [company_b] WHERE company_code=? and state=0";
        String[] parameters = {companyid};
        List<Map<String,Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList("redmany",sql,parameters);
        if(datas!=null&&datas.size()>0){
            Map<String,Object> map = datas.get(0);
            companyStatus = "exist";
            //企业过期时间毫秒数
            long date = Timestamp.valueOf(map.get("expire_time").toString()).getTime();
            //当前时间毫秒数
            long dateNow = System.currentTimeMillis();
            if(date < dateNow){
                //判断企业是否过期
                companyStatus = "expire";
            } else if (users_mobile > Integer.parseInt(map.get("users_mobile").toString())) {
                //判断企业移动端人数是否超出限制
                companyStatus = "muser_over";
            } else if (users_computer >  Integer.parseInt(map.get("users_computer").toString())) {
                //判断企业电脑端人数是否超出限制
                companyStatus = "cuser_over";
            }
        }
        return companyStatus;
    }

    /**
     * 登录获取用户信息
     * @param Company_Id
     * @param username
     * @param password
     * @return
     */
    public List<Map<String, Object>> login(String Company_Id,String username, String password){
        String sql = "SELECT * FROM [User] WHERE UserName=? AND UserPassword=?";
        String[] parameters = {username,password};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(Company_Id,sql,parameters);
        return datas;
    }

    /**
     * 登录获取用户信息 免密
     * @param Company_Id
     * @param username
     * @return
     */
    public List<Map<String, Object>> login(String Company_Id,String username){
        String sql = "SELECT * FROM [User] WHERE UserName=?";
        String[] parameters = {username};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(Company_Id,sql,parameters);
        return datas;
    }

    public Long register(String CompanyId,String username,String password,String DeptId, String RoleId){
        String sql = "insert into [User] (UserName,UserPassword,DeptId,RoleId) values (?,?,?,?)";
        String[] parameters = {username,password,DeptId,RoleId};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        Long Id = sqlHelper.ExecuteInsertGetId(CompanyId,sql,parameters);
        return Id;
    }

    /**
     * 获取用户一级菜单
     * @param CompanyId
     * @param roldId
     * @return
     */
    public List<Map<String,Object>> GetUserPanels(String CompanyId,String roldId){
        String sql = "select * from UserPanel as a where charindex(',"+roldId+",',','+cast(a.UserRole as varchar(100))+',')>0 order by sortindex";
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,null);
        return datas;
    }

    /**
     * 根据用户Id查询用户信息
     * @param CompanyId
     * @param Id
     * @return
     */
    public List<Map<String,Object>> getUserById(String CompanyId, String Id){
        String sql = "SELECT * FROM [User] WHERE Id=?";
        String[] parameters = {Id};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }

    /**
     * 根据用户openId查询用户信息
     * @param CompanyId
     * @param OpenId
     * @return
     */
    public Map<String,Object> getUserByOpenId(String CompanyId, String OpenId){
        String sql = "SELECT * FROM [User] WHERE OpenId=?";
        String[] parameters = {OpenId};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        Map<String,Object> map = null;
        if(datas.size()>0){
            map = datas.get(0);
        }
        return map;
    }

    /**
     * 根据用户信息获取菜单项
     * @param CompanyId
     * @param UserId
     * @param PanelId
     * @return
     */
    public List<Map<String,Object>> GetSysManageTree(String CompanyId,String UserId,String PanelId){
        String procedure = "{call GetMyMenu(?,?)}";
        String[] parameters = {UserId,PanelId};
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
        datas = sqlHelper.executeQueryProcedure(CompanyId,procedure,parameters);
        return datas;
    }

    public int bindWeChat(String CompanyId,String userid, String openid){
        int result = 0;
        String sql = "update [User] set OpenId=? where Id=?";
        String[] parameters = {openid,userid};
        result = sqlHelper.ExecuteNonQuery(CompanyId,sql,parameters);
        return result;
    }

    public Map<String,Object> getBalance(String CompanyId,String userId){
        String sql = "SELECT Balance FROM [User] WHERE Id=?";
        String[] parameters = {userId};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        Map<String,Object> map = null;
        if(datas.size()>0){
            map=datas.get(0);
        }
        return map;
    }
}
