package com.redmany.ram.common;

import com.redmany.ram.dao.OaCopModelDao;
import com.redmany.ram.model.Variable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/3.
 */
public class TargetManager {

    /**
     * 跳转页面所需的formName
     */
    private static String formName = "";
    /**
     * 跳转页面所需的showType
     */
    private static String showType = "";
    /**
     * 附带传输条件
     */
    private static String transfer = "";

    private static OaCopModelDao oaCopModelDao = new OaCopModelDao();

    public static List<Map<String,Object>> judge(String target) {

        if(StringUtils.isBlank(target)){
            return null;
        }

        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();


        String[] targets = target.split("\\[\\^\\]");
        String oneTarget = targets[0];
        if (matchTarget(oneTarget, "goto") && targets.length == 1) {
            getFormNameAndShowType(oneTarget);
            if("copForm".equalsIgnoreCase(showType)){
                String CompanyId = Variable.getCompanyId();
                List<Map<String,Object>> data = oaCopModelDao.getOaCopModel(CompanyId,formName);
                String naviTitle = data.get(0).get("title").toString();
                if(data != null && data.size() >0){
                    for (Map<String,Object> mapData : data){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("CompanyId", CompanyId);
                        map.put("FormName",mapData.get("copFormName").toString());
                        map.put("ShowType",mapData.get("showType").toString());
                        map.put("Attribute",ObjIsNull(mapData.get("attributeId"),"0"));
                        map.put("naviTitle",naviTitle);
                        mapList.add(map);
                    }
                }
            }else{
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("CompanyId", Variable.getCompanyId());
                map.put("FormName",formName);
                map.put("ShowType",showType);
                map.put("Transfer",transfer);
                mapList.add(map);
            }
        } else if(matchTarget(oneTarget, "dismissDialog") && targets.length == 1){

        } else if (matchTarget(oneTarget, "alipay") && targets.length == 1) {

        } else if (matchTarget(oneTarget, "wxpay") && targets.length == 1) {

        } else if (matchTarget(oneTarget, "addshoppingcart") && targets.length == 1) {

        } else if(matchTarget(oneTarget,"scan") && targets.length == 1){
            //String url = "test.html";
        } else {
            for (String item : targets) {
                if (matchTarget(item, "goto")) {
                    getFormNameAndShowType(item);
                    if("copForm".equalsIgnoreCase(showType)){
                        String CompanyId = Variable.getCompanyId();
                        List<Map<String,Object>> data = oaCopModelDao.getOaCopModel(CompanyId,formName);
                        String naviTitle = data.get(0).get("title").toString();
                        if(data != null && data.size() >0){
                            for (Map<String,Object> mapData : data){
                                Map<String,Object> map = new HashMap<String,Object>();
                                map.put("CompanyId", CompanyId);
                                map.put("FormName",mapData.get("copFormName").toString());
                                map.put("ShowType",mapData.get("showType").toString());
                                map.put("Attribute",ObjIsNull(mapData.get("attributeId"),"0"));
                                map.put("naviTitle",naviTitle);
                                mapList.add(map);
                            }
                        }
                    }else{
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("CompanyId", Variable.getCompanyId());
                        map.put("FormName",formName);
                        map.put("ShowType",showType);
                        map.put("Transfer",transfer);
                        mapList.add(map);
                    }
                } else if (matchTarget(item, "finish")) {

                } else if (matchTarget(item, "finishRefresh")) {

                } else if (matchTarget(item, "dismissDialog")) {

                }
            }
        }

        return mapList;
    }

    /**
     * 动作目标匹配
     *
     * @param wholeTarget 完整的target
     * @param targetLabel 动作标记
     * @return
     */
    private static boolean matchTarget(String wholeTarget, String targetLabel) {
        boolean result = false;
        String wholeLabel = wholeTarget.split(":")[0];
        if (wholeLabel.equalsIgnoreCase(targetLabel)) {
            result = true;
        }
        return result;
    }

    /**
     * 获取当前的formName和showType
     *
     * @param target defineFields 的属性值
     */
    private static void getFormNameAndShowType(String target) {
        try {
            String[] label = target.split(":");
            String[] fs = label[1].split(",");
            formName = fs[0];
            showType = fs[1];
            if(fs.length > 2){
                transfer = fs[2];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断Object类型是否为null
     * @param obj
     * @param trueValue 若为Null 返回该值
     * @return
     */
    public static String ObjIsNull(Object obj, String trueValue){
        String result;
        if(obj==null){
            result = trueValue;
        }else{
            result = obj.toString();
        }
        return result;
    }
}
