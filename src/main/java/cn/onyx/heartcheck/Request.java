package cn.onyx.heartcheck;

import java.io.Serializable;

public class Request implements Serializable{

    private int id;
    private String name;
    private String  msg;

    public Request() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
