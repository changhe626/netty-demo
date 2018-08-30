package nio_ser.xyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

//从网络地址中不断的接收信息
public class Demo4 {
    public static void main(String[] args) throws IOException {

        // Get the selector
        Selector selector = Selector.open();
        System.out.println("Selector is open for making connection: " + selector.isOpen());
        //获取本地socket的链接地址
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress("localhost", 8080));
        socketChannel.configureBlocking(false);
        int ops = socketChannel.validOps();
        SelectionKey selectKy = socketChannel.register(selector, ops, null);
        while (true) {
            System.out.println("Waiting for the select operation...");
            int select = selector.select();
            System.out.println("The Number of selected keys are: " + select);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isAcceptable()) {
                    // The new client connection is accepted
                    SocketChannel client = socketChannel.accept();
                    client.configureBlocking(false);
                    // The new connection is added to a selector
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("The new connection is accepted from the client: " + client);
                } else if (key.isReadable()) {
                    // Data is read from the client
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    client.read(buffer);
                    String output = new String(buffer.array()).trim();
                    System.out.println("Message read from client: " + output);
                    if (output.equals("Bye Bye")) {
                        client.close();
                        System.out.println("The Client messages are complete; close the session.");
                    }
                }

            }
        }
    }
}
