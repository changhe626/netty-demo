package cn.onyx.helloworld2;

import java.io.Serializable;

/**
 * 要实现Serializable接口
 * 因为要在网络上传输...
 */
public class User implements Serializable{

    private static final long SerialVersionUID=1L;

    private int id;
    private String name;


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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
