package com.redmany.ram.common;

import java.security.MessageDigest;

/**
 * Created by hy on 2017/6/7.
 * 密码加密
 */
public class MD5 {

    public static String MD5(String inStr) {
        byte[] bytes = new byte[inStr.length() * 2];
        for(int i = 0; i < inStr.length(); i++){
            int code = inStr.charAt(i) & 0xffff;
            bytes[i * 2] = (byte)(code & 0x00ff);
            bytes[i * 2 + 1] = (byte)(code >> 8);
        }
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(bytes);
            byte[] after = md5.digest();

            for (int i = 0; i < after.length; i++) {
                String hex = Integer.toHexString(0xff & after[i]);
                if (hex.length() == 1)
                    hex = "" + hex;
                sb.append(hex);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    // 测试主函数
    public static void main(String args[]) {
        String s = new String("123");
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + MD5(s));
    }
}
