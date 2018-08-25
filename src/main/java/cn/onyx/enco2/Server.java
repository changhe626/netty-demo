package cn.onyx.enco2;

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
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 使用定长作为进行消息的处理
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
                //只能解析5个的,如果不足5个,需要自己补空格进行补全...
                ch.pipeline().addLast(new FixedLengthFrameDecoder(5));
                //设置字符串形式解码,变成String
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
