package com.redmany.ram.model;

import java.io.Serializable;

/**
 * Created by hy on 2017/6/9.
 * Form[表单] 表 实体类
 */
public class Form implements Serializable {
    private Integer Id;
    private String FormName;
    private String Title;
    private String List_fields;
    private String Sort_fields;
    private String Search_fields;
    private String Modify_fields;
    private String Getter;
    private String Putter;
    private String Modifyer;
    private String conn_str;
    private String Get_data_sql;
    private String ReplaceName;
    private String Get_data_proc_name;
    private String Get_data_proc_params;
    private String Put_data_proc_name;
    private String Put_data_proc_params;
    private String Modify_data_proc_name;
    private String Modify_data_proc_params;
    private String Put_data_sql_set_field;
    private String Put_data_sql_set_value;
    private String Table_name;
    private String DataDepFieldName;
    private String DataOwneFieldName;
    private String Key_field;
    private String Key_field_type;
    private String MenuType;
    private Integer State;
    private String last_modify_time;
    private String submit_url;
    private String dataRefreshInterval;
    private String get_data_sql_where;
    private String insert_op_type;
    private String get_dataset_dealer;
    private String UsersWhere;
    private Integer ExportToExcel;
    private String xcolumn;
    private String ycolumn;
    private String xycolumn;
    private String bFormColumn;
    private String html_template;
    private String doc_template;
    private String push_message;
    private String template_type;
    private String returnCode;
    private String returnCodeLMF;
    private String searchparams;
    private String trueName;
    private String resultFunc;
    private String isupdate;
    private String loadWay;
    private String target;
    private String showPage;
    private String transferParams;
    private String dataCounts;
    private String columnCount;
    private String rowCount;
    private String isScroll;
    private String isShowAll;
    private String isHasTopOrBottomEvent;

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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getList_fields() {
        return List_fields;
    }

    public void setList_fields(String list_fields) {
        List_fields = list_fields;
    }

    public String getSort_fields() {
        return Sort_fields;
    }

    public void setSort_fields(String sort_fields) {
        Sort_fields = sort_fields;
    }

    public String getSearch_fields() {
        return Search_fields;
    }

    public void setSearch_fields(String search_fields) {
        Search_fields = search_fields;
    }

    public String getModify_fields() {
        return Modify_fields;
    }

    public void setModify_fields(String modify_fields) {
        Modify_fields = modify_fields;
    }

    public String getGetter() {
        return Getter;
    }

    public void setGetter(String getter) {
        Getter = getter;
    }

    public String getPutter() {
        return Putter;
    }

    public void setPutter(String putter) {
        Putter = putter;
    }

    public String getModifyer() {
        return Modifyer;
    }

    public void setModifyer(String modifyer) {
        Modifyer = modifyer;
    }

    public String getConn_str() {
        return conn_str;
    }

    public void setConn_str(String conn_str) {
        this.conn_str = conn_str;
    }

    public String getGet_data_sql() {
        return Get_data_sql;
    }

    public void setGet_data_sql(String get_data_sql) {
        Get_data_sql = get_data_sql;
    }

    public String getReplaceName() {
        return ReplaceName;
    }

    public void setReplaceName(String replaceName) {
        ReplaceName = replaceName;
    }

    public String getGet_data_proc_name() {
        return Get_data_proc_name;
    }

    public void setGet_data_proc_name(String get_data_proc_name) {
        Get_data_proc_name = get_data_proc_name;
    }

    public String getGet_data_proc_params() {
        return Get_data_proc_params;
    }

    public void setGet_data_proc_params(String get_data_proc_params) {
        Get_data_proc_params = get_data_proc_params;
    }

    public String getPut_data_proc_name() {
        return Put_data_proc_name;
    }

    public void setPut_data_proc_name(String put_data_proc_name) {
        Put_data_proc_name = put_data_proc_name;
    }

    public String getPut_data_proc_params() {
        return Put_data_proc_params;
    }

    public void setPut_data_proc_params(String put_data_proc_params) {
        Put_data_proc_params = put_data_proc_params;
    }

    public String getModify_data_proc_name() {
        return Modify_data_proc_name;
    }

    public void setModify_data_proc_name(String modify_data_proc_name) {
        Modify_data_proc_name = modify_data_proc_name;
    }

    public String getModify_data_proc_params() {
        return Modify_data_proc_params;
    }

    public void setModify_data_proc_params(String modify_data_proc_params) {
        Modify_data_proc_params = modify_data_proc_params;
    }

    public String getPut_data_sql_set_field() {
        return Put_data_sql_set_field;
    }

    public void setPut_data_sql_set_field(String put_data_sql_set_field) {
        Put_data_sql_set_field = put_data_sql_set_field;
    }

    public String getPut_data_sql_set_value() {
        return Put_data_sql_set_value;
    }

    public void setPut_data_sql_set_value(String put_data_sql_set_value) {
        Put_data_sql_set_value = put_data_sql_set_value;
    }

    public String getTable_name() {
        return Table_name;
    }

    public void setTable_name(String table_name) {
        Table_name = table_name;
    }

    public String getDataDepFieldName() {
        return DataDepFieldName;
    }

    public void setDataDepFieldName(String dataDepFieldName) {
        DataDepFieldName = dataDepFieldName;
    }

    public String getDataOwneFieldName() {
        return DataOwneFieldName;
    }

    public void setDataOwneFieldName(String dataOwneFieldName) {
        DataOwneFieldName = dataOwneFieldName;
    }

    public String getKey_field() {
        return Key_field;
    }

    public void setKey_field(String key_field) {
        Key_field = key_field;
    }

    public String getKey_field_type() {
        return Key_field_type;
    }

    public void setKey_field_type(String key_field_type) {
        Key_field_type = key_field_type;
    }

    public String getMenuType() {
        return MenuType;
    }

    public void setMenuType(String menuType) {
        MenuType = menuType;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public String getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(String last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public String getSubmit_url() {
        return submit_url;
    }

    public void setSubmit_url(String submit_url) {
        this.submit_url = submit_url;
    }

    public String getDataRefreshInterval() {
        return dataRefreshInterval;
    }

    public void setDataRefreshInterval(String dataRefreshInterval) {
        this.dataRefreshInterval = dataRefreshInterval;
    }

    public String getGet_data_sql_where() {
        return get_data_sql_where;
    }

    public void setGet_data_sql_where(String get_data_sql_where) {
        this.get_data_sql_where = get_data_sql_where;
    }

    public String getInsert_op_type() {
        return insert_op_type;
    }

    public void setInsert_op_type(String insert_op_type) {
        this.insert_op_type = insert_op_type;
    }

    public String getGet_dataset_dealer() {
        return get_dataset_dealer;
    }

    public void setGet_dataset_dealer(String get_dataset_dealer) {
        this.get_dataset_dealer = get_dataset_dealer;
    }

    public String getUsersWhere() {
        return UsersWhere;
    }

    public void setUsersWhere(String usersWhere) {
        UsersWhere = usersWhere;
    }

    public Integer getExportToExcel() {
        return ExportToExcel;
    }

    public void setExportToExcel(Integer exportToExcel) {
        ExportToExcel = exportToExcel;
    }

    public String getXcolumn() {
        return xcolumn;
    }

    public void setXcolumn(String xcolumn) {
        this.xcolumn = xcolumn;
    }

    public String getYcolumn() {
        return ycolumn;
    }

    public void setYcolumn(String ycolumn) {
        this.ycolumn = ycolumn;
    }

    public String getXycolumn() {
        return xycolumn;
    }

    public void setXycolumn(String xycolumn) {
        this.xycolumn = xycolumn;
    }

    public String getbFormColumn() {
        return bFormColumn;
    }

    public void setbFormColumn(String bFormColumn) {
        this.bFormColumn = bFormColumn;
    }

    public String getHtml_template() {
        return html_template;
    }

    public void setHtml_template(String html_template) {
        this.html_template = html_template;
    }

    public String getDoc_template() {
        return doc_template;
    }

    public void setDoc_template(String doc_template) {
        this.doc_template = doc_template;
    }

    public String getPush_message() {
        return push_message;
    }

    public void setPush_message(String push_message) {
        this.push_message = push_message;
    }

    public String getTemplate_type() {
        return template_type;
    }

    public void setTemplate_type(String template_type) {
        this.template_type = template_type;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnCodeLMF(){
        return returnCodeLMF;
    }

    public void setReturnCodeLMF(String returnCode) {
        this.returnCodeLMF = returnCodeLMF;
    }

    public String getSearchparams() {
        return searchparams;
    }

    public void setSearchparams(String searchparams) {
        this.searchparams = searchparams;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getResultFunc() {
        return resultFunc;
    }

    public void setResultFunc(String resultFunc) {
        this.resultFunc = resultFunc;
    }

    public String getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }

    public String getLoadWay() {
        return loadWay;
    }

    public void setLoadWay(String loadWay) {
        this.loadWay = loadWay;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getShowPage() {
        return showPage;
    }

    public void setShowPage(String showPage) {
        this.showPage = showPage;
    }

    public String getTransferParams() {
        return transferParams;
    }

    public void setTransferParams(String transferParams) {
        this.transferParams = transferParams;
    }

    public String getDataCounts() {
        return dataCounts;
    }

    public void setDataCounts(String dataCounts) {
        this.dataCounts = dataCounts;
    }

    public String getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(String columnCount) {
        this.columnCount = columnCount;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
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

    public String getIsHasTopOrBottomEvent() {
        return isHasTopOrBottomEvent;
    }

    public void setIsHasTopOrBottomEvent(String isHasTopOrBottomEvent) {
        this.isHasTopOrBottomEvent = isHasTopOrBottomEvent;
    }
}
