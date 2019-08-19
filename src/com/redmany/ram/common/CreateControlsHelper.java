package com.redmany.ram.common;

import com.redmany.ram.dao.*;
import com.redmany.ram.model.Form;
import com.redmany.ram.model.FormFiled;
import com.redmany.ram.model.OpType;
import com.redmany.ram.model.Replacer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/6/15.
 */
public class CreateControlsHelper {

    private FormDao formDao = new FormDao();
    private FormFiledDao formFiledDao = new FormFiledDao();
    private ReplacerDao replacerDao = new ReplacerDao();
    private OaAttributeDao oaAttributeDao = new OaAttributeDao();
    private TableDao tableDao = new TableDao();
    private DSourceHelper dSourceHelper = new DSourceHelper();


    public String CreateContorlItemsInPage(HttpServletRequest req, String CompanyId, String FormName, OpType opType, String[] strReadOnly, String[] strUnVisible)throws IOException{
        System.out.println("into CreateContorlItemsInPage");
        String contorlStr = "";
        String modifyFields = "";
        //获取Form表数据
        Form form = formDao.getForm(CompanyId,FormName);
        System.out.println("form:..."+form);
        //获取FormField集合
        List<Map<String,Object>> formFieldList = formFiledDao.getFormFiled(CompanyId,FormName);
        System.out.println("formfieldlist:..."+formFieldList);
        //判断Form表与FormField集合是否为空
        if(form!=null&&formFieldList!=null&&formFieldList.size()>0){
            //遍历FormField集合
            for (Map<String,Object> map : formFieldList) {
                System.out.println("formfieldmap:..."+map);
                //如果为修改操作，过滤非修改字段
                modifyFields = form.getModify_fields();
                FormFiled fd = new FormFiled();
                //将Map集合转实体类
                fd = formFiledDao.toFormFiled(map);

                if(opType == OpType.Insert){
                    if(fd.getShowType()!=null&&!"".equals(fd.getShowType())&&!"NEWFORM".equals(fd.getShowType().toUpperCase())){
                        continue;
                    }
                } else if(opType==OpType.Update){
                    if(fd.getShowType()==null || "".equals(fd.getShowType()) || "LISTMODIFYFORM".equals(fd.getShowType().toUpperCase())){
                        if(!"1".equals(form.getMenuType())){
                            boolean flag = false;
                            String[] mfields = modifyFields.toLowerCase().split(",");
                            for (String str : mfields){
                                if(str.equals(fd.getName().toLowerCase())){
                                    flag=true;
                                    break;
                                }
                            }
                            if(!flag){
                                fd.setIsReadOnly(true);
                            }
                        }
                    }else{
                        continue;
                    }
                } else if(opType==OpType.Select){
                    if(fd.getShowType()==null || "".equals(fd.getShowType()) || "LISTFORM".equals(fd.getShowType())){
                        boolean flag = false;
                        String Search_fields = form.getSearch_fields();
                        String[] sfields = Search_fields.toLowerCase().split(",");
                        for (String str : sfields){
                            if(str.equals(fd.getName().toLowerCase())){
                                flag = true;
                                break;
                            }
                        }
                        if(!flag){
                            continue;
                        }
                    }else{
                        continue;
                    }
                } else if(opType==OpType.Look){
                    if(fd.getShowType()==null || "".equals(fd.getShowType()) || "LISTFORM".equals(fd.getShowType())){
                        fd.setIsReadOnly(true);
                    }else{
                        continue;
                    }
                }

                if(strReadOnly != null){
                    for (String str : strReadOnly){
                        if(str.equals(fd.getName())){
                            fd.setIsReadOnly(true);
                            break;
                        }
                    }
                }
                if(strUnVisible != null){
                    boolean flag = false;
                    for (String str: strUnVisible){
                        if(str.equals(fd.getName())){
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        continue;
                    }
                }
                if(strReadOnly != null){
                    contorlStr += CreateControlByType(req,CompanyId,form,fd,opType,strReadOnly);
                }else{
                    if(!"checkbox".equals(fd.getType())){
                        contorlStr += CreateControlByType(req,CompanyId,form,fd,opType,strReadOnly);
                    }
                    if("checkbox".equals(fd.getType())){
                        contorlStr += CreateControlByType(req,CompanyId,form,fd,opType,strReadOnly);
                    }
                }
            }
        }

        return contorlStr;
    }

    public String CreateControlByType(HttpServletRequest req,String CompanyId,Form form, FormFiled fd, OpType opType,String[] strReadOnly)throws IOException{
        System.out.println("into CreateControlByType");
        StringBuffer sb = new StringBuffer();
        String attributeId = fd.getAttributeId().toString();
        String label = "";
        String control = "";
        String controlID = fd.getName() + fd.getId().toString();
        String divClazz = "";
        String clazz = "";
        String divStyle = "";
        String style = "";
        if(attributeId != null && !"".equals(attributeId) && !"0".equals(attributeId)){
            System.out.println("attributeId:.."+attributeId);
            Map<String,Object> oaAttrMap = oaAttributeDao.getOaAttribute(CompanyId,attributeId).get(0);
            Object obj = oaAttrMap.get("wapAttribute");
            String attr = "";
            if(obj != null){
                attr = oaAttrMap.get("").toString();
                if(attr != null && !"".equals(attr)){
                    String[] attrArr = attr.split("\\^");
                    for (String aa : attrArr){
                        String[] type = aa.split("=");
                        String[] item = type[1].split(",");
                        switch (type[0].toUpperCase()){
                            case "DIVCLASS":
                                for(String dc : item){
                                    divClazz += dc + " ";
                                }
                            case "CLASS":
                                for(String c : item){
                                    clazz += c + " ";
                                }
                                break;
                            case "DIVSTYLE":
                                for(String ds : item){
                                    divStyle += ds + ";";
                                }
                            case "STYLE":
                                for(String s : item){
                                    style += s + ";";
                                }
                                break;
                        }
                    }
                }
            }
        }
        sb.append("<div class=\""+divClazz+"\" style=\""+divStyle+"\">");
        if(opType != OpType.Insert){

        }
        String name = fd.getName();
        //创建控件
        switch (fd.getType().toLowerCase()){
            case "text":
                control += "<input type=\"text\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                //if(js事件){
                //  control+="onchange=\""+js()+"\" ";
                // }
                control+="class=\"" + clazz + "\" style=\"" + style + "\">";
                break;
            case "personcheckbox":
                control += "<input type=\"text\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                //control+="onclick=\""+js()+"\" ";
                control+="class=\"" + clazz + "\" style=\"" + style + "\">";
                break;
            case "inttext":
                control += "<input type=\"text\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                //if(js事件){
                //  control+="onblur=\""+js()+"\" ";
                // }
                control+="class=\"" + clazz + "\" style=\"" + style + "\">";
                break;
            case "literal":
                break;
            case "multitext":
                control += "<textarea id=\"" +controlID+ "\" name=\"" + name + "\">";
                //if(value){
                //  control+="value";
                // }
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                control += "</textarea>";
                break;
            case "select":
                System.out.println("select");
                control += "<select id=\"" + controlID + "\" name=\"" + name + "\" ";
                /*if(fd.getIsReadOnly()){
                  control+="disabled=\"disabled\" ";
                }*/
                String select = GetReplacerSource(req,CompanyId,fd.getData_replacer());
                control += "class=\"" + clazz + "\" style=\"" + style + "\">";
                if(select!=null&&!"".equals(select)){
                    control += select;
                }
                control += "</select>";
                break;
            case "checkbox":
                //for(){
                    control += "<input type=\"checkbox\" id=\"" +controlID+ "\" name=\"" +name+ "\" ";
                    //control += "value=\"" +dataid+ "\" ";
                    //if(readonly){
                    //  control+="disabled=\"disabled\" ";
                    // }
                    //if(checked){
                    //  control+="checked=\"checked\" ";
                    // }
                    control += "class=\"" + clazz + "\" style=\"" +style+ "\">";
                    //control += data;
                // }
                break;
            case "datetime":
                control += "<input type=\"text\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                control += "class=\"Wdate " + clazz + "\" style=\"" +style+ "\">";
                break;
            case "multiselect":
                break;
            case "file":
                control += "<input type=\"file\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                control+="class=\"" + clazz + "\" style=\"" + style + "\">";
                break;
            case "freetext":
                break;
            case "tele":
                break;
            case "tcode":
                control += "<input type=\"text\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                control+="class=\"" + clazz + "\" style=\"" + style + "\">";
                break;
            case "location":
                control += "<input type=\"text\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="disabled=\"disabled\" ";
                // }
                control+="class=\"" + clazz + "\" style=\"" + style + "\">";
                break;
            case "image":
                control += "<input type=\"image\" id=\"" + controlID + "\" name=\"" + name + "\" ";
                //if(value){
                //  control+="value=\""+value+"\" ";
                //}
                //if(readonly){
                //  control+="readonly=\"readonly\" ";
                // }
                control+="class=\"" + clazz + "\" style=\"" + style + "\">";
                break;
            default:
                control="";
                break;
        }
        if(!"".equals(control)){
            String litID = "lit"+ controlID;
            String litText = "";
            if(!"".equals(fd.getTitle())){
                litText = fd.getTitle() + ":";
            }
            label += "<label id=\"lit" +controlID+ "\" class=\"\" style=\"\">"+litText+"</label>";
            sb.append(label);
            sb.append(control);
        }
        sb.append("</div>");

        return sb.toString();
    }

    public String CreateTableControl(HttpServletRequest req, String CompanyId,String FormName) throws IOException{
        System.out.println("into CreateTableContorl");
        String contorlStr = "";
        //获取Form表数据
        Form form = formDao.getForm(CompanyId,FormName);
        System.out.println("form:..."+form);
        String Table_name = form.getTable_name();
        String List_fields = form.getList_fields();
        String Sort_fields = form.getSort_fields();
        System.out.println("Table_name:..."+Table_name);
        System.out.println("List_fields:..."+List_fields);
        System.out.println("Sort_fields:..."+Sort_fields);
        if(!"".equals(Table_name)&&!"".equals(List_fields)){
            System.out.println("togettabletitle");
            //获取FormFiled表数据
            List<Map<String,Object>> fdList = formFiledDao.getFormFiled(CompanyId,FormName);
            //将List_fields数据转入数组
            String[] f = List_fields.split(",");
            //存放筛选出来的字段
            List<String> fieldName = new ArrayList<String>();
            //存放筛选出来的字段的Title值
            List<String> fieldTitle = new ArrayList<String>();
            //遍历FormFiled表数据 将匹配的字段筛选出来
            for (Map<String,Object> m : fdList){
                for(String ff : f){
                    if(m.get("Name").toString().equals(ff)){
                        fieldName.add(m.get("Name").toString());
                        fieldTitle.add(m.get("Title").toString());
                    }
                }
            }
            System.out.println("fieldName:..."+fieldName);
            System.out.println("fieldTitle:..."+fieldTitle);
            //拼接表头
            contorlStr += "<tr>";
            for (String key : fieldTitle){
                contorlStr += "<th>"+key+"</th>";
            }
            contorlStr += "<th>操作</th>";
            contorlStr += "</tr>";
            //获取数据集合
            List<Map<String,Object>> dataList = tableDao.getTable(CompanyId,FormName,List_fields,Table_name,Sort_fields);
            System.out.println(dataList);
            //遍历拼接表数据
            if(dataList!=null&&dataList.size()>0){
                for(Map<String,Object> Data : dataList){
                    contorlStr += "<tr>";
                    //遍历每一行每一列
                    for(int i = 0; i < fieldName.size(); i++){
                        //第n行第i列的数据
                        String mapData = Data.get(fieldName.get(i)).toString();
                        //检测该字段是否在替换集合中
                        for(Map<String,Object> checkReplaceMap : fdList){
                            if(fieldName.get(i).equals(checkReplaceMap.get("Name").toString())){
                                Object dataRep = checkReplaceMap.get("data_replacer");
                                if(dataRep!=null){
                                    Replacer replacer = replacerDao.getReplacer(CompanyId,dataRep.toString());
                                    List<Map<String,Object>> replacerData = dSourceHelper.GetReplacerBind(req,replacer);
                                    System.out.println("replacerData:..."+replacerData);
                                    for(Map<String,Object> repMap : replacerData){
                                        if(repMap.get("value").toString().equals(mapData)){
                                            mapData = repMap.get("name").toString();
                                        }
                                    }
                                }
                            }
                        }
                        contorlStr += "<td>"+mapData+"</td>";
                    }
                    contorlStr += "<td></td>";
                    contorlStr += "<tr>";
                }
            }
        }
        return contorlStr;
    }

    public String GetReplacerSource(HttpServletRequest req,String CompanyId, String Replacername)throws IOException{
        System.out.println("into GetReplacerSource");
        String str = "";
        Replacer replacer = replacerDao.getReplacer(CompanyId,Replacername);
        System.out.println(replacer);
        List<Map<String,Object>> replaceList = dSourceHelper.GetReplacerBind(req,replacer);
        if(replaceList!=null&&!"".equals(replaceList)&&replaceList.size()>0){
            str += "<option value=\"0\">==请选择==</option>";
            for (Map<String,Object> map : replaceList){
                String value = map.get("value").toString();
                String name = map.get("name").toString();
                str += "<option value=\""+value+"\">"+name+"</option>";
            }
        }
        return str;
    }

}
