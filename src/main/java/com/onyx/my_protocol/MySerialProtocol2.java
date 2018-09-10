package com.onyx.my_protocol;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 自定义的序列化协议2
 */
public class MySerialProtocol2 {

    public static void main(String[] args) {

        int id = 101;
        int age = 21;

        //必须制定大小,而且不会自动扩容....
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(id);
        buffer.putInt(age);
        byte[] array = buffer.array();
        System.out.println(Arrays.toString(array));


        ByteBuffer allocate = ByteBuffer.wrap(array);
        System.out.println(allocate.getInt()+"~~"+allocate.getInt());

    }
}
