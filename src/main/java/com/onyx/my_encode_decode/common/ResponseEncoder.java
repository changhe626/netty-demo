package com.onyx.my_encode_decode.common;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

/**
 *
 * 响应编码对象
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
public class ResponseEncoder extends OneToOneDecoder{

    @Override
    protected Object decode(ChannelHandlerContext context, Channel channel, Object o) throws Exception {
        Response response = (Response) o;
        //一个对象转换成二进制数据发出去
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        //包头,4个字节
        buffer.writeInt(ConstantValue.FLAG);
        //模块号
        buffer.writeShort(response.getModule());
        //cmd
        buffer.writeShort(response.getCmd());
        //状态码
        buffer.writeInt(ConstantValue.SUCCESS);
        //长度,最好不要这样,在实体类中提供方法
        //buffer.writeInt(response.getData().length);
        buffer.writeInt(response.getLength());
        //data
        if(response.getData()!=null){
            buffer.writeBytes(response.getData());
        }
        return buffer;
    }
}
