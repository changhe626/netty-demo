package cn.onyx.connect;

import java.io.Serializable;

public class Response implements Serializable{

    private static final long SerialVersionUID=1L;

    private int id;
    private String name;
    private String  responseMessage;

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

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
