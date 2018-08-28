package cn.onyx.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AddressBookProtos.Person person = (AddressBookProtos.Person) msg;
        System.out.println(person);

        //响应

        AddressBookProtos.Person john = AddressBookProtos.Person
                .newBuilder()
                .setId(2)
                .setName("john2")
                .setEmail("john@youku.com2")
                .addPhone(
                        AddressBookProtos.Person.PhoneNumber
                                .newBuilder()
                                .setNumber("1861xxxxxxx2")
                                .setType(AddressBookProtos.Person.PhoneType.WORK)
                                .build()
                ).build();

        ctx.writeAndFlush(john);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
