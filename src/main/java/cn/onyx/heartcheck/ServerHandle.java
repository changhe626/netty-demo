package cn.onyx.heartcheck;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * IdleStateHandler这个类会根据你设置的超时参数的类型和值，
 * 循环去检测channelRead和write方法多久没有被调用了，如果这个时间超过了你设置的值，
 * 那么就会触发对应的事件，read触发read，write触发write，all触发all
 * 如果超时了，则会调用userEventTriggered方法，且会告诉你超时的类型
 * 如果没有超时，则会循环定时检测，除非你将IdleStateHandler移除Pipeline
 */

public class ServerHandle extends ChannelInboundHandlerAdapter {

    //定义了心跳时，要发送的内容
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("heartbeat", CharsetUtil.UTF_8));


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断是否是 IdleStateEvent 事件，是则处理
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle";
            } else if (event.state() == IdleState.WRITER_IDLE) {
                type = "write idle";
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "all idle";
            }
            System.out.println("超时的类型是:"+type);
            //将心跳内容发送给客户端
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            System.out.println("远端的地址是:"+ctx.channel().remoteAddress());
        }else {
            //不是超时的时候
            super.userEventTriggered(ctx,evt);
        }
    }
}
