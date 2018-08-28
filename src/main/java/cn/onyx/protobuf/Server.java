package cn.onyx.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class Server {
    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        b.group(bossGroup,workGroup);
        b.channel(NioServerSocketChannel.class);
        b.option(ChannelOption.SO_BACKLOG,1000);
        b.handler(new LoggingHandler(LogLevel.INFO));
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                /**
                 * 其主要用于半包处理随后添加ProtobufDecoder，它的参数类型是com.google.protobuf.MessageLite，
                 * 实际上就是告诉ProtobufDecoder需要解码的目标类是什么，否则仅仅从字节数组中是无法判断出
                 * 要解码的目标类型的，这里我们设置的是AddressBookProtos.Person类型实例，在.proto文件中
                 * 所有的定义的message在生成的java类，例如这里的Person，都实现了MessageLite接口。
                 */
                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                //ProtobufEncoder类用于对输出的消息进行编码。
                ch.pipeline().addLast(new ProtobufDecoder(AddressBookProtos.Person.getDefaultInstance()));
                ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                ch.pipeline().addLast(new ProtobufEncoder());
                //ServerHandler是我们自己定义处理类，在其channelRead方法参数中，
                // Object msg就是解码后的Person，在返回数据时，
                ch.pipeline().addLast(new ServerHandler());
            }
        });

        ChannelFuture future = b.bind(8080).sync();
        future.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();

    }
}
