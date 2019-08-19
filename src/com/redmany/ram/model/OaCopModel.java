package com.redmany.ram.model;

import java.io.Serializable;

/**
 * Created by hy on 2017/7/4.
 */
public class OaCopModel implements Serializable{

    private Integer Id;
    private Integer state;
    private String title;
    private String modifyFields;
    private String listType;
    private String formStatic;
    private String compoundName;
    private String copFormName;
    private String showType;
    private String align;
    private String attributeId;
    private String columnCount;
    private String Index_number;
    private String listDataCount;
    private String isScroll;
    private String isShowAll;
    private String submitDate;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModifyFields() {
        return modifyFields;
    }

    public void setModifyFields(String modifyFields) {
        this.modifyFields = modifyFields;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getFormStatic() {
        return formStatic;
    }

    public void setFormStatic(String formStatic) {
        this.formStatic = formStatic;
    }

    public String getCompoundName() {
        return compoundName;
    }

    public void setCompoundName(String compoundName) {
        this.compoundName = compoundName;
    }

    public String getCopFormName() {
        return copFormName;
    }

    public void setCopFormName(String copFormName) {
        this.copFormName = copFormName;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(String columnCount) {
        this.columnCount = columnCount;
    }

    public String getIndex_number() {
        return Index_number;
    }

    public void setIndex_number(String index_number) {
        Index_number = index_number;
    }

    public String getListDataCount() {
        return listDataCount;
    }

    public void setListDataCount(String listDataCount) {
        this.listDataCount = listDataCount;
    }

    public String getIsScroll() {
        return isScroll;
    }

    public void setIsScroll(String isScroll) {
        this.isScroll = isScroll;
    }

    public String getIsShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(String isShowAll) {
        this.isShowAll = isShowAll;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }
}
