package com.redmany.ram.control;

import com.redmany.ram.common.DSourceHelper;
import com.redmany.ram.dao.FormFiledDao;
import com.redmany.ram.dao.OaAttributeDao;
import com.redmany.ram.dao.ReplacerDao;
import com.redmany.ram.dao.TableDao;
import com.redmany.ram.model.Form;
import com.redmany.ram.model.FormFiled;
import com.redmany.ram.model.Replacer;
import com.redmany.ram.model.Variable;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/8/18.
 */
public class CreateControl {

    private static FormFiledDao formFiledDao = new FormFiledDao();
    private static OaAttributeDao oaAttributeDao = new OaAttributeDao();
    private static TableDao tableDao = new TableDao();
    private static ReplacerDao replacerDao = new ReplacerDao();

    private static DSourceHelper dSourceHelper = new DSourceHelper();

    public static String Control(HttpServletRequest req, Map<String, Object> map, Form form, List<Map<String,Object>> formFieldList,String ListClass,String ListStyle){
        System.out.println("into ListControl");
        StringBuffer sb = new StringBuffer();
        //获取Form表数据
        System.out.println("form:..."+form);
        String List_fields = form.getList_fields();
        String Data_sql = form.getGet_data_sql();
        String Sort_fields = form.getSort_fields();
        if(!"".equals(Data_sql)&&!"".equals(List_fields)){
            List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
            //获取数据集合
            dataList = tableDao.getTableData(req, map.get("CompanyId").toString(),Data_sql);
            System.out.println(dataList);
            //遍历拼接表数据
            if(dataList!=null&&dataList.size()>0){
                for(Map<String,Object> Data : dataList){
                    sb.append("<div class=\""+ListClass+"\" style=\""+ListStyle+"\">");
                    for(Map<String,Object> formFieldMap : formFieldList){
                        FormFiled fd = new FormFiled();

                        fd = formFiledDao.toFormFiled(formFieldMap);
                        String attributeId = fd.getAttributeId().toString();
                        String controlID = fd.getName() + fd.getId().toString();
                        Map<String,String> attributeMap = getAttribute(map.get("CompanyId").toString(), attributeId);

                        String DataTarget = null;
                        if(Data.containsKey("target")){
                            try {
                                DataTarget = Data.get("target").toString();
                            }catch (Exception e){
                                DataTarget = null;
                            }
                        }
                        String value = null;
                        if(Data.containsKey(fd.getName())){
                            try {
                                value = Data.get(fd.getName()).toString();
                            }catch (Exception e){
                                value = null;
                            }
                        }
                        String naviTitle = null;
                        if(map.containsKey("naviTitle")){
                            try {
                                naviTitle = map.get("naviTitle").toString();
                            }catch (Exception e){
                                naviTitle = null;
                            }
                        }
                        String s = null;
                        if(StringUtils.isNotBlank(fd.getShowState())){
                            String state = null;
                            if(Data.containsKey("state")){
                                try {
                                    state = Data.get("state").toString();
                                }catch (Exception e){
                                    state = null;
                                }
                            }
                            if(StringUtils.equals(StringUtils.trimToNull(state),StringUtils.trimToNull(fd.getShowState()))){
                                s = getTypeControl(req,fd,attributeMap,controlID,value,DataTarget,naviTitle);
                            }
                        } else {
                            s = getTypeControl(req,fd,attributeMap,controlID,value,DataTarget,naviTitle);
                        }

                        if(StringUtils.isNotBlank(s)){
                            sb.append(s);
                        }
                    }
                    sb.append("</div>");
                }
            }else if(formFieldList!=null&&formFieldList.size()>0){
                for(Map<String,Object> formFieldMap : formFieldList){
                    FormFiled fd = new FormFiled();

                    fd = formFiledDao.toFormFiled(formFieldMap);
                    String attributeId = fd.getAttributeId().toString();
                    String controlID = fd.getName() + fd.getId().toString();
                    Map<String,String> attributeMap = getAttribute(map.get("CompanyId").toString(), attributeId);

                    String naviTitle = null;
                    if(map.containsKey("naviTitle")){
                        naviTitle = map.get("naviTitle").toString();
                    }

                    String s = getTypeControl(req,fd,attributeMap,controlID,null,null,naviTitle);

                    if(StringUtils.isNotBlank(s)){
                        sb.append(s);
                    }
                }
            }
        }else if(formFieldList!=null&&formFieldList.size()>0){
            for(Map<String,Object> formFieldMap : formFieldList){
                FormFiled fd = new FormFiled();

                fd = formFiledDao.toFormFiled(formFieldMap);
                String attributeId = fd.getAttributeId().toString();
                String controlID = fd.getName() + fd.getId().toString();
                Map<String,String> attributeMap = getAttribute(map.get("CompanyId").toString(), attributeId);

                String naviTitle = null;
                if(map.containsKey("naviTitle")){
                    naviTitle = map.get("naviTitle").toString();
                }

                String s = getTypeControl(req,fd,attributeMap,controlID,null,null,naviTitle);

                if(StringUtils.isNotBlank(s)){
                    sb.append(s);
                }
            }
        }
        return sb.toString();
    }

    public static Map<String,String> getAttribute(String CompanyId,String AttributeId){
        Map<String,String> map = new HashMap<String,String>();
        // 表单
        // <div class=FormClass style=FormStyle> *** FormFiled 集 *** </div>
        String FormStyle = "";
        String FormClass = "";
        //列表
        String ListClass = "";
        String ListStyle = "";
        // 字段
        // <FormFiled 类型 class=FormClass style=FormStyle></FormFiled>
        String FiledStyle = "";
        String FiledClass = "";
        // 字段父级div
        // <div class=DivClass style=DivStyle> --- FormFiled --- </div>
        String DivStyle = "";
        String DivClass = "";
        // 文本
        // <label class=TextClass style=TextStyle>
        String TextStyle = "";
        String TextClass = "";
        // 是否编辑
        // 0:否 表现为<input type=text>
        // 1:是 表现为<span>
        String IsEdit = "";
        // 按钮类型
        // submit 表现为 <input type=submit>
        // button 表现为 <button></button>
        // a 表现为 <a></a>
        String ButtonType = "";

        if (StringUtils.isNotBlank(AttributeId) && !StringUtils.equalsIgnoreCase("0", AttributeId)) {
            Map<String, Object> oaAttrMap = oaAttributeDao.getOaAttribute(CompanyId, AttributeId).get(0);
            Object obj = oaAttrMap.get("wapAttribute");
            if (obj != null) {
                String attr = obj.toString();
                String[] attrArr = attr.split("\\[\\^\\]");
                for (String aa : attrArr) {
                    String[] type = aa.split("=");
                    String item = type[1];
                    switch (type[0].toUpperCase()) {
                        case "FORMSTYLE":
                            FormStyle = item;
                            break;
                        case "FORMCLASS":
                            FormClass = item;
                            break;
                        case "LISTSTYLE":
                            ListStyle = item;
                            break;
                        case "LISTCLASS":
                            ListClass = item;
                            break;
                        case "FILEDSTYLE":
                            FiledStyle = item;
                            break;
                        case "FILEDCLASS":
                            FiledClass = item;
                            break;
                        case "DIVSTYLE":
                            DivStyle = item;
                            break;
                        case "DIVCLASS":
                            DivClass = item;
                            break;
                        case "TEXTSTYLE":
                            TextStyle = item;
                            break;
                        case "TEXTCLASS":
                            TextClass = item;
                            break;
                        case "ISEDIT":
                            IsEdit = item;
                            break;
                        case "BUTTONTYPE":
                            ButtonType = item;
                            break;
                    }
                }
            }
        }

        map.put("FormStyle",FormStyle);
        map.put("ListStyle",ListStyle);
        map.put("FiledStyle",FiledStyle);
        map.put("DivStyle",DivStyle);
        map.put("TextStyle",TextStyle);
        map.put("FormClass",FormClass);
        map.put("ListClass",ListClass);
        map.put("FiledClass",FiledClass);
        map.put("DivClass",DivClass);
        map.put("TextClass",TextClass);

        map.put("IsEdit",IsEdit);

        map.put("ButtonType",ButtonType);
        return map;
    }

    public static String getTypeControl(HttpServletRequest req,FormFiled fd,Map<String,String> attributeMap, String controlID, String value, String DataTarget, String naviTitle){
        String str = null;
        switch (fd.getType().toLowerCase()){
            case "text":
                str = control_text(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "textnotitle":
                str = control_textnotitle(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "personcheckbox":
                str = control_personcheckbox(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "inttext":
                str = control_inttext(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "literal":
                str = control_literal(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "multitext":
                str = control_multitext(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "select":
                try {
                    str = control_select(req,fd,attributeMap,controlID,value,DataTarget);
                }catch (IOException e){
                    str = "";
                    e.printStackTrace();
                }
                break;
            case "checkbox":
                str = control_checkbox(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "datetime":
                str = control_datetime(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "multiselect":
                str = control_multiselect(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "file":
                str = control_file(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "freetext":
                str = control_freetext(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "tele":
                str = control_tele(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "tcode":
                str = control_tcode(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "location":
                str = control_location(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "image":
                str = control_image(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "coppwd":
                str = control_coppwd(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "coppwdnotitle":
                str = control_coppwdnotitle(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "button":
                str = control_button(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "buttonnotitle":
                str = control_buttonnotitle(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "navibackbtn":
                str = control_navibackbtn(fd,attributeMap,controlID,value,DataTarget);
                break;
            case "toptext":
                str = control_toptext(fd,attributeMap,controlID,value,DataTarget,naviTitle);
                break;
            case "none":
                str = control_none(attributeMap);
                break;
            default:
                str = "";
                break;
        }
        return str;
    }

    public static String control_text(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        String TextStyle = attributeMap.get("TextStyle");
        String TextClass = attributeMap.get("TextClass");
        String IsEdit = attributeMap.get("IsEdit");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        String label = "";

        if(StringUtils.isNotBlank(Title)){
            if(Title.endsWith(".png") || Title.endsWith(".PNG") || Title.endsWith(".jpg") || Title.endsWith(".JPG")){
                label = "<img src=\""+ Variable.getImageHttp() + Title +"\" class=\""+TextClass+"\" style=\""+TextStyle+"\" ";
                if(StringUtils.isNotBlank(DataTarget)){
                    label+="onclick=\"tar(\\'"+DataTarget+"\\')\" />";
                } else {
                    label+="/>";
                }
            } else {
                Title = Title + "：";
                label = "<label id=\"lit"+ID+"\" class=\""+TextClass+"\" style=\""+TextStyle+"\"";
                if(StringUtils.isNotBlank(DataTarget)){
                    label+="onclick=\"tar(\\'"+DataTarget+"\\')\" >"+Title+"</label>";
                } else {
                    label+=">"+Title+"</label>";
                }
            }
        }
        String control = "";

        if(!"".equals(IsEdit) && "0".equals(IsEdit)){
            control += "<span id=\""+ID+"\" class=\""+FiledClass+"\" style=\""+FiledStyle+"\" ";
            if(StringUtils.isNotBlank(DataTarget)){
                control+="onclick=\"tar(\\'"+DataTarget+"\\')\" >";
            } else {
                control+=">";
            }
            if(StringUtils.isNotBlank(Value)){
                control+=Value;
            }
            control += "</span>";
        } else {
            control += "<input type=\"text\" id=\"" + ID + "\" name=\"" + Name + "\" ";
            if(StringUtils.isNotBlank(Value)){
                control+="value=\""+Value+"\" ";
            }
            control+="class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\">";
        }

        sb.append(label);
        sb.append(control);

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_textnotitle(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        String IsEdit = attributeMap.get("IsEdit");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        String control = "";

        if(!"".equals(IsEdit) && "0".equals(IsEdit)){
            control += "<span id=\""+ID+"\" class=\""+FiledClass+"\" style=\""+FiledStyle+"\" ";
            if(StringUtils.isNotBlank(DataTarget)){
                control+="onclick=\"tar(\\'"+DataTarget+"\\')\" >";
            } else {
                control+=">";
            }
            if(StringUtils.isNotBlank(Value)){
                control+=Value;
            } else {
                control += Title;
            }
            control += "</span>";
        } else {
            control += "<input type=\"text\" id=\"" + ID + "\" name=\"" + Name + "\" ";
            if(StringUtils.isNotBlank(Value)){
                control+="value=\""+Value+"\" ";
            }
            control+="class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\">";
        }

        sb.append(control);

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_personcheckbox(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_inttext(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_literal(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_multitext(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_select(HttpServletRequest req,FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget) throws IOException{
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String TextStyle = attributeMap.get("TextStyle");
        String TextClass = attributeMap.get("TextClass");
        String IsEdit = attributeMap.get("IsEdit");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        String label = "";
        if(StringUtils.isNotBlank(Title)){
            Title = Title + "：";
            label = "<label id=\"lit"+ID+"\" class=\""+TextClass+"\" style=\""+TextStyle+"\"";
            if(StringUtils.isNotBlank(DataTarget)){
                label+="onclick=\"tar(\\'"+DataTarget+"\\')\" >"+Title+"</label>";
            } else {
                label+=">"+Title+"</label>";
            }
        }

        String control = "";
        String CompanyId = Variable.getCompanyId();
        String Replacername = fd.getData_replacer();
        Replacer replacer = replacerDao.getReplacer(CompanyId,Replacername);
        List<Map<String,Object>> replaceList = dSourceHelper.GetReplacerBind(req,replacer);
        if(replaceList!=null&&!"".equals(replaceList)&&replaceList.size()>0){
            if(!"".equals(IsEdit) && "0".equals(IsEdit)){
                control += "<span id=\""+ID+"\" class=\""+FiledClass+"\" style=\""+FiledStyle+"\" >";
                for (Map<String,Object> map : replaceList){
                    String value = map.get("value").toString();
                    String name = map.get("name").toString();
                    if(StringUtils.isNotBlank(Value)){
                        if(Value.equals(value)){
                            control += name;
                        }
                    }
                }
                control += "</span>";
            } else {
                control += "<select id=\"" + ID + "\" name=\"" + Name + "\" class=\""+FiledClass+"\" style=\""+FiledStyle+"\" >";
                control += "<option value=\"-1\">==请选择==</option>";
                for (Map<String,Object> map : replaceList){
                    String value = map.get("value").toString();
                    String name = map.get("name").toString();
                    if(StringUtils.isNotBlank(Value)){
                        if(Value.equals(value)){
                            control += "<option value=\""+value+"\" selected=\"true\">"+name+"</option>";
                        } else {
                            control += "<option value=\""+value+"\">"+name+"</option>";
                        }
                    } else {
                        control += "<option value=\""+value+"\">"+name+"</option>";
                    }
                }
                control += "</select>";
            }

        }

        sb.append(label);
        sb.append(control);
        sb.append("</div>");
        return sb.toString();
    }

    public static String control_checkbox(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_datetime(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String TextStyle = attributeMap.get("TextStyle");
        String TextClass = attributeMap.get("TextClass");
        String IsEdit = attributeMap.get("IsEdit");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        String label = "";
        if(StringUtils.isNotBlank(Title)){
            Title = Title + "：";
            label = "<label id=\"lit"+ID+"\" class=\""+TextClass+"\" style=\""+TextStyle+"\"";
            if(StringUtils.isNotBlank(DataTarget)){
                label+="onclick=\"tar(\\'"+DataTarget+"\\')\" >"+Title+"</label>";
            } else {
                label+=">"+Title+"</label>";
            }
        }

        String control = "";

        if(!"".equals(IsEdit) && "0".equals(IsEdit)){
            control += "<span id=\""+ID+"\" class=\""+FiledClass+"\" style=\""+FiledStyle+"\" ";
            if(StringUtils.isNotBlank(DataTarget)){
                control+="onclick=\"tar(\\'"+DataTarget+"\\')\" >";
            } else {
                control+=">";
            }
            if(StringUtils.isNotBlank(Value)){
                control+="value=\""+Value+"\" ";
            }
            control += "</span>";
        } else {
            control += "<input type=\"text\" id=\"" + ID + "\" name=\"" + Name + "\" ";
            if(StringUtils.isNotBlank(Value)){
                control+="value=\""+Value+"\" ";
            }
            control+="class=\"Wdate " + FiledClass + "\" style=\"" + FiledStyle + "\">";
        }

        sb.append(label);
        sb.append(control);

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_multiselect(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_file(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_freetext(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_tele(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_tcode(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_location(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        //create control here

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_image(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        //String IsEdit = attributeMap.get("IsEdit");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" ");
        if(StringUtils.isBlank(Target)){
            sb.append(">");
        }else{
            sb.append("onclick=\"tar(\\'"+Target+"\\')\" >");
        }

        String control = "";
        if(StringUtils.isNotBlank(Value)){
            control += "<img src=\""+ Variable.getImageHttp()+Value+"\" ";
        } else if(!Title.endsWith(".png")){
            Name = Title;
            Title = null;
            control += "<img src=\""+Title+"\" ";
        } else {
            control += "<img src=\""+Title+"\" ";
        }
        control += "alt=\""+Name+"\" class=\""+FiledClass+"\" style=\""+FiledStyle+"\" ";
        if(StringUtils.isNotBlank(DataTarget)){
            control += "onclick=\"tar(\\'" + DataTarget + "\\')\" />";
        } else {
            control+= "/>";
        }
        sb.append(control);

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_button(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        String ButtonType = attributeMap.get("ButtonType");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" >");

        String control = "";

        switch (ButtonType.toLowerCase()){
            case "submit":
                control += "<input type=\"hidden\" name=\"target\" value=\""+Target+"\" />";
                control += "<input type=\"submit\" id=\""+ID+"\" ";
                if(Value != null){
                    control += "value=\"" + Value + "\" ";
                }else{
                    control += "value=\"" + Title + "\" ";
                }
                control += "class=\""+FiledClass+"\" style=\""+FiledStyle+"\"/>";
                break;
            case "button":
                control = "<button id=\"" + ID + "\" ";
                if(StringUtils.isNotBlank(DataTarget)){
                    control+="onclick=\"tar(\\'"+DataTarget+"\\')\" ";
                } else if(StringUtils.isNotBlank(Target)) {
                    control+="onclick=\"tar(\\'"+Target+"\\')\" ";
                }
                control += "class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\" >";
                if(StringUtils.isNotBlank(Value)){
                    control += Value + "</button>";
                }else{
                    control += Title + "</button>";
                }
                break;
            case "a":
                control = "<a id=\"" + ID + "\" ";
                if(StringUtils.isNotBlank(DataTarget)){
                    control+="onclick=\"tar(\\'"+DataTarget+"\\')\" ";
                } else if(StringUtils.isNotBlank(Target)) {
                    control+="onclick=\"tar(\\'"+Target+"\\')\" ";
                }
                control += "class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\" >";
                if(StringUtils.isNotBlank(Value)){
                    control += Value + "</a>";
                }else{
                    control += Title + "</a>";
                }
                break;
            default:
                control = "<a id=\"" + ID + "\" ";
                if(StringUtils.isNotBlank(DataTarget)){
                    control+="onclick=\"tar(\\'"+DataTarget+"\\')\" ";
                } else if(StringUtils.isNotBlank(Target)) {
                    control+="onclick=\"tar(\\'"+Target+"\\')\" ";
                }
                control += "class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\" >";
                if(StringUtils.isNotBlank(Value)){
                    control += Value + "</a>";
                }else{
                    control += Title + "</a>";
                }
                break;
        }

        sb.append(control);

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_buttonnotitle(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        String ButtonType = attributeMap.get("ButtonType");

        String Title = fd.getTitle();
        String Name = fd.getName();
        String Target = fd.getTarget();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" >");

        String control = "";

        switch (ButtonType.toLowerCase()){
            case "submit":
                control += "<input type=\"hidden\" name=\"target\" value=\""+Target+"\" />";
                control += "<input type=\"submit\" id=\""+ID+"\" value=\" \" ";
                /*if(Value != null){
                    control += "value=\"" + Value + "\" ";
                }else{
                    control += "value=\"" + Title + "\" ";
                }*/
                control += "class=\""+FiledClass+"\" style=\""+FiledStyle+"\"/>";
                break;
            case "button":
                control = "<button id=\"" + ID + "\" ";
                if(StringUtils.isNotBlank(DataTarget)){
                    control+="onclick=\"tar(\\'"+DataTarget+"\\')\" ";
                } else if(StringUtils.isNotBlank(Target)) {
                    control+="onclick=\"tar(\\'"+Target+"\\')\" ";
                }
                control += "class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\" >";
                /*if(StringUtils.isNotBlank(Value)){
                    control += Value + "</button>";
                }else{
                    control += Title + "</button>";
                }*/
                control += "</button>";
                break;
            case "a":
                control = "<a id=\"" + ID + "\" ";
                if(StringUtils.isNotBlank(DataTarget)){
                    control+="onclick=\"tar(\\'"+DataTarget+"\\')\" ";
                } else if(StringUtils.isNotBlank(Target)) {
                    control+="onclick=\"tar(\\'"+Target+"\\')\" ";
                }
                control += "class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\" >";
                /*if(StringUtils.isNotBlank(Value)){
                    control += Value + "</a>";
                }else{
                    control += Title + "</a>";
                }*/
                control += "</a>";
                break;
            default:
                control = "<a id=\"" + ID + "\" ";
                if(StringUtils.isNotBlank(DataTarget)){
                    control+="onclick=\"tar(\\'"+DataTarget+"\\')\" ";
                } else if(StringUtils.isNotBlank(Target)) {
                    control+="onclick=\"tar(\\'"+Target+"\\')\" ";
                }
                control += "class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\" >";
                /*if(StringUtils.isNotBlank(Value)){
                    control += Value + "</a>";
                }else{
                    control += Title + "</a>";
                }*/
                control += "</a>";
                break;
        }

        sb.append(control);

        sb.append("</div>");
        return sb.toString();
    }

    public static String control_coppwd(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");
        String TextStyle = attributeMap.get("TextStyle");
        String TextClass = attributeMap.get("TextClass");

        String Title = fd.getTitle();
        String Name = fd.getName();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" >");

        String label = "";

        if(StringUtils.isNotBlank(Title)){
            if(Title.endsWith(".png") || Title.endsWith(".PNG") || Title.endsWith(".jpg") || Title.endsWith(".JPG")){
                label = "<img src=\"" + Variable.getImageHttp() + Title+"\">";
            } else {
                Title = Title + "：";
                label = "<label id=\"lit"+ID+"\" class=\""+TextClass+"\" style=\""+TextStyle+"\">"+Title+"</label>";
            }
        }

        String control = "";
        control += "<input type=\"password\" id=\"" + ID + "\" name=\"" + Name + "\" ";
        control+="class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\">";
        sb.append(label);
        sb.append(control);
        sb.append("</div>");
        return sb.toString();
    }

    public static String control_coppwdnotitle(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        String Name = fd.getName();

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" >");

        String control = "";
        control += "<input type=\"password\" id=\"" + ID + "\" name=\"" + Name + "\" ";
        control+="class=\"" + FiledClass + "\" style=\"" + FiledStyle + "\">";
        sb.append(control);
        sb.append("</div>");
        return sb.toString();
    }

    public static String control_navibackbtn(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" >");

        String control = "";
        control = "<img id=\""+ID+"\" src=\""+Variable.getImageHttp()+"to_left.png\" class=\""+FiledClass+"\" style=\""+FiledStyle+"\" onclick=\"javascript:history.back(-1);\" />";

        sb.append(control);
        sb.append("</div>");
        return sb.toString();
    }

    public static String control_toptext(FormFiled fd, Map<String,String> attributeMap, String ID, String Value, String DataTarget, String naviTitle){
        String FiledStyle = attributeMap.get("FiledStyle");
        String FiledClass = attributeMap.get("FiledClass");
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" >");
        String control = "";

        control = "<span id=\""+ID+"\"  class=\""+FiledClass+"\" style=\""+FiledStyle+"\">";
        control += naviTitle;
        control += "</span>";
        sb.append(control);
        sb.append("</div>");
        return sb.toString();
    }

    public static String control_none(Map<String,String> attributeMap){
        String DivStyle = attributeMap.get("DivStyle");
        String DivClass = attributeMap.get("DivClass");

        StringBuffer sb = new StringBuffer();

        sb.append("<div class=\""+DivClass+"\" style=\""+DivStyle+"\" >");
        sb.append("</div>");
        return sb.toString();
    }
}
