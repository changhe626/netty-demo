package cn.onyx.connect;

import java.io.Serializable;

/**
 * @author zhangke
 */
public class Request implements Serializable{

    private static final long SerialVersionUID=1L;

    private int id;
    private String name;
    private String  requestMessage;

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


    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}
