package cn.onyx.connect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Client {

    private static class SingletonHolder{
        static final Client  instance=new Client();
    }

    public static Client getInstance(){
        return SingletonHolder.instance;
    }


    private Bootstrap bootstrap;
    private NioEventLoopGroup group;
    private ChannelFuture channelFuture;

    private Client() {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        //日志记录
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline().addLast(new ReadTimeoutHandler(5));
                ch.pipeline().addLast(new ClientHandle());
            }
        });
        channelFuture = bootstrap.connect("127.0.0.1", 8080);

        Channel channel = channelFuture.channel();

        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        group.shutdownGracefully();
    }










    public static void main(String[] args) throws InterruptedException {


    }

}
