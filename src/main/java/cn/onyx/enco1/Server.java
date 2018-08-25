package cn.onyx.enco1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 使用特殊字符进行消息的分割处理,
 * 只要读取到了间隔符就会进行消息的分割了....,客户端的处理也是一样的
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup connect = new NioEventLoopGroup();
        NioEventLoopGroup handle = new NioEventLoopGroup();
        bootstrap.group(connect,handle);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG,1024);//tcp缓冲区
        bootstrap.option(ChannelOption.SO_SNDBUF,32*1024);//发送缓冲大小
        bootstrap.option(ChannelOption.SO_RCVBUF,32*1024);//接收缓冲大小
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);//保持连接
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //设置特殊分隔符
                ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
                //设置字符串形式解码
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new ServerHandle());


            }
        });

        //4.进行绑定
        ChannelFuture future = bootstrap.bind(8080).sync();

        future.channel().closeFuture().sync();

        connect.shutdownGracefully();
        handle.shutdownGracefully();


    }
}
