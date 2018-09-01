package cn.onyx.helloworld2;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandle extends ChannelInboundHandlerAdapter {

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
        System.out.println("激活了");
    }
}
