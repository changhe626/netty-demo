package cn.onyx.netty3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 时间客户端
 */
public class TimeClient {

    public static void main(String[] args) {
        String host="127.0.0.1";
        int port=9000;
        NioEventLoopGroup workerGroup  = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new TimeClientHandler());
            }
        });
        try {
            //启动客户端
            ChannelFuture future = bootstrap.bind(host, port).sync();
            //等待连接关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        }finally {
            workerGroup.shutdownGracefully();
        }
    }


}
