package cn.onyx.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
        //制定NIO的模式,服务端这里是NioServerSocketChannel
        bootstrap.channel(NioServerSocketChannel.class);
        //进行服务端的参数的设置...
        bootstrap.option(ChannelOption.SO_BACKLOG,1024);//tcp缓冲区
        //bootstrap.option(ChannelOption.SO_SNDBUF,32*1024);//发送缓冲大小
        //bootstrap.option(ChannelOption.SO_RCVBUF,32*1024);//接收缓冲大小
        //不管是服务端还是客户端这里都是SocketChannel
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //3.配置具体数据接收方法的处理
                ch.pipeline().addLast(new ServerHandle());
            }
        });

        //4.进行绑定,服务端只需要绑定端口号
        ChannelFuture future = bootstrap.bind(8080).sync();

        future.channel().closeFuture().sync();


        //再次绑定个其他的端口,进行服务的提供
        ChannelFuture future1 = bootstrap.bind(8081).sync();
        future1.channel().closeFuture().sync();

        connect.shutdownGracefully();
        handle.shutdownGracefully();


    }
}
