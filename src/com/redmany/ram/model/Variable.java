package com.redmany.ram.model;

import java.io.Serializable;

/**
 * Created by hy on 2017/6/23.
 * 全局变量
 */
public class Variable implements Serializable{

    //配置默认参数 企业ID
    private static String CompanyId = "bzService";

    private static String Identification = "";

    //配置默认参数 用于区分设备 手机：MOBILE  电脑：COMPUTER
    private static String UserAgent = "MOBILE";

    //配置默认参数 获取网络图片路径
    private static String ImageHttp = "http://120.78.15.108:50003/document/";

    //获取系统参数的roleId
    private static String RoleId = "112";

    //键值对需要存入全局的标志
    public static final String globalFormNameStart = "globalVariable:";
    //需要从全局获取值得键前缀
    public static final String cachePrefix = "fromGlobal_";


    public static String getCompanyId() {
        return CompanyId;
    }

    public static void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public static String getIdentification() {
        return Identification;
    }

    public static void setIdentification(String identification) {
        Identification = identification;
    }

    public static String getUserAgent() {
        return UserAgent;
    }

    public static void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public static String getImageHttp() {
        return ImageHttp;
    }

    public static void setImageHttp(String imageHttp) {
        ImageHttp = imageHttp;
    }

    public static String getRoleId() {
        return RoleId;
    }

    public static void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public static String getGlobalFormNameStart() {
        return globalFormNameStart;
    }

    public static String getCachePrefix() {
        return cachePrefix;
    }
}
