package cn.onyx.helloworld2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandle extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            User user = (User) msg;
            System.out.println("客户端接收到的信息是:"+user);
        }finally {
            //客户端一定要释放
            //因为服务端在向外写数据的时候会自动的去释放,不需要手动,所以没有,大师客户端需要,
            //否则链接多了就会造成缓冲区的溢出
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端数据读取完毕");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        cause.getClass();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("激活了");
        //向服务的发送数据
        User user = new User();
        user.setId(1);
        user.setName("zhaojun");
        ctx.writeAndFlush(user);
    }
}
