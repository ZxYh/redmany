/**
 * Created by Administrator on 2017/6/15.
 */
public class Test {

    public static void main(String[] args) {
        String orderNo = "118_20170828150535";
        outPrint(orderNo);
    }

    public static void outPrint (String str){
        int i = str.indexOf("_");
        if(i > 0){
            String s = str.substring(0,i);
            System.out.println(s);
        }
    }

}
