package nio_ser.xyz.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class Demo7 {
    public static void main(String[] args) throws IOException {

        /*Pipe pipe = Pipe.open();
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        int read = sourceChannel.read(buffer);


        Pipe.SinkChannel sinkChannel = pipe.sink();
        sinkChannel.write(buffer);*/


        // The Pipe is created
        Pipe pipe = Pipe.open();
        // For accessing the pipe sink channel
        Pipe.SinkChannel skChannel = pipe.sink();
        String td = "管道的数据的放置于读取,if this is chinese ,it will be hard to understand it ";
        ByteBuffer bb = ByteBuffer.allocate(512);
        bb.clear();
        bb.put(td.getBytes());
        bb.flip();
        // write the data into a sink channel.
        while (bb.hasRemaining()) {
            skChannel.write(bb);
        }
        // For accessing the pipe source channel
        Pipe.SourceChannel sourceChannel = pipe.source();
        bb = ByteBuffer.allocate(512);
        // The data is write to the console
        while (sourceChannel.read(bb) > 0) {
            bb.flip();
            while (bb.hasRemaining()) {
                char TestData = (char) bb.get();
                System.out.print(TestData);
            }
            bb.clear();
        }
    }
}
