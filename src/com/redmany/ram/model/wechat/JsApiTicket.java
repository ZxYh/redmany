package com.redmany.ram.model.wechat;

/**
 * Created by Administrator on 2017/8/3.
 */
public class JsApiTicket {
    private String ticket;
    private int expiresIn;
    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    public int getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
