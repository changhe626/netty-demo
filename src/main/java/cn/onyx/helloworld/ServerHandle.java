package cn.onyx.helloworld;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("add");
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("removed");
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        int i = buf.readableBytes();
        byte[] bytes = new byte[i];
        buf.readBytes(bytes);
        String string = new String(bytes, "UTF-8");
        System.out.println("读取到的数据是:"+string);

        //服务端给客户端的响应
        String response="昂首千秋远,笑傲风间,堪寻敌手共论剑,高处不胜寒";
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
                //.addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //也只是激活一次
        System.out.println("激活通道...");

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读取完成...");
    }
}
