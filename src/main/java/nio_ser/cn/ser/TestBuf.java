package nio_ser.cn.ser;

import java.io.Serializable;
import java.util.ArrayList;

public class TestBuf implements Serializable {

    private int id;
    private String url;
    private ArrayList<String> name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }
}