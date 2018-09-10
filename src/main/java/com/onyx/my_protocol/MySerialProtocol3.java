package com.onyx.my_protocol;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * 自定义的序列化协议3
 * 把对象转换成byte
 */
public class MySerialProtocol3 {



    public static void main(String[] args) {
        /*
        int id = 101;
        double age = 21.1;

        //netty3 中的
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        buffer.writeInt(id);
        buffer.writeDouble(80.1);

        byte[] bytes = new byte[buffer.writerIndex()];
        buffer.readBytes(bytes);

        System.out.println(Arrays.toString(bytes));

        "abc".getBytes();

        //================================================
        ChannelBuffer wrappedBuffer = ChannelBuffers.wrappedBuffer(bytes);
        System.out.println(wrappedBuffer.readInt());
        System.out.println(wrappedBuffer.readDouble());*/
    }

    /**
     * netty4  把对象转换成byte
     */
    static class Netty4Encode extends MessageToByteEncoder {
        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            byte[] body = convertToBytes(msg);  //将对象转换为byte，伪代码，具体用什么进行序列化，你们自行选择。可以使用我上面说的一些
            int dataLength = body.length;  //读取消息的长度
            out.writeInt(dataLength);  //先将消息长度写入，也就是消息头
            out.writeBytes(body);  //消息体中包含我们要发送的数据
        }
    }


    /**
     * netty4
     */
    static class Netty4Decode extends ByteToMessageDecoder{
        //判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 byte+byte+int = 1+1+4 = 6
        private static final int HEAD_LENGTH = 6;
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (in.readableBytes() < HEAD_LENGTH) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
                return;
            }
            in.markReaderIndex();                  //我们标记一下当前的readIndex的位置
            int dataLength = in.readInt();       // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
            if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
                ctx.close();
            }
            if (in.readableBytes() < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
                in.resetReaderIndex();
                return;
            }
            byte[] body = new byte[dataLength];  //  嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
            in.readBytes(body);  //
            Object o = convertToObject(body);  //将byte数据转化为我们需要的对象。伪代码，用什么序列化，自行选择
            out.add(o);
        }
    }


    private static  byte[] convertToBytes(Object msg) {
        return null;
    }

    private static Object convertToObject(byte[] body) {
        return null;
    }



}


