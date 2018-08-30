package nio_ser.xyz.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class TimeServer {
    // The port we'll actually use
    private static int port = 8125;

    // Charset and encoder for US-ASCII
    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetEncoder encoder = charset.newEncoder();

    // Direct byte buffer for writing
    private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

    // Open and bind the server-socket channel
    //
    private static ServerSocketChannel setup() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        String host = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Listen at Host:" + host + ", port:" + port);
        InetSocketAddress isa = new InetSocketAddress(host, port);
        serverSocketChannel.socket().bind(isa);
        return serverSocketChannel;
    }

    /**
     * 接收请求
     * @param serverSocketChannel
     * @throws IOException
     */
    private static void server(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        try {
            String now = new SimpleDateFormat("yyyy-MM-hh HH:MM:ss").format(new Date());
            socketChannel.write(encoder.encode(CharBuffer.wrap(now + "\r\n")));
            System.out.println(socketChannel.socket().getInetAddress() + " : " + now);
        } finally {
            // Make sure we close the channel (and hence the socket)
            socketChannel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.err.println("Usage: java TimeServer [port]");
            return;
        }

        // If the first argument is a string of digits then we take that
        // to be the port number
        if ((args.length == 1) && Pattern.matches("[0-9]+", args[0])){
            port = Integer.parseInt(args[0]);
        }

        ServerSocketChannel serverSocketChannel = setup();
        while (true){
            server(serverSocketChannel);
        }
    }
}
