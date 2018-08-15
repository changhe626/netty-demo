package cn.onyx.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 使用DiscardServerHandler启动服务器。
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        //1.
        NioEventLoopGroup bossGroup  = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup  = new NioEventLoopGroup();
        try{
            //2.
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    //3.
                    .channel(NioServerSocketChannel.class)
                    //4.
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    //5.
                    .option(ChannelOption.SO_BACKLOG,128)
                    //6.
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            //7.绑定和开始接受连接
            ChannelFuture future = bootstrap.bind(port).sync();
            //8.等待,直到被关闭
            future.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new DiscardServer(8080).run();
    }

}
