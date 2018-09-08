package cn.onyx.heartcheck;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws InterruptedException {

        //处理服务端接收客户端的链接
        NioEventLoopGroup connect = new NioEventLoopGroup();
        //进行网络通信(网络读写)
        NioEventLoopGroup handle = new NioEventLoopGroup();
        //辅助工具类,服务器通道的配置
        ServerBootstrap bootstrap = new ServerBootstrap();
        //绑定两个线程组
        bootstrap.group(connect,handle);
        //制定NIO的模式
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG,1024);//tcp缓冲区
        //设置日志
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //读超时时间
                //写超时时间
                //其他所有超时时间
                //时间类型,单位
                //在 IdleStateHandler 中定义了读超时的时间是 4 秒， 写超时的时间是 5 秒，其他所有的超时时间是 7 秒。
                ch.pipeline().addLast(new IdleStateHandler(4,5,7, TimeUnit.SECONDS));
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
