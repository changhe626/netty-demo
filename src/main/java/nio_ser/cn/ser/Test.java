package nio_ser.cn.ser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author zk
 * @Description:
 * @date 2018-07-25 11:45
 */
public class Test {

    public static void main(String[]args)throws IOException {

        TestBuf testBuf1 = new TestBuf();
        testBuf1.setId(1);
        testBuf1.setUrl("www.baidu.com");
        ArrayList<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        testBuf1.setName(list);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream1 = new ObjectOutputStream(outputStream);
        outputStream1.writeObject(testBuf1);
        System.out.println("Serializable====="+outputStream.toByteArray().length);
    }
}
