package com.redmany.ram.dao;

import com.redmany.ram.common.SQLHelper;
import com.redmany.ram.model.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/15.
 * Form 表 Dao
 */
public class FormDao {

    private SQLHelper sqlHelper = new SQLHelper();

    /**
     * 根据FormName查询返回结果集
     * @param CompanyId 企业Id
     * @param FormName FormName
     * @return
     */
    public List<Map<String,Object>> getFormList(String CompanyId, String FormName){
        String sql = "SELECT * FROM [Form] WHERE FormName=?";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }

    /**
     * 根据FormName查询返回实体
     * @param CompanyId
     * @param FormName
     * @return
     */
    public Form getForm(String CompanyId, String FormName){
        String sql = "SELECT * FROM [Form] WHERE FormName=?";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        Form form = new Form();
        if(datas!=null&&datas.size()>0){
            Map<String,Object> map = datas.get(0);
            form = toForm(map);
        }
        return form;
    }

    /**
     * 将结果集转为实体
     * @param map
     * @return
     */
    public Form toForm(Map<String,Object> map){
        Form form = new Form();
        if(map!=null&&map.size()>0){
            form.setId(Integer.parseInt(ObjIsNull(map.get("Id"),"0")));
            form.setFormName(ObjIsNull(map.get("FormName"),""));
            form.setTitle(ObjIsNull(map.get("Title"),""));
            form.setList_fields(ObjIsNull(map.get("List_fields"),""));
            form.setSort_fields(ObjIsNull(map.get("Sort_fields"),""));
            form.setSearch_fields(ObjIsNull(map.get("Search_fields"),""));
            form.setModify_fields(ObjIsNull(map.get("Modify_fields"),""));
            form.setGetter(ObjIsNull(map.get("Getter"),""));
            form.setPutter(ObjIsNull(map.get("Putter"),""));
            form.setModifyer(ObjIsNull(map.get("Modifyer"),""));
            form.setConn_str(ObjIsNull(map.get("conn_str"),""));
            form.setGet_data_sql(ObjIsNull(map.get("Get_data_sql"),""));
            form.setReplaceName(ObjIsNull(map.get("ReplaceName"),""));
            form.setGet_data_proc_name(ObjIsNull(map.get("Get_data_proc_name"),""));
            form.setGet_data_proc_params(ObjIsNull(map.get("Get_data_proc_params"),""));
            form.setPut_data_proc_name(ObjIsNull(map.get("Put_data_proc_name"),""));
            form.setPut_data_proc_params(ObjIsNull(map.get("Put_data_proc_params"),""));
            form.setModify_data_proc_name(ObjIsNull(map.get("Modify_data_proc_name"),""));
            form.setModify_data_proc_params(ObjIsNull(map.get("Modify_data_proc_params"),""));
            form.setPut_data_sql_set_field(ObjIsNull(map.get("Put_data_sql_set_field"),""));
            form.setPut_data_sql_set_value(ObjIsNull(map.get("Put_data_sql_set_value"),""));
            form.setTable_name(ObjIsNull(map.get("Table_name"),""));
            form.setDataDepFieldName(ObjIsNull(map.get("DataDepFieldName"),""));
            form.setDataOwneFieldName(ObjIsNull(map.get("DataOwneFieldName"),""));
            form.setKey_field(ObjIsNull(map.get("Key_field"),""));
            form.setKey_field_type(ObjIsNull(map.get("Key_field_type"),""));
            form.setMenuType(ObjIsNull(map.get("MenuType"),""));
            form.setState(Integer.parseInt(ObjIsNull(map.get("State"),"0")));
            form.setLast_modify_time(ObjIsNull(map.get("last_modify_time"),""));
            form.setSubmit_url(ObjIsNull(map.get("submit_url"),""));
            form.setDataRefreshInterval(ObjIsNull(map.get("dataRefreshInterval"),""));
            form.setGet_data_sql_where(ObjIsNull(map.get("get_data_sql_where"),""));
            form.setInsert_op_type(ObjIsNull(map.get("insert_op_type"),""));
            form.setGet_dataset_dealer(ObjIsNull(map.get("get_dataset_dealer"),""));
            form.setUsersWhere(ObjIsNull(map.get("UsersWhere"),""));
            form.setExportToExcel(Integer.parseInt(ObjIsNull(map.get("ExportToExcel"),"0")));
            form.setXcolumn(ObjIsNull(map.get("xcolumn"),""));
            form.setYcolumn(ObjIsNull(map.get("ycolumn"),""));
            form.setXycolumn(ObjIsNull(map.get("xycolumn"),""));
            form.setbFormColumn(ObjIsNull(map.get("bFormColumn"),""));
            form.setHtml_template(ObjIsNull(map.get("html_template"),""));
            form.setDoc_template(ObjIsNull(map.get("doc_template"),""));
            form.setPush_message(ObjIsNull(map.get("push_message"),""));
            form.setTemplate_type(ObjIsNull(map.get("template_type"),""));
            form.setReturnCode(ObjIsNull(map.get("returnCode"),""));
            form.setReturnCodeLMF(ObjIsNull(map.get("returnCodeLMF"),""));
            form.setSearchparams(ObjIsNull(map.get("searchparams"),""));
            form.setTrueName(ObjIsNull(map.get("trueName"),""));
            form.setResultFunc(ObjIsNull(map.get("resultFunc"),""));
            form.setIsupdate(ObjIsNull(map.get("isupdate"),""));
            form.setLoadWay(ObjIsNull(map.get("loadWay"),""));
            form.setTarget(ObjIsNull(map.get("target"),""));
            form.setShowPage(ObjIsNull(map.get("showPage"),""));
            form.setTransferParams(ObjIsNull(map.get("transferParams"),""));
            form.setDataCounts(ObjIsNull(map.get("dataCounts"),""));
            form.setColumnCount(ObjIsNull(map.get("columnCount"),""));
            form.setRowCount(ObjIsNull(map.get("rowCount"),""));
            form.setIsScroll(ObjIsNull(map.get("isScroll"),""));
            form.setIsShowAll(ObjIsNull(map.get("isShowAll"),""));
            form.setIsHasTopOrBottomEvent(ObjIsNull(map.get("isHasTopOrBottomEvent"),""));
        }
        return form;
    }

    /**
     * 判断Object类型是否为null
     * @param obj
     * @param trueValue 若为Null 返回该值
     * @return
     */
    public String ObjIsNull(Object obj, String trueValue){
        String result;
        if(obj==null){
            result = trueValue;
        }else{
            result = obj.toString();
        }
        return result;
    }

}
