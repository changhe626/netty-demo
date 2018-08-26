package org.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

//.proto的文件名和内容没有什么关系的....生成的文件很大....
public class ToJava {


    /**
     * protobuf是一种数据交换的格式，以二进制的格式进行数据交换，主要用于网络传输、配置文件、
     * 数据存储等诸多领域
     * 下面我们就将指定格式的信息转换成字节形式数据，然后将字节形式数据恢复成指定格式的信息
     * 读者可以简单的看下.proto文件生成的BatteryData类的结构
     */
    public static void main(String[] args) {
        System.out.println("===== 构建一个GPS模型开始 =====");
        //获得BatteryData对象
        //这里的BatteryData对象构造器被私有化，我们通过通过BatteryData的内部类Builder来构建builder
        BatteryData.gps_data.Builder builder = BatteryData.gps_data.newBuilder();
        //BatteryData类未提供相关属性的set方法，而Student的内部类builder提供了构建Student相关属性的set方法
        builder.setAltitude(1);
        builder.setDataTime("2017-12-17 16:21:44");
        builder.setGpsStatus(1);
        builder.setLat(39.123);
        builder.setLon(120.112);
        builder.setDirection(30.2F);
        builder.setId(100L);

        BatteryData.gps_data data = builder.build();
        //这里你我们将封装有数据的对象实例，转换为字节数组，用于数据传输、存储等
        byte[] bytes = data.toByteArray();

        System.out.println(data.toString());
        System.out.println("===== 构建GPS模型结束 =====");

        System.out.println("===== gps Byte 开始=====");
        for(byte b : bytes){
            System.out.print(b);
        }
        System.out.println("\n" + "bytes长度" + data.toByteString().size());
        System.out.println("===== gps Byte 结束 =====");

        System.out.println("===== 使用gps 反序列化生成对象开始 =====");
        BatteryData.gps_data gd = null;
        try {
            //我们可以将该数据进行数据传输或存储，这里至于用什么技术传输就根据具体情况而定
            //将字节数据转换为对应的对象实例
            gd = BatteryData.gps_data.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.print(gd.toString());
        System.out.println("===== 使用gps 反序列化生成对象结束 =====");



        System.out.println("===== 使用gps 转成json对象开始 =====");
        String jsonString = "";

        try {
            jsonString = JsonFormat.printer().print(gd);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        System.out.println(jsonString.toString());
        System.out.println("json数据大小：" + jsonString.getBytes().length);
        System.out.println("===== 使用gps 转成json对象结束 =====");

    }



}
