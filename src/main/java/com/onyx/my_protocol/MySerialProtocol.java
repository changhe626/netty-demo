package com.onyx.my_protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 自定义的序列化协议
 */
public class MySerialProtocol {

    public static void main(String[] args) throws IOException {
        int id = 101;
        int age = 21;

        //写出去
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(int2byte(id));
        stream.write(int2byte(age));

        byte[] bytes = stream.toByteArray();
        System.out.println(Arrays.toString(bytes));

        //序列化回来
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        byte[] bytesId = new byte[4];
        inputStream.read(bytesId);
        System.out.println("id:" + byte2int(bytesId));

        byte[] bytesAge = new byte[4];
        inputStream.read(bytesAge);
        System.out.println("age:" + byte2int(bytesAge));


    }


    /**
     * int 变成byte字节数组,大端字节序列(先写高位,再写低位)
     * 还有低端的
     * 百度下  大小端字节区别,怎么获取int的字节
     *
     * @param i
     * @return
     */
    public static byte[] int2byte(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >> 3 * 8);
        bytes[1] = (byte) (i >> 2 * 8);
        bytes[2] = (byte) (i >> 1 * 8);
        bytes[3] = (byte) (i >> 0 * 8);
        return bytes;
    }

    /**
     * byte[] 变成int,上面是大端,下面也是大端的转化
     *
     * @param bytes
     * @return
     */
    public static int byte2int(byte[] bytes) {
        return (bytes[0] << 3 * 8) |
                (bytes[1] << 2 * 8) |
                (bytes[2] << 1 * 8) |
                (bytes[3] << 0 * 8);
    }

}
