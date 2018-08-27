package cn.onyx.enco2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Client {
    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //固定长度...
                ch.pipeline().addLast(new FixedLengthFrameDecoder(5));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new ClientHandle());
            }
        });

        ChannelFuture future = bootstrap.connect("127.0.0.1",8080).sync();
        Channel channel = future.channel();

        //向服务的发送数据
        channel.writeAndFlush(Unpooled.copiedBuffer("2222299999".getBytes()));
        Thread.sleep(1000);
        channel.writeAndFlush(Unpooled.copiedBuffer("_____#####".getBytes()));
        Thread.sleep(1000);
        //进行空格的补全5个,否则后面的数据就丢了
        channel.writeAndFlush(Unpooled.copiedBuffer("hello$_   ".getBytes()));
        Thread.sleep(1000);

        future.channel().closeFuture().sync();
        group.shutdownGracefully();


    }
}
