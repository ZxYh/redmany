package com.redmany.ram.control;

import com.redmany.ram.common.AnalyzeTransferParamsUtils;
import com.redmany.ram.dao.FormFiledDao;
import com.redmany.ram.dao.OaAttributeDao;
import com.redmany.ram.dao.TableDao;
import com.redmany.ram.model.Form;
import com.redmany.ram.model.FormFiled;
import com.redmany.ram.model.Variable;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/23.
 */
public class Control {

    private static FormFiledDao formFiledDao = new FormFiledDao();
    private static OaAttributeDao oaAttributeDao = new OaAttributeDao();
    private static TableDao tableDao = new TableDao();

    public static String Control(Map<String,Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into Control");
        StringBuffer sb = new StringBuffer();

        for(Map<String,Object> formFieldMap : formFieldList){
            FormFiled fd = new FormFiled();

            fd = formFiledDao.toFormFiled(formFieldMap);
            String attributeId = fd.getAttributeId().toString();
            String controlID = fd.getName() + fd.getId().toString();
            Map<String,String> attributeMap = getAttribute(map.get("CompanyId").toString(), attributeId);
            String divClazz = attributeMap.get("divClazz");
            String clazz = attributeMap.get("clazz");
            String divStyle = attributeMap.get("divStyle");
            String style = attributeMap.get("style");
            String isEdit = attributeMap.get("isEdit");
            String textStyle = attributeMap.get("textStyle");
            String textClazz = attributeMap.get("textClazz");

            String naviTitle = null;
            if(map.containsKey("naviTitle") && map.keySet().contains("naviTitle")){
                naviTitle = map.get("naviTitle").toString();
            }

            sb.append("<div class=\""+divClazz+"\" style=\""+divStyle+"\">");

            String s = getTypeControl(fd,clazz,textClazz,style,textStyle,controlID,null,null,naviTitle,isEdit);
            sb.append(s);
            sb.append("</div>");
        }

        return sb.toString();
    }

    public static String ListControl(HttpServletRequest req, Map<String, Object> map, Form form, List<Map<String,Object>> formFieldList){
        System.out.println("into ListControl");
        StringBuffer sb = new StringBuffer();
        //获取Form表数据
        System.out.println("form:..."+form);
        //String Table_name = form.getTable_name();
        String List_fields = form.getList_fields();
        //String Sort_fields = form.getSort_fields();
        String Data_sql = form.getGet_data_sql();
        if(!"".equals(Data_sql)&&!"".equals(List_fields)){
            List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
            //获取数据集合
            dataList = tableDao.getTableData(req, map.get("CompanyId").toString(),Data_sql);
            System.out.println(dataList);
            //遍历拼接表数据
            if(dataList!=null&&dataList.size()>0){
                for(Map<String,Object> Data : dataList){
                    for(Map<String,Object> formFieldMap : formFieldList){
                        FormFiled fd = new FormFiled();

                        fd = formFiledDao.toFormFiled(formFieldMap);
                        String attributeId = fd.getAttributeId().toString();
                        String controlID = fd.getName() + fd.getId().toString();
                        Map<String,String> attributeMap = getAttribute(map.get("CompanyId").toString(), attributeId);
                        String divClazz = attributeMap.get("divClazz");
                        String clazz = attributeMap.get("clazz");
                        String divStyle = attributeMap.get("divStyle");
                        String style = attributeMap.get("style");
                        String isEdit = attributeMap.get("isEdit");
                        String textStyle = attributeMap.get("textStyle");
                        String textClazz = attributeMap.get("textClazz");

                        String tableTarget = null;
                        if(Data.containsKey("target")){
                            try {
                                tableTarget = Data.get("target").toString();
                            }catch (Exception e){
                                tableTarget = null;
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
                                s = getTypeControl(fd,clazz,textClazz,style,textStyle,controlID,value,tableTarget,naviTitle,isEdit);
                            }
                        } else {
                            s = getTypeControl(fd,clazz,textClazz,style,textStyle,controlID,value,tableTarget,naviTitle,isEdit);
                        }

                        if(StringUtils.isNotBlank(s)){
                            String target = fd.getTarget();
                            sb.append("<div class=\""+divClazz+"\" style=\""+divStyle+"\" ");
                            if(StringUtils.isBlank(target)){
                                sb.append(">");
                            }else{
                                sb.append("onclick=\"tar(\\'"+target+"\\')\" >");
                            }
                            sb.append(s);
                            sb.append("</div>");
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
                    String divClazz = attributeMap.get("divClazz");
                    String clazz = attributeMap.get("clazz");
                    String divStyle = attributeMap.get("divStyle");
                    String style = attributeMap.get("style");
                    String isEdit = attributeMap.get("isEdit");
                    String textStyle = attributeMap.get("textStyle");
                    String textClazz = attributeMap.get("textClazz");


                    String naviTitle = null;
                    if(map.containsKey("naviTitle")){
                        naviTitle = map.get("naviTitle").toString();
                    }

                    String s = getTypeControl(fd,clazz,textClazz,style,textStyle,controlID,null,null,naviTitle,isEdit);

                    if(StringUtils.isNotBlank(s)){
                        String target = fd.getTarget();
                        sb.append("<div class=\""+divClazz+"\" style=\""+divStyle+"\" ");
                        if(StringUtils.isBlank(target)){
                            sb.append(">");
                        }else{
                            sb.append("onclick=\"tar(\\'"+target+"\\')\" >");
                        }
                        sb.append(s);
                        sb.append("</div>");
                    }
                }
            }
        } else if(formFieldList!=null&&formFieldList.size()>0){
            for(Map<String,Object> formFieldMap : formFieldList){
                FormFiled fd = new FormFiled();

                fd = formFiledDao.toFormFiled(formFieldMap);
                String attributeId = fd.getAttributeId().toString();
                String controlID = fd.getName() + fd.getId().toString();
                Map<String,String> attributeMap = getAttribute(map.get("CompanyId").toString(), attributeId);
                String divClazz = attributeMap.get("divClazz");
                String clazz = attributeMap.get("clazz");
                String divStyle = attributeMap.get("divStyle");
                String style = attributeMap.get("style");
                String isEdit = attributeMap.get("isEdit");
                String textStyle = attributeMap.get("textStyle");
                String textClazz = attributeMap.get("textClazz");

                String naviTitle = null;
                if(map.containsKey("naviTitle")){
                    naviTitle = map.get("naviTitle").toString();
                }

                String s = getTypeControl(fd,clazz,textClazz,style,textStyle,controlID,null,null,naviTitle,isEdit);

                if(StringUtils.isNotBlank(s)){
                    String target = fd.getTarget();
                    sb.append("<div class=\""+divClazz+"\" style=\""+divStyle+"\" ");
                    if(StringUtils.isBlank(target)){
                        sb.append(">");
                    }else{
                        sb.append("onclick=\"tar(\\'"+target+"\\')\" >");
                    }
                    sb.append(s);
                    sb.append("</div>");
                }
            }
        }


        return sb.toString();
    }

    public static Map<String,String> getAttribute(String CompanyId,String AttributeId){
        Map<String,String> map = new HashMap<String,String>();
        String divClazz = "";
        String clazz = "";
        String divStyle = "";
        String style = "";
        String isEdit = "";
        String textClazz = "";
        String textStyle = "";
        if (StringUtils.isNotBlank(AttributeId) && !StringUtils.equalsIgnoreCase("0", AttributeId)) {
            Map<String, Object> oaAttrMap = oaAttributeDao.getOaAttribute(CompanyId, AttributeId).get(0);
            Object obj = oaAttrMap.get("wapAttribute");
            if (obj != null) {
                String attr = obj.toString();
                //divClass=aa bb cc[^]divStyle=width:100px;height:100px;[^]class=aa bb cc[^]style=height:26px;width:26px;margin:12px 12px;position:absolute;[^]isEdit=0
                String[] attrArr = attr.split("\\[\\^\\]");
                for (String aa : attrArr) {
                    String[] type = aa.split("=");
                    String item = type[1];
                    switch (type[0].toUpperCase()) {
                        case "DIVCLASS":
                            divClazz = item;
                        case "CLASS":
                            clazz = item;
                            break;
                        case "DIVSTYLE":
                            divStyle = item;
                        case "STYLE":
                            style = item;
                            break;
                        case "ISEDIT":
                            isEdit = item;
                            break;
                        case "TEXTCLASS":
                            textClazz = item;
                            break;
                        case "TEXTSTYLE":
                            textStyle = item;
                            break;
                    }
                }
            }
        }
        map.put("divClazz",divClazz);
        map.put("textClazz",textClazz);
        map.put("clazz",clazz);
        map.put("divStyle",divStyle);
        map.put("textStyle",textStyle);
        map.put("style",style);
        map.put("isEdit",isEdit);
        return map;
    }

    public static String getTypeControl(FormFiled fd, String clazz, String textClazz,String style, String textStyle, String controlID, String value, String tableTarget, String naviTitle,String isEdit){
        String str = null;
        switch (fd.getType().toLowerCase()){
            case "text":
                str = control_text(fd,clazz,textClazz,style,textStyle,controlID,value,tableTarget,isEdit);
                break;
            case "textnotitle":
                str = control_textnotitle(fd,clazz,style,controlID,value,tableTarget,isEdit);
                break;
            case "personcheckbox":
                str = control_personcheckbox(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "inttext":
                str = control_inttext(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "literal":
                str = control_literal(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "multitext":
                str = control_multitext(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "select":
                str = control_select(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "checkbox":
                str = control_checkbox(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "datetime":
                str = control_datetime(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "multiselect":
                str = control_multiselect(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "file":
                str = control_file(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "freetext":
                str = control_freetext(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "tele":
                str = control_tele(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "tcode":
                str = control_tcode(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "location":
                str = control_location(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "image":
                str = control_image(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "coppwd":
                str = control_coppwd(fd,clazz,textClazz,style,textStyle,controlID,value,tableTarget,isEdit);
                break;
            case "coppwdnotitle":
                str = control_coppwdnotitle(fd,clazz,textClazz,style,textStyle,controlID,value,tableTarget,isEdit);
                break;
            case "button":
                str = control_button(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "navibackbtn":
                str = control_navibackbtn(fd,clazz,style,controlID,value,tableTarget);
                break;
            case "toptext":
                str = control_toptext(fd,clazz,style,controlID,value,tableTarget,naviTitle);
                break;
            default:
                str = "";
                break;
        }
        return str;
    }

    public static String control_text(FormFiled fd,String Clazz,String textClazz,String Style,String textStyle,String ID,String Value, String tableTarget,String isEdit){
        StringBuffer sb = new StringBuffer();
        String label = "";

        if(StringUtils.isNotBlank(fd.getTitle())){
            String title = fd.getTitle();
            if(title.endsWith(".png")){
                label = "<img src=\""+ Variable.getImageHttp() + title +"\" ";
                if(StringUtils.isNotBlank(tableTarget)){
                    label+="onclick=\"tar(\\'"+tableTarget+"\\')\" />";
                } else {
                    label+="/>";
                }
            } else {
                title = title + "：";
                label = "<label id=\"lit"+ID+"\" class=\""+textClazz+"\" style=\""+textStyle+"\"";
                if(StringUtils.isNotBlank(tableTarget)){
                    label+="onclick=\"tar(\\'"+tableTarget+"\\')\" >"+title+"</label>";
                } else {
                    label+=">"+title+"</label>";
                }
            }

        }

        String control = "";

        if(!"".equals(isEdit) && "0".equals(isEdit)){
            control += "<span id=\""+ID+"\" class=\""+Clazz+"\" style=\""+Style+"\" ";
            if(StringUtils.isNotBlank(tableTarget)){
                control+="onclick=\"tar(\\'"+tableTarget+"\\')\" >";
            } else {
                control+=">";
            }
            if(StringUtils.isNotBlank(Value)){
                control+="value=\""+Value+"\" ";
            }
            control += "</span>";
        } else {
            control += "<input type=\"text\" id=\"" + ID + "\" name=\"" + fd.getName() + "\" ";
            if(StringUtils.isNotBlank(Value)){
                control+="value=\""+Value+"\" ";
            }
            control+="class=\"" + Clazz + "\" style=\"" + Style + "\">";
        }

        sb.append(label);
        sb.append(control);

        return sb.toString();
    }

    public static String control_textnotitle(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget,String isEdit){
        StringBuffer sb = new StringBuffer();

        String control = "";
        if(!"".equals(isEdit) && "0".equals(isEdit)){
            control += "<span id=\""+ID+"\" class=\""+Clazz+"\" style=\""+Style+"\" ";
            if(StringUtils.isNotBlank(tableTarget)){
                control+="onclick=\"tar(\\'"+tableTarget+"\\')\" >";
            } else {
                control+=">";
            }
            if(StringUtils.isNotBlank(Value)){
                control += Value;
            } else {
                control += fd.getTitle();
            }
            control += "</span>";
        } else {
            control += "<input type=\"text\" id=\"" + ID + "\" name=\"" + fd.getName() + "\" ";
            if(StringUtils.isNotBlank(Value)){
                control+="value=\""+Value+"\" ";
            }
            control+="class=\"" + Clazz + "\" style=\"" + Style + "\">";
        }

        sb.append(control);

        return sb.toString();
    }

    public static String control_personcheckbox(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_inttext(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_literal(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_multitext(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_select(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_checkbox(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_datetime(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_multiselect(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_file(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_freetext(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_tele(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_tcode(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_location(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    public static String control_image(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();

        String control = "";
        String title = fd.getTitle();
        String name = fd.getName();
        if(StringUtils.isNotBlank(Value)){
            control += "<img src=\""+Variable.getImageHttp()+Value+"\" ";
        } else if(!title.endsWith(".png")){
            name = title;
            title = null;
            control += "<img src=\""+title+"\" ";
        } else {
            control += "<img src=\""+title+"\" ";
        }
        control += "alt=\""+name+"\" class=\""+Clazz+"\" style=\""+Style+"\" ";
        if(StringUtils.isNotBlank(tableTarget)){
            control += "onclick=\"tar(\\'" + tableTarget + "\\')\" />";
        } else {
            control+= "/>";
        }

        sb.append(control);
        return sb.toString();
    }

    public static String control_button(FormFiled fd,String Clazz,String Style,String ID ,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();
        String control = "";
        String target = fd.getTarget();

        boolean isSubmit = AnalyzeTransferParamsUtils.isSubmit(target);
        if(isSubmit){
            control += "<input type=\"hidden\" name=\"target\" value=\""+target+"\" />";
            control += "<input type=\"submit\" id=\""+ID+"\" ";
            if(Value != null){
                control += "value=\"" + Value + "\" ";
            }else{
                control += "value=\"" + fd.getTitle() + "\" ";
            }
            control += "class=\""+Clazz+"\" style=\""+Style+"\"/>";
        }else{
            control = "<a id=\"" + ID + "\" ";
            if(StringUtils.isNotBlank(tableTarget)){
                control+="onclick=\"tar(\\'"+tableTarget+"\\')\" ";
            } else if(StringUtils.isNotBlank(target)) {
                control+="onclick=\"tar(\\'"+target+"\\')\" ";
            }
            control += "class=\"" + Clazz + "\" style=\"" + Style + "\" >";
            if(StringUtils.isNotBlank(Value)){
                control += Value + "</a>";
            }else{
                control += fd.getTitle() + "</a>";
            }
        }
        sb.append(control);
        return sb.toString();
    }

    public static String control_coppwd(FormFiled fd, String Clazz, String textClazz, String Style, String textStyle,String ID, String Value, String tableTarget,String isEdit){
        StringBuffer sb = new StringBuffer();
        String label = "";

        if(StringUtils.isNotBlank(fd.getTitle())){
            String title = fd.getTitle();
            if(title.endsWith(".png") || title.endsWith(".jpg")){
                label = "<img src=\"" + Variable.getImageHttp() + title+"\">";
            } else {
                title = title + "：";
                label = "<label id=\"lit"+ID+"\" class=\""+textClazz+"\" style=\""+textStyle+"\">"+title+"</label>";
            }
        }

        String control = "";
        control += "<input type=\"password\" id=\"" + ID + "\" name=\"" + fd.getName() + "\" ";
        control+="class=\"" + Clazz + "\" style=\"" + Style + "\">";
        sb.append(label);
        sb.append(control);
        return sb.toString();
    }

    public static String control_coppwdnotitle(FormFiled fd, String Clazz, String textClazz, String Style, String textStyle,String ID, String Value, String tableTarget,String isEdit){
        StringBuffer sb = new StringBuffer();

        String control = "";
        control += "<input type=\"password\" id=\"" + ID + "\" name=\"" + fd.getName() + "\" ";
        control+="class=\"" + Clazz + "\" style=\"" + Style + "\">";
        sb.append(control);
        return sb.toString();
    }

    public static String control_navibackbtn(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget){
        StringBuffer sb = new StringBuffer();
        String control = "";

        control = "<img id=\""+ID+"\" src=\""+Variable.getImageHttp()+"to_left.png\" class=\""+Clazz+"\" style=\""+Style+"\" onclick=\"javascript:history.back(-1);\" />";

        sb.append(control);
        return sb.toString();
    }

    public static String control_toptext(FormFiled fd,String Clazz,String Style,String ID,String Value, String tableTarget,String naviTitle){
        StringBuffer sb = new StringBuffer();
        String control = "";

        control = "<span id=\""+ID+"\"  class=\""+Clazz+"\" style=\""+Style+"\">";
        control += naviTitle;
        control += "</span>";
        sb.append(control);
        return sb.toString();
    }
}