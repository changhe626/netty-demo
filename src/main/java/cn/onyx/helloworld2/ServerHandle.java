package cn.onyx.helloworld2;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandle extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        User msg1 = (User) msg;
        System.out.println("服务端接收到的信息是:"+msg1);

        //服务端给客户端的响应
        User user = new User();
        user.setId(2);
        user.setName("zhangke");
        ctx.writeAndFlush(user);

        //一响应就关闭
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
