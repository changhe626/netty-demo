package cn.onyx.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //在此示例中，接收到的消息的类型为ByteBuf,进行数据的转换
        ByteBuf buf = (ByteBuf) msg;
        try {
            while (buf.isReadable()){
                //打印接收的信息
                System.out.println((char) buf.readByte());
            }
        }finally {
            //ByteBuf是引用计数的对象，必须通过release()方法显式释放。
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常
        cause.printStackTrace();
        // 出现异常时关闭连接。
        ctx.close();
    }
}
