package com.example.speechdemo.data.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yangyong on 20-5-29.
 */

public class AccountInfo extends RealmObject {
    // 类别:吃喝,住,行,人情,转账,购物,其他
    // 时间
    // 内容
    // 金额

    @PrimaryKey
    private String time;

    int accountType;

    private String content;

    private int monney;

    public AccountInfo() {}

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMonney() {
        return monney;
    }

    public void setMonney(int monney) {
        this.monney = monney;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountType=" + accountType +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", monney='" + monney + '\'' +
                '}';
    }
}
