package com.redmany.ram.model.wechat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hy on 2017/8/3.
 *
 */
public class Singleton {

    // 缓存Map
    // access_token
    // expires_in
    // ticket
    private Map<String, String> map = new HashMap<>();

    private Singleton() {
    }

    private static Singleton single = null;

    // 静态工厂方法
    public static Singleton getInstance() {
        if (single == null) {
            single = new Singleton();
        }
        return single;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static Singleton getSingle() {
        return single;
    }

    public static void setSingle(Singleton single) {
        Singleton.single = single;
    }

    /**
     * 开启线程，设置Singleton为空
     */
    public static void initThread(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //睡眠7000秒
                    Thread.sleep(7000*1000);
                    single=null;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
