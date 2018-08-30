package nio_ser.xyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Demo6 {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(8080));



        socketChannel.close();
        while (true){
            SocketChannel accept = socketChannel.accept();
            ByteBuffer allocate = ByteBuffer.allocate(100);
            accept.read(allocate);
        }


    }
}
