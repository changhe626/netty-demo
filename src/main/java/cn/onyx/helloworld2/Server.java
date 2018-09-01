package cn.onyx.helloworld2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

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
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        //不管是服务端还是客户端这里都是SocketChannel
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //进行pojo的转换

                //添加对象解码器,负责对序列化pojo对象进行解码,设置对象序列化对大长度为1M,防止内存溢出
                //设置线程安全的WeakReferenceMap对类加载器进行缓存,支持多线程并发访问防止内存溢出
                ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));

                //添加对象编码器,在服务器对外发送消息时候自动将序列化pojo对象编码
                ch.pipeline().addLast(new ObjectEncoder());

                //3.配置具体数据接收方法的处理
                ch.pipeline().addLast(new ServerHandle());
            }
        });

        //4.进行绑定,服务端只需要绑定端口号
        ChannelFuture future = bootstrap.bind(8080).sync();

        future.channel().closeFuture().sync();

        connect.shutdownGracefully();
        handle.shutdownGracefully();


    }
}
