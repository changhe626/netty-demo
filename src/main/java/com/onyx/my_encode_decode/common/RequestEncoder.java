package com.onyx.my_encode_decode.common;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

/**
 *
 * 请求编码对象
 *
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * | 包头	| 模块号  | 命令号 |  长度  |   数据  |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节short
 * 命令号2字节short
 * 长度4字节(描述数据部分字节长度)
 *
 */
public class RequestEncoder extends OneToOneDecoder{

    @Override
    protected Object decode(ChannelHandlerContext context, Channel channel, Object o) throws Exception {
        Request request = (Request) o;
        //一个对象转换成二进制数据发出去
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        //包头,4个字节
        buffer.writeInt(ConstantValue.FLAG);
        //模块号
        buffer.writeShort(request.getModule());
        //cmd
        buffer.writeShort(request.getCmd());
        //长度,最好不要这样,在实体类中提供方法
        //buffer.writeInt(request.getData().length);
        buffer.writeInt(request.getLength());
        //data
        if(request.getData()!=null){
            buffer.writeBytes(request.getData());
        }
        return buffer;
    }
}
