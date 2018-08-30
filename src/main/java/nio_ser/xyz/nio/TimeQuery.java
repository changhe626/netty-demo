package nio_ser.xyz.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class TimeQuery {

    // The port we'll actually use
    private static int port = 8125;

    // Charset and decoder for US-ASCII
    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetDecoder decoder = charset.newDecoder();

    // Direct byte buffer for reading
    private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

    // Ask the given host what time it is
    //
    private static void query(String host) throws IOException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
        SocketChannel socketChannel = null;

        try {
            // Connect
            socketChannel = SocketChannel.open();
            socketChannel.connect(address);

            // Read the time from the remote host. For simplicity we assume
            // that the time comes back to us in a single packet, so that we
            // only need to read once.
            dbuf.clear();
            socketChannel.read(dbuf);

            // Print the remote address and the received time
            dbuf.flip();
            CharBuffer cb = decoder.decode(dbuf);
            System.out.print(address + " : " + cb);

        } finally {
            // Make sure we close the channel (and hence the socket)
            if (socketChannel != null){
                socketChannel.close();
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        /**
         * if (args.length < 1) { System.err.println("Usage: java TimeQuery
         * [port] host..."); return; }
         */
        String host = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Connect to Host:" + host + ", port:" + port);
        try {
            query(host);
        } catch (IOException x) {
            System.err.println(host + ": " + x);
        }
    }



}
