package com.onyx.my_protocol;

import java.util.Arrays;

/**
 * 自定义序列化test
 * netty  3
 */
public class MySerialProtocol4{

    public static void main(String[] args) {
        //序列化
        MySerialProtocol4Bean demo = new MySerialProtocol4Bean();
        demo.setId(156L);
        demo.setName("zhaojun");
        demo.getSkills().add("A");

        byte[] bytes = demo.getBytes();
        System.out.println("序列化后的字节是:"+ Arrays.toString(bytes));

        //反序列化
        MySerialProtocol4Bean demo2 = new MySerialProtocol4Bean();
        demo2.readFromBytes(bytes);
        System.out.println(demo2.getId());
        System.out.println(demo2.getName());
        System.out.println(demo2.getSkills());

    }
}

