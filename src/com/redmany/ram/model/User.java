package com.redmany.ram.model;

import java.io.Serializable;

/**
 * Created by hy on 2017/6/8.
 * User[用户] 表 实体类
 */
public class User implements Serializable {
    private Integer Id;
    private String UserName;
    private String UserPassword;
    private String companyId;
    private Integer DeptId;
    private String xmpp;
    private String email;
    private String postion;
    private String officephone;
    private String createtime;
    private String masterid;
    private String birthdate;
    private String sex;
    private String msn;
    private Integer roleid;
    private String qq;
    private String sip;
    private String realname;
    private String imin;
    private String mobile;
    private Integer state;
    private String apnusername;
    private String lgtime;
    private Integer status;
    private String handPic;
    private String deviceToken;
    private String deviceType;
    private Integer longilati;
    private String pcapnUserName;
    private Integer area;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getDeptId() {
        return DeptId;
    }

    public void setDeptId(Integer deptId) {
        DeptId = deptId;
    }

    public String getXmpp() {
        return xmpp;
    }

    public void setXmpp(String xmpp) {
        this.xmpp = xmpp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public String getOfficephone() {
        return officephone;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMasterid() {
        return masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getImin() {
        return imin;
    }

    public void setImin(String imin) {
        this.imin = imin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getApnusername() {
        return apnusername;
    }

    public void setApnusername(String apnusername) {
        this.apnusername = apnusername;
    }

    public String getLgtime() {
        return lgtime;
    }

    public void setLgtime(String lgtime) {
        this.lgtime = lgtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHandPic() {
        return handPic;
    }

    public void setHandPic(String handPic) {
        this.handPic = handPic;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getLongilati() {
        return longilati;
    }

    public void setLongilati(Integer longilati) {
        this.longilati = longilati;
    }

    public String getPcapnUserName() {
        return pcapnUserName;
    }

    public void setPcapnUserName(String pcapnUserName) {
        this.pcapnUserName = pcapnUserName;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

}
