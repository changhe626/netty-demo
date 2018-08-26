package cn.onyx.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                ch.pipeline().addLast(new ClientHandle());
            }
        });
        //这里需要绑定服务端的ip和端口号
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080);

        Channel channel = future.channel();

        //向服务的发送数据
        channel.writeAndFlush(Unpooled.copiedBuffer("hello neety server1".getBytes()));
        Thread.sleep(1000);
        channel.writeAndFlush(Unpooled.copiedBuffer("hello neety server2".getBytes()));
        Thread.sleep(1000);
        channel.writeAndFlush(Unpooled.copiedBuffer("hello neety server3".getBytes()));
        Thread.sleep(1000);
        channel.writeAndFlush(Unpooled.copiedBuffer("hello neety server4".getBytes()));
        Thread.sleep(1000);

        channel.closeFuture().sync();

        loopGroup.shutdownGracefully();



        //再绑定一个其他的端口
        ChannelFuture future2 = bootstrap.connect("127.0.0.1", 8081);
        Channel channel2 = future2.channel();

        //向服务的发送数据
        channel2.writeAndFlush(Unpooled.copiedBuffer("hello2 neety server1".getBytes()));
        Thread.sleep(1000);
        channel2.writeAndFlush(Unpooled.copiedBuffer("hello2 neety server2".getBytes()));
        Thread.sleep(1000);
        channel2.writeAndFlush(Unpooled.copiedBuffer("hello2 neety server3".getBytes()));
        Thread.sleep(1000);
        channel2.closeFuture().sync();



    }

}
