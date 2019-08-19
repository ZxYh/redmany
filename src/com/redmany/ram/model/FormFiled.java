package com.redmany.ram.model;

import java.io.Serializable;

/**
 * Created by hy on 2017/6/9.
 * FormFiled[表单项] 表 实体类
 */
public class FormFiled implements Serializable {
    private Integer Id;
    private String FormName;
    private String Name;
    private String Title;
    private String Type;
    private String ShowType;
    private String reportShowType;
    private String ValidateExpreesion;
    private String ValidateErrorMessage;
    private String ValidateGroup;
    private String data_replacer;
    private String data_source;
    private String optioner;
    private String Database_field;
    private String Database_field_type;
    private Integer AutoPostback;
    private String StatisticsField;
    private Integer Index_number;
    private String IsNull;
    private Integer publish;
    private String defaultContent;
    private String jsmethod;
    private Integer clickbutton;
    private String clickmethod;
    private String image;
    private Integer attributeId;
    private String wapAttribute;
    private String target;
    private Integer isDataItem;
    private String transferParams;
    private String listAttributeId;
    private String showState;
    private String showPage;

    private Boolean isReadOnly;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFormName() {
        return FormName;
    }

    public void setFormName(String formName) {
        FormName = formName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getShowType() {
        return ShowType;
    }

    public void setShowType(String showType) {
        ShowType = showType;
    }

    public String getReportShowType() {
        return reportShowType;
    }

    public void setReportShowType(String reportShowType) {
        this.reportShowType = reportShowType;
    }

    public String getValidateExpreesion() {
        return ValidateExpreesion;
    }

    public void setValidateExpreesion(String validateExpreesion) {
        ValidateExpreesion = validateExpreesion;
    }

    public String getValidateErrorMessage() {
        return ValidateErrorMessage;
    }

    public void setValidateErrorMessage(String validateErrorMessage) {
        ValidateErrorMessage = validateErrorMessage;
    }

    public String getValidateGroup() {
        return ValidateGroup;
    }

    public void setValidateGroup(String validateGroup) {
        ValidateGroup = validateGroup;
    }

    public String getData_replacer() {
        return data_replacer;
    }

    public void setData_replacer(String data_replacer) {
        this.data_replacer = data_replacer;
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public String getOptioner() {
        return optioner;
    }

    public void setOptioner(String optioner) {
        this.optioner = optioner;
    }

    public String getDatabase_field() {
        return Database_field;
    }

    public void setDatabase_field(String database_field) {
        Database_field = database_field;
    }

    public String getDatabase_field_type() {
        return Database_field_type;
    }

    public void setDatabase_field_type(String database_field_type) {
        Database_field_type = database_field_type;
    }

    public Integer getAutoPostback() {
        return AutoPostback;
    }

    public void setAutoPostback(Integer autoPostback) {
        AutoPostback = autoPostback;
    }

    public String getStatisticsField() {
        return StatisticsField;
    }

    public void setStatisticsField(String statisticsField) {
        StatisticsField = statisticsField;
    }

    public Integer getIndex_number() {
        return Index_number;
    }

    public void setIndex_number(Integer index_number) {
        Index_number = index_number;
    }

    public String getIsNull() {
        return IsNull;
    }

    public void setIsNull(String isNull) {
        IsNull = isNull;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }

    public String getJsmethod() {
        return jsmethod;
    }

    public void setJsmethod(String jsmethod) {
        this.jsmethod = jsmethod;
    }

    public Integer getClickbutton() {
        return clickbutton;
    }

    public void setClickbutton(Integer clickbutton) {
        this.clickbutton = clickbutton;
    }

    public String getClickmethod() {
        return clickmethod;
    }

    public void setClickmethod(String clickmethod) {
        this.clickmethod = clickmethod;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getWapAttribute() {
        return wapAttribute;
    }

    public void setWapAttribute(String wapAttribute) {
        this.wapAttribute = wapAttribute;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getIsDataItem() {
        return isDataItem;
    }

    public void setIsDataItem(Integer isDataItem) {
        this.isDataItem = isDataItem;
    }

    public String getTransferParams() {
        return transferParams;
    }

    public void setTransferParams(String transferParams) {
        this.transferParams = transferParams;
    }

    public String getListAttributeId() {
        return listAttributeId;
    }

    public void setListAttributeId(String listAttributeId) {
        this.listAttributeId = listAttributeId;
    }

    public String getShowState() {
        return showState;
    }

    public void setShowState(String showState) {
        this.showState = showState;
    }

    public String getShowPage() {
        return showPage;
    }

    public void setShowPage(String showPage) {
        this.showPage = showPage;
    }

    public Boolean getIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(Boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
}
