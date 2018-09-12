package com.onyx.my_encode_decode.common;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 解码器
 * FrameDecoder可以协助我们解决黏包,分包问题
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
 */
public class RequestDecoder extends FrameDecoder{

    /**
     * 数据包基本长度
     */
    public static int baseLength=4+2+2+4;

    @Override
    protected Object decode(ChannelHandlerContext context, Channel channel, ChannelBuffer buffer) throws Exception {

        //可读长度大于基本长度
        if(buffer.readableBytes()>=baseLength){
            //记录包头的开始的位置
            int beginReader=buffer.readerIndex();

            //包头
            while (true){
                int anInt = buffer.readInt();
                if(anInt==ConstantValue.FLAG){
                    break;
                }
            }
            //模块号
            short module = buffer.readShort();
            //命令号
            short cmd = buffer.readShort();
            //长度
            int length = buffer.readInt();

            //请求包的所有数据到了吗?
            if(buffer.readableBytes()<length){
                //数据还没有到齐
                //还原刚才读取的数据...要不然下次再去就不是这样的了
                //还原读指针
                buffer.readerIndex(beginReader);
                return null;
            }
            //数据
            byte[] data = new byte[length];
            buffer.readBytes(data);

            Request request = new Request();
            request.setCmd(cmd);
            request.setData(data);
            request.setModule(module);

            //继续往下传递,通过方法sendUpStreamEvent...往下传递
            return request;

        }else {
            //数据还没到齐,不完整,需要等待后面的包来
            return null;
        }
    }


}
