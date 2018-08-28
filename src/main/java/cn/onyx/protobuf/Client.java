package cn.onyx.protobuf;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                ch.pipeline().addLast(new ProtobufDecoder(AddressBookProtos.Person.getDefaultInstance()));
                ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                ch.pipeline().addLast(new ProtobufEncoder());
                ch.pipeline().addLast(new ClientHandle());
            }
        });

        ChannelFuture future = bootstrap.connect("127.0.0.1",8080).sync();
        Channel channel = future.channel();

        //向服务的发送数据

        AddressBookProtos.Person john = AddressBookProtos.Person
                .newBuilder()
                .setId(1)
                .setName("john")
                .setEmail("john@youku.com")
                .addPhone(
                        AddressBookProtos.Person.PhoneNumber
                                .newBuilder()
                                .setNumber("1861xxxxxxx")
                                .setType(AddressBookProtos.Person.PhoneType.WORK)
                                .build()
                ).build();


        channel.writeAndFlush(john);

        future.channel().closeFuture().sync();
        group.shutdownGracefully();


    }
}
