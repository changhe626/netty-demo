package nio_ser.xyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Demo5 {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));


        ByteBuffer buffer = ByteBuffer.allocate(20);
        socketChannel.read(buffer);
        socketChannel.close();




        /*String data="昂首千秋远,笑傲风间,堪寻敌手共论剑,高处不胜寒";
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.clear();
        buffer.put(data.getBytes());
        buffer.flip();
        SocketChannel channel = SocketChannel.open();
        channel.bind(new InetSocketAddress("127.0.0.1",8082));
        channel.configureBlocking(false);
        while (buffer.hasRemaining()){
            channel.write(buffer);
        }*/
    }
}
