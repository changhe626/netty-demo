package cn.onyx.helloworld2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Client {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup);

        //客户端这里是NioSocketChannel
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            //不管是客户端还是服务端这里都是SocketChannel
            protected void initChannel(SocketChannel ch) throws Exception {
                //进行pojo的转换

                //添加自定义的编码器和解码器
                //添加pojo对象解码器,禁止缓存类加载器
                ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));

                //设置发送消息解码器
                ch.pipeline().addLast(new ObjectEncoder());

                ch.pipeline().addLast(new ClientHandle());
            }
        });
        //这里需要绑定服务端的ip和端口号
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080);

        Channel channel = future.channel();

        channel.closeFuture().sync();
        loopGroup.shutdownGracefully();

    }

}
