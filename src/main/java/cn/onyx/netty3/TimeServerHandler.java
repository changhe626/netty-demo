package cn.onyx.netty3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * 时间服务器1
 */
public class TimeServerHandler   extends ChannelInboundHandlerAdapter {

    /**
     * channelActive() 方法将会在连接被建立并且准备进行通信时被调用。
     * 因此让我们在这个方法里完成一个代表当前时间的32位整数消息的构建工作。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 为了发送一个新的消息，我们需要分配一个包含这个消息的新的缓冲。因为我们需要写入一个32位的整数，
         * 因此我们需要一个至少有4个字节的 ByteBuf。通过 ChannelHandlerContext.alloc()
         * 得到一个当前的ByteBufAllocator，然后分配一个新的缓冲。
         */
        ByteBuf buffer = ctx.alloc().buffer(4);
        buffer.writeInt((int)(System.currentTimeMillis()/1000L+2208988800L));

        ChannelFuture channelFuture = ctx.writeAndFlush(buffer);
        /**
         * 当一个写请求已经完成是如何通知到我们？这个只需要简单地在返回的 ChannelFuture 上增加一个
         * ChannelFutureListener。这里我们构建了一个匿名的 ChannelFutureListener 类用来在操作完成
         * 时关闭 Channel。或者，你可以使用简单的预定义监听器代码:
         */
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert channelFuture==future;
                ctx.close();
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
