package com.redmany.ram.common;

import com.redmany.ram.model.Variable;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *
 * Created by hy on 2017/6/26.
 */
public class AnalyzeTransferParamsUtils {

    public static  final String FORMNAME ="formNameoOrId";
    public static  final String CONDITION ="CONDITION//";
    public static  final String OTHER="OTHER//";
    public static  final String SUBMIT ="SUBMIT//";

    public static boolean isSubmit(String target){
        int index1 = target.indexOf("submit:");
        int index2 = target.indexOf("login:");
        if(index1 > -1 || index2 > -1){
            return true;
        }else {
            return false;
        }
    }

    /***
     * @param transferParams TransferParams
     * @return
     */
    public static String formatter(HttpServletRequest request, String transferParams){
        String target2 = null;
        if(StringUtils.isBlank(transferParams)){
            return "";
        }
        //MyApp = (MyApplication) context.getApplicationContext();
        checkSomethingEspecial(transferParams);
        target2 = transferParams;
        int begin = 0;
        char signBegin = '{';
        char signEnd = '}';
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < transferParams.length(); i++) {
            char that = transferParams.charAt(i);
            if (that == signBegin) {
                begin = i;
            } else if (signEnd == that) {
                String value= transferParams.substring(begin , i+1);
                String key = transferParams.substring(begin + 1, i);
                String data ="";
                if (key.equals("cacheId")) {
                    //data =MyApp.getString("UserID");
                    data = request.getSession().getAttribute("UserID").toString();
                }else if(key.startsWith(Variable.getCachePrefix())){
                    key = key.replace(Variable.getCachePrefix(), "");
                    //data = MyApp.cacheValue.get(key);
                }else{
                    /*if (saveDatafieldsValue!=null) {
                        data = saveDatafieldsValue.GetFieldValue(key);
                    }*/
                }
                target2  = target2.replace(value,data);
            }

        }
        return null;
    }

    /**
     * 检测是否有需要跨页面使用的值
     * @param transferParams
     */
    private static String checkSomethingEspecial(String transferParams) {
        String tpAfter = "";
        if (transferParams.contains(Variable.getGlobalFormNameStart())) {
            String[] tps = transferParams.split("\\[\\^\\]");
            List<String> tpList = new ArrayList<>();
            for (String tp : tps) {
                tpList.add(tp);
            }
            Iterator<String> it = tpList.iterator();
            while (it.hasNext()) {
                String oneTP = it.next();
                if (oneTP.startsWith(Variable.getGlobalFormNameStart())) {
                    String kvsToSave = oneTP.replace(Variable.getGlobalFormNameStart(), "");
                    saveSomethingEspecial(kvsToSave);
                    it.remove();
                    break;
                } else {
                    continue;
                }
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < tpList.size(); i++) {
                String tp = tpList.get(i);
                if (i>0) {
                    sb.append("[^]");
                }
                sb.append(tp);
            }
            tpAfter = sb.toString();
        }
        return tpAfter;
    }

    /**
     * 存储需要跨页面使用的值
     * @param kvsToSave
     */
    private static void saveSomethingEspecial(String kvsToSave) {
        String[] kvs = kvsToSave.split("\\,");
        List<String> kvList = Arrays.asList(kvs);
        Iterator<String> it = kvList.iterator();
        while (it.hasNext()) {
            String oneKV = it.next();
            String[] keys = oneKV.split("\\=");
            String key1 = keys[0];
            String key2 = keys[1];
            String data ="";
            /*if (saveDatafieldsValue!=null) {
                try {
                    data = saveDatafieldsValue.GetFieldValue(key2 );
                    MyApp.cacheValue.put(key1, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/

        }
    }


    public static Map<String,Object> getFormNameOrIdAndCondition(HttpServletRequest request, String transferParam){
        Map<String,Object> mMap =new HashMap<>();
        mMap.put(CONDITION,"");
        mMap.put(OTHER,"");
        mMap.put(FORMNAME,"");
        if(StringUtils.isBlank(transferParam)){
            return mMap;
        }
        String replace = formatter(request,transferParam);
        String[] transferParames =replace.split(":");
        int length = transferParames.length;
        if (length>0) {
            String formNameOrId = transferParames[0];
            mMap.put(FORMNAME,formNameOrId);
        }
        if (length > 1) {
            String condition = transferParames[1];
            splitCondition(condition,mMap);
        }
        return mMap;
    }

    /**
     * 设置传递的条件、值等
     * @param condition
     * @param mMap
     */
    private static void splitCondition(String condition, Map<String, Object> mMap) {
        String [] conditions =  condition.split("\\$");
        mMap.put(CONDITION,conditions[0]);
        if (conditions.length>1) {
            mMap.put(OTHER,conditions[1]);
        }
    }


}
